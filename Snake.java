package com.company;

import java.awt.*;

public class Snake {
    int X = 0, Y = 1, initX = 200, initY = 120;
    int speed, size;
    String dir;
    int[][] pos = new int[100][100];
    String status;

    public Snake() {
        speed = 20;
        size = 5;
        for (int i = 0; i < size; i++) {
            pos[X][i] = initX - (i * 20);
            pos[Y][i] = initY;
        }
    }

    public Snake(int x, int y) {
        size = 5;
        speed = 20;
        initX = x;
        initY = y;
        for (int i = 0; i < size; i++) {
            pos[X][i] = initX - (i * 20);
            pos[Y][i] = initY;
        }
    }

    public void moveBody() {
        for (int i = this.getSize(); i > 0; i--) {
            pos[X][i] = pos[X][i - 1];
            pos[Y][i] = pos[Y][i - 1];
        }
    }

    public void setSize(int n) {
        size = n;
    }

    public void setSpeed(int x) {
        speed = x;
    }

    public void newLevel() {
        speed = 20;
        size = 5;
        for (int i = 0; i < size; i++) {
            pos[X][i] = 200 - (i * 20);
            pos[Y][i] = 120;
        }
    }

    public void setDirection(String d) {
        dir = d;
        if (dir == "Right")
            pos[X][0] += speed;
        else if (dir == "Left")
            pos[X][0] += speed;
        else if (dir == "Up")
            pos[Y][0] += speed;
        else if (dir == "Down")
            pos[Y][0] += speed;
        else if (dir == "null") {
            pos[X][0] += 0;
            pos[Y][0] += 0;
        }
    }

    public void stopHittingUrself() {
        for (int i = getSize(); i > 0; i--) {
            if (pos[X][0] == pos[X][i] && pos[Y][0] == pos[Y][i]) {
                status = "dead";
            }
        }
    }

    public Boolean outOfBounds(int level, int incrament, String direc) {
        dir = direc;
        if (pos[X][0] == 780 && dir == "Right") {
            dir = "Up";
            speed = -incrament;
            if (level != 1) {
                return true;
            }
        } else if (pos[X][0] == 20 && dir == "Left") {
            dir = "Down";
            speed = incrament;
            if (level != 1) {
                return true;
            }
        }

        if (pos[Y][0] <= 105 && dir == "Up") {
            dir = "Left";
            speed = -incrament;
            if (level != 1) {
                return true;
            }
        } else if (pos[Y][0] >= 780 && dir == "Down") {
            dir = "Right";
            speed = incrament;
            if (level != 1) {
                return true;
            }
        }
        if (level == 2) {
            if (dir == "Right" || dir == "Left") {
                if ((((pos[X][0] >= 640 && pos[X][0] <= 700) && (pos[Y][0] >= 320 && pos[Y][0] <= 480))
                        || ((pos[X][0] >= 470 && pos[X][0] <= 700) && (pos[Y][0] >= 300 && pos[Y][0] <= 320))
                        || ((pos[X][0] <= 220 && pos[X][0] >= 160) && (pos[Y][0] >= 300 && pos[Y][0] <= 610)))) {
                    dir = "Up";
                    speed = -incrament;
                    return true;
                }
            }
            if (dir == "Up" || dir == "Down") {
                if ((((pos[X][0] >= 650 && pos[X][0] <= 680) && (pos[Y][0] >= 300 && pos[Y][0] <= 500))
                        || ((pos[X][0] >= 490 && pos[X][0] <= 680) && (pos[Y][0] >= 280 && pos[Y][0] <= 340))
                        || ((pos[X][0] <= 210 && pos[X][0] >= 170) && (pos[Y][0] >= 280 && pos[Y][0] <= 620)))) {
                    dir = "Left";
                    speed = -incrament;
                    return true;
                }
            }
        } else if (level == 3) {
            if (dir == "Up" || dir == "Down") {
                if (((((pos[X][0] >= 0 && pos[X][0] <= 500) && (pos[Y][0] >= 180 && pos[Y][0] <= 240))
                        || ((pos[X][0] >= 0 && pos[X][0] <= 500) && (pos[Y][0] >= 580 && pos[Y][0] <= 640))))) {
                    dir = "Right";
                    speed = incrament;
                    return true;
                } else if ((pos[X][0] >= 290 && pos[X][0] <= 790) && (pos[Y][0] >= 380 && pos[Y][0] <= 440)) {
                    dir = "Left";
                    speed = -incrament;
                    return true;
                }
            }
            if (dir == "Right" || dir == "Left") {
                if (pos[X][0] == 20 && dir == "Left")
                    dir = "Up";
                else if (((((pos[X][0] >= 0 && pos[X][0] <= 520) && (pos[Y][0] >= 190 && pos[Y][0] <= 230))
                        || ((pos[X][0] >= 0 && pos[X][0] <= 520) && (pos[Y][0] >= 590 && pos[Y][0] <= 630))
                        || ((pos[X][0] >= 280 && pos[X][0] <= 790) && (pos[Y][0] >= 390 && pos[Y][0] <= 430))))) {
                    dir = "Up";
                    speed = -incrament;
                    return true;
                }
            }
        }
        return false;
    }

    public String getDirection() {
        return dir;
    }

    public Boolean munchMunch(int appX, int appY) {
        if (((pos[X][0] > appX - 20) && (pos[X][0] < appX + 20)) && ((pos[Y][0] > appY - 20) && (pos[Y][0] < appY + 20))) {
            setSize(getSize() + 1);
            return true;
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String x) {
        status = x;
    }

    public int getSize() {
        return size;
    }

    public void reset() {
        size = 5;
        speed = 20;
        dir = "null";
        status = "alive";
        for (int i = 0; i < size; i++) {
            pos[X][i] = initX - (i * 20);
            pos[Y][i] = initY;
        }
    }

    public void paint(Graphics g,Color color) {
        g.setColor(color);
        for (int i = 0; i < getSize(); i++)
            g.fillRect(pos[X][i], pos[Y][i], 19, 19);
    }
}
