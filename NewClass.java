package snake;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NewClass extends JPanel {
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		HandlingEvents1 ex = new HandlingEvents1();
		frame.getContentPane().add(ex);
		frame.setSize(1145, 855);
		ex.requestFocusInWindow();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}
