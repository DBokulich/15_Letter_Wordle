import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Wordle implements WordleInterface {
  public void Wordle_Game(int wrdLe, int numTries) { // Make seperate Class for Wordle_Game inputs

    //Exception checking:
    if (wrdLe <= 0) {
      System.out.println("Error in Wordle_Game, wrdLe <= 0");
      return;
    }

    // Extracts the words from a text file to an array list //////////

    StringFixedArrayList wordleList = new StringFixedArrayList(1);

    File inputFile = new File("./texts/scrabble.txt");

    Scanner scan = null;
    try {
      scan = new Scanner(inputFile);
    } catch (FileNotFoundException e) {
      System.err.println(e);
      System.exit(1);
    }

    while (scan.hasNext()) {
      String word = scan.next();
      wordleList.add(word);
    }

    // Game setup ////////////////////////////////////////

    Scanner scanner = new Scanner(System.in);

    String inWord = null; // Holds the player's guess
    String secWord = wordleList.random();
    while (secWord.length() != wrdLe) {
      secWord = wordleList.random(); // Holds the random secret word
    }
    // System.out.println("secWord: " + secWord); //FIXME testing
    boolean game = true; // loops the game while it has not ended
    boolean win = false; // Tracks whether the player won when the game ends
    int tries = 0; // Tracks the current number of tries
    int maxTries = numTries; // The maximum number of tries

    System.out.println("Word length: " + secWord.length());
    // Game loop /////////////////////////////////////////

    while (game) {
      boolean enterGuess = true;
      while (enterGuess) { // Loops until a valid word is entered
        System.out.print("Please enter a guess: ");
        inWord = scanner.nextLine();

        if (inWord.length() < secWord.length()) {
          System.out.println("That word is " + (secWord.length() - inWord.length()) + " too short!");
        } else if (inWord.length() > secWord.length()) {
          System.out.println("That word is " + (inWord.length() - secWord.length()) + " too long!");
        } else if (!wordleList.contains(inWord)) {
          System.out.println("Please enter a real word!");
        } else {
          enterGuess = false;
        }
      }

      win = Output(inWord, secWord); // The heart of the game. Checks whether the guess is correct and outputs the " ", "*", and "|"

      tries++;

      if (win || (tries >= maxTries)) { // Checks whether the game has ended
        game = false;
      }
      System.out.print("\n");
    }
    if (win) { // If the player has won
      if (tries == 1) {
        System.out.println("Wow, you won on the first try!");
      } else {
        System.out.println("You win! you used " + tries + " tries.");
      }
    } else { // If the player has lost
      System.out.println("You lose... The word was: " + secWord);
    }
    return;
  }

  // Function used in main
  // The heart of the game
  // Outputs the " ", "*", and "|",
  // Returns true if the guess (inWord) matches the secret word (secWord)
  private static boolean Output(String inWord, String secWord) {
    int totalTrue = 0; // Tracks the number of letters that have matched
    int[] lettersMarked = new int[secWord.length()]; // Array that stores what indexes are correct, in the word, and wrong

    // Counts the number of each letter in the word, ex. [0] == numA
    int[] secCount = countLetters(secWord); //FIXME constant while game runs?
    // Tracks the number of letters correct or misplaced so far
    int[] soFar = new int[26];

    // For each letter
    for (int i = 0; i < secWord.length(); i++) {
      // if it is in the word
      if (secWord.contains(inWord.substring(i, i + 1))) {
        // and is in this index
        if (Character.compare((inWord.charAt(i)), (secWord.charAt(i))) == 0) {
          lettersMarked[i] = 2;
          totalTrue++;
          int letter = (int) inWord.charAt(i) - 65;
          soFar[letter] += 1;
        }
      }
    }

    // For each letter
    for (int i = 0; i < secWord.length(); i++) {
      // if it is in the word
      if (secWord.contains(inWord.substring(i, i + 1))) {
        // and is not in this index
        if (Character.compare(inWord.charAt(i), secWord.charAt(i)) != 0) {
          lettersMarked[i] = Misplaced(lettersMarked, inWord, i, soFar, secCount);
          if (lettersMarked[i] == 1) {
            int letter = (int) inWord.charAt(i) - 65;
            //System.out.println(letter + " " + inWord.charAt(i)); //FIXME testing
            soFar[letter] += 1;
          }
        }
        // else if not in the word at all
      } else {
        lettersMarked[i] = 0;
      }
    }

    printOut(inWord, lettersMarked, soFar, secCount);

    // Is the guess correct?
    if (totalTrue >= secWord.length()) {
      return true;
    } else {
      return false;
    }
  }

  // returns lettersMarked with the current letter properly marked as in the wrong spot or uneeded
  private static int Misplaced(int[] lettersMarked, String inWord, int currIndex, int[] soFar, int[] secCount) {
    int letter = inWord.charAt(currIndex) - 65;
    // Compares the number of the input letter in secWord and the number of that letter we have gone through in inWord
    if (soFar[letter] < secCount[letter]) {
      return 1;
    } else {
      return 0;
    }
  }

  // Method used to count the number of each letter in the given word
  // Returns a vector of ints of size 26 each index corrosponding to a letter of the alphabet
  private static int[] countLetters(String word) {
    // The rv
    int[] alphabet = new int[26];
    // For each letter in the word
    for (int i = 0; i < word.length(); i++) {
      // For each letter of the alphabet
      for (int j = 0; j < alphabet.length; j++) {
        // If the ascii values match
        if (((int)(word.charAt(i))) == (j + 65)) {
          // Add one to the corrosponding index in the rv and leave this loop
          //FIXME System.out.println("Letter: " + word.charAt(i) + "\tj: " + j); //FIXME testing
          alphabet[j] += 1; 
          j = alphabet.length;
        }
      }
    }
    return alphabet;
  }

  private static void printOut(String inWord, int[] lettersMarked, int[] soFar, int[] secCount) {
    for (int i = 0; i < inWord.length(); i++) {
      if (lettersMarked[i] == 0) {
        System.out.print(" " + (inWord.charAt(i)) + " ");
      } else if (lettersMarked[i] == 1) {
        System.out.print(" " + "*" + (inWord.charAt(i)) + "*" + " ");
      } else if (lettersMarked[i] == 2) {
        System.out.print(" " + "|" + (inWord.charAt(i)) + "|" + " ");
      }
    }

    /*
    // Visual output for testing
    System.out.print("\nAlphabet:\t\t");
    for (int i = 0; i < soFar.length; i++) 
    System.out.print((char)(i + 65) + " ");
    
    System.out.print("\nsoFar:\t\t\t");
    for (int i = 0; i < soFar.length; i++) 
    System.out.print(soFar[i] + " ");
    System.out.print("\nsecCount:\t\t");
    for (int i = 0; i < secCount.length; i++) 
    System.out.print(secCount[i] + " ");
    System.out.print("\n\nlettersmarked:\t");
    for (int i = 0; i < lettersMarked.length; i++) 
    System.out.print(lettersMarked[i] + " ");
    */
  }
}
