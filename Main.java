package com.company;

import javax.swing.*;

public class Main extends JPanel {

    public static void main(String[] args) {
	    JFrame frame = new JFrame("Snake");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
		GamePlay ex = new GamePlay();
		frame.getContentPane().add(ex);
		frame.setSize(1145,855);
		ex.requestFocusInWindow();
		frame.setResizable(false);
		frame.setVisible(true);
    }
}
