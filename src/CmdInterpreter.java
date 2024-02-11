public class CmdInterpreter
{
    private final short[] VALID_SIZES = {16,32,64,128,256,512};

    private final String[] VALID_CMD_WORD = {"help", "new", "save", "write", "set"};

    private final byte STARTING_CHAR = 0;

    public CmdInterpreter()
    {}

    public boolean checkBounds(short[] data, int[] addr)
    {
        boolean tempCheck = false;
        for(short size: VALID_SIZES)
        {
            if(size == Main.fileSizeKB)
            {
                tempCheck = true;
                break;
            }
        }
        if(!tempCheck)
            return false;
        for(int address: addr)
        {
            if (address < 0 || address > Main.fileSizeBytes) {
                return false;
            }
        }
        for(short dataC: data)
        {
            if (dataC < 0 || dataC > 255) {
                return false;
            }
        }
        return true;
    }
    private boolean validCommand(String cmd)
    {
        for (String command : VALID_CMD_WORD) {
            if (command.equals(cmd)) {
                return true;
            }
        }
        return false;
    }
    public Command interpretCmd(String cmd)
    {
        if(cmd.isEmpty())
            return null;
        String cmdType;
        String tempCmd;
        boolean isMulti;
        boolean read;
        boolean usePrev;
        String wordCMD;
        String[] argument = null;
        int addrS;
        int addrE;
        short data;
        if(cmd.contains("."))
            cmdType = ".";
        else if(cmd.contains(":"))
            cmdType = ":";
        else
            cmdType = " ";
        for(String c: VALID_CMD_WORD)
        {
            if(cmd.startsWith(String.valueOf(c.toCharArray()[STARTING_CHAR])))
            {
                tempCmd = cmd.trim();
                if(tempCmd.contains(" "))
                {
                    argument = (cmd.substring(cmd.indexOf(" "))).split("\\s+");
                    wordCMD = cmd.substring(0, cmd.indexOf(" "));
                }
                return new Command(cmd, argument);
            }
        }
        tempCmd = cmd.replace(cmdType," ");
        String[] temp = tempCmd.split("\\s+");
        if(temp.length <= 2)
        {
            isMulti = false;
            if(temp.length == 2)
            {
                if(!tempCmd.startsWith(":") || !tempCmd.startsWith("."))
                {
                    usePrev = false;
                    switch (cmdType)
                    {
                        case ":":
                            read = false;
                            addrS = stringToHexToDecimal(temp[0]);
                            data = (short)stringToHexToDecimal(temp[1]);
                            return new Command(read, isMulti, usePrev, addrS, data);
                        case ".":
                            read = true;
                            addrS = stringToHexToDecimal(temp[0]);
                            addrE = stringToHexToDecimal(temp[1]);
                            return new Command(read, isMulti, usePrev, addrS, addrE);
                    }
                }else
                {
                    usePrev = true;
                    switch (cmdType) {
                        case ":":
                            read = false;
                            data = (short) stringToHexToDecimal(temp[1]);
                            return new Command(read, isMulti, usePrev, data);
                        case ".":
                            read = true;
                            addrE = stringToHexToDecimal(temp[1]);
                            return new Command(read, isMulti, usePrev, addrE);
                    }
                }
            }else{
                read = true;
                addrS = stringToHexToDecimal(temp[0]);
                return new Command(read, isMulti, usePrev, addrS);
            }
        }
        isMulti = true;

        return new Command();

    }
    private int stringToHexToDecimal(String hex)
    {
        return Integer.parseInt(hex, 16);
    }
}
