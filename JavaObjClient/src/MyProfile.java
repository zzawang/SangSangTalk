import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class MyProfile extends JLabel{

	private static final long serialVersionUID = 200L;
	private JLabel userProfile;
	private JLabel userNameLabel;
	private JLabel activeMsgLabel;
	private ChangeUserInfo changeUserInfo;
	
	private JavaObjClientMainHome main;
	private ImageIcon userImage;
	private String  userName;
	private String activeMsg;
	 
	public MyProfile(JavaObjClientMainHome main, ImageIcon userImage, String userName, String activeMsg) {
		
		this.main = main;
		this.userName = userName;
		this.activeMsg = activeMsg;
		this.userImage = userImage;
		
		MyMouseListener mouseListener = new MyMouseListener();
		
		setBounds(0, 0, 300, 70);
		setLayout(null);

		userProfile = new JLabel(userImage);
		add(userProfile);
		userProfile.addMouseListener(mouseListener);
		userProfile.setBounds(0, 5, 60, 60);
		setVisible(true);
		
		userNameLabel = new JLabel(userName);
		userNameLabel.setBorder(null);
		userNameLabel.setBackground(Color.WHITE);
		userNameLabel.setFont(new Font("굴림", Font.BOLD, 14));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel.setBounds(80, 12, 200, 20);
		add(userNameLabel);
		setVisible(true);
		
		activeMsgLabel = new JLabel(activeMsg);
		activeMsgLabel.setBorder(null);
		activeMsgLabel.setBackground(Color.WHITE);
		activeMsgLabel.setFont(new Font("굴림", Font.PLAIN, 14));
		activeMsgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		activeMsgLabel.setBounds(80, 34, 200, 20);
		add(activeMsgLabel);
		setVisible(true);
		
	}
	
	public JLabel getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(JLabel userProfile) {
		this.userProfile = userProfile;
	}
	
	public JLabel getUserNameLabel() {
		return userNameLabel;
	}

	public void setUserNameLabel(JLabel userNameLabel) {
		this.userNameLabel = userNameLabel;
	}
	
	public JLabel getActiveMsgLabel() {
		return activeMsgLabel;
	}

	public void setActiveMsgLabel(JLabel activeMsgLabel) {
		this.activeMsgLabel = activeMsgLabel;
	}

	public ImageIcon getUserImage() {
		return userImage;
	}

	public void setUserImage(ImageIcon userImage) {
		this.userImage = userImage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActiveMsg() {
		return activeMsg;
	}

	public void setActiveMsg(String activeMsg) {
		this.activeMsg = activeMsg;
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
				if(changeUserInfo == null) {
					changeUserInfo = new ChangeUserInfo(main, userImage, userName, activeMsg);
				}
				else {
					changeUserInfo.setVisible(true);
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
