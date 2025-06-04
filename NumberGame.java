import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Task 1 – Number Game
 *
 * • Generates a random number between 1 – 100
 * • User guesses with feedback (too high / too low)
 * • 7-try limit per round
 * • Supports multiple rounds and keeps score
 */
public class NumberGame {

    private static final int MAX_ATTEMPTS = 7;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("🎉 Welcome to the Number Guessing Game!");

        System.out.print("How many rounds would you like to play? ");
        int rounds = safeIntInput(in, 1, Integer.MAX_VALUE);

        int score = 0;
        for (int r = 1; r <= rounds; r++) {
            if (playRound(in, r)) {
                score++;
            }
        }

        System.out.printf("%n🏁 Game over – you won %d out of %d rounds.%n",
                          score, rounds);
        in.close();
    }

    /** Plays one round; returns true if user guesses correctly. */
    private static boolean playRound(Scanner in, int roundNo) {
        System.out.printf("%n--- Round %d ---%n", roundNo);
        int secret = ThreadLocalRandom.current().nextInt(1, 101);
        int attempts = 0;

        while (attempts < MAX_ATTEMPTS) {
            System.out.printf("Attempt %d of %d – enter your guess: ",
                              attempts + 1, MAX_ATTEMPTS);
            int guess = safeIntInput(in, 1, 100);
            attempts++;

            if (guess == secret) {
                System.out.printf("✅ Correct! You guessed it in %d attempt%s.%n",
                                  attempts, attempts == 1 ? "" : "s");
                return true;
            } else if (guess < secret) {
                System.out.println("🔻 Too low.");
            } else {
                System.out.println("🔺 Too high.");
            }
        }

        System.out.printf("❌ Out of attempts! The number was %d.%n", secret);
        return false;
    }

    /** Safely reads an int within [min..max] (re-prompts until valid). */
    private static int safeIntInput(Scanner in, int min, int max) {
        while (true) {
            if (in.hasNextInt()) {
                int n = in.nextInt();
                if (n >= min && n <= max) {
                    return n;
                }
            } else {
                in.next();  // discard invalid token
            }
            System.out.printf("⚠️  Please enter a whole number between %d and %d: ",
                              min, max);
        }
    }
}
