import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class main {

    public static void main() {
        Instant start = Instant.now();
        String csvFile = "./data/weather_stations.csv";
        BufferedReader br = null;
        String line;
        String csvSeparator = ";";
        int rowCount = 0;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    rowCount++;
                }
            }
            System.out.println("Total number of rows: " + rowCount);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println("Elapsed time: " + timeElapsed.toSeconds());
    }
}
