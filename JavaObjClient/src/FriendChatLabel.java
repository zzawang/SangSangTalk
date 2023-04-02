import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FriendChatLabel extends JLabel {
	private ImageIcon userProfile;
	private String userName;
	
	private JLabel userProfileLabel;
	private JLabel userNameLabel;
	
	public FriendChatLabel(ImageIcon userProfile, String userName) {
		this.userProfile = userProfile;
		this.userName = userName;
		
		setPreferredSize(new Dimension(300, 70));
		setLayout(null);
		setVisible(true);

		userProfileLabel = new JLabel(userProfile);
		add(userProfileLabel);
		userProfileLabel.setBounds(0, 5, 60, 60);
		
		userNameLabel = new JLabel(userName);
		userNameLabel.setBorder(null);
		userNameLabel.setBackground(Color.WHITE);
		userNameLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel.setBounds(75, 35, 200, 20);
		add(userNameLabel);
		
	}

	public ImageIcon getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(ImageIcon userProfile) {
		this.userProfile = userProfile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
