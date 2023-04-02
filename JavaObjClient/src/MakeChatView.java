
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class MakeChatView extends JFrame{
	private static final long serialVersionUID = 400L;
	private JavaObjClientMainHome main;
	
	private JPanel makeChatPanel;
	private JScrollPane scrollPane;
	private JButton createChatRoomBtn;
	
	private UserAddProfile[] addProfile;
	private Vector<User> friendVector;
	private String clients;
	
	public MakeChatView(JavaObjClientMainHome main, Vector<User> friendVec) {
		this.main = main;
		this.friendVector = friendVec;
		
		addProfile = new UserAddProfile[20];
		clients = "";
		
		addComponentListener(new ComponentAdapter( ) {
			@Override
			public void componentShown(ComponentEvent e) {
				setBounds(main.getX() + main.getWidth(), main.getY(), 330 ,400);
			}
		});
		
		setVisible(true);
		Container contentPane = getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		
		makeChatPanel = new JPanel();
		makeChatPanel.setLayout(null);
		makeChatPanel.setBackground(Color.WHITE);
		makeChatPanel.setPreferredSize(new Dimension(20, 71*friendVector.size()));
		
		scrollPane = new JScrollPane(makeChatPanel, scrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, scrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(0, 0, 330, 320);  
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		
		for(int i = 0; i< friendVec.size(); i++) {
			addProfile[i] = new UserAddProfile(friendVec.get(i).getUserImage(), friendVec.get(i).getUserName());
			makeChatPanel.add(addProfile[i]);
			addProfile[i].setBackground(Color.WHITE);
			addProfile[i].setBounds(10, i*70 + 10 , 310, 70);
		}
		
		createChatRoomBtn = new JButton(" 초대하기 ");
		createChatRoomBtn.setBounds(118, 328, 80, 35);
		createChatRoomBtn.setFont(new Font("굴림", Font.PLAIN, 14));
		LineBorder border = new LineBorder(Color.BLACK,1);
		createChatRoomBtn.setBorder(border);
		contentPane.add(createChatRoomBtn);
		
		createChatRoomBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i< friendVector.size(); i++) {
					if(addProfile[i].getFlag() == true) {
						clients += addProfile[i].getUserName();
						clients += " "; // split 용도 
					}
				}
				System.out.println(clients);
				main.makeChatRoom(clients);
				setVisible(false);
			}
		});
	}
	
	public String getClients() {
		return clients;
	}
}
