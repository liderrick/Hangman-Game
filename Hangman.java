/*
 * Hangman.java
 */

import acm.program.*;
import acm.util.*;

import java.io.*;
import java.util.*;

public class Hangman extends ConsoleProgram {

/** Canvas height offset*/
	private static final int CANVAS_HEIGHT_OFFSET = 0;

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 700;
	public static final int APPLICATION_HEIGHT = 700 + CANVAS_HEIGHT_OFFSET;

/** Dimensions of play area */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT - CANVAS_HEIGHT_OFFSET;

	/** Number of guesses permitted */
	private static final int NUMBER_OF_GUESSES = 8;

	/** Class (or instance) variables */
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvas canvas;

	private String word, answer;
	private int guessRemaining;

	/** Adds canvas to console */
	public void init() {
		canvas = new HangmanCanvas();
		canvas.setSize(WIDTH/2,HEIGHT);
		add(canvas);
		canvas.reset();

		/** The following System.out.println commands were used to troubleshoot GCanvas size issues
		 * and are non-essential for the program to run. It is kept in place for future reference. */
		System.out.println("application width: " + this.getWidth());
		System.out.println("application height: " + this.getHeight());

		System.out.println("canvas object width: " + canvas.getWidth());
		System.out.println("canvas object height: " + canvas.getHeight());
	}

    public static void main(String[] args) {
        new Hangman().start(args);
    }

	/** Runs the hangman program */
    public void run() {
    	initializeGame();
    	wordChooser();
    	playGame();
	}

    /** Setup and initialize game */
    private void initializeGame() {
    	println("Welcome to Hangman!");
    	guessRemaining = NUMBER_OF_GUESSES;
    }

    /** Call on methods to open txt file and read each line into an ArrayList.
     * Then randomly chooses a word from the ArrayList and assigns it to the variable "word". */
    private void wordChooser() {

    	BufferedReader rd = openFileReader("HangmanLexicon.txt");
    	ArrayList<String> wordList = generateArrayList(rd);
    	word = wordList.get(rgen.nextInt(wordList.size()));
    	answer = dashMaker(word.length());
    }

    /** Takes in BufferedReader and generates and returns an Array of Strings*/
    private ArrayList<String> generateArrayList (BufferedReader rd) {
    	ArrayList<String> wordList = new ArrayList<String>();
    	try {
    		while (true) {
    			String line = rd.readLine();
    			if (line == null) break;
    			wordList.add(line);
    		}
    		rd.close();
    	} catch (IOException ex) {
    		throw new ErrorException(ex);
    	}
    	return wordList;
    }

    /** Opens up txt file and returns a BufferedReader*/
    private BufferedReader openFileReader(String fileName) {
    	BufferedReader rd = null;
    	try {
    		rd = new BufferedReader(new FileReader (fileName));
    	} catch (IOException ex) {
    		println("Can't open " + fileName);
    	}
    	return rd;
    }

    /** Plays the game */
    private void playGame() {

    	while(guessRemaining > 0 && unknownCounter(answer) > 0) {

    		println("The word now looks like this: " + answer);
    		canvas.displayWord(answer);
    		println("You have " + guessRemaining + " guesses left.");
    		String input = readLine("Your guess: ");

    		if (input.length() == 1) {
    			char ch = input.charAt(0);
    			ch = Character.toUpperCase(ch);
    			if (ch >= 'A' && ch <= 'Z' ) {

    				int index = word.indexOf(ch);

    				if (index != -1) {
    					answer = replaceCharacter(answer, ch, index);
    					while (true) {
    						index = word.indexOf(ch, index + 1);
    						if (index != -1) {
    							answer = replaceCharacter(answer, ch, index);
    						} else {
    							break;
    						}
    					}
    					println("That guess is correct.");

    				} else {
    					println("There are no " + ch + "'s in the word.");
    					guessRemaining--;
    					canvas.noteIncorrectGuess(ch);
    				}

    			} else {
    				println("Your guess is illegal. Please try again.");
    			}
    		} else {
    			println("Your guess is illegal. Please try again.");
    		}

    	}

    	if (guessRemaining == 0) {
    		println("You're completely hung.");
    		println("The word was: " + word);
    		println("You lose.");
    	} else {
    		println("You guessed the word: " + word);
    		println("You win.");
    		canvas.displayWord(answer);
    	}

    }

    /** Returns a string of dashes "-" equal to the number of characters in word */
    private String dashMaker (int n) {
    	String result = "";
    	for (int i = 0; i < n; i++) {
    		result += "-";
    	}
    	return result;
    }

    /** Replaces string (str) with character (ch) at index position n */
    private String replaceCharacter (String str, char ch, int n) {
    	String result = str;
    	return result = str.substring(0, n) + ch + str.substring(n+1);
    }

    /** Counts the number of dashes remaining in the answer */
    private int unknownCounter (String str) {
    	int n = 0;
    	for (int i = 0; i < str.length(); i++) {
    		if (str.charAt(i) == '-') n++;
    	}
    	return n;
    }
}
