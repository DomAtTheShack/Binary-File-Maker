import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class TerminalDisplay
{
    private final PrintStream TERMINAL;
    private final String OS;

    public TerminalDisplay(PrintStream Term)
    {
        TERMINAL = Term;
        OS = System.getProperty("os.name").toLowerCase();
    }
    public void print(String disp)
    {
        TERMINAL.print(disp);
    }
    public void println(String disp)
    {
        TERMINAL.println(disp);
    }

    public void displayLoop(Runnable Loop, CmdInterpreter Inter)
    {
        Loop = () -> {
            boolean Using = true;
            Scanner UserInput = new Scanner(System.in);
            Command.setDISPLAY(this);
            Command.setCmdInput(UserInput);
            boolean printDollar = true;
            String CurrentInput;
            clear();
            println(Main.PrgName + " v" + Main.PrgVersion);
            println("/");
            while(Using)
            {
                if(printDollar)
                    System.out.print("$ ");
                printDollar = true;
                CurrentInput = UserInput.nextLine();
                if (!CurrentInput.trim().isEmpty()) {
                    Command UserCmd = Inter.interpretCmd(CurrentInput);
                    if(UserCmd != null)
                    {
                        try {
                            String CurrentCmd = UserCmd.Excute();
                            if(CurrentCmd.equals("#"))
                            {
                                Using = false;
                                System.out.println("Saving to documents...");
                            }else
                            {
                                System.out.println(CurrentCmd);
                            }
                        } catch (IOException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }else
                    {
                        System.out.println("Err");
                    }
                }else{
                    printDollar = false;
                }
            }
        };
        new Thread(Loop).start();
    }
    public void clear()
    {
        try {
            // Checking the operating system
            if (OS.contains("unix") || OS.contains("linux")) {
                // For Unix/Linux/Mac
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            } else if (OS.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                for (int i = 0; i < 36; i++)
                    System.out.println();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void printHexDump(int[] data, int baseAddress) {
        for (int i = 0; i < data.length; i += 8) {
            StringBuilder builder = new StringBuilder();
            builder.append(String.format("%04x  ", baseAddress + i));

            for (int j = 0; j < 8; j++) {
                if (j + i < data.length) {
                    builder.append(String.format("%02x ", data[j + i]));
                } else {
                    builder.append("   ");
                }

                if (j == 7) {
                    builder.append(" ");
                }
            }

            builder.append(" ");

            for (int j = 0; j < 8; j++) {
                if (j + i < data.length) {
                    if (data[j + i] < 0x20 || data[j + i] >= 0x7f) {
                        builder.append(".");
                    } else {
                        builder.append(new String(Character.toChars(data[j + i])));
                    }
                }
            }

            TERMINAL.println(builder.toString());
        }
    }
}
