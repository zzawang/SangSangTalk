import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageView extends JFrame {
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private ChatRoom parent;
	private Image image;
	private JPanel imgPanel;

	public ImageView(ChatRoom parent, Image image) {
		this.parent = parent;
		this.image = image;
		
		addComponentListener(new ComponentAdapter( ) {
			@Override
			public void componentShown(ComponentEvent e) {
				setBounds(parent.getX() + parent.getWidth(), parent.getY(), 450 ,500);
			}
		});
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("»çÁø");
		MyPanel panel = new MyPanel(image);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		add(panel);
        this.setLocationRelativeTo(null);
		setVisible(true);
	}
	
	class MyPanel extends JPanel{
		private Image img;
        public MyPanel(Image img) {
        	this.img = img;
        }
        
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            if(img.getWidth(this)>img.getHeight(this)) {
            	 g.drawImage(img, 0, 0, this.getWidth(), img.getHeight(this) + this.getWidth() - img.getWidth(this), this);
            }
            else {
            	 g.drawImage(img, 0, 0, img.getWidth(this) + this.getHeight() - img.getHeight(this),  this.getHeight(), this);
            }
        }
    }
	
	
}
