import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserProfile extends JLabel{

	private static final long serialVersionUID = 200L;
	private JLabel userProfile;
	private JLabel userNameLabel;
	private JLabel activeMsgLabel;

	private ImageIcon userImage;
	private String  userName;
	private String activeMsg;
	 
	public UserProfile(ImageIcon userImage, String userName, String activeMsg) {
		
		this.userName = userName;
		this.activeMsg = activeMsg;
		this.userImage = userImage;
		
		setBounds(0, 0, 300, 70);
		setLayout(null);

		userProfile = new JLabel(userImage);
		add(userProfile);
		userProfile.setBounds(0, 5, 60, 60);
		setVisible(true);
		
		userNameLabel = new JLabel(userName);
		userNameLabel.setBorder(null);
		userNameLabel.setBackground(Color.WHITE);
		userNameLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel.setBounds(80, 12, 200, 20);
		add(userNameLabel);
		
		activeMsgLabel = new JLabel(activeMsg);
		activeMsgLabel.setBorder(null);
		activeMsgLabel.setBackground(Color.WHITE);
		activeMsgLabel.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		activeMsgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		activeMsgLabel.setBounds(80, 34, 200, 20);
		add(activeMsgLabel);
		
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
	
}
