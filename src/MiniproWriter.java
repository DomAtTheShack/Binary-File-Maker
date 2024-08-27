import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Set;

public class MiniproWriter
{
    private final String OS;
    private final String FileName;
    private final String ChipType;
    private final String lilaURL = "http://lila.domserver.xyz/programs/notMyPrmgs/minipro";
    private final String fileName = System.getProperty("user.home") + "/minipro";
    private boolean downloaded;


    public MiniproWriter(String FileName, String ChipType)
    {
        this.OS = System.getProperty("os.name").toLowerCase();
        this.ChipType = ChipType;
        this.FileName = FileName;
        downloaded = false;
    }

    public boolean WriteFile() throws IOException, InterruptedException
    {
        try {
            if (OS.contains("unix") || OS.contains("linux")) {
                String projectPath = System.getProperty("user.dir");
                ProcessBuilder processBuilder;
                if(!downloaded)
                    processBuilder = new ProcessBuilder("minipro", "-p" + ChipType, "-w", FileName);
                else
                    processBuilder = new ProcessBuilder(fileName, "-p" + ChipType, "-w", FileName);

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
            } else {
                System.out.println("Not Available on windows!");
                return false;
            }
        }catch (IOException e)
        {
            getMiniproPrg();
            return false;
        }
    }

    private void getMiniproPrg() throws IOException {
        URL miniURL = new URL(lilaURL);
        HttpURLConnection httpCon = (HttpURLConnection) miniURL.openConnection();
        httpCon.setRequestProperty("User-Agent", "Mozilla/5.0");
        httpCon.setRequestMethod("GET");

        ArrayList<Integer> miniproData = new ArrayList<>();
        try (BufferedInputStream in = new BufferedInputStream(httpCon.getInputStream());
             FileOutputStream fileOut = new FileOutputStream(fileName)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOut.write(dataBuffer, 0, bytesRead);
            }
        }

        httpCon.disconnect();

        Path filePath = Paths.get(fileName);
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxr-xr-x"); // owner: read/write/execute, group: read/execute, others: read/execute
        Files.setPosixFilePermissions(filePath, perms);

        System.out.println("Downloaded minipro Program in Root of User Dir.");
        downloaded = true;
    }
}
