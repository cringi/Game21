/**
 * Game 21
 * Author: Brandon B.
 * Date: 10-19-15
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game21 {
	// Set some constant variables
	private static final int MAX_CARDS = 3, MAX_TOTAL = 21;
	private static final int PLAYER_USER = 0, PLAYER_COMPUTER = 1;
	private static final String RESULT_WIN_USER = "\nYou won!",
								RESULT_WIN_COMPUTER = "\nThe computer won!", RESULT_DRAW = "\nDraw!";

	// Initiate variables to be used later.
	private static int CARDS_REQUESTED, PLAYER_USER_SCORE = 0, PLAYER_COMPUTER_SCORE = 0;
	private static List<Integer> playerCards = new ArrayList<Integer>();
	private static List<Integer> pcCards = new ArrayList<Integer>();

	// Initialize other classes
	private static Random rollGenerator = new Random();
	private static Scanner systemPrompt = new Scanner(System.in);

    public static void main(String[] args) {
    	System.out.println("Welcome to the game of 21!");
    	System.out.println("You may draw up to three cards maximum.");
    	System.out.println("If you draw more than 21, you lose.");

    	// Start off by asking how many cards the user should draw.
    	queryNumCards();
    }

	private static void queryNumCards() {
		while (true) {
			System.out.print("How many cards would you like to draw? ");
			CARDS_REQUESTED = systemPrompt.nextInt();

			if (CARDS_REQUESTED > MAX_CARDS) {
				// They requested for more than three cards. We can't have that.
				System.out.printf("%nYou may only draw up to %d cards.%n", MAX_CARDS);
				continue;
			}
			else if (CARDS_REQUESTED < 1) {
				// They requested for 0 or a negative amount of cards. That won't work.
				System.out.println("\nYou must draw at least one card!");
				continue;
			}
			else {
				// No problems. Let's leave.
				break;
			}
		}

		generateCards(PLAYER_USER);
		generateCards(PLAYER_COMPUTER);
		reportResults();
	}

	private static void queryReplay() {
		// Reset both players since we're done with them.
		resetPlayer(PLAYER_USER);
		resetPlayer(PLAYER_COMPUTER);

		// Ask the user if they want to play again.
		String USER_REPLAY;
		System.out.print("Would you like to play again? (y/n) ");
		USER_REPLAY = systemPrompt.next();
		USER_REPLAY = USER_REPLAY.toLowerCase();

		if (USER_REPLAY.equals("y")) {
			// They want to play again!
			queryNumCards();
		}
		else {
			// If they entered n or something else, we're bailing out.
			System.exit(0);
		}
	}

    private static void generateCards(int player) {
    	// Uses the random class to generate cards.
    	if (player == PLAYER_USER) {
    		for (int plays = 0;plays < CARDS_REQUESTED;plays++) {
    			int card = rollGenerator.nextInt(9) + 1;
    			playerCards.add(card);
    		}
    	}
    	else if (player == PLAYER_COMPUTER) {
    		// The computer always rolls 3 cards.
    		for (int plays = 0;plays < 3;plays++) {
    			int card = rollGenerator.nextInt(9) + 1;
    			pcCards.add(card);
    		}
    	}
    	else {
    		throw new IllegalArgumentException("Invalid player specified.");
    	}
    }

    private static void resetPlayer(int player) {
    	// Resets cards and score for the player specified.
		if (player == PLAYER_USER) {
			// Reset the cards and score for the current user.
			PLAYER_USER_SCORE = 0;
			playerCards.clear();
		}
		else if (player == PLAYER_COMPUTER) {
			// Reset the cards for the computer.
			PLAYER_COMPUTER_SCORE = 0;
			pcCards.clear();
		}
		else {
			throw new IllegalArgumentException("Invalid player specified.");
		}
	}

	private static void reportResults() {
		for (Integer point:playerCards) {
			PLAYER_USER_SCORE += point;
		}
		for (Integer point:pcCards) {
			PLAYER_COMPUTER_SCORE += point;
		}

		System.out.printf("%nYou drew: %s [%d total]%n", playerCards, PLAYER_USER_SCORE);
		System.out.printf("Computer drew: %s [%d total]%n", pcCards, PLAYER_COMPUTER_SCORE);
		reportWinner();
	}

	private static void reportWinner() {
		if (PLAYER_USER_SCORE == PLAYER_COMPUTER_SCORE) {
			// CASE: Both players have tied. It's a draw.
			System.out.println(RESULT_DRAW);
		}
		else if (PLAYER_USER_SCORE > MAX_TOTAL && MAX_TOTAL < PLAYER_COMPUTER_SCORE) {
			// CASE: Both players drew over 21. It's a draw.
			System.out.println(RESULT_DRAW);
		}
		else if (PLAYER_USER_SCORE > MAX_TOTAL && MAX_TOTAL > PLAYER_COMPUTER_SCORE) {
			// CASE: The user drew over 21, but the computer did not.
			System.out.println(RESULT_WIN_COMPUTER);
		}
		else if (PLAYER_USER_SCORE < MAX_TOTAL && MAX_TOTAL < PLAYER_COMPUTER_SCORE) {
			// CASE: The computer drew over 21, but the compute did not.
			System.out.println(RESULT_WIN_USER);
		}
		else if (PLAYER_USER_SCORE == MAX_TOTAL) {
			// CASE: The user scored 21.
			// Why is this safe without checking the computer's score? We already have
			// a statement that looks to see if they've tied. We've already established that they haven't.
			System.out.println(RESULT_WIN_USER);
		}
		else if (PLAYER_COMPUTER_SCORE == MAX_TOTAL) {
			// CASE: The computer scored 21.
			System.out.println(RESULT_WIN_COMPUTER);
		}
		else {
			// They didn't match one of our cases. Now we have to get dirty.
			// We've already established they both scored under 21.
			if (PLAYER_USER_SCORE > PLAYER_COMPUTER_SCORE) {
				// The player won!
				System.out.println(RESULT_WIN_USER);
			}
			else {
				// [hopefully] it's safe to assume that the computer has won at this point.
				System.out.println(RESULT_WIN_COMPUTER);
			}
		}
		queryReplay();
	}
}
