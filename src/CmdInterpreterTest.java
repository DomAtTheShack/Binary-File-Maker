import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CmdInterpreterTest {

    @Test
    void interpretCmdMW() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = "23:FF FE FF FE"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(false, true,":",false,35 ,new short[]{255,254,255,254}); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdMWA() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = ":FF FE"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(false, true,":",true,new short[]{255,254}); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdRA() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = ".FF"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(true, false,".",true,255); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdR() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = "FF"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(true, false," ",false,255); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdWAS() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = ":FF"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(false, false,":",true,255); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdWS() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = "FF:FE"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = new Command(false, false,":",false,255,254); // please replace ... with your actual expected output

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdNull1() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = "FF.FE"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = null;
        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
    @Test
    void interpretCmdNull2() {
        // For the setup, we create the CmdInterpreter object
        CmdInterpreter cmdInterpreter = new CmdInterpreter();

        // The test input for our method
        String testInput = ".FT"; // please replace ... with your actual test input

        // The output we expect to get from the method when it's working correctly
        Command expectedOutput = null;

        // Here we call the method with our test input
        Command result = cmdInterpreter.interpretCmd(testInput);

        // And here we check if the output was as expected
        assertEquals(expectedOutput, result);
    }
}