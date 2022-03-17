import java.util.ArrayList;
import java.util.Scanner;

public class HangmanGame {

    static Game game = new Game();
    Word word = new Word();
    Player player = new Player();

    public static void main(String[] args) {
        game.setUpGame();
    }

    //set up game and call the drawGallows method with an initial game state
    public void setUpGame() {
        player.reset();
        word.reset();
        game.setPhase(1);
        System.out.println("H A N G M A N");
        String[] wordsList = {"cat", "horse", "money", "stack", "overflow", "runtime", "error",
                "establish", "infer", "subterranean", "infidel", "tyranny", "inescapable",
                "inexcusable", "somewhere", "impossible", "word", "goal", "orientation",
                "supper", "festival", "interrogate", "holiday", "sunrise", "sunset",
                "plausible", "predictable", "unpredictable", "wash", "surprise", "serendipity"};
        word.setWord(wordsList[Math.round(Math.round(Math.random()*wordsList.length))]);
        drawGallows();
        System.out.println("Missed Letters: ");
        System.out.println(initialWordState(word.getWord()));
        playerGuess();
    }
    //check for win/loss conditions and take player's guess for comparison against
    //the mystery word and previously guessed letters
    public void playerGuess() {
        if (game.getPhase() >= 7) {
            lose();
        }
        else if (!word.getLettersGuessed().contains(false)) {
            win();
        }
        System.out.println("Take a guess.");
        String input = getInput();
        if (input.equals(word.getWord())) {
            win();
        }
        else {
            char guess = input.charAt(0);
            player.setGuess(guess);
            compareGuess(player.getGuess());
        }
    }
    //compare the guess against the word and previously guessed letters
    public void compareGuess(char guess) {
        if (player.getGuessedLetters().contains(guess)) {
            System.out.println("You already guessed that letter. Guess again.");
            playerGuess();
        }
        else {
            player.addGuessedLetter(guess);
            if (guessIsWrong(guess)) {
                game.incrementPhase();
                player.addMissedLetter(guess);
            }
            drawOutput();
            playerGuess();
        }
    }
    //this method simply gets and returns the input from the scanner
    //this is necessary to simplify the usage of the scanner and prevent
    //bugs caused by unexpected scanner behavior
    public String getInput() {
        String[] input = game.scanner.nextLine().split(" ");
        return input[0].toLowerCase();
    }

    //method to draw in the entire output at the start of the game and after each guess
    public void drawOutput() {
        drawGallows();
        drawBottom();
    }

    //this method will handle drawing the gallows based on the current game state
    //which has been passed to this method as an argument
    public void drawGallows() {
        if (game.getPhase() == 1) {
            System.out.println(" +---+");
            System.out.println("     |");
            System.out.println("     |");
            System.out.println("     |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 2) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println("     |");
            System.out.println("     |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 3) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println(" |   |");
            System.out.println("     |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 4) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println(" |   |");
            System.out.println(" |   |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 5) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println(" |   |");
            System.out.println(" ||  |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 6) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println(" |-  |");
            System.out.println(" ||  |");
            System.out.println("    ===");
        }
        else if (game.getPhase() == 7) {
            System.out.println(" +---+");
            System.out.println(" O   |");
            System.out.println("-|-  |");
            System.out.println(" ||  |");
            System.out.println("    ===");
        }
        //just in case there is an error in handling the game state
        else {
            System.out.println("ERROR: Invalid phase data");
        }
    }

    //method to draw in the parts under the gallows
    public void drawBottom() {
        if (player.getMissedLetters().isEmpty()) {
            System.out.println("Missed Letters: ");
        }
        else {
            System.out.println("Missed Letters: " + player.getMissedLetters());
        }
        System.out.println(updateWordState(player.getGuess()));
    }

    //method to check the guessed letter against the word
    public boolean guessIsWrong(char guess) {
        return (!word.getCharList().contains(guess));
    }

    //method to manage initial word state before guesses
    public String initialWordState(String str) {
        return "_ ".repeat(str.length());
    }

    //method to manage the current word state
    //and to provide a string to print based on that state
    public String updateWordState(char letter) {
        //determine which letters have been guessed in the word
        //and alter the word state accordingly
        String string = word.getWord();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == letter) {
                word.setLettersGuessed(i);
            }
        }
        //now to print the revealed letters and dashes for hidden letters
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (word.getLettersGuessedIndex(i)) {
                sb.append(string.charAt(i));
            }
            else {
                sb.append("_");
            }
            sb.append(" ");
        }
        return sb.toString();
    }
    public void win() {
        System.out.println("Yes! The word is: " + word.getWord());
        askToPlayAgain();
    }
    public void lose() {
        System.out.println("Sorry, you're out of guesses and the man has been hanged.");
        System.out.println("The word was: " + word.getWord());
        askToPlayAgain();
    }
    public void askToPlayAgain() {
        System.out.println("Do you want to play again? (yes or no)");
        String input = getInput().toUpperCase();
        if (input.equals("YES")) {
            setUpGame();
        }
        else if (input.equals("NO")) {
            game.end();
        }
        else {
            System.out.println("ERROR: invalid answer");
            askToPlayAgain();
        }
    }
}

class Game extends HangmanGame {
    private int phase;
    Scanner scanner = new Scanner(System.in);

    public void setPhase(int phase) {
        this.phase = phase;
    }
    public int getPhase() {
        return phase;
    }
    public void incrementPhase() {
        phase++;
    }
    public void end() {
        scanner.close();
        System.exit(0);
    }
}

class Player {
    private char guess;
    private final StringBuilder missedLetters = new StringBuilder();
    private final ArrayList<Character> guessedLetters = new ArrayList<>();

    public void setGuess(char letter) {
        guess = letter;
    }
    public char getGuess(){
        return guess;
    }
    public void addMissedLetter(char letter) {
        missedLetters.append(letter);
    }
    public String getMissedLetters() {
        return missedLetters.toString();
    }
    public void addGuessedLetter(char letter) {
        guessedLetters.add(letter);
    }
    public ArrayList<Character> getGuessedLetters() {
        return guessedLetters;
    }
    public void reset() {
        missedLetters.delete(0, missedLetters.length());
        guessedLetters.clear();
    }
}

class Word {
    private String word = "";
    private final ArrayList<Character> charList = new ArrayList<>();
    private final ArrayList<Boolean> lettersGuessed = new ArrayList<>();

    //here we save the word which has been set
    //and also set array lists accordingly to access the letters individually
    //as well as whether those letters have yet been guessed
    public void setWord(String word) {
        this.word = word;
        for (int i = 0; i < word.length(); i++) {
            charList.add(word.charAt(i));
            lettersGuessed.add(false);
        }
    }
    public String getWord() {
        return word;
    }
    public ArrayList<Character> getCharList() {
        return charList;
    }
    public void setLettersGuessed(int index) {
        lettersGuessed.set(index, true);
    }
    public ArrayList<Boolean> getLettersGuessed() {
        return lettersGuessed;
    }
    public boolean getLettersGuessedIndex(int index) {
        return lettersGuessed.get(index);
    }
    public void reset() {
        word = "";
        charList.clear();
        lettersGuessed.clear();
    }
}
