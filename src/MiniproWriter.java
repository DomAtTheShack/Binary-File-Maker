import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MiniproWriter
{
    private final String OS;
    private final String FileName;
    private final String ChipType;

    public MiniproWriter(String FileName, String ChipType)
    {
        this.OS = System.getProperty("os.name").toLowerCase();
        this.ChipType = ChipType;
        this.FileName = FileName;
    }

    public boolean WriteFile() throws IOException, InterruptedException
    {
        if(OS.contains("unix") || OS.contains("linux")) {
            String projectPath = System.getProperty("user.dir");
            ProcessBuilder processBuilder = new ProcessBuilder("minipro", "-p " + ChipType, "-w ", FileName);
            processBuilder.directory(new File(projectPath));
            processBuilder.redirectErrorStream(true); // Redirect error stream to standard output
            Process process = processBuilder.inheritIO().start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int exitCode = process.waitFor();
            return exitCode == 0;
        }else{
            System.out.println("Not Available on windows!");
            return false;
        }
    }
}
