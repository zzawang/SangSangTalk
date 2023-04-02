import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class ChangeUserInfo extends JFrame{

	private static final long serialVersionUID = 200L;
	private JLabel topicLabel;
	private JLabel userImgLabel;
	private JLabel userNameLabel;
	private JLabel userMsgLabel;
	private JTextField nameInput;
	private JTextField msgInput;
	private JButton okBtn;
	
	private ImageIcon userImage;
	private String  userName;
	private String activeMsg;
	
	private ChangeUser changeUser;
	 
	public ChangeUserInfo(JavaObjClientMainHome main, ImageIcon userImage, String userName, String activeMsg) {
		
		this.userImage = userImage;
		this.userName = userName;
		this.activeMsg = activeMsg;
		
		addComponentListener(new ComponentAdapter( ) {
			@Override
			public void componentShown(ComponentEvent e) {
				setBounds(main.getX() + main.getWidth(), main.getY(), 320 ,420);
			}
		});
		
		setVisible(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		
		topicLabel = new JLabel("ÇÁ·ÎÇÊ ¹Ù²Ù±â");
		topicLabel.setBorder(null);
		topicLabel.setBackground(Color.WHITE);
		topicLabel.setFont(new Font("±¼¸²", Font.BOLD, 15));
		topicLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topicLabel.setBounds(108, 5, 100, 30);
		contentPane.add(topicLabel);
		
		userImgLabel = new JLabel(userImage);
		userImgLabel.setBounds(110, 45, 100, 100);
		contentPane.add(userImgLabel);
		
		userNameLabel = new JLabel("ÀÌ¸§");
		userNameLabel.setBorder(null);
		userNameLabel.setBackground(Color.WHITE);
		userNameLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		userNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userNameLabel.setBounds(30, 170, 40, 30);
		contentPane.add(userNameLabel);
		
		nameInput = new JTextField();
		nameInput.setText(userName);
		nameInput.setBackground(Color.WHITE);
		nameInput.setFont(new Font("±¼¸²", Font.BOLD, 14));
		nameInput.setHorizontalAlignment(SwingConstants.LEFT);
		nameInput.setBounds(25, 200, 270, 40);
		nameInput.setColumns(20);
		contentPane.add(nameInput);
		
		userMsgLabel = new JLabel("»óÅÂ ¸Þ½ÃÁö");
		userMsgLabel.setBorder(null);
		userMsgLabel.setBackground(Color.WHITE);
		userMsgLabel.setFont(new Font("±¼¸²", Font.BOLD, 14));
		userMsgLabel.setHorizontalAlignment(SwingConstants.LEFT);
		userMsgLabel.setBounds(30, 240, 100, 30);
		contentPane.add(userMsgLabel);
		
		msgInput = new JTextField();
		msgInput.setText(activeMsg);
		msgInput.setBackground(Color.WHITE);
		msgInput.setFont(new Font("±¼¸²", Font.BOLD, 14));
		msgInput.setHorizontalAlignment(SwingConstants.LEFT);
		msgInput.setBounds(25, 270, 270, 40);
		msgInput.setColumns(20);
		contentPane.add(msgInput);
		
		okBtn = new JButton(" Àû¿ë ");
		okBtn.setBounds(118, 340, 80, 35);
		okBtn.setFont(new Font("±¼¸²", Font.PLAIN, 14));
		LineBorder border = new LineBorder(Color.BLACK,1);
		okBtn.setBorder(border);
		contentPane.add(okBtn);
		
		okBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = null;
				name = nameInput.getText();
				
				String msg = null;
				msg = msgInput.getText();
				
				changeUser = new ChangeUser(userImage, userName, activeMsg, userImage, name, msg);
				main.SendObject(changeUser);
				main.changeMyProfile(userImage, name, msg);
				setVisible(false);
			}
		});
		
	}

	

	public String getActiveMsg() {
		return activeMsg;
	}

	public void setActiveMsg(String activeMsg) {
		this.activeMsg = activeMsg;
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
	
}
