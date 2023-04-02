import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UserChat extends JLabel{
	private static final long serialVersionUID = 87L;
	private JLabel usersProfiles;
	private ImageIcon usersProfilesImage;
	
	private Vector<ImageIcon> imageVector;
	private JLabel userListLabel;
	private JLabel lastMsgLabel;
	
	private JavaObjClientMainHome main;
	private ChatRoom chatRoom;
	private String userName;
	private String roomId;
	private String userList;
	private String lastMsg;
	
	public UserChat(JavaObjClientMainHome javaObjClientMainHome, String username, String room_id, String user_list) {
		
		main = javaObjClientMainHome;
		userName = username;
		roomId = room_id;
		userList = user_list;
		lastMsg = "";
		
		MyMouseListener mouseListener = new MyMouseListener();
		
		String[] roomUsers = userList.split(" "); // 단어들을 분리한다.
		String[] roomUsersExceptMe = new String[roomUsers.length-1];
		String userChatRoomName = "";
		
		if(userList.equals(userName) == true) {
			userChatRoomName = userName + " (나)";
			
		}
		else {
			int k = 0;
			for(int i = 0; i < roomUsers.length; i++) {
				if(!roomUsers[i].equals(userName)) {
					roomUsersExceptMe[k] = roomUsers[i];
					k++;
				}
			}
			
			if(roomUsersExceptMe.length != 1) {
				userChatRoomName = String.join(", ", roomUsersExceptMe);
			}
			else {
				userChatRoomName = roomUsersExceptMe[0];
			}
		}
		
		
		// 1:1 대화 
		if(roomUsers.length == 1 || roomUsers.length == 2) {
			usersProfilesImage = new ImageIcon("images/userBasicImage-crop.png");
		}
		// 2명이랑 대화 
		else if(roomUsers.length == 3) {
			usersProfilesImage = new ImageIcon("images/userChat2.png");
		}
		// 3명이랑 대화 
		else if(roomUsers.length == 4){
			usersProfilesImage = new ImageIcon("images/userChat3.png");
		}
		// 4명 이상이랑 대화 
		else {
			usersProfilesImage = new ImageIcon("images/userChat4.png");
		}
		
		
		setBounds(0, 0, 300, 70);
		setLayout(null);
		setVisible(true);
		addMouseListener(mouseListener);

		usersProfiles = new JLabel(usersProfilesImage);
		add(usersProfiles);
		usersProfiles.setBounds(0, 5, 60, 60);
		
		userListLabel = new JLabel(userChatRoomName);
		userListLabel.setBorder(null);
		userListLabel.setBackground(Color.WHITE);
		userListLabel.setFont(new Font("굴림", Font.BOLD, 14));
		userListLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userListLabel.setBounds(80, 12, 200, 20);
		userListLabel.addMouseListener(mouseListener);
		add(userListLabel);
		
		lastMsgLabel = new JLabel(lastMsg);
		lastMsgLabel.setBorder(null);
		lastMsgLabel.setBackground(Color.WHITE);
		lastMsgLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		lastMsgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lastMsgLabel.setBounds(80, 34, 200, 20);
		lastMsgLabel.addMouseListener(mouseListener);
		add(lastMsgLabel);

	}
	
	public ChatRoom getChatRoom() {
		return chatRoom;
	}

	public void setChatRoom(ChatRoom chatRoom) {
		this.chatRoom = chatRoom;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	public String getUserList() {
		return userList;
	}

	public void setUserList(String userList) {
		this.userList = userList;
	}


	public String getLastMsg() {
		return lastMsg;
	}

	public void setLastMsg(String lastMsg) {
		this.lastMsg = lastMsg;
		lastMsgLabel.setText(lastMsg);
	}


	class MyMouseListener implements MouseListener, MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount() == 2) {
				// 마우스가 2번 눌리면 
				System.out.println(userName + "    " + roomId + "    " + userList);
				if(chatRoom == null) {
					chatRoom = new ChatRoom(main, userName, roomId, userList);
				}
				else {
					chatRoom.setVisible(true);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
