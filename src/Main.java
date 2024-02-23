import java.io.*;
import java.util.Scanner;

public class Main {
    public static int fileSizeKB = 32;
    public static int fileSizeBytes = fileSizeKB * 1024;
    public static String fileName = "at28c256.bin";
    public static int prevAddr = 0;
    public static RandomAccessFile file;

    static {
        try {
            file = new RandomAccessFile(fileName, "rw");
        } catch (FileNotFoundException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        CommandLoop();
        File delete = new File(fileName);
        String IsDeleted = delete.delete() ?
                        "File " + fileName + " is deleted and new can be written!" :
                        "Error deleting file!";
        System.out.println(IsDeleted);
    }

    public static void CommandLoop()
    {
        Runnable TerminalLoop = null;
        CmdInterpreter CommandInterp = new CmdInterpreter();
        TerminalDisplay Term = new TerminalDisplay(System.out);
        Term.displayLoop(TerminalLoop ,CommandInterp);
    }

}
