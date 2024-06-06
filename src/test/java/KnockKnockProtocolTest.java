import org.Luka.KnockKnockProtocol;
import org.testng.annotations.Test;
import static org.testng.Assert.*;


public class KnockKnockProtocolTest {
    @Test
    void checkIfEmptyInput() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        String input = "";
        String expectedOutput = "Knock! Knock!";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(1, kkp.getState());
    }

    @Test
    void testSendClueIfInputIsRight() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        String userInput = kkp.processInput("Who's there?");
        assertEquals("Turnip", userInput);
        assertEquals(2, kkp.getState());
    }
    @Test
    void testSentKnockKnockStateIncorrectResponse() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        String input = "Something else";
        String expectedOutput = "You're supposed to say \"Who's there?\"! Try again. Knock! Knock!";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(1, kkp.getState());
    }

    @Test
    void testSentClueStateCorrectResponse() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        kkp.processInput("Who's there?");
        String input = "Turnip who?";
        String expectedOutput = "Turnip the heat, it's cold in here! Want another? (y/n)";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(3, kkp.getState());
    }

    @Test
    void testSentClueStateIncorrectResponse() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        kkp.processInput("Who's there?");
        String input = "Something else";
        String expectedOutput = "You're supposed to say \"Turnip who?\"! Try again. Knock! Knock!";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(1, kkp.getState());
    }

    @Test
    void testAnotherStateYesResponse() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        kkp.processInput("Who's there?");
        kkp.processInput("Turnip who?");
        String input = "y";
        String expectedOutput = "Knock! Knock!";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(1, kkp.getState());
    }

    @Test
    void testAnotherStateNoResponse() {
        KnockKnockProtocol kkp = new KnockKnockProtocol();
        kkp.processInput("");
        kkp.processInput("Who's there?");
        kkp.processInput("Turnip who?");
        String input = "n";
        String expectedOutput = "Bye.";
        String actualOutput = kkp.processInput(input);
        assertEquals(expectedOutput, actualOutput);
        assertEquals(0, kkp.getState());
    }
}
