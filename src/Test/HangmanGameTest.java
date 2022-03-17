import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HangmanGameTest {

    @BeforeEach
    void setUp() {
        Game game = new Game();
        Player player = new Player();
        Word word = new Word();
        setUp();
        player.setGuess('a');
        word.setWord("cat");
    }

    @Test
    void guessIsWrong() {
        assertEquals("false", HangmanGame.game.guessIsWrong(HangmanGame.game.player.getGuess()),
                "ERROR: unexpected boolean value returned");
    }

    @Test
    void initialWordState() {
        assertEquals("_ _ _ ", HangmanGame.game.initialWordState(HangmanGame.game.word.getWord()),
                "ERROR: unexpected initial word state");
    }

    @Test
    void updateWordState() {
        assertEquals("_ a _ ", HangmanGame.game.updateWordState('a'),
                "ERROR: unexpected update to word state");
    }

    @AfterEach
    void tearDown() {
        HangmanGame.game.end();
    }
}