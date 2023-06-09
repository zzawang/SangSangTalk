
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class JavaObjServer extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JTextField txtPortNumber;

	private ServerSocket socket; // 서버소켓
	private Socket client_socket; // accept() 에서 생성된 client 소켓
	private Vector<UserService> UserVec = new Vector<UserService>(); // 연결된 사용자를 저장할 벡터
	private Vector<User> UserProfileVec= new Vector<User>();
	private Vector<ChatRoom> UserChatRoomVec= new Vector<ChatRoom>();
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaObjServer frame = new JavaObjServer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public JavaObjServer() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 300, 298);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);

		JLabel lblNewLabel = new JLabel("Port Number");
		lblNewLabel.setBounds(13, 318, 87, 26);
		contentPane.add(lblNewLabel);

		txtPortNumber = new JTextField();
		txtPortNumber.setHorizontalAlignment(SwingConstants.CENTER);
		txtPortNumber.setText("30000");
		txtPortNumber.setBounds(112, 318, 199, 26);
		contentPane.add(txtPortNumber);
		txtPortNumber.setColumns(10);

		JButton btnServerStart = new JButton("Server Start");
		btnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket = new ServerSocket(Integer.parseInt(txtPortNumber.getText()));
				} catch (NumberFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				AppendText("Chat Server Running..");
				btnServerStart.setText("Chat Server Running..");
				btnServerStart.setEnabled(false); // 서버를 더이상 실행시키지 못 하게 막는다
				txtPortNumber.setEnabled(false); // 더이상 포트번호 수정못 하게 막는다
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// 새로운 참가자 accept() 하고 user thread를 새로 생성한다.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept가 일어나기 전까지는 무한 대기중
					AppendText("새로운 참가자 from " + client_socket);
					// User 당 하나씩 Thread 생성
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // 새로운 참가자 배열에 추가
					new_user.start(); // 만든 객체의 스레드 실행
					
					AppendText("현재 참가자 수 " + UserVec.size());
				} catch (IOException e) {
					AppendText("accept() error");
					// System.exit(0);
				}
			}
		}
	}

	public void AppendText(String str) {
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	public void AppendObject(ChatMsg msg) {
		textArea.append("code = " + msg.getCode() + "\n");
		textArea.append("id = " + msg.getId() + "\n");
		textArea.append("data = " + msg.getData() + "\n");
		textArea.setCaretPosition(textArea.getText().length());
	}

	// User 당 생성되는 Thread
	// Read One 에서 대기 -> Write All
	class UserService extends Thread {
		private InputStream is;
		private OutputStream os;
		private DataInputStream dis;
		private DataOutputStream dos;

		private ObjectInputStream ois;
		private ObjectOutputStream oos;

		private Socket client_socket;
		private Vector user_vc;
		public ImageIcon userImage;
		public String userName = "";
		public String userActiveMsg = "";
		public String UserStatus;

		public UserService(Socket client_socket) {
			// TODO Auto-generated constructor stub
			// 매개변수로 넘어온 자료 저장
			this.client_socket = client_socket;
			this.user_vc = UserVec;
			try {

				oos = new ObjectOutputStream(client_socket.getOutputStream());
				oos.flush();
				ois = new ObjectInputStream(client_socket.getInputStream());

				
			} catch (Exception e) {
				AppendText("userService error");
			}
		}

		public void Login() {
			AppendText("새로운 참가자 " + userName + " 입장.");
			WriteOne("Welcome to Java chat server\n");
			WriteOne(userName + "님 환영합니다.\n"); // 연결된 사용자에게 정상접속을 알림
			String msg = "[" + userName + "]님이 입장 하였습니다.\n";
			WriteOthers(msg); // 아직 user_vc에 새로 입장한 user는 포함되지 않았다.
		}

		public void Logout() {
			String msg = "[" + userName + "]님이 퇴장 하였습니다.\n";
			UserVec.removeElement(this); // Logout한 현재 객체를 벡터에서 지운다
			WriteAll(msg); // 나를 제외한 다른 User들에게 전송
			AppendText("사용자 " + "[" + userName + "] 퇴장. 현재 참가자 수 " + UserVec.size());
		}

		// 모든 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		// 나를 제외한 User들에게 방송. 각각의 UserService Thread의 WriteONe() 을 호출한다.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
		public byte[] MakePacket(String msg) {
			byte[] packet = new byte[BUF_LEN];
			byte[] bb = null;
			int i;
			for (i = 0; i < BUF_LEN; i++)
				packet[i] = 0;
			try {
				bb = msg.getBytes("euc-kr");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (i = 0; i < bb.length; i++)
				packet[i] = bb[i];
			return packet;
		}
		
		// UserService Thread가 담당하는 Client 에게 1:1 전송
		public void sendUser(User user) {
			try {
				oos.writeObject(user);
			} catch (IOException e) {
				AppendText("sendUser error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
    	}

		// UserService Thread가 담당하는 Client 에게 1:1 전송
		public void WriteOne(String msg) {
			try {
				ChatMsg obcm = new ChatMsg("SERVER", "300", msg);
				oos.writeObject(obcm);
			} catch (IOException e) {
				AppendText("dos.writeObject() error");
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout(); // 에러가난 현재 객체를 벡터에서 지운다
			}
		}
		
		public void makeRoomAndSend(ChatMsg cm) {
			// 방 만들기 
			ChatRoom chatRoom = null;
			String roomId = null;
			String userList = cm.getUserList();
			
			System.out.println("server : receive from main" + userList);
			chatRoom = new ChatRoom(roomId = "chatRoom@" + userList, userList);
			UserChatRoomVec.add(chatRoom);
			
			String[] roomUsers = userList.split(" "); // 단어들을 분리한다.
			System.out.println("server :roomUsers" + roomUsers);
		    ChatMsg newRoom = new ChatMsg("Server" , "110", "makeRoomAndSend");
		    newRoom.setRoomId(roomId = "chatRoom@"+ userList);
		    newRoom.setUserList(userList);
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				// 채팅방을 생성해야 하는 유저 이름과 일치한다면 
				for(int j = 0; j < roomUsers.length; j++) {
					if(user.userName.equals(roomUsers[j])) {
						System.out.println("서버 : " + user.userName + " =  " + roomUsers[j]);
						user.WriteOneObject(newRoom);
					}
				}
			}
		}
		
		public void findRoomAndSend(ChatMsg cm) {
			// 방에 연결되어 있는 유저들 
			String roomUserList = "";
			// 방 아이디 
			String roomId = cm.getRoomId();
			// roomId 를 RoomVector 에서 찾는다. 
			for(int i = 0; i < UserChatRoomVec.size(); i++) {
				// roomId가 일치하면 
				if(UserChatRoomVec.get(i).getRoomId().equals(roomId)) {
					roomUserList = UserChatRoomVec.get(i).getUserList();
				}
			}
			
			// 비어있지 않으면 (오류 대비)
			if(roomUserList != "") {
				String[] roomUsers = roomUserList.split(" "); // 단어들을 분리한다.
				for (int i = 0; i < user_vc.size(); i++) {
					UserService user = (UserService) user_vc.elementAt(i);
					// 메세지를 보내야 하는 유저 이름과 일치한다면 
					for(int j = 0; j < roomUsers.length; j++) {
						if(user.userName.equals(roomUsers[j])) {
							System.out.println("서버 : " + user.userName + " =  " + roomUsers[j]);
							user.WriteOneObject(cm);
						}
					}
				}
			}
		}
		
		public void WriteOneObject(Object ob) {
			try {
			    oos.writeObject(ob);
			} 
			catch (IOException e) {
				AppendText("oos.writeObject(ob) error");		
				try {
					ois.close();
					oos.close();
					client_socket.close();
					client_socket = null;
					ois = null;
					oos = null;				
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Logout();
			}
		}
		
		public void run() {
			while (true) { // 사용자 접속을 계속해서 받기 위해 while문
				try {
					Object obcm = null;
					String msg = null;
					ChatMsg cm = null;
					User users = null;
					ChangeUser changeUsers = null;
					if (socket == null) {
						System.out.println("server : socket is null");
						break;
					}
					try {
						obcm = ois.readObject();
						System.out.println("server : obcm");
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return;
					}
					if (obcm == null) {
						System.out.println("server : obj is null");
						break;
					}
					if (obcm instanceof ChatMsg) {
						System.out.println("server : cm");
						cm = (ChatMsg) obcm;
						AppendObject(cm);
					} else if (obcm instanceof User) {
						System.out.println("server : users");
						users = (User) obcm;
					}else if (obcm instanceof ChangeUser) {
						System.out.println("server : changeusers");
						changeUsers = (ChangeUser) obcm;
					}
					else
						continue;
					if(users != null) {
						UserProfileVec.add(users);
						userImage = users.getUserImage();
						userName = users.getUserName();
						userActiveMsg = users.getActiveMsg();
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							// 본인 제외 client들에게만 본인 프로필 전송 
							for(int j = 0; j < UserProfileVec.size(); j++) {
								if(UserProfileVec.get(j).getUserName().equals(user.userName) == false) {
									user.sendUser(UserProfileVec.get(j));
								}
							}
						}
					}
					if(cm != null) {
						if (cm.getCode().matches("100")) {
							makeRoomAndSend(cm);
						}
						// Chat Message
						else if (cm.getCode().matches("300")) { // chat Msg
							findRoomAndSend(cm);
						} else if (cm.getCode().matches("400")) { // image
							findRoomAndSend(cm);
						}else if (cm.getCode().matches("500")) {  // emoticon 
							findRoomAndSend(cm);
						}else if (cm.getCode().matches("800")) { // logout message 처리
							Logout();
							break;
						} else {
							
						}
					}
					if(changeUsers != null) {
						User addUserChange = new User(changeUsers.getNowImage(), changeUsers.getNowName(), changeUsers.getNowMsg());
						for(int j = 0; j < UserProfileVec.size(); j++) {
							// 바꿔야 하는 상대 바꿔 줌 
							if(UserProfileVec.get(j).getUserName().equals(changeUsers.getPastName()) == true) {
								UserProfileVec.remove(j);
								UserProfileVec.add(addUserChange);
							}
						}
						
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							// 본인 제외 client들에게만 본인 프로필 전송 
							if(user.getName().equals(addUserChange.getUserName())== false) {
								user.WriteOneObject(changeUsers);
							}
						}
					}
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
						ois.close();
						oos.close();
						client_socket.close();
						Logout(); // 에러가난 현재 객체를 벡터에서 지운다
						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝
			} // while
		} // run
	}

}
