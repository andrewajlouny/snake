package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePlay extends JPanel {
    final int X = 0, Y = 1;
    int[] border = new int[100];
    String direction = new String(),
        bonusDir = "Right";
    int level = 0,
        score = 0,
        totalScore = 0,
        increment = 20;
    long time = 85;

    String gameMode = "regular",
        status = "alive";
    Boolean gameStatus = true;

    Snake snake = new Snake();
    Snake bonus = new Snake(200, 220);
    Apple apple = new Apple();
    Scores hsList = new Scores(true);

    JButton submit = new JButton("Submit");
    JTextField init = new JTextField("enter initials here");
    String tmpinit;

    public GamePlay() {
        apple.newApple(level);
        setSize(820, 820);
        setBackground(Color.BLACK);
        // Reference point for when the walls get painted
        for (int i = 0; i < 100; i++) {
            border[i] = ((i + 1) * 10);
        }
        makeIO();
        addKeyListener(new KeyAdapter() {
            @SuppressWarnings("deprecation")
            public void keyPressed(KeyEvent evt) {
                // This lets the game know what direction the snake goes
                switch(evt.getKeyCode()) {
                    case KeyEvent.VK_SPACE:
                        if(level == 0) {
                            direction = "Right";
                            snake.setSpeed(increment);
                            score = 0;
                            level = 4;
                            gameThread.start();
                        } else if ((level == 1 && score == 10) || (level == 2 && score == 30) || (level == 3 && score == 40)) {
                            score = 0;
                            level++;
                            direction = "null";
                            gameThread.resume();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (snake.pos[Y][0] >= 780 || direction == "Up")
                            ;
                        else {
                            direction = "Down";
                            snake.setSpeed(increment);
                        }
                        break;
                    case KeyEvent.VK_LEFT:
                        if (snake.pos[X][0] <= 20 || direction == "Right")
                            ;
                        else {
                            direction = "Left";
                            snake.setSpeed(-increment);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (snake.pos[X][0] >= 780 || direction == "Left")
                            ;
                        else {
                            direction = "Right";
                            snake.setSpeed(increment);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if (snake.pos[Y][0] <= 110 || direction == "Down")
                            ;
                        else {
                            direction = "Up";
                            snake.setSpeed(-increment);
                        }
                        break;
                    case KeyEvent.VK_P: // This pauses the game
                        gameThread.suspend();
                        gameStatus = false;
                        break;
                    case KeyEvent.VK_L: // This un-pauses
                        gameThread.resume();
                        gameStatus = true;
                        break;
                    case KeyEvent.VK_W:
                        gameMode = "Wumbo";
                        break;
                    case KeyEvent.VK_ESCAPE:
                        if(level == 4) {
                            score = 0;
                            level = 0;
                            direction = "null";
                            gameThread.resume();
                        } else if(status == "dead") {
                            resetGame();
                            repaint();
                            gameThread.resume();
                        }
                        break;
                }
            }
        });
    }

    public void resetGame() {
        level = 0;
        score = 0;
        totalScore = 0;
        direction = "null";
        snake.reset();
        bonus.reset();
    }

    Thread gameThread = new Thread() {
        @SuppressWarnings("deprecation")
        public void run() {
            while (true) {
                // This will change the point of one snake node to the previous
                // one until all of the snake nodes have gone through
                if (direction != "null") {
                    snake.moveBody();
                    if (level == 4) bonus.moveBody();
                }
                status = "alive";

                // Change direction of snake
                snake.setDirection(direction);
                if(level == 4) bonus.setDirection(bonusDir);

                // Check if snake collided with itself
                snake.stopHittingUrself();
                status = snake.getStatus();
                snake.setStatus("alive");

                // Check if snake collided wall
                if(snake.outOfBounds(level, increment, direction))
                    score = score - 2;

                // Checks if snake ate the apple
                if(snake.munchMunch(apple.getAppleX(), apple.getAppleY())) {
                    apple.newApple(level);
                    if(level == 1) score = score + 1;
                    else score = score + 2;
                }

                if (level == 4) bonusSnakeActivate();

                direction = snake.getDirection();
                repaint();
                if (level == 1 && score == 10 || level == 2 && score == 30 || level == 3 && score == 40) {
                    totalScore = totalScore + score;
                    suspend();
                    repaint();
                    snake.newLevel();
                    apple.newApple(level);
                } else if (status == "dead") {
                    totalScore = totalScore + score;
                    submitScore();
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

    public void bonusSnakeActivate() {
        // If the snake at the same x position of the apple
        if(bonus.pos[X][0] == apple.getAppleX()) {
            // If the snake is below the apple, move up
            if (bonus.pos[Y][0] > apple.getAppleY()) {
                bonusDir = "Up";
                bonus.setSpeed(-increment);
                // If the snake is above the apple, move down
            } else {
                bonusDir = "Down";
                bonus.setSpeed(increment);
            }
        }
        // If the snake is to the right of the apple move left
        else if (bonus.pos[X][0] > apple.getAppleX()) {
            bonusDir = "Left";
            bonus.setSpeed(-increment);
        }
        // If the snake is to the left of the apple
        else if (bonus.pos[X][0] <= apple.getAppleX()) {
            bonusDir = "Right";
            bonus.setSpeed(increment);
        }

        bonus.outOfBounds(level, increment, bonusDir);
        bonusDir = bonus.getDirection();
        if(bonus.munchMunch(apple.getAppleX(), apple.getAppleY()))
            apple.newApple(level);
    }

    public void makeIO() {
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
        init.setVisible(false);
        submit.setVisible(false);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tmpinit = init.getText().substring(0, 3);
                if(e.getActionCommand() == "Submit")
                    requestFocus();
                System.out.println(e.getActionCommand());
                System.out.println(tmpinit);
                hsList.setInitials(tmpinit);
                hsList.setScore(totalScore);
                hsList.addToScoreSheet();
                submit.setVisible(false);
                init.setVisible(false);
                init.setText("enter initials here");
                hsList.updateList();
            }
        });
    }

    public void submitScore() {
        init.setVisible(true);
        submit.setVisible(true);
    }

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

        // paints walls and obstacles
        paintBorder(g, randomColor0, randomColor2);

        if (level == 2)
            paintObstaclesLevel2(g, randomColor0, randomColor1);
        if (level == 3)
            paintObstaclesLevel3(g, randomColor0, randomColor1);

        // paints the snake
        if (gameMode == "regular")
            snake.paint(g, Color.white);
        else
            snake.paint(g, randomColor0);
        if (level == 4)
            bonus.paint(g, Color.red);

        // draws the apple
        apple.paint(g, Color.red);

        g.setColor(Color.green);
        // This will display messages for when the player wins a level or looses

        paintMessages(g);
    }

    public void paintMessages(Graphics g) {
        Font gameOver = new Font("bauhaus 93", 1, 60);
        Font nextLevel = new Font("bauhaus 93", 1, 30);
        String teamName = "THE RAAZ KHOSHNOOD FAN CLUB";
        String spaceBar = "CLICK SPACE BAR TO GO TO NEXT LEVEL";
        String escapeKey = "CLICK ESCAPE KEY TO GO TO MAIN MENU";
        if(gameMode == "Wumbo")
            g.setColor(Color.white);
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
        }
        else if (level == 0) {
            g.setFont(gameOver);
            FontMetrics fm = g.getFontMetrics();
            g.drawString("CREATED BY", (820 - fm.stringWidth("CREATED BY")) / 2,
                    ((getHeight() - fm.getHeight()) / 2) + fm.getAscent() - 50);
            g.setFont(nextLevel);
            FontMetrics fm1 = g.getFontMetrics();
            g.drawString(teamName, (820 - fm1.stringWidth(teamName)) / 2,
                    ((getHeight() - fm1.getHeight()) / 2) + fm1.getAscent() + 20);
            g.drawString("SPACE BAR TO PLAY", (820 - fm1.stringWidth("SPACE BAR TO PLAY")) / 2,
                    ((getHeight() - fm1.getHeight()) / 2) + fm1.getAscent() + 50);
        }
        else if (status == "dead") {
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

    public void paintBorder(Graphics g, Color random, Color random2) {
        if (gameMode == "Wumbo") // This is for party mode
            g.setColor(random);
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

        // draws the boxes on the top
        if (gameMode == "regular")
            g.setColor(Color.green);
        else
            g.setColor(random2);
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
            g.drawString(hsList.displayList[i], 865, 150 + (i * 50));

        }
    }

    public void paintObstaclesLevel2(Graphics g, Color random0, Color random1) {
        if (gameMode == "Wumbo")
            g.setColor(random0);
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
            g.setColor(random1);
        for (int i = 0; i < 17; i++) {
            g.fillRect(border[i] + 500, 310, 19, 19);
            g.fillRect(670, border[i] + 300, 19, 19);
        }
        for (int i = 0; i < 29; i++)
            g.fillRect(190, border[i] + 300, 19, 19);
    }

    public void paintObstaclesLevel3(Graphics g, Color random0, Color random1) {
        g.setColor(Color.green.darker());
        if (gameMode == "Wumbo")
            g.setColor(random0);
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
            g.setColor(random1);
        for (int i = 0; i < 48; i++) {
            g.fillRect(border[i] + 10, 210, 19, 19);
            g.fillRect(border[i] + 10, 610, 19, 19);
            g.fillRect(border[i] + 300, 410, 19, 19);
        }
    }
}
