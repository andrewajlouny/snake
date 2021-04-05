package com.company;

import java.io.*;
import javax.swing.*;

public class Scores extends JPanel {
    String winInits;
    int winHS;
    String fileDir = "/Users/andrewajlouny/Desktop/school/spring 2021/EECE1610/Snake/src/com/company/highScores.txt";

    String[][] list;
    String[] displayList;

    public Scores() {
        list = getList();
        displayList = displayList(list);
    }

    public Scores(Boolean reset) {
        list = new String[][]{{"1", "---", "000"},
                {"2", "---", "000"},
                {"3", "---", "000"},
                {"4", "---", "000"},
                {"5", "---", "000"},
                {"6", "---", "000"}};
        displayList = displayList(list);
    }

    public void setInitials(String inits) {
        winInits = inits;
    }

    public String getInitials() {
        return winInits;
    }

    public void setScore(int sco) {
        winHS = sco;
    }

    public String getScore() {
        return String.format("%03d", winHS);
    }

    public String[][] getList() {
        String[][] tmp = new String[6][3];
        try {
            File file = new File(fileDir); // Creates a new file instance
            FileReader fr = new FileReader(file); // Reads the file
            BufferedReader br = new BufferedReader(fr); // Creates a buffering character input stream
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                tmp[i] = line.split("\\.");
                i++;
            }
            br.close();
        } catch (IOException e) {
        }
        return tmp;
    }

    public void updateList() {
        list = getList();
        displayList = displayList(list);
    }

    public void addToScoreSheet() {
        list[5][1] = getInitials();
        list[5][2] = getScore();
        sortHighScores(0,5);
        for(int i = 0; i < 6; i++)
            list[i][0] = Integer.toString(i + 1);
        writeToFile();
    }

    public String[] displayList(String[][] conList) {
        String[] tmp = new String[5];
        for(int i = 0; i < 5; i++)
            tmp[i] = conList[i][0] + "." + conList[i][1] + " " + conList[i][2];
        return tmp;
    }

    public void sortHighScores(int low, int high) {
        if(low < high) {
            int pi = partition(low, high);
            sortHighScores(low, pi - 1);
            sortHighScores(pi + 1, high);
        }
    }

    public int partition(int low, int high) {
        int pivot = getInt(list[high][2]);
        int i = (low - 1);
        for(int j = low; j <= high - 1; j++) {
            if(getInt(list[j][2]) > pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    public void swap(int i, int j) {
        String[] tmp = list[i];
        list[i] = list[j];
        list[j] = tmp;
    }

    public int getInt(String sco) {
        int tmp = 0, fact = 1;
        for(int i = 2; i >= 0; i--) {
            tmp = tmp + Character.getNumericValue(sco.charAt(i)) * fact;
            fact = fact * 10;
        }
        System.out.println(tmp);
        return tmp;
    }

    public void writeToFile() {
        File oldF = new File(fileDir);
        oldF.delete();
        File newF = new File(fileDir);
        try {
            FileWriter fw = new FileWriter(newF, false);
            for(int i = 0; i < 6; i++) {
                fw.write(list[i][0] + "." + list[i][1] + "." + list[i][2] + "\r\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
