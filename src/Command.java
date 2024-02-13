import java.util.Arrays;
import java.util.Objects;

public class Command
{
    private char CmdType;
    private boolean isMulti;
    private boolean read;
    private boolean usePrev;
    private short[] dataM;
    private int data;
    private String WordCMD = "";
    private int addressStart;
    private int addressEnd;
    private String[] args = new String[]{};


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
        this.data = dataOrAddress;
    }
    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, int addrS, short[] data)
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
        this.data = dataOrAddress;
    }
    public Command(boolean read, boolean isMulti, String cmdType,
                   boolean usePrev, short[] data)
    {
        this.read = read;
        this.isMulti = isMulti;
        this.CmdType = cmdType.charAt(0);
        this.usePrev = usePrev;
        this.dataM = data;
    }

    public char getCmdType() {
        return CmdType;
    }

    public boolean isMulti() {
        return isMulti;
    }

    public boolean isRead() {
        return read;
    }

    public boolean isUsePrev() {
        return usePrev;
    }

    public short[] getDataM() {
        return dataM;
    }

    public int getData() {
        return data;
    }

    public String getWordCMD() {
        return WordCMD;
    }

    public int getAddressStart() {
        return addressStart;
    }

    public int getAddressEnd() {
        return addressEnd;
    }

    public String[] getArgs() {
        return args;
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
