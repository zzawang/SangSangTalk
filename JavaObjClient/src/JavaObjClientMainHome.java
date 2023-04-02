import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;


public class JavaObjClientMainHome extends JFrame{
	
	private static final long serialVersionUID = 2L;
	private JPanel btnPanel;
	private JButton friendListBtn;
	private JButton chatListBtn;
	private JPanel listPanel;
	private JPanel chatPanel;
	private JLabel myProfileText;
	private JLabel chattingText;
	private JButton chattingBtn;
	private JLabel friendProfileText;
	private JLabel msgCountLabel;

	private MyProfile myProfile;
    private UserProfile[] friendProfile;
    private UserChat userChat;
    private UserChat[] userChats;
    
    private MakeChatView chatView;
    private ChatRoom chatRoom;
    
    private Vector<User> users = new Vector<User>();
    private Vector<User> friendList;
    private Vector<UserChat> chatRooms;
    
    private JavaObjClientMainHome home;
    
    private int msgCount;
    
	private int friendIndex;
	private int chatIndex;
	private int flag;
	
	private Boolean chatBtnClick;
	
	private static final int BUF_LEN = 128;
	private Socket socket; 
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	//private ImageIcon userBasicImage = new ImageIcon("images/userBasicImage-crop.png");
	private ImageIcon userImage;
	private String userMsg;
	private String userName;
	
	public JavaObjClientMainHome(ImageIcon userimage, String username, String ip_addr, String port_no) {
		
		this.userName = username;
		this.userImage = userimage;
		
		if(userimage == null) userImage = new ImageIcon("images/userBasicImage-crop.png");
		
		// 기본 설정 상태메세지 
		userMsg = "상태메세지를 입력해주세요.";
		
		// 일단 최대 20개까지 가능하도록 함.
		friendProfile = new UserProfile[20];
		userChats = new UserChat[20];
		
		home = this;
		
		friendList = new Vector<User>();
		chatRooms  = new Vector<UserChat>();
		flag = 0;
		friendIndex = 0;
		chatIndex = 0;
		msgCount = 0;
		
		ImageIcon userBtnClickImage = new ImageIcon("images/userBtnClick.png");
		ImageIcon userBtnNoClickImage = new ImageIcon("images/userBtnNoClick.png");
		ImageIcon chatBtnClickImage = new ImageIcon("images/chatBtnClick.png");
		ImageIcon chatBtnNoClickImage = new ImageIcon("images/chatBtnNoClick.png");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 650);
		setVisible(true);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);

		listPanel = new JPanel();
		listPanel.setBounds(100, 0, 300, 620);
		listPanel.setBorder(null);
		listPanel.setLayout(null);
		listPanel.setBackground(Color.WHITE);
		contentPane.add(listPanel);
		listPanel.setVisible(true);
		
		myProfileText = new JLabel("내 프로필 ");
		listPanel.add(myProfileText);
		myProfileText.setBorder(null);
		myProfileText.setBackground(Color.WHITE);
		myProfileText.setFont(new Font("굴림", Font.PLAIN, 12));
		myProfileText.setHorizontalAlignment(SwingConstants.LEFT);
		myProfileText.setBounds(10, 8, 250, 20);
		
		myProfile = new MyProfile(home, userImage, userName, userMsg);
		listPanel.add(myProfile);
		myProfile.setBackground(Color.WHITE);
		myProfile.setBounds(10, 30, 260, 70);
		
		friendProfileText = new JLabel("친구 프로필 ");
		listPanel.add(friendProfileText);
		friendProfileText.setBorder(null);
		friendProfileText.setBackground(Color.WHITE);
		friendProfileText.setFont(new Font("굴림", Font.PLAIN, 12));
		friendProfileText.setHorizontalAlignment(SwingConstants.LEFT);
		friendProfileText.setBounds(10, 110, 250, 20);
		
		chatPanel = new JPanel();
		chatPanel.setBounds(100, 0, 300, 620);
		chatPanel.setBorder(null);
		chatPanel.setLayout(null);
		chatPanel.setBackground(Color.WHITE);
		contentPane.add(chatPanel);
		chatPanel.setVisible(false);
		
		chattingText = new JLabel("채팅 ");
		chatPanel.add(chattingText);
		chattingText.setBorder(null);
		chattingText.setBackground(Color.WHITE);
		chattingText.setFont(new Font("굴림", Font.PLAIN, 12));
		chattingText.setHorizontalAlignment(SwingConstants.LEFT);
		chattingText.setBounds(10, 8, 250, 20);
		
		chattingBtn = new JButton("+");
		chattingBtn.setBounds(270, 8, 20, 20);
		chattingBtn.setFont(new Font("굴림", Font.PLAIN, 20));
		chattingBtn.setBackground(Color.WHITE);
		chattingBtn.setBorder(null);
		chatPanel.add(chattingBtn);
		chatBtnClick = false;
		
		chattingBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chatView = new MakeChatView(home, friendList);
			}
		});
		
		btnPanel = new JPanel();
		contentPane.add(btnPanel);
		btnPanel.setLayout(null);
		btnPanel.setBounds(0, 0, 100, 630);
		btnPanel.setBackground(new java.awt.Color(64, 54, 49));

		msgCountLabel = new JLabel("  " + String.valueOf(msgCount));
		msgCountLabel.setBackground(Color.RED);
		msgCountLabel.setForeground(Color.WHITE);
		msgCountLabel.setBounds(55, 85, 23, 22);
		msgCountLabel.setOpaque(true);
		btnPanel.add(msgCountLabel);
		msgCountLabel.setVisible(false);
		
		friendListBtn = new JButton();
		friendListBtn.setIcon(userBtnClickImage);
		friendListBtn.setBorder(BorderFactory.createEmptyBorder());
		friendListBtn.setContentAreaFilled(false);
		friendListBtn.setFocusable(false);
		friendListBtn.setBounds(20, 15, 60, 60);
		btnPanel.add(friendListBtn);
		
		friendListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listPanel.setVisible(true);
				chatPanel.setVisible(false);
				friendListBtn.setIcon(userBtnClickImage);
				chatListBtn.setIcon(chatBtnNoClickImage);
				chatBtnClick = false;
			}
		});
		
		chatListBtn = new JButton();
		chatListBtn.setBounds(20, 80, 60, 60);
		chatListBtn.setIcon(chatBtnNoClickImage);
		chatListBtn.setBorder(BorderFactory.createEmptyBorder());
		chatListBtn.setContentAreaFilled(false);
		chatListBtn.setFocusable(false);
		btnPanel.add(chatListBtn);
		
		chatListBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listPanel.setVisible(false);
				chatPanel.setVisible(true);
				friendListBtn.setIcon(userBtnNoClickImage);
				chatListBtn.setIcon(chatBtnClickImage);
				msgCountLabel.setVisible(false);
				msgCount = 0;
				chatBtnClick = true;
			}
		});
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());
			User user = new User(userImage, userName, userMsg);
			users.add(user);
			SendObject(user);
			makeChatRoom(userName);
			ListenNetwork net = new ListenNetwork();
			net.start();

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connect error");
		}
	}
	
	// Server Message를 수신해서 화면에 표시
		class ListenNetwork extends Thread {
			public void run() {
				while (true) {
					try {
						Object obcm = null;
						String msg = null;
						User user = null;
						ChangeUser changeUser = null;
						ChatMsg cm = null;
						try {
							obcm = ois.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							break;
						}
						if (obcm == null)
							break;
						if (obcm instanceof User) {
							user = (User) obcm;
							// 친구만 추가 
							for(int k = 0; k < friendList.size(); k++) {
								if(friendList != null && friendList.get(k).getUserName().equals(user.getUserName())) {
									flag = 1;
									break;
								}
							}
							if(flag == 0) {
								friendList.add(user);
								friendProfile[friendIndex] = new UserProfile(user.getUserImage(), user.getUserName(), user.getActiveMsg());
								listPanel.add(friendProfile[friendIndex]);
								friendProfile[friendIndex].setBackground(Color.WHITE);
								friendProfile[friendIndex].setBounds(10, friendIndex*70 + 135 , 260, 70);
								friendIndex++;
							}
							flag = 0;
						} 
						else if (obcm instanceof ChangeUser) {
							changeUser = (ChangeUser) obcm;
							System.out.println(changeUser.getPastName() + changeUser.getPastMsg());
							System.out.println( changeUser.getNowName() + changeUser.getNowMsg());
							for(int i = 0; i<friendList.size(); i++) {
								if(friendList.get(i).getUserName().equals(changeUser.getPastName()) == true) {
									User addUser = new User(changeUser.getNowImage(), changeUser.getNowName(), changeUser.getNowMsg());
									friendList.remove(i);
									friendList.add(addUser);
									System.out.println(friendList.get(i).getUserName() +  friendList.get(i).getActiveMsg());
								}
							}
							
							for(int k =0; k < friendProfile.length; k ++) {
								if(friendProfile[k]!=null) {
									if(friendProfile[k].getUserName().equals(changeUser.getPastName()) == true) {
										System.out.println(changeUser.getPastName() + changeUser.getPastMsg());
										System.out.println( changeUser.getNowName() + changeUser.getNowMsg());
								
										friendProfile[k].getUserProfile().setIcon(changeUser.getNowImage());
										friendProfile[k].getUserNameLabel().setText(changeUser.getNowName());
										friendProfile[k].getActiveMsgLabel().setText(changeUser.getNowMsg());
									}
								}
								else
									continue;
							}
						} 
						else if (obcm instanceof ChatMsg) {
							cm = (ChatMsg) obcm;
							msg = cm.getData();
							
							switch (cm.getCode()) {
							case "110": // 채팅방에 참여 
								String roomId = cm.getRoomId();
								String userList = cm.getUserList();
								System.out.println("roomId" + roomId);
								System.out.println("msg" + msg);
								userChats[chatIndex] = new UserChat(home, userName, roomId, userList);
								userChats[chatIndex].setChatRoom(new ChatRoom(home, userName, roomId, userList));
								chatRooms.add(userChats[chatIndex]);
								chatPanel.add(userChats[chatIndex]);
								userChats[chatIndex].setBackground(Color.WHITE);
								userChats[chatIndex].setBounds(10, chatIndex*70 + 30 , 260, 70);
								chatIndex++;
								break;
							case "300": // chat message
								roomId = cm.getRoomId();
								String sendMsgUser = cm.getId();
								for(int i =0; i < chatRooms.size(); i++) {
									System.out.println("chatRooms.size() : " + chatRooms.size());
									if(chatRooms.get(i).getRoomId().equals(roomId)) {
										if(sendMsgUser.equals(userName)) {
											chatRooms.get(i).setLastMsg(cm.getData());
											chatRooms.get(i).getChatRoom().AppendTextR(cm);
										}
										else {
											chatRooms.get(i).setLastMsg(cm.getData());
											chatRooms.get(i).getChatRoom().AppendTextL(cm);
											addMsgCount();
										}
									}
								}
								break;
							case "400": // chat image
								roomId = cm.getRoomId();
								sendMsgUser = cm.getId();
								for(int i =0; i < chatRooms.size(); i++) {
									System.out.println("chatRooms.size() : " + chatRooms.size());
									if(chatRooms.get(i).getRoomId().equals(roomId)) {
										if(sendMsgUser.equals(userName)) {
											chatRooms.get(i).setLastMsg("사진");
											chatRooms.get(i).getChatRoom().AppendImageR(cm.getImg());
										}
										else {
											chatRooms.get(i).setLastMsg("사진");
											chatRooms.get(i).getChatRoom().AppendImage(cm.getImg(), cm.getId());
											addMsgCount();
										}
									}
								}
								break;
							case "500": // chat emoticon
								roomId = cm.getRoomId();
								sendMsgUser = cm.getId();
								for(int i =0; i < chatRooms.size(); i++) {
									System.out.println("chatRooms.size() : " + chatRooms.size());
									if(chatRooms.get(i).getRoomId().equals(roomId)) {
										if(sendMsgUser.equals(userName)) {
											chatRooms.get(i).setLastMsg("이모티콘");
											chatRooms.get(i).getChatRoom().AppendEmoticonR(cm.getImg());
										}
										else {
											chatRooms.get(i).setLastMsg("이모티콘");
											chatRooms.get(i).getChatRoom().AppendEmoticon(cm.getImg(), cm.getId());
											addMsgCount();
										}
									}
								}
								break;
							}
						} 
						else
							continue;
					} catch (IOException e) {
						System.out.println("ois.readObject() error");
						try {
							ois.close();
							oos.close();
							socket.close();
							break;
						} catch (Exception ee) {
							break;
						} // catch문 끝
					} // 바깥 catch문끝

				}
			}
		}
	
	public void addMsgCount() {
		// 클릭 중이지 않을 때 
		if(!chatBtnClick) {
			msgCount ++ ;
			if(msgCount < 10) {
				msgCountLabel.setText("  " + String.valueOf(msgCount));
			}
			else {
				msgCountLabel.setText(" " + String.valueOf(msgCount));
			}
			msgCountLabel.setVisible(true);
		}
	}
	
	public void changeMyProfile(ImageIcon userImage, String userName, String activeMsg) {
		myProfile.getUserProfile().setIcon(userImage);
		myProfile.getUserNameLabel().setText(userName);
		myProfile.getActiveMsgLabel().setText(activeMsg);
	}
		
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			System.out.println("SendObject Error");
		}
	}
	
	public void makeChatRoom(String clients) {
		try {
			// 방 생성, 코드는 100 
			if (clients.equals(userName)) // 나와의 채팅
				clients = userName;
			else
				clients += userName;
			
			ChatMsg obcm = new ChatMsg(userName, "100", "makeRoom");
			obcm.setUserList(clients);
			System.out.println("main to server " + clients);
			oos.writeObject(obcm);
		} catch (IOException e) {
			System.out.println("oos.writeObject() error");
			try {
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	// Server에게 network으로 전송
		public void SendMessage(String sendUserName, String roomId, String msg) {
			try {
				ChatMsg obcm = new ChatMsg(sendUserName, "300", msg);
				obcm.setRoomId(roomId);
				oos.writeObject(obcm);
			} catch (IOException e) {
				System.out.println("oos.writeObject() error");
				try {
					ois.close();
					oos.close();
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(0);
				}
			}
		}
		
}
