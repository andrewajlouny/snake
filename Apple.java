package snake;

import java.util.Random;

public class Apple {
	int appleX;
	int appleY;

	public void apple(int level) {
		Random randy = new Random();
		if (level == 1 || level == 0 || level == 4) {
			appleX = (randy.nextInt(39 - 1) + 1) * 20;
			appleY = (randy.nextInt(39 - 5) + 5) * 20;
		} else if (level == 2) {
			do {
				appleX = (randy.nextInt(39 - 1) + 1) * 20;
				appleY = (randy.nextInt(39 - 5) + 5) * 20;
			} while ((((appleX >= 620 && appleX <= 700) && (appleY >= 300 && appleY <= 480))
					|| ((appleX >= 470 && appleX <= 700) && (appleY >= 300 && appleY <= 320))
					|| ((appleX <= 220 && appleX >= 160) && (appleY >= 300 && appleY <= 610))));
		} else if (level == 3) {
			do {
				appleX = (randy.nextInt(39 - 1) + 1) * 20;
				appleY = (randy.nextInt(39 - 5) + 5) * 20;
			} while ((((appleX >= 0 && appleX <= 520) && (appleY >= 180 && appleY <= 220))
					|| ((appleX >= 0 && appleX <= 520) && (appleY >= 580 && appleY <= 620))
					|| ((appleX <= 800 && appleX >= 260) && (appleY >= 380 && appleY <= 420))));
		}
	}


	public int getAppleX() {
		return appleX;
	}

	public int getAppleY() {
		return appleY;
	}
}
