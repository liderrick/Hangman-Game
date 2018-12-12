/*
 * HangmanCanvas.java
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int BODY_OFFSET = 2;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

	/** Class (or instance) variables */
	private GLabel wordDisplay = new GLabel("");

	private String incorrectString = "";
	private GLabel incorrectDisplay = new GLabel("");

	private int incorrectGuess = 0;

/** Resets the display so that only the scaffold appears */
	public void reset() {

		GLine scaffold_post = new GLine(0,0,0,SCAFFOLD_HEIGHT);
		scaffold_post.setLocation(getWidth()/2 - BEAM_LENGTH, getHeight() * 1/8);
		add(scaffold_post);

		GLine scaffold_beam = new GLine(0,0,BEAM_LENGTH,0);
		scaffold_beam.setLocation(getWidth()/2 - BEAM_LENGTH, getHeight() * 1/8);
		add(scaffold_beam);

		GLine scaffold_rope = new GLine(0,0,0,ROPE_LENGTH-BODY_OFFSET);
		scaffold_rope.setLocation(getWidth()/2, getHeight() * 1/8);
		add(scaffold_rope);

		/** The following System.out.println commands were used to troubleshoot GCanvas size issues
		 * and are non-essential for the program to run. It is kept in place for future reference. */
		System.out.println("HangmanCanvas width: " + getWidth());
		System.out.println("HangmanCanvas height: " + getHeight());
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		wordDisplay.setLabel(word);
		wordDisplay.setFont("Times-Bold-36");
		add(wordDisplay, getWidth() * 1/8, getHeight() * 5.9/8);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {

		incorrectGuess++;

		incorrectString += letter;
		incorrectDisplay.setLabel(incorrectString);
		incorrectDisplay.setFont("Times-20");
		add(incorrectDisplay, getWidth() * 1/8, getHeight() * 6.2/8);

		GOval head = new GOval(HEAD_RADIUS * 2, HEAD_RADIUS * 2);
		GLine body = new GLine(0,0,0,BODY_LENGTH);

		GLine left_upper_arm = new GLine(0,0,-UPPER_ARM_LENGTH,0);
		GLine right_upper_arm = new GLine(0,0,UPPER_ARM_LENGTH,0);

		GLine lower_arm = new GLine(0,0,0,LOWER_ARM_LENGTH);

		GLine hip = new GLine(0,0,HIP_WIDTH,0);
		GLine leg = new GLine(0,0,0,LEG_LENGTH);

		GLine left_foot = new GLine(0,0,-FOOT_LENGTH,0);
		GLine right_foot = new GLine(0,0,FOOT_LENGTH,0);

		switch (incorrectGuess) {
			case 0: break;
			case 1: /* adds head */
				add(head, getWidth()/2 - HEAD_RADIUS, getHeight() * 1/8 + ROPE_LENGTH);
				break;
			case 2: /* adds body */
				add(body, getWidth()/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2);
				break;
			case 3: /* adds left arm */
				add(left_upper_arm, getWidth()/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
				add(lower_arm, getWidth()/2 - UPPER_ARM_LENGTH, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
				break;
			case 4: /* adds right arm */
				add(right_upper_arm, getWidth()/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
				add(lower_arm, getWidth()/2 + UPPER_ARM_LENGTH, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
				break;
			case 5: /* adds left leg (and hip) */
				add(hip, (getWidth() - HIP_WIDTH)/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
				add(leg, (getWidth() - HIP_WIDTH)/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
				break;
			case 6: /* adds right leg */
				add(leg, (getWidth() + HIP_WIDTH)/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH);
				break;
			case 7: /* adds left foot */
				add(left_foot, (getWidth() - HIP_WIDTH)/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
				break;
			case 8: /* adds right foot */
				add(right_foot, (getWidth() + HIP_WIDTH)/2, getHeight() * 1/8 + ROPE_LENGTH + HEAD_RADIUS * 2 + BODY_LENGTH + LEG_LENGTH);
				break;
			default: break;
		}
	}
}
