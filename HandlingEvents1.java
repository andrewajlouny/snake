package snake;
//https://beginnersbook.com/2014/09/java-enum-examples/

///https://stackoverflow.com/questions/20098124/displaying-an-image-in-a-jframe

// USE KEY BINDING
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.Random;

@SuppressWarnings("serial")
public class HandlingEvents1 extends JPanel {
	int[] snakeX = new int[900], snakeY = new int[900];
	int[] bonusX = new int[900], bonusY = new int[900];

	int[] border = new int[100];
	String[] highScore = new String[6];
	String[] highScore1 = new String[6];

	String direction = new String();
	String bonusD = "Right";
	String initials;

	int level = 0;
	int score = 0;
	int totalScore = 0;
	long time = 85;
	int incrament = 20;
	int bonusInc = 20;

	String gameMode = "regular";
	String status = "alive";
	Boolean gameStatus = true;
	String x = null;

	Snake snake = new Snake();
	Snake bonusSnake = new Snake();
	Apple apple = new Apple();
	Scores highScoreList = new Scores();
	

	public HandlingEvents1() {
		apple.apple(level);
		setSize(820, 820);
		setBackground(Color.BLACK);
		// Initializes where the snakes starts
		for (int i = 0; i < snake.getSnakeSize(); i++) {
			snakeX[i] = 200 - (i * 20);
			snakeY[i] = 120;
		}
		for (int i = 0; i < bonusSnake.getSnakeSize(); i++) {
			bonusX[i] = 200 - (i * 20);
			bonusY[i] = 220;
		}
		// Reference point for when the walls get painted
		for (int i = 0; i < 100; i++) {
			border[i] = ((i + 1) * 10);
		}
		addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent evt) {
				// This lets the game know what direction the snake goes
				if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
					direction = "Right";
					snake.setSnakeSpeed(incrament);
				} else if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
					if (snakeY[0] >= 780 || direction == "Up")
						;
					else {
						direction = "Down";
						snake.setSnakeSpeed(incrament);
					}
				} else if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
					if (snakeX[0] <= 20 || direction == "Right")
						;
					else {
						direction = "Left";
						snake.setSnakeSpeed(-incrament);
					}
				} else if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (snakeX[0] >= 780 || direction == "Left")
						;
					else {
						direction = "Right";
						snake.setSnakeSpeed(incrament);
					}
				} else if (evt.getKeyCode() == KeyEvent.VK_UP) {
					if (snakeY[0] <= 110 || direction == "Down")
						;
					else {
						direction = "Up";
						snake.setSnakeSpeed(-incrament);
					}
				}
				if (evt.getKeyCode() == KeyEvent.VK_P) { // This pauses the game
					gameThread.suspend();
					gameStatus = false;
				} else if (evt.getKeyCode() == KeyEvent.VK_L) { // This
																// un-pauses
					gameThread.resume();
					gameStatus = true;
				}
				/* This will get you from one level to the next */
				if (level == 0) {
					if (evt.getKeyCode() == KeyEvent.VK_W)
						gameMode = "Wumbo";
					if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
						score = 0;
						level++;
						direction = "Right";
						snake.setSnakeSpeed(incrament);
						gameThread.start();
					}
				}
				if ((level == 1 && score == 10) || (level == 2 && score == 30) || (level == 3 && score == 40)) {
					if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
						snake.setScore(0);
						score = 0;
						level++;
						direction = "null";
						gameThread.resume();
					} else if (level == 3 && (evt.getKeyCode() == KeyEvent.VK_ESCAPE)) {
						snake.setScore(0);
						score = 0;
						level = 0;
						direction = "null";
						gameThread.resume();
					}
				} else if (status == "dead") {
					highScoreList.setNewHighScore(highScore, winnerInitials, totalScore);
					
					highScore1 = highScoreList.getSortedHighScores();
					
					if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
						//winnerInitials = highScoreList.getInitials();
						score = 0;
						snake.setScore(0);
						totalScore = 0;
						level = 0;
						direction = "null";
						initials = null;
						status = "alive";
						snake.setSnakeSize(5);
						bonusSnake.setSnakeSize(5);
						for (int i = 0; i < snake.getSnakeSize(); i++) {
							snakeX[i] = 200 - (i * 20);
							snakeY[i] = 200;
						}
						repaint();
						gameThread.resume();
						highScoreList.again();
					}
				}
			}
		});

		initialsInput();

		// Here we sort the current high scores from the ones that were entered
		// after death
		highScoreList.sortHighScores(highScore);
		highScore1 = highScoreList.getSortedHighScores();
	}

	JButton submit = new JButton("Submit");
	JTextField init = new JTextField("enter initials here");
	String winnerInitials;

	public void initialsInput() {
			Font font = new Font("bauhaus 93", 1, 20);
			submit.setBackground(Color.BLACK);
			submit.setForeground(Color.green.darker());
			submit.setFont(font);

			init.setBackground(Color.BLACK);
			init.setForeground(Color.green.darker());
			init.setHorizontalAlignment(JTextField.CENTER);
			init.setFont(font);
			setLayout(null);

			add(init);
			add(submit);
			init.setBounds(875, 375, 200, 60);
			submit.setBounds(875, 450, 200, 30);
			submit.setFocusable(false);
			
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					winnerInitials = init.getText().substring(0, 3);
					if(e.getActionCommand() == "Submit") 
						requestFocus();
					System.out.println(e.getActionCommand());
					System.out.println(winnerInitials);
				}
			});
	}

	Thread gameThread = new Thread() {
		@SuppressWarnings("deprecation")
		public void run() {
			while (true) {
				// This will change the point of one snake node to the previous
				// one until all of the snake nodes have gone through
				if (direction != "null") {
					for (int i = snake.getSnakeSize(); i > 0; i--) {
						snakeX[i] = snakeX[i - 1];
						snakeY[i] = snakeY[i - 1];
					}
					if (level == 4) {
						for (int i = bonusSnake.getSnakeSize(); i > 0; i--) {
							bonusX[i] = bonusX[i - 1];
							bonusY[i] = bonusY[i - 1];
						}
					}
				}
				status = "alive";
				snake.setDirection(direction, snakeX[0], snakeY[0]); // This will change the direction of the snake
				snakeX[0] = snake.getX();
				snakeY[0] = snake.getY();
				if (level == 4) {
					bonusSnake.setDirection(bonusD, bonusX[0], bonusY[0]);
					bonusX[0] = bonusSnake.getX();
					bonusY[0] = bonusSnake.getY();
				}
				snake.stopHittingUrself(snakeX[0], snakeY[0], snakeX, snakeY);
				status = snake.getStatus();
				snake.setStatus("alive");
				// Checks if the snake impacts something
				snake.outOfBounds(snakeX[0], snakeY[0], level, score, totalScore, incrament, direction);
				// Checks if the snake ate the apple
				snake.munchMunch(snakeX[0], snakeY[0], 0, apple.getAppleX(), apple.getAppleY(), level, score,
						totalScore);
				score = snake.getScore();
				totalScore = snake.getTotalScore();
				direction = snake.getDirection();
				if (snake.getNewApple() == true) {
					apple.apple(level);
					snake.newApple(false);
				}

				if (level == 4) {
					if (bonusX[0] >= apple.getAppleX()) { // This makes the
															// snake move
															// towards the apple
						if (bonusX[0] == apple.getAppleX()) {
							if (bonusY[0] > apple.getAppleY()) {
								bonusD = "Up";
								bonusSnake.setSnakeSpeed(-bonusInc);
							} else {
								bonusD = "Down";
								bonusSnake.setSnakeSpeed(bonusInc);
							}
						} else {
							bonusD = "Left";
							bonusSnake.setSnakeSpeed(-bonusInc);
						}
					} else if (bonusX[0] <= apple.getAppleX()) {
						if (bonusX[0] == apple.getAppleX()) {
							if (bonusY[0] > apple.getAppleY()) {
								bonusD = "Down";
								bonusSnake.setSnakeSpeed(bonusInc);
							} else {
								bonusD = "Up";
								bonusSnake.setSnakeSpeed(-bonusInc);
							}
						} else {
							bonusD = "Right";
							bonusSnake.setSnakeSpeed(bonusInc);
						}
					}
					bonusSnake.outOfBounds(bonusX[0], bonusY[0], level, score, totalScore, bonusInc, bonusD);
					bonusSnake.munchMunch(bonusX[0], bonusY[0], 1, apple.getAppleX(), apple.getAppleY(), level, score,
							totalScore);
					if (bonusSnake.getNewApple() == true) {
						apple.apple(level);
						bonusSnake.newApple(false);
					}
					bonusD = bonusSnake.getDirection();
				}
				repaint();
				if (level == 1 && score == 10 || level == 2 && score == 30 || level == 3 && score == 40) {
					suspend();
					repaint();
					snake.setInitialConds();
					apple.apple(level);
					for (int i = 0; i < snake.getSnakeSize(); i++) {
						snakeX[i] = 200 - (i * 20);
						snakeY[i] = 120;
					}
				} else if (status == "dead") {
					suspend();
					repaint();
				}
				try {
					Thread.sleep(time);
				} catch (InterruptedException ex) {
				}
			}
		}
	};

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Random randy = new Random();
		float red = (float) (randy.nextFloat() / 2f + 0.5);
		float green = (float) (randy.nextFloat() / 2f + 0.5);
		float blue = (float) (randy.nextFloat() / 2f + 0.5);
		Color randomColor0 = new Color(red, green, blue);
		red = (float) (randy.nextFloat() / 2f + 0.5);
		green = (float) (randy.nextFloat() / 2f + 0.5);
		blue = (float) (randy.nextFloat() / 2f + 0.5);
		Color randomColor1 = new Color(red, green, blue);
		red = (float) (randy.nextFloat() / 2f + 0.5);
		green = (float) (randy.nextFloat() / 2f + 0.5);
		blue = (float) (randy.nextFloat() / 2f + 0.5);
		Color randomColor2 = new Color(red, green, blue);
		g.setColor(Color.green.darker());
		if (gameMode == "Wumbo") // This is for party mode
			g.setColor(randomColor0);
		for (int i = 0; i < 72; i++) { // This is the border wall
			g.fillRect(9, border[i] + 79, 10, 10);
			g.fillRect(800, border[i] + 79, 10, 10);
			g.fillRect(820, border[i] + 79, 10, 10);
			g.fillRect(1120, border[i] + 79, 10, 10);
		}
		for (int i = 0; i < 31; i++) {
			g.fillRect(border[i] + 810, 800, 10, 10);
			g.fillRect(border[i] + 810, 89, 10, 10);
		}
		for (int i = 0; i < 80; i++) {
			g.fillRect(border[i], 800, 10, 10);
			g.fillRect(border[i], 89, 10, 10);
		}

		if (level == 2) {
			for (int i = 0; i < 20; i++) { // Border wall to the right
				g.fillRect(border[i] + 490, 300, 9, 9);// outside top
				g.fillRect(690, border[i] + 290, 9, 9); // outside right
			}
			for (int i = 0; i < 17; i++) {
				g.fillRect(border[i] + 490, 330, 9, 9); // inside top
				g.fillRect(660, border[i] + 320, 9, 9); // inside right
			}
			for (int i = 0; i < 4; i++) {
				g.fillRect(border[i] + 650, 490, 9, 9); // Right walls v
				g.fillRect(500, border[i] + 290, 9, 9);
				g.fillRect(border[i] + 170, 300, 9, 9); // Left wall v
				g.fillRect(border[i] + 170, 610, 9, 9);
			}
			for (int i = 0; i < 32; i++) { // Border wall to the left
				g.fillRect(210, border[i] + 290, 9, 9); // right outside
				g.fillRect(180, border[i] + 290, 9, 9); // left outside
			}
			g.setColor(Color.green);
			if (gameMode == "Wumbo")
				g.setColor(randomColor1);
			for (int i = 0; i < 17; i++) {
				g.fillRect(border[i] + 500, 310, 19, 19);
				g.fillRect(670, border[i] + 300, 19, 19);
			}
			for (int i = 0; i < 29; i++)
				g.fillRect(190, border[i] + 300, 19, 19);
		}
		if (level == 3) {
			g.setColor(Color.green.darker());
			if (gameMode == "Wumbo")
				g.setColor(randomColor0);
			for (int i = 0; i < 50; i++) {
				g.fillRect(border[i], 200, 9, 9); // Left side 1
				g.fillRect(border[i], 230, 9, 9);
				g.fillRect(border[i], 600, 9, 9); // Left side 2
				g.fillRect(border[i], 630, 9, 9);
				g.fillRect(border[i] + 290, 400, 9, 9); // Right side
				g.fillRect(border[i] + 290, 430, 9, 9);
			}
			for (int i = 0; i < 4; i++) {
				g.fillRect(510, border[i] + 190, 9, 9);
				g.fillRect(300, border[i] + 390, 9, 9);
				g.fillRect(510, border[i] + 590, 9, 9);
			}
			g.setColor(Color.green);
			if (gameMode == "Wumbo")
				g.setColor(randomColor1);
			for (int i = 0; i < 48; i++) {
				g.fillRect(border[i] + 10, 210, 19, 19);
				g.fillRect(border[i] + 10, 610, 19, 19);
				g.fillRect(border[i] + 300, 410, 19, 19);
			}
		}

		if (gameMode == "regular")
			g.setColor(Color.WHITE);
		for (int i = 0; i < snake.getSnakeSize(); i++)
			g.fillRect(snakeX[i], snakeY[i], 19, 19);
		//
		if (level == 4) {
			g.setColor(Color.red);
			for (int i = 0; i < bonusSnake.getSnakeSize(); i++)
				g.fillRect(bonusX[i], bonusY[i], 19, 19);
		}

		// draws the apple
		g.setColor(Color.red);
		g.fillRect(apple.getAppleX(), apple.getAppleY(), 19, 19);

		// draws the boxes on the top
		if (gameMode == "regular")
			g.setColor(Color.green);
		else
			g.setColor(randomColor2);
		g.fillRect(10, 10, 800, 70);
		g.fillRect(820, 10, 310, 70);

		// draws the words in the box
		Font titleFont = new Font("bauhaus 93", 1, 50);
		if (gameMode == "regular")
			g.setColor(Color.green.darker());
		else
			g.setColor(Color.white);
		g.setFont(titleFont);
		FontMetrics fm2 = g.getFontMetrics();
		g.drawString("SNAKE", (820 - fm2.stringWidth("SNAKE")) / 2, 65);

		Font highScoreFont = new Font("bauhaus 93", 1, 40);
		g.setFont(highScoreFont);
		fm2 = g.getFontMetrics();
		g.drawString("HIGH SCORES", (getWidth() - fm2.stringWidth("HIGH SCORES") + 810) / 2, 60);

		// draws the score
		Font scoreFont = new Font("bauhaus 93", 1, 20);
		g.setFont(scoreFont);
		g.drawString("SCORE: " + score, 560, 60);
		scoreFont = new Font("bauhaus 93", 1, 40);
		g.setFont(scoreFont);
		for (int i = 0; i < 5; i++) {
			g.drawString(highScore1[i], 865, 150 + (i * 50));

		}

		// This will display messages for when the player wins a level or looses
		Font gameOver = new Font("bauhaus 93", 1, 60);
		Font nextLevel = new Font("bauhaus 93", 1, 30);
		String spaceBar = "CLICK SPACE BAR TO GO TO NEXT LEVEL";
		String escapeKey = "CLICK ESCAPE KEY TO GO TO MAIN MENU";
		if ((level == 1 && score == 10) || (level == 2 && score == 30) || (level == 3 && score == 40)) {
			g.setFont(gameOver);
			FontMetrics fm = g.getFontMetrics();
			g.drawString("YOU WIN", (820 - fm.stringWidth("YOU WIN")) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getAscent() - 50);
			g.setFont(nextLevel);
			FontMetrics fm1 = g.getFontMetrics();
			g.drawString(spaceBar, (820 - fm1.stringWidth(spaceBar)) / 2,
					((getHeight() - fm1.getHeight()) / 2) + fm1.getAscent() + 50);
			if (level == 3) {
				g.drawString(escapeKey, (820 - fm1.stringWidth(escapeKey)) / 2,
						((820 - fm1.getHeight()) / 2) + fm1.getAscent() + 10);
			}
		} else if (level == 0) {
			g.setFont(gameOver);
			FontMetrics fm = g.getFontMetrics();
			g.drawString("CREATED BY", (820 - fm.stringWidth("CREATED BY")) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getAscent() - 50);
			g.setFont(nextLevel);
			FontMetrics fm1 = g.getFontMetrics();
			g.drawString("THE RAAZ KHOSHNOOD FAN CLUB", (820 - fm1.stringWidth("THE RAAZ KHOSHNOOD FAN CLUB")) / 2,
					((getHeight() - fm1.getHeight()) / 2) + fm1.getAscent() + 20);
			g.drawString("SPACE BAR TO PLAY", (820 - fm1.stringWidth("SPACE BAR TO PLAY")) / 2,
					((getHeight() - fm1.getHeight()) / 2) + fm1.getAscent() + 50);
		} else if (status == "dead") {
			g.setFont(gameOver);
			FontMetrics fm = g.getFontMetrics();
			g.drawString("YOU LOSE", (820 - fm.stringWidth("YOU LOSE")) / 2,
					((getHeight() - fm.getHeight()) / 2) + fm.getAscent() - 50);
			g.setFont(nextLevel);
			FontMetrics fm1 = g.getFontMetrics();
			g.drawString(escapeKey, (820 - fm1.stringWidth(escapeKey)) / 2,
					((820 - fm1.getHeight()) / 2) + fm1.getAscent() + 50);
		}
	}

}
