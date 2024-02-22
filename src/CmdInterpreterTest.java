import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CmdInterpreterTest {
    @Test
        // This test checks if the method can interpret multiple write command with integers in hexadecimal form
    void interpretCmdMW() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = "23:FF FE FF FE";
        Command expectedOutput = new Command(false, true,":",false,35 ,new int[]{255,254,255,254});
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can interpret multiple write command in ASCI form
    void interpretCmdMWA() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = ":FF FE";
        Command expectedOutput = new Command(false, true,":",true,new int[]{255,254});
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can interpret read command in ASCI form
    void interpretCmdRA() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = ".FF";
        Command expectedOutput = new Command(true, false,".",true,255);
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can interpret simple read command
    void interpretCmdR() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = "FF";
        Command expectedOutput = new Command(true, false," ",false,255);
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can interpret write command in ASCI form
    void interpretCmdWAS() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = ":FF";
        Command expectedOutput = new Command(false, false,":",true,255);
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can interpret multiple write command separated by ":"
    void interpretCmdWS() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = "FF:FE";
        Command expectedOutput = new Command(false, false,":",false,255,254);
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can handle invalid command form
    void interpretCmdNull1() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = "FF.FE";
        Command expectedOutput = null;
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can handle invalid hexadecimal form
    void interpretCmdNull2() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = ".FT";
        Command expectedOutput = null;
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can handle out of bound hexadecimal form
    void interpretCmdOOB() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = ".F11FF";
        Command expectedOutput = null;
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }

    @Test
        // This test checks if the method can handle under bound hexadecimal form
    void interpretCmdOOBUnder() {
        CmdInterpreter cmdInterpreter = new CmdInterpreter();
        String testInput = "-FF";
        Command expectedOutput = null;
        Command result = cmdInterpreter.interpretCmd(testInput);
        assertEquals(expectedOutput, result);
    }
}