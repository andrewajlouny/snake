package snake;

public class Snake {
	int snakeSpeed = 20;
	int snakeSize = 5;
	String direction;
	int snakeX = 0;
	int snakeY = 0;
	
	String status;

	int score;
	int totalScore;
	Boolean newApple = false;

	public void setSnakeSize(int x) {
		snakeSize = x;
	}

	public void setSnakeSpeed(int x) {
		snakeSpeed = x;
	}

	public void setInitialConds() {
		snakeSpeed = 20;
		snakeSize = 5;
	}

	public void setDirection(String d, int x, int y) {
		direction = d;
		snakeX = x;
		snakeY = y;
		if (direction == "Right")
			snakeX += snakeSpeed;
		else if (direction == "Left")
			snakeX += snakeSpeed;
		else if (direction == "Up")
			snakeY += snakeSpeed;
		else if (direction == "Down")
			snakeY += snakeSpeed;
		else if (direction == "null") {
			snakeX += 0;
			snakeY += 0;
		}
	}
	
	public void stopHittingUrself(int X, int Y, int[] snakeX, int[] snakeY) {
		for (int i = getSnakeSize(); i > 0; i--) {
			if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
				status = "dead";
			}
		}
	}

	public void outOfBounds(int x, int y, int level, int s, int totalS, int incrament, String direc) {
		snakeX = x;
		snakeY = y;
		direction = direc;
		if (x == 780 && direction == "Right") {
			direction = "Up";
			snakeSpeed = -incrament;
			if (level != 1) {
				totalScore = totalS - 2;
				score = s - 2;
			}
		} else if (x == 20 && direction == "Left") {
			direction = "Down";
			snakeSpeed = incrament;
			if (level != 1) {
				totalScore = totalS - 2;
				score = s - 2;
			}
		}

		if (y <= 105 && direction == "Up") {
			direction = "Left";
			snakeSpeed = -incrament;
			if (level != 1) {
				totalScore = totalS - 2;
				score = s - 2;
			}
		} else if (y >= 780 && direction == "Down") {
			direction = "Right";
			snakeSpeed = incrament;
			if (level != 1) {
				totalScore = totalS - 2;
				score = s - 2;
			}
		}
		if (level == 2) {
			if (direction == "Right" || direction == "Left") {
				if ((((x >= 640 && x <= 700) && (y >= 320 && y <= 480))
						|| ((x >= 470 && x <= 700) && (y >= 300 && y <= 320))
						|| ((x <= 220 && x >= 160) && (y >= 300 && y <= 610)))) {
					direction = "Up";
					snakeSpeed = -incrament;
					totalScore = totalS - 2;
					score = s - 2;
				}
			}
			if (direction == "Up" || direction == "Down") {
				if ((((x >= 650 && x <= 680) && (y >= 300 && y <= 500))
						|| ((x >= 490 && x <= 680) && (y >= 280 && y <= 340))
						|| ((x <= 210 && x >= 170) && (y >= 280 && y <= 620)))) {
					direction = "Left";
					snakeSpeed = -incrament;
					totalScore = totalS - 2;
					score = s - 2;
				}
			}
		} else if (level == 3) {
			if (direction == "Up" || direction == "Down") {
				if (((((x >= 0 && x <= 500) && (y >= 180 && y <= 240))
						|| ((x >= 0 && x <= 500) && (y >= 580 && y <= 640))))) {
					direction = "Right";
					snakeSpeed = incrament;
					totalScore = totalS - 2;
					score = s - 2;
				} else if ((x >= 290 && x <= 790) && (y >= 380 && y <= 440)) {
					direction = "Left";
					snakeSpeed = -incrament;
					totalScore = totalS - 2;
					score = s - 2;
				}
			}
			if (direction == "Right" || direction == "Left") {
				if (x == 20 && direction == "Left")
					direction = "Up";
				else if (((((x >= 0 && x <= 520) && (y >= 190 && y <= 230))
						|| ((x >= 0 && x <= 520) && (y >= 590 && y <= 630))
						|| ((x >= 280 && x <= 790) && (y >= 390 && y <= 430))))) {
					direction = "Up";
					snakeSpeed = -incrament;
					totalScore = totalS - 2;
					score = s - 2;
				}
			}
		}
	}
	
	public void setScore(int s) {
		score = s;
	}

	public void munchMunch(int X, int Y, int T, int appX, int appY, int level, int sco, int totalSco) {
		if (((X > appX - 20) && (X < appX + 20)) && ((Y > appY - 20) && (Y < appY + 20))) {
			if (level == 4) {
				if (T == 1) {
					newApple = true;
					setSnakeSize(getSnakeSize() + 1);
				} else if (T == 0) {
					score = sco + 1;
					totalScore = totalSco + 1;
					newApple = true;
					setSnakeSize(getSnakeSize() + 1);
				}
			} else if (level == 1) {
				score = sco + 1;
				totalScore = totalSco + 1;
				newApple = true;
				setSnakeSize(getSnakeSize() + 1);
			} else {
				score = sco + 2;
				totalScore = totalSco + 2;
				// time -= 1;
				newApple = true;
				setSnakeSize(getSnakeSize() + 1);
			}
		}
	}

	public void newApple(Boolean x) {
		newApple = x;
	}
	

	public Boolean getNewApple() {
		return newApple;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String x) {
		status = x;
	}

	public int getScore() {
		return score;
	}

	public int getTotalScore() {
		return totalScore;
	}

	public int getX() {
		return snakeX;
	}

	public int getY() {
		return snakeY;
	}

	public String getDirection() {
		return direction;
	}

	public int getSnakeSpeed() {
		return snakeSpeed;
	}

	public int getSnakeSize() {
		return snakeSize;
	}
}
