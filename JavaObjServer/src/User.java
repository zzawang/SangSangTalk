import java.io.Serializable;

import javax.swing.ImageIcon;

public class User implements Serializable {
	private static final long serialVersionUID = 11L;
	private ImageIcon userImage;
	private String userName;
	private String activeMsg;
	
	public User(ImageIcon image, String name, String msg) {
		this.userImage = image;
		this.userName = name;
		this.activeMsg = msg;
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
	
	public void setActiveMsg(String userMsg) {
		this.activeMsg = userMsg;
	}

}
