public class CmdInterpreter
{
    private final short[] VALID_SIZES = {16,32,64,128,256,512};

    private final String[] VALID_CMD = {"help", "new", "save", "write", "set", ":", "."};

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
        for (String command : VALID_CMD) {
            if (command.equals(cmd)) {
                return true;
            }
        }
        return false;
    }
    /*public Command interpretCmd(String cmd)
    {

    }*/
}
