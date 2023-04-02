import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class UserAddProfile extends JLabel{

	private static final long serialVersionUID = 200L;
	private JLabel userProfile;
	private JLabel userNameLabel;
	private JCheckBox userCheckbox;
	
	private ImageIcon userImage;
	private String  userName;
	private boolean flag;
	 
	public UserAddProfile(ImageIcon userImage, String userName) {
		
		this.userImage = userImage;
		this.userName = userName;
		this.flag = false;
		
		setBounds(0, 0, 310, 70);
		setLayout(null);
		setVisible(true);

		userProfile = new JLabel(userImage);
		userProfile.setBounds(0, 5, 60, 60);
		add(userProfile);
		
		userNameLabel = new JLabel(userName);
		userNameLabel.setBorder(null);
		userNameLabel.setBackground(Color.WHITE);
		userNameLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel.setBounds(80, 11, 180, 40);
		add(userNameLabel);
		
		userCheckbox = new JCheckBox();
		userCheckbox.setBounds(270, 22, 25, 25);
		add(userCheckbox);
		
		userCheckbox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				flag = !flag;
				System.out.println(String.valueOf(flag));
			}
			
		});
		
	}

	public ImageIcon getUserImage() {
		return userImage;
	}

	public String getUserName() {
		return userName;
	}
	
	public boolean getFlag() {
		return flag;
	}
}
