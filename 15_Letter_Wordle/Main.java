import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
  public static void main(String[] args) {
    WordleInterface play = new Wordle(); // Play a game of Wordle //FIXME: Needs to be able to have a specific length and difficulty
    // Set up the game ////////////////////////////////////
    Scanner scanner = new Scanner(System.in);
    String input = null; // Holds the player's guess
    boolean gameLoop = true;

    // Initalizing gameState, the array which holds most game fields
    int[] gameState;
    gameState = new int[15]; // Array that keeps the games values
    gameState[0] = 10; // Initial starting health
    int MaxwrdLe = 15;
    int minwrdLe = 1;
    int wrdLe = (((int)((Math.random() * MaxwrdLe) % MaxwrdLe)) + minwrdLe); // Variable word length, make it's own class or method

    wrdLe = 15; //FIXME testing
    
    int tries = 10; // Variable number of tries
    
    
    int[] items = new int[15]; // Array that keeps track of which items the player has
      
    
    // Gameplay loop //////////////////////////////////
    while (gameLoop) {
      
      boolean loop = true;
      play.Wordle_Game(gameState, items, wrdLe, tries);
      System.out.print("Play again? 0 for yes, 1 for no: ");
      input = scanner.nextLine();
      while (loop) {
        if (input.compareTo("0") == 0) {
          loop = false;
        } else if (input.compareTo("1") == 0) {
          gameLoop = false;
          loop = false;
        } else {
          System.out.print("Come again? ");
          input = scanner.nextLine();
        }
      }
    }
    System.out.println("K bye");
  }
}