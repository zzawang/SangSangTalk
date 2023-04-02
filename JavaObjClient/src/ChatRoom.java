import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatRoom extends JFrame {
	private static final long serialVersionUID = 500L;
	private JPanel contentPane;
	private JTextField txtInput;
	private JButton sendBtn;
	private JButton imgSendBtn;
	private JLabel lblEmoticon;
	private JTextPane textArea;
	private JButton exitBtn;
	private Frame frame;
	private FileDialog fd;
	private ImageIcon emoticon;
	private Image showImg;
	private ImageView imageView;
	
	private ChatRoom chatRoom;
	private JavaObjClientMainHome main;
	private EmoticonView emoticonView;
	private String userName;
	private String roomId;
	private String userList;
	
	private MyMouseListener mouseListener;
	
	private ImageIcon usersProfileBasicImage = new ImageIcon("images/userBasicImage-crop.png");
	
	private static final int BUF_LEN = 128; // Windows ó�� BUF_LEN �� ����
	
	public ChatRoom(JavaObjClientMainHome javaObjClientMainHome, String username, String room_id, String user_list) {
		
		main = javaObjClientMainHome;
		userName = username;
		roomId = room_id;
		userList = user_list;
		
		chatRoom = this;
		mouseListener = new MyMouseListener();
		
		addComponentListener(new ComponentAdapter( ) {
			@Override
			public void componentShown(ComponentEvent e) {
				setBounds(main.getX() + 50, main.getY() + 50, 400 ,650);
			}
		});
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle(roomId);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		setVisible(false);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 400, 500);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);
		textArea = new JTextPane();
		textArea.setLayout(new FlowLayout());
		textArea.setEditable(false);
		textArea.setFocusable(false);
		textArea.setFont(new Font("����ü", Font.PLAIN, 14));
		textArea.setBackground(new java.awt.Color(240, 248, 255));;
		scrollPane.setViewportView(textArea);

		txtInput = new JTextField();
		txtInput.setBounds(80, 520, 210, 45);
		txtInput.setFocusable(true);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		sendBtn = new JButton("����");
		sendBtn.setFont(new Font("����", Font.PLAIN, 14));
		sendBtn.setBounds(300, 520, 80, 45);
		contentPane.add(sendBtn);

		imgSendBtn = new JButton("+");
		imgSendBtn.setFont(new Font("����", Font.PLAIN, 16));
		imgSendBtn.setBounds(15, 523, 50, 40);
		contentPane.add(imgSendBtn);
		
		exitBtn = new JButton("������");
		exitBtn.setFont(new Font("����", Font.PLAIN, 14));
		exitBtn.setBounds(300, 570, 80, 45);
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(userName, "800", "Bye");
				main.SendObject(msg);
				System.exit(0);
			}
		});
		contentPane.add(exitBtn);
		
		TextSendAction textSendAction = new TextSendAction();
		sendBtn.addActionListener(textSendAction);
		txtInput.addActionListener(textSendAction);
		txtInput.requestFocus();
		ImageSendAction imageSendAction = new ImageSendAction();
		imgSendBtn.addActionListener(imageSendAction);
		
		// �̸�Ƽ�� ��ư(label)
		lblEmoticon = new JLabel();
		emoticon = new ImageIcon("emoticon/smile.png");
		lblEmoticon.setIcon(emoticon);
		lblEmoticon.setBounds(24, 570, 40, 40);
		contentPane.add(lblEmoticon);
		
		// �̸�Ƽ�� ������
		lblEmoticon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(emoticonView == null) {
					emoticonView = new EmoticonView(chatRoom, main, userName, roomId, userList);
				}
				else {
					emoticonView.setVisible(true);
				}
			}
		});
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button�� �����ų� �޽��� �Է��ϰ� Enter key ġ��
			if (e.getSource() == sendBtn || e.getSource() == txtInput) {
				if(txtInput.getText() != null) {
					String msg = null;
					msg = txtInput.getText();
					main.SendMessage(userName, roomId, msg);
					txtInput.setText(""); // �޼����� ������ ���� �޼��� ����â�� ����.
					txtInput.requestFocus(); // �޼����� ������ Ŀ���� �ٽ� �ؽ�Ʈ �ʵ�� ��ġ��Ų��
				}
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �׼� �̺�Ʈ�� sendBtn�϶� �Ǵ� textField ���� Enter key ġ��
			if (e.getSource() == imgSendBtn) {
				frame = new Frame("�̹���÷��");
				fd = new FileDialog(frame, "�̹��� ����", FileDialog.LOAD);
				fd.setVisible(true);
				ChatMsg obcm = new ChatMsg(userName, "400", "IMG");
				ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
				obcm.setRoomId(roomId);
				obcm.setImg(img);
				main.SendObject(obcm);
			}
		}
	}
	
	public String getTimeNow() {
      LocalTime now = LocalTime.now();
      String date;
      String dayOrNight;
      int hour = now.getHour();
      int minute = now.getMinute();
      
      // ���� 
      if(hour >= 12) {
      	dayOrNight = "����";
      	if(hour != 12) { hour %= 12;}
      }
      // ���� 
      else {
      	dayOrNight = "����";
      }
      date = dayOrNight+ " " + String.valueOf(hour) + ":";
      if(minute<10) {
    	  date += "0";
    	  date += String.valueOf(minute);
      }else {
    	  date += String.valueOf(minute);
      }
      return date;
	}
	
	public void AppendTextL(ChatMsg cm) {
		textArea.setCaretPosition(textArea.getDocument().getLength());
		
		JLabel ucImgLabel = new JLabel(usersProfileBasicImage);
		ucImgLabel.setSize(new Dimension(50, 50));
		ucImgLabel.setBorder(null);
		ucImgLabel.setBackground(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucImgLabel);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel ucLabel = new JLabel("  " + cm.getId());
		ucImgLabel.setFont(new Font("����", Font.PLAIN, 16));
		ucImgLabel.setBorder(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucLabel);
		
		AppendTextL("\n");
		AppendTextL("       ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		JLabel label = new JLabel(" " + cm.getData() + " ");
		label.setBorder(null);
		label.setOpaque(true);
		label.setFont(new Font("����", Font.PLAIN, 18));
		label.setBackground(Color.YELLOW);
		textArea.insertComponent(label);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		AppendTextL("  ");
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextL("\n");
	}
	
	public synchronized void AppendTextL(String msg) {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
		if(msg != null && !msg.equals("  ") && !msg.equals("       ") && !msg.equals("\n")) {
			StyleConstants.setBackground(left, Color.WHITE);
		}
		doc.setParagraphAttributes(doc.getLength(), 1, left, true);
		
		try {
			if(msg.equals("\n")) {
				doc.insertString(doc.getLength(), msg, left);
			}
			else {
				doc.insertString(doc.getLength(), msg, left);
			}
		}catch (BadLocationException e) {
			e.printStackTrace();
		}
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}

	public void AppendTextR(ChatMsg cm) {
		AppendTextR("\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		
		AppendTextR("  ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		JLabel label = new JLabel(" " + cm.getData() + " ");
		label.setBorder(null);
		label.setOpaque(true);
		label.setFont(new Font("����", Font.PLAIN, 18));
		label.setBackground(Color.YELLOW);
		textArea.insertComponent(label);
		AppendTextR("  ");
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextR("\n");
		AppendTextR("\n");
	}
	
	public synchronized void AppendTextR(String msg) {
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLACK);
		if(msg != null && !msg.equals("  ") && !msg.equals("       ") && !msg.equals("\n")) {
			StyleConstants.setBackground(right, Color.WHITE);
		}
		doc.setParagraphAttributes(doc.getLength(), 1, right, true);
		
		try {
			if(msg.equals("\n")) {
				doc.insertString(doc.getLength(), msg, right);
			}
			else {
				doc.insertString(doc.getLength(), msg, right);
			}
		}catch (BadLocationException e) {
			e.printStackTrace();
		}
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	public void AppendEmoticon(ImageIcon ori_icon, String name) {
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel ucImgLabel = new JLabel(usersProfileBasicImage);
		ucImgLabel.setSize(new Dimension(50, 50));
		ucImgLabel.setBorder(null);
		ucImgLabel.setBackground(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucImgLabel);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel ucLabel = new JLabel("  " + name);
		ucImgLabel.setFont(new Font("����", Font.PLAIN, 16));
		ucImgLabel.setBorder(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucLabel);
		
		AppendTextL("\n");
		AppendTextL("       ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
		if (width > 200 || height > 200) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);
		} else
			textArea.insertIcon(ori_icon);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		AppendTextL("  ");
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextL("\n");
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}

	// �̹��� ���� ���
	public void AppendEmoticonR(ImageIcon ori_icon) {
		
		AppendTextR("\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		AppendTextR("  ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLACK);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
		if (width > 200 || height > 200) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			try {
				doc.insertString(doc.getLength(), "\n", right );
				textArea.insertIcon(new_icon);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			//textArea.insertIcon(ori_icon);
			try {
				doc.insertString(doc.getLength(), "\n", right );
				textArea.insertIcon(ori_icon);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextR("\n");
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		
	}
	
	public void AppendImage(ImageIcon ori_icon, String name) {
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel ucImgLabel = new JLabel(usersProfileBasicImage);
		ucImgLabel.setSize(new Dimension(50, 50));
		ucImgLabel.setBorder(null);
		ucImgLabel.setBackground(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucImgLabel);
		
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel ucLabel = new JLabel("  " + name);
		ucImgLabel.setFont(new Font("����", Font.PLAIN, 16));
		ucImgLabel.setBorder(null);
		ucImgLabel.setOpaque(true);
		textArea.insertComponent(ucLabel);
		
		AppendTextL("\n");
		AppendTextL("       ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
		if (width > 200 || height > 200) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			
			JLabel imgLabel = new JLabel(new_icon);
			imgLabel.addMouseListener(mouseListener);
			textArea.insertComponent(imgLabel);
		} else { 
			JLabel imgLabel2 = new JLabel(ori_icon);
			imgLabel2.addMouseListener(mouseListener);
			textArea.insertComponent(imgLabel2);
		}

		textArea.setCaretPosition(textArea.getDocument().getLength());
		AppendTextL("  ");
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextL("\n");
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
	}

	// �̹��� ���� ���
	public void AppendImageR(ImageIcon ori_icon) {
		
		AppendTextR("\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
		JLabel timeLabel = new JLabel(" " + getTimeNow() + " ");
		timeLabel.setFont(new Font("����", Font.PLAIN, 10));
		timeLabel.setBorder(null);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.WHITE);
		textArea.insertComponent(timeLabel);
		
		AppendTextR("  ");
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLACK);	
	    doc.setParagraphAttributes(doc.getLength(), 1, right, false);
		// Image�� �ʹ� ũ�� �ִ� ���� �Ǵ� ���� 200 �������� ��ҽ�Ų��.
		if (width > 200 || height > 200) {
			if (width > height) { // ���� ����
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // ���� ����
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			Image new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			ImageIcon new_icon = new ImageIcon(new_img);
			try {
				doc.insertString(doc.getLength(), "\n", right );
				JLabel imgLabel = new JLabel(new_icon);
				imgLabel.addMouseListener(mouseListener);
				textArea.insertComponent(imgLabel);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			//textArea.insertIcon(ori_icon);
			try {
				doc.insertString(doc.getLength(), "\n", right );
				JLabel imgLabel2 = new JLabel(ori_icon);
				imgLabel2.addMouseListener(mouseListener);
				textArea.insertComponent(imgLabel2);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		AppendTextR("\n");
		
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		
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
				// ���콺�� 2�� ������ 
				System.out.println("dsfsdfdfdfsfdsdfdfds");
				JLabel label = (JLabel)e.getSource();
				ImageIcon icon = (ImageIcon) label.getIcon();
				Image image = icon.getImage();
				imageView = new ImageView(chatRoom, image);
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
