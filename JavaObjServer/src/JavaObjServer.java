
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

	private ServerSocket socket; // ��������
	private Socket client_socket; // accept() ���� ������ client ����
	private Vector<UserService> UserVec = new Vector<UserService>(); // ����� ����ڸ� ������ ����
	private Vector<User> UserProfileVec= new Vector<User>();
	private Vector<ChatRoom> UserChatRoomVec= new Vector<ChatRoom>();
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����


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
				btnServerStart.setEnabled(false); // ������ ���̻� �����Ű�� �� �ϰ� ���´�
				txtPortNumber.setEnabled(false); // ���̻� ��Ʈ��ȣ ������ �ϰ� ���´�
				AcceptServer accept_server = new AcceptServer();
				accept_server.start();
			}
		});
		btnServerStart.setBounds(12, 356, 300, 35);
		contentPane.add(btnServerStart);
	}

	// ���ο� ������ accept() �ϰ� user thread�� ���� �����Ѵ�.
	class AcceptServer extends Thread {
		@SuppressWarnings("unchecked")
		public void run() {
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
				try {
					AppendText("Waiting new clients ...");
					client_socket = socket.accept(); // accept�� �Ͼ�� �������� ���� �����
					AppendText("���ο� ������ from " + client_socket);
					// User �� �ϳ��� Thread ����
					UserService new_user = new UserService(client_socket);
					UserVec.add(new_user); // ���ο� ������ �迭�� �߰�
					new_user.start(); // ���� ��ü�� ������ ����
					
					AppendText("���� ������ �� " + UserVec.size());
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

	// User �� �����Ǵ� Thread
	// Read One ���� ��� -> Write All
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
			// �Ű������� �Ѿ�� �ڷ� ����
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
			AppendText("���ο� ������ " + userName + " ����.");
			WriteOne("Welcome to Java chat server\n");
			WriteOne(userName + "�� ȯ���մϴ�.\n"); // ����� ����ڿ��� ���������� �˸�
			String msg = "[" + userName + "]���� ���� �Ͽ����ϴ�.\n";
			WriteOthers(msg); // ���� user_vc�� ���� ������ user�� ���Ե��� �ʾҴ�.
		}

		public void Logout() {
			String msg = "[" + userName + "]���� ���� �Ͽ����ϴ�.\n";
			UserVec.removeElement(this); // Logout�� ���� ��ü�� ���Ϳ��� �����
			WriteAll(msg); // ���� ������ �ٸ� User�鿡�� ����
			AppendText("����� " + "[" + userName + "] ����. ���� ������ �� " + UserVec.size());
		}

		// ��� User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
		public void WriteAll(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		// ���� ������ User�鿡�� ���. ������ UserService Thread�� WriteONe() �� ȣ���Ѵ�.
		public void WriteOthers(String str) {
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				if (user != this && user.UserStatus == "O")
					user.WriteOne(str);
			}
		}

		// Windows ó�� message ������ ������ �κ��� NULL �� ����� ���� �Լ�
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
		
		// UserService Thread�� ����ϴ� Client ���� 1:1 ����
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
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
    	}

		// UserService Thread�� ����ϴ� Client ���� 1:1 ����
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
				Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
			}
		}
		
		public void makeRoomAndSend(ChatMsg cm) {
			// �� ����� 
			ChatRoom chatRoom = null;
			String roomId = null;
			String userList = cm.getUserList();
			
			System.out.println("server : receive from main" + userList);
			chatRoom = new ChatRoom(roomId = "chatRoom@" + userList, userList);
			UserChatRoomVec.add(chatRoom);
			
			String[] roomUsers = userList.split(" "); // �ܾ���� �и��Ѵ�.
			System.out.println("server :roomUsers" + roomUsers);
		    ChatMsg newRoom = new ChatMsg("Server" , "110", "makeRoomAndSend");
		    newRoom.setRoomId(roomId = "chatRoom@"+ userList);
		    newRoom.setUserList(userList);
			for (int i = 0; i < user_vc.size(); i++) {
				UserService user = (UserService) user_vc.elementAt(i);
				// ä�ù��� �����ؾ� �ϴ� ���� �̸��� ��ġ�Ѵٸ� 
				for(int j = 0; j < roomUsers.length; j++) {
					if(user.userName.equals(roomUsers[j])) {
						System.out.println("���� : " + user.userName + " =  " + roomUsers[j]);
						user.WriteOneObject(newRoom);
					}
				}
			}
		}
		
		public void findRoomAndSend(ChatMsg cm) {
			// �濡 ����Ǿ� �ִ� ������ 
			String roomUserList = "";
			// �� ���̵� 
			String roomId = cm.getRoomId();
			// roomId �� RoomVector ���� ã�´�. 
			for(int i = 0; i < UserChatRoomVec.size(); i++) {
				// roomId�� ��ġ�ϸ� 
				if(UserChatRoomVec.get(i).getRoomId().equals(roomId)) {
					roomUserList = UserChatRoomVec.get(i).getUserList();
				}
			}
			
			// ������� ������ (���� ���)
			if(roomUserList != "") {
				String[] roomUsers = roomUserList.split(" "); // �ܾ���� �и��Ѵ�.
				for (int i = 0; i < user_vc.size(); i++) {
					UserService user = (UserService) user_vc.elementAt(i);
					// �޼����� ������ �ϴ� ���� �̸��� ��ġ�Ѵٸ� 
					for(int j = 0; j < roomUsers.length; j++) {
						if(user.userName.equals(roomUsers[j])) {
							System.out.println("���� : " + user.userName + " =  " + roomUsers[j]);
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
			while (true) { // ����� ������ ����ؼ� �ޱ� ���� while��
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
							// ���� ���� client�鿡�Ը� ���� ������ ���� 
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
						}else if (cm.getCode().matches("800")) { // logout message ó��
							Logout();
							break;
						} else {
							
						}
					}
					if(changeUsers != null) {
						User addUserChange = new User(changeUsers.getNowImage(), changeUsers.getNowName(), changeUsers.getNowMsg());
						for(int j = 0; j < UserProfileVec.size(); j++) {
							// �ٲ�� �ϴ� ��� �ٲ� �� 
							if(UserProfileVec.get(j).getUserName().equals(changeUsers.getPastName()) == true) {
								UserProfileVec.remove(j);
								UserProfileVec.add(addUserChange);
							}
						}
						
						for (int i = 0; i < user_vc.size(); i++) {
							UserService user = (UserService) user_vc.elementAt(i);
							// ���� ���� client�鿡�Ը� ���� ������ ���� 
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
						Logout(); // �������� ���� ��ü�� ���Ϳ��� �����
						break;
					} catch (Exception ee) {
						break;
					} // catch�� ��
				} // �ٱ� catch����
			} // while
		} // run
	}

}
