package snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Scores extends JFrame  {
	JFrame frame = new JFrame("Hello Player");
	JButton submit = new JButton("Submit");
	JTextField initials = new JTextField("Enter initials here");
	String winnerInitials;
	
	String[] finalizedList = new String[6];
	String[] tempList = new String[6];

	public void setNewHighScore(String[] highScore, String inits, int score) {
		String fileName = "C:/Users/Andrew/workspace/Snake1.1/src/snake/highScores.txt";
		String line = null;
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				tempList[i] = line;
				i++;
			}
			bufferedReader.close();
			tempList[5] = "6. " + inits + " " + score;

			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (i = 0; i < 6; i++) {
				bufferedWriter.write(tempList[i]);
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}

		try {
			FileReader fileReader1 = new FileReader(fileName);
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			int i = 0;
			while ((line = bufferedReader1.readLine()) != null) {
				finalizedList[i] = line;
				i++;
			}
			bufferedReader1.close();
		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}
		sortHighScores(finalizedList);
	}

	public void sortHighScores(String[] highScore) {
		String fileName = "C:/Users/Andrew/workspace/Snake1.1/src/snake/highScores.txt";
		String line = null;
		int[] highScoreVal = new int[6];

		// Here we will pull all the high score data from the text file
		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			int i = 0;
			while ((line = bufferedReader.readLine()) != null) {
				highScore[i] = line;
				i++;
			}
			for (i = 0; i < 6; i++) {
				highScoreVal[i] = Integer.parseInt(highScore[i].substring(7));
			}

			// This will sort the numerical values of the high score pulled out
			// from above
			highScoreVal = sortingThing(highScoreVal);

			String[] tempHighScore = new String[6];
			for (i = 0; i < highScoreVal.length; i++) {
				for (int j = 0; j < 6; j++) {
					// Here if the value of the original array containing the
					// original score list equals the sorted scores then that
					// string will go into the temp string but in the correct
					// order
					if (Integer.parseInt(highScore[j].substring(7)) == highScoreVal[i]) {
						tempHighScore[i] = highScore[j].substring(3);
					}
				}
			}
			
			//Here we write the sorted values into the text file
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (i = 0; i < 6; i++) {
				bufferedWriter.write(i + 1 + ". " + tempHighScore[i]);
				bufferedWriter.newLine();
			}
			bufferedReader.close();
			bufferedWriter.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
		
		//Here we read the finalized and sorted high score list
		String fileName1 = "C:/Users/Andrew/workspace/Snake1.1/src/snake/highScores.txt";
		String line1 = null;
		try {
			FileReader fileReader1 = new FileReader(fileName1);
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			int i = 0;
			while ((line1 = bufferedReader1.readLine()) != null) {
				finalizedList[i] = line1;
				i++;
			}

			bufferedReader1.close();

		} catch (FileNotFoundException ex) {
		} catch (IOException ex) {
		}
	}

	public static int[] sortingThing(int[] input) {
		for (int i = 1; i < input.length; i++) {
			for (int j = i; j > 0; j--) {
				// This part checks to see if a value is smaller than another
				if (input[j] > input[j - 1]) {
					// If that value is smaller then it puts it before it
					int newValue = input[j];
					input[j] = input[j - 1];
					input[j - 1] = newValue;
				}
			}
		}
		return input;
	}
	
	public String[] getSortedHighScores() {
		return finalizedList;
	}

	public String getInitials() {
		return winnerInitials;

	}

	public void again() {

	}
	
	public static void main(String[] args) {
		
	}
}
