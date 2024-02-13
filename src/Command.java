public class Command
{
    private char CmdType;
    private boolean isMulti;
    private boolean read;
    private boolean usePrev;
    private short[] dataM;
    private int data;
    private String WordCMD;
    private int addressStart;
    private int addressEnd;
    private String[] args;


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
}
