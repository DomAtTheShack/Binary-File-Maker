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
        short[] dataA;
        if(cmd.contains("."))
            cmdType = ".";
        else if(cmd.contains(":"))
            cmdType = ":";
        else
            cmdType = " ";
        for(String c: VALID_CMD_WORD) // Check if the cmd is a Word command
        {
            if(cmd.equals(c))
            {
                tempCmd = cmd.trim();
                if(tempCmd.contains(" ")) // If found trim leading space, then if space is found, grab the argument
                {
                    argument = (cmd.substring(cmd.indexOf(" "))).split("\\s+");
                    wordCMD = cmd.substring(0, cmd.indexOf(" "));
                }
                return new Command(cmd, argument); // Return word cmd with argument if it has one
            }
        }
        tempCmd = cmd.replace(cmdType," ").trim();
        String[] temp = tempCmd.split("\\s+"); //Replace all key chars ':' or '.' with space
                                                    // then split string into an array and cut at the space
        if(temp.length <= 2 || checkStartingChar(cmd.trim())) // Check if the input has one or two parts meaning a cmd like 5F.4B or FF
        {
            isMulti = false;
            if (!checkStartingChar(cmd)) {
                usePrev = false;
                switch (cmdType) // sets all the booleans beforehand
                // stating that it's not a multi-access and its not anonymous access
                {
                    case ":":
                        read = false;
                        addrS = stringToHexToDecimal(temp[0]);
                        data = (short) stringToHexToDecimal(temp[1]);
                        return new Command(read, isMulti, cmdType, usePrev, addrS, data); // Return a write Command
                        // with proper stating address and data to write
                    case ".":
                        read = true;
                        addrS = stringToHexToDecimal(temp[0]);
                        addrE = stringToHexToDecimal(temp[1]);
                        return new Command(read, isMulti, cmdType, usePrev, addrS, addrE); // Return a read chunk cmd
                        // with start and stop address
                    default:
                        break;
                }
            } else {
                usePrev = true;
                if(!checkStartingChar(cmd) || temp.length == 1) {
                    switch (cmdType) { // Say that you are using the previous value
                        // and give the proper end address and/or data
                        case ":":
                            read = false;
                            data = (short) stringToHexToDecimal(temp[0]);
                            return new Command(read, isMulti, cmdType, usePrev, data);
                        case ".":
                            read = true;
                            addrE = stringToHexToDecimal(temp[0]);
                            return new Command(read, isMulti, cmdType, usePrev, addrE);
                    }
                }
            }
            if(!checkStartingChar(cmd)) {
                usePrev = false;
                read = true;
                addrS = stringToHexToDecimal(temp[0]);
                return new Command(read, isMulti, cmdType, usePrev, addrS);
            }
            }
        isMulti = true;
        if(!cmd.startsWith(":"))
        { // Multi can only take in a : write or single lookup
            usePrev = false;
            read = false;
            addrS = stringToHexToDecimal(temp[0]);
            dataA = readInData(temp, usePrev);
            return new Command(read, isMulti, cmdType, usePrev, addrS, dataA); // Return a write Command
                // with proper stating address and data to write
        }else if (cmd.startsWith(".") || cmd.startsWith(":")) // If not do an anonymous multi write
        {
            if(cmd.startsWith(":")) {
                usePrev = true;
                read = false;
                dataA = readInData(temp, usePrev);
                return new Command(read, isMulti, cmdType, usePrev, dataA);
            }else {
                usePrev = true;
                read = true;
                dataA = readInData(temp, true);
                return new Command(read, isMulti, cmdType, usePrev, dataA);
            }
        }

    usePrev = false;
    read = true;
    dataA = readInData(temp, true);
    return new Command(read, isMulti, cmdType, usePrev, dataA);
    }
    private int stringToHexToDecimal(String hex)
    {
        return Integer.parseInt(hex, 16);
    }
    private boolean checkStartingChar(@org.jetbrains.annotations.NotNull String tempCmd)
    {
        return tempCmd.startsWith(":") || tempCmd.startsWith(".");
    }
    private short[] readInData(String[] dataA, boolean anny)
    {
        int i = anny ? 0 : 1;
        short[] shortData = new short[dataA.length];
        for (i = 0; i < dataA.length; i++) {
            shortData[i] = (short) stringToHexToDecimal(dataA[i]);
        }
        return shortData;
    }

}
