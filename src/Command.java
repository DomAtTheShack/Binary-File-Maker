import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Command
{
    private char CmdType;
    private boolean isMulti;
    private boolean read;
    private boolean usePrev;
    private int[] dataM;
    private int data;
    private String WordCMD = "";
    private int addressStart;
    private int addressEnd;
    private String[] args = new String[]{};

    private static Scanner CmdInput;
    private static TerminalDisplay DISPLAY;

    private final String NoChange = "Your file has been left untouched!";


    public Command(String cmd, String[] argument)
    {
        args = argument;
        WordCMD = cmd;
    }

    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, int addrS, int dataOrAddress)
    {
        this.read = read;
        this.isMulti = isMulti;
        this.CmdType = cmdType.charAt(0);
        this.usePrev = usePrev;
        this.addressStart = addrS;
        if(read)
            this.addressEnd = dataOrAddress;
        else
            this.data = dataOrAddress;
    }
    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, int addrS, int[] data)
    {
        this.read = read;
        this.dataM = data;
        this.addressStart = addrS;
        this.CmdType = cmdType.charAt(0);
        this.isMulti = isMulti;
        this.usePrev = usePrev;
    }

    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, int dataOrAddress)
    {
        this.read = read;
        this.isMulti = isMulti;
        this.CmdType = cmdType.charAt(0);
        this.usePrev = usePrev;
        if(read && !usePrev)
            this.addressStart = dataOrAddress;
        else if(usePrev)
            this.addressEnd = dataOrAddress;
        else
            this.data = dataOrAddress;
    }
    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, int[] data)
    {
        this.read = read;
        this.isMulti = isMulti;
        this.CmdType = cmdType.charAt(0);
        this.usePrev = usePrev;
        this.dataM = data;
    }
    public String Excute() throws IOException, InterruptedException {
            switch (this.CmdType) {
                case ':':
                    writeByte();
                    return "";
                case '.':
                    readBytes();
                    return "";
                case ' ':
                    readByte();
                    return "";
            }
            if(!WordCMD.isEmpty())
                return WordCmds();

        return "err";
    }

    private String WordCmds() throws IOException, InterruptedException {
        switch (WordCMD)
        {
            case "help":
                return HelpCmd();
            case "new":
                return MakeNewFile();
            case "save":
                return saveFile();
            case "set":
                return setCmd(args);
            case "write":
                return writeToMini();
            case "cls":
                DISPLAY.clear();
                return "/";
        }
        return "err";
    }

    private String writeToMini() throws IOException, InterruptedException {
        if(Main.ChipType == null)
        {
            return "Run Set CT to set your chip type";
        }
        MiniproWriter CurrentFile = new MiniproWriter(Main.fileName, Main.ChipType);
        if(CurrentFile.WriteFile())
        {
            return ("Write successful");
        }else
        {
            return "Error in writing check chip size, if mini pro is installed, or the chip type";
        }
    }

    public static void setCmdInput(Scanner input)
    {
        CmdInput = input;
    }
    public static void setDISPLAY(TerminalDisplay Disp)
    {
        DISPLAY = Disp;
    }
    private String setCmd(String[] args)
    {
        String Return = "";
        for(String arg: args)
        {
            switch (arg)
            {
                case "n":
                    DISPLAY.print("New file name: ");
                    String NewName = CmdInput.nextLine();
                    DISPLAY.println("\nChanging " + Main.fileName + " to " + NewName);
                    Main.fileName = NewName;
                    break;
                case "s":
                    DISPLAY.print("New size in (4k, 8k, 16k, 32k, 64k, 128k)" +
                            "\nALL DATA WILL BE LOST type -1 to cancel: ");
                    try {
                        int SizeKB = CmdInput.nextInt();
                        if(SizeKB == -1)
                            return NoChange;
                        boolean goodSize = false;
                        for(int size: CmdInterpreter.VALID_SIZES)
                        {
                             if(size == SizeKB) {
                                 goodSize = true;
                                 break;
                             }
                        }
                        if(!goodSize)
                            throw new NumberFormatException();
                        SizeKB *= 1024;
                        Main.fileSizeBytes = SizeKB;
                        Main.file.setLength(SizeKB);
                        Return = "File size changed to " + SizeKB + "KBs";
                    }
                    catch (NumberFormatException e)
                    {
                        DISPLAY.println("Invalid size!");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "ct":
                    DISPLAY.print("What to change Chip type to: ");
                    Main.ChipType = CmdInput.nextLine();
                    Return = "Changed Chip type to " + Main.ChipType;
                    break;
                default:
                    Return = "You need an Argument or a proper one \n Type help to see available options";
                    break;
            }
        }
        return Return;
    }

    private String saveFile() throws IOException {
        System.out.println("Do you want to save the file(y/n)");
        boolean isSaving = Command.CmdInput.nextLine().equals("y");
        if(isSaving)
        {
            Path Documents = Paths.get(System.getProperty("user.home"), "Documents/" + Main.fileName);
            Path SourceFile = Paths.get(Main.fileName);
            Main.file.close();
            Files.move(SourceFile,Documents, StandardCopyOption.REPLACE_EXISTING);
            Main.isRunning = false;
            return "#";
        }
        return NoChange;
}


    private String MakeNewFile() throws IOException {
        System.out.println("Do you want to save the file(y/n)");
        boolean makeNew = Command.CmdInput.nextLine().equals("y");
        if(makeNew)
        {
            Main.file.seek(0);
            byte[] tempZero = new byte[Main.fileSizeBytes];
            Main.file.write(tempZero);
            return "Your file has been erased!";
        }
        return NoChange;
    }

    private String HelpCmd()
    {
        return "This program (JAVAMON) simulates the Wozmon program for the 6502 CPU. " +
                "PS(This is way bigger than 256 bytes and Wozniak would be disappointed) " + "\n Read Single Byte: Address to read -> Ab02 then it will return the byte at the address" +
                "\n Read Multi Byte: Starting address -> 00FC . <-must have Ending address -> 010F " +
                "then it will print all bytes between(Inclusive) " +
                "\n Write Byte: Address to write -> 00FF : <-must have Byte to write(8-bits) -> ff " +
                "after writing it will print the data .previously at that location" +
                "\n Multi Write: Address to start writing: 00AB : 00 FA EA CD <- Then multi bytes seperated by a space" +
                " and then it will write and increment the address so one data byte = one address space" +
                "\n\n Anonymous access to addresses: Also after running any on the commands above " +
                "JAVAMON will store the FIRST address location accessed and you can anonymously access it. " +
                "\n Multi Read Anon: . FFFF <- ''.'' comes first then the address to end the search " +
                "\n Write Anon: To use this write it like : FF  Start with '':'' then followed by the 8-bit data value" +
                "\n Multi Write Anon: Same as above but includes multiple bytes : FF FC EA EE 0F 34"+
                "\n\n New word commands: " +
                "\n help -> Displays this menu" +
                "\n new -> Creates a new file" +
                "\n save -> Will save the file to documents folder" +
                "\n write -> Work in progress but it will write bin file to Minipro on Linux or Mac based on Chip type" +
                "\n cls -> Will clear the screen" +
                "\n set -> Needs an argument set n: Sets name of the file"+
                "\n set s: Sets the size of the bin file and erases data" +
                "\n set ct: when the Minipro is working you can use this to set the chip type";

    }

    private void writeByte() throws IOException {
        int CurrentAdr;
        if(usePrev)
        {
            Main.file.seek(Main.prevAddr);
            CurrentAdr = Main.prevAddr;
        }else
        {
            Main.file.seek(addressStart);
            Main.prevAddr = addressStart;
            CurrentAdr = addressStart;
        }
        if(isMulti)
        {
            int[] dataT = new int[dataM.length];
            for (int i = 0; i < dataT.length; i ++) {
                dataT[i] = Main.file.read();
                Main.file.seek(CurrentAdr);
                CurrentAdr ++;
                Main.file.write(dataM[i]);
            }
            DISPLAY.printHexDump(dataT, Main.prevAddr);
        }else
        {
            int[] dataT = new int[1];
            dataT[0] = Main.file.read();
            Main.file.seek(CurrentAdr);
            Main.file.write(data);
            DISPLAY.printHexDump(dataT, addressStart);
        }
    }
    private void readBytes() throws IOException {
        if(usePrev)
        {
            int[] dataT = new int[(addressEnd - Main.prevAddr) + 1];
            Main.file.seek(Main.prevAddr);
            for(int i = 0; i < dataT.length;i++)
            {
                dataT[i] = Main.file.read();
            }
            DISPLAY.printHexDump(dataT, Main.prevAddr);
        }else {
            Main.file.seek(addressStart);
            Main.prevAddr = addressStart;
            int[] dataT = new int[(addressEnd - addressStart) + 1];
            for (int i = 0; i < dataT.length; i++) {
                 dataT[i] = Main.file.read();
            }
            DISPLAY.printHexDump(dataT, addressStart);
        }
    }
    private void readByte() throws IOException {
        Main.prevAddr = addressStart;
        Main.file.seek(addressStart);
        DISPLAY.printHexDump(new int[]{Main.file.read()}, addressStart);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Command command = (Command) o;
        return CmdType == command.CmdType &&
                isMulti == command.isMulti &&
                read == command.read &&
                usePrev == command.usePrev &&
                data == command.data &&
                addressStart == command.addressStart &&
                addressEnd == command.addressEnd &&
                Arrays.equals(dataM, command.dataM) &&
                WordCMD.equals(command.WordCMD) &&
                Arrays.equals(args, command.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(CmdType, isMulti, read, usePrev, data, WordCMD, addressStart, addressEnd);
        result = 31 * result + Arrays.hashCode(dataM);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
