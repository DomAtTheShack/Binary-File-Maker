import java.io.*;
import java.util.Scanner;

public class Main {
    public static int fileSizeKB = 32;
    public static int fileSizeBytes = fileSizeKB * 1024;
    public static String fileName = "at28c256.bin";
    public static int prevAddr = 0;
    public static String PrgName = "JAVAMON";
    public static float PrgVersion = 0.2f;
    public static String ChipType;
    public static RandomAccessFile file;

    public static Boolean isRunning = true;

    static {
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        CommandLoop();
        byte[] temp = new byte[fileSizeBytes];
        file.write(temp);
        while(isRunning)
            Thread.sleep(1000);


    }

    public static void CommandLoop()
    {
        Runnable TerminalLoop = null;
        CmdInterpreter CommandInterp = new CmdInterpreter();
        TerminalDisplay Term = new TerminalDisplay(System.out);
        Term.displayLoop(TerminalLoop ,CommandInterp);
    }

}
