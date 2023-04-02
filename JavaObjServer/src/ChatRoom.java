
public class ChatRoom {
	private static final long serialVersionUID = 456L;
	
	private String roomId;
	private String userList;
	
	public ChatRoom(String room_id, String user_list) {
		setRoomId(room_id);
		setUserList(user_list);
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
}
