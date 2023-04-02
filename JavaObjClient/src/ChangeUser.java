import java.io.Serializable;

import javax.swing.ImageIcon;

public class ChangeUser implements Serializable {
	private static final long serialVersionUID = 11L;
	private ImageIcon pastImage;
	private String pastName;
	private String pastMsg;
	
	private ImageIcon nowImage;
	private String nowName;
	private String nowMsg;
	
	public ChangeUser(ImageIcon pastImage, String pastName, String pastMsg, ImageIcon nowImage, String nowName, String nowMsg) {
		this.pastImage = pastImage;
		this.pastName = pastName;
		this.pastMsg = pastMsg;
		
		this.nowImage = nowImage;
		this.nowName = nowName;
		this.nowMsg = nowMsg;
	}

	public ImageIcon getPastImage() {
		return pastImage;
	}

	public void setPastImage(ImageIcon pastImage) {
		this.pastImage = pastImage;
	}

	public String getPastName() {
		return pastName;
	}

	public void setPastName(String pastName) {
		this.pastName = pastName;
	}

	public String getPastMsg() {
		return pastMsg;
	}

	public void setPastMsg(String pastMsg) {
		this.pastMsg = pastMsg;
	}

	public ImageIcon getNowImage() {
		return nowImage;
	}

	public void setNowImage(ImageIcon nowImage) {
		this.nowImage = nowImage;
	}

	public String getNowName() {
		return nowName;
	}

	public void setNowName(String nowName) {
		this.nowName = nowName;
	}

	public String getNowMsg() {
		return nowMsg;
	}

	public void setNowMsg(String nowMsg) {
		this.nowMsg = nowMsg;
	}
	
	
}
