import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EmoticonView extends JFrame{
	private JPanel emoticonPane;
	private ImageIcon emo;
	
	private ChatRoom parent;
	private JavaObjClientMainHome main;
	private String userName;
	private String roomId;
	private String userList;
	
	public EmoticonView(ChatRoom parent, JavaObjClientMainHome javaObjClientMainHome, String username, String room_id, String user_list) {
		this.parent= parent;
		main = javaObjClientMainHome;
		userName = username;
		roomId = room_id;
		userList = user_list;
		
		JLabel haha;
		ImageIcon hahaEmoticon = new ImageIcon("emoticon/haha.png");
		hahaEmoticon = imageSetSize(hahaEmoticon, 50, 50);
		JLabel aing;
		ImageIcon aingEmoticon = new ImageIcon("emoticon/aing.png");
		aingEmoticon = imageSetSize(aingEmoticon, 50, 50);
		JLabel gamdong;
		ImageIcon gamdongEmoticon = new ImageIcon("emoticon/gamdong.png");
		gamdongEmoticon = imageSetSize(gamdongEmoticon, 50, 50);
		JLabel joa;
		ImageIcon joaEmoticon = new ImageIcon("emoticon/joa.png");
		joaEmoticon = imageSetSize(joaEmoticon, 55, 55);
		JLabel sinna;
		ImageIcon sinnaEmoticon = new ImageIcon("emoticon/sinna.png");
		sinnaEmoticon = imageSetSize(sinnaEmoticon, 50, 50);
		JLabel uwa;
		ImageIcon uwaEmoticon = new ImageIcon("emoticon/uwa.png");
		uwaEmoticon = imageSetSize(uwaEmoticon, 50, 50);
		JLabel simgag;
		ImageIcon simgagEmoticon = new ImageIcon("emoticon/simgag.png");
		simgagEmoticon = imageSetSize(simgagEmoticon, 50, 50);
		JLabel heartppyong;
		ImageIcon heartppyongEmoticon = new ImageIcon("emoticon/heartppyong.png");
		heartppyongEmoticon = imageSetSize(heartppyongEmoticon, 50, 50);
		JLabel jjinggeus;
		ImageIcon jjinggeusEmoticon = new ImageIcon("emoticon/jjinggeus.png");
		jjinggeusEmoticon = imageSetSize(jjinggeusEmoticon, 50, 50);
		JLabel kkamjjag;
		ImageIcon kkamjjagEmoticon = new ImageIcon("emoticon/kkamjjag.png");
		kkamjjagEmoticon = imageSetSize(kkamjjagEmoticon, 50, 50);
		JLabel jjajeung;
		ImageIcon jjajeungEmoticon = new ImageIcon("emoticon/jjajeung.png");
		jjajeungEmoticon = imageSetSize(jjajeungEmoticon, 50, 50);
		JLabel ssiig;
		ImageIcon ssiigEmoticon = new ImageIcon("emoticon/ssiig.png");
		ssiigEmoticon = imageSetSize(ssiigEmoticon, 50, 50);
		JLabel heog;
		ImageIcon heogEmoticon = new ImageIcon("emoticon/heog.png");
		heogEmoticon = imageSetSize(heogEmoticon, 50, 50);
		JLabel yeolbada;
		ImageIcon yeolbadaEmoticon = new ImageIcon("emoticon/yeolbada.png");
		yeolbadaEmoticon = imageSetSize(yeolbadaEmoticon, 50, 50);
		JLabel heung;
		ImageIcon heungEmoticon = new ImageIcon("emoticon/heung.png");
		heungEmoticon = imageSetSize(heungEmoticon, 50, 50);
		JLabel menbung;
		ImageIcon menbungEmoticon = new ImageIcon("emoticon/menbung.png");
		menbungEmoticon = imageSetSize(menbungEmoticon, 50, 50);
		JLabel ssugseu;
		ImageIcon ssugseuEmoticon = new ImageIcon("emoticon/ssugseu.png");
		ssugseuEmoticon = imageSetSize(ssugseuEmoticon, 50, 50);
		JLabel good;
		ImageIcon goodEmoticon = new ImageIcon("emoticon/good.png");
		goodEmoticon = imageSetSize(goodEmoticon, 55, 55);
		JLabel huljjeog;
		ImageIcon huljjeogEmoticon = new ImageIcon("emoticon/huljjeog.png");
		huljjeogEmoticon = imageSetSize(huljjeogEmoticon, 55, 55);
		JLabel buleuleu;
		ImageIcon buleuleuEmoticon = new ImageIcon("emoticon/buleuleu.png");
		buleuleuEmoticon = imageSetSize(buleuleuEmoticon, 55, 55);
		
		addComponentListener(new ComponentAdapter( ) {
			@Override
			public void componentShown(ComponentEvent e) {
				setBounds(parent.getX() - 320, parent.getY() + parent.getHeight() - 320, 320, 320);
			}
		});
		
		setTitle("이모티콘");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//setSize(320, 320);
		setResizable(false);
		setVisible(false);
		GridLayout grid = new GridLayout(5,4);
//		setLayout(grid);
		emoticonPane = new JPanel();
		setContentPane(emoticonPane);
		emoticonPane.setLayout(grid);
		emoticonPane.setBackground(Color.WHITE);
		
		haha = new JLabel(hahaEmoticon);
//		haha.setBorderPainted(false);
//		haha.setPreferredSize(new Dimension(30, 30));
		aing = new JLabel(aingEmoticon);
		gamdong = new JLabel(gamdongEmoticon);
		joa = new JLabel(joaEmoticon);
		sinna = new JLabel(sinnaEmoticon);
		uwa = new JLabel(uwaEmoticon);
		simgag = new JLabel(simgagEmoticon);
		heartppyong = new JLabel(heartppyongEmoticon);
		jjinggeus = new JLabel(jjinggeusEmoticon);
		kkamjjag = new JLabel(kkamjjagEmoticon);
		jjajeung = new JLabel(jjajeungEmoticon);
		ssiig = new JLabel(ssiigEmoticon);
		heog = new JLabel(heogEmoticon);
		yeolbada = new JLabel(yeolbadaEmoticon);
		heung = new JLabel(heungEmoticon);
		menbung = new JLabel(menbungEmoticon);
		ssugseu = new JLabel(ssugseuEmoticon);
		good = new JLabel(goodEmoticon);
		huljjeog = new JLabel(huljjeogEmoticon);
		buleuleu = new JLabel(buleuleuEmoticon);
		emoticonPane.add(haha);
		emoticonPane.add(uwa);
		emoticonPane.add(simgag);
		emoticonPane.add(heartppyong);
		emoticonPane.add(aing);
		emoticonPane.add(jjinggeus);
		emoticonPane.add(kkamjjag);
		emoticonPane.add(jjajeung);
		emoticonPane.add(gamdong);
		emoticonPane.add(heung);
		emoticonPane.add(menbung);
		emoticonPane.add(ssugseu);
		emoticonPane.add(joa);
		emoticonPane.add(good);
		emoticonPane.add(huljjeog);
		emoticonPane.add(buleuleu);
		emoticonPane.add(sinna);
		emoticonPane.add(ssiig);
		emoticonPane.add(heog);
		emoticonPane.add(yeolbada);

		haha.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/haha.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		aing.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/aing.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		gamdong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/gamdong.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);				
			}
		});
		
		joa.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/joa.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);		
			}
		});
		
		sinna.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/sinna.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		uwa.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/uwa.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		simgag.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/simgag.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		heartppyong.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/heartppyong.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		jjinggeus.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/jjinggeus.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		kkamjjag.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/kkamjjag.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		jjajeung.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/jjajeung.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		ssiig.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/ssiig.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		heog.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/heog.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		yeolbada.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/yeolbada.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		heung.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/heung.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		menbung.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/menbung.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		ssugseu.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/ssugseu.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		good.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/good.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		huljjeog.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/huljjeog.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
		
		buleuleu.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				ChatMsg obcm = new ChatMsg(userName, "500", "EMOTICON");
				emo = new ImageIcon("emoticon/buleuleu.png");
				obcm.setImg(emo);
				obcm.setRoomId(roomId);
				main.SendObject(obcm);
			}
		});
	}
	
	private ImageIcon imageSetSize(ImageIcon icon, int i, int j) {
		Image ximg = icon.getImage();  //ImageIcon을 Image로 변환.
		Image yimg = ximg.getScaledInstance(i, j, java.awt.Image.SCALE_SMOOTH);
		ImageIcon xyimg = new ImageIcon(yimg); 
		return xyimg;
}
	

}
