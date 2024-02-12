import java.io.IOException;
import java.io.PrintStream;

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

    public void printHexDump(short[] data, int baseAddress) {
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
