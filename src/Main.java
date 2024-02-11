import java.io.*;
import java.util.Scanner;

public class Main {
    public static int fileSizeKB = 32;
    public static int fileSizeBytes = fileSizeKB * 1024;
    public static String fileName = "at28c256.bin";
    public static int prevAddr = 0;

    public static void main(String[] args) {
        File delete = new File(fileName);
        CmdInterpreter test = new CmdInterpreter();
        test.interpretCmd("Set".toLowerCase());
                String IsDeleted = delete.delete() ?
                        "File " + fileName + " is deleted and new can be written!" :
                        "Error deleting file!";
        System.out.println(IsDeleted);
        try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
            // Fill the file with 00 bytes initially
            byte[] zeros = new byte[fileSizeBytes];
            file.write(zeros);

            // Move to the beginning of the file and write some bytes
            file.seek(0); // Move to the beginning
            file.write(0xFF);
            file.write(0xE8);
            file.write(0xF0);
            file.write(0xFE);

            System.out.println("File '" + fileName + "' created with size: " + fileSizeKB + " KB");
            System.out.println("Manually set bytes at the beginning of the file.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void CommandLoop()
    {
        Scanner UserInput = new Scanner(System.in);
        String Command = UserInput.nextLine();
    }

}
