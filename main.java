import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class main {

    static String csvSeparator = ";";
    static HashMap<String, String> map = new HashMap();
    static DecimalFormat decimalFormat = new DecimalFormat(("#,0"));
    static Map<String, ArrayList<Double>> cityMeasurements = new HashMap();
    static ArrayList<String> values = new ArrayList<>();

    public static void main(String[] args) {
        Instant start = Instant.now();
        String csvFile = "./data/weather_stations.csv";
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty() && !line.startsWith("#")) {
                    values.add(line);
                }
            }
            checkValues();
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
        System.out.println("Elapsed time: " + timeElapsed.toMillis());
    }

    public static void checkValues() {
        for (var val: values) {
            var arr = val.split(csvSeparator);
            if (cityMeasurements.containsKey(arr[0])) {
                var city = cityMeasurements.get(arr[0]);
                var doubleValue = Double.valueOf(arr[1]);
                var dfValue = decimalFormat.format(doubleValue);
                city.add(Double.valueOf(dfValue));
            } else {
                ArrayList<Double> list = new ArrayList<>();
                var doubleValue = Double.valueOf(arr[1]);
                var dfValue = decimalFormat.format(doubleValue);
                list.add(Double.valueOf(dfValue));
                cityMeasurements.put(arr[0], list);
            }
        }
        calculateValue();
    }

    public static void calculateValue() {
        HashMap<String, String> calculatedMap = new HashMap<>();
        var lowest = 0D;
        var median = "";
        var highest = 0D;
        var value = "";
        for (Map.Entry<String, ArrayList<Double>> stringArrayListEntry : cityMeasurements.entrySet()) {
            if (stringArrayListEntry.getValue().size() >= 2) {
                var val = stringArrayListEntry.getValue();
                Collections.sort(val);
                lowest = val.get(0);
                highest = val.get(val.size() - 1);
                median = decimalFormat.format(calculateMedian(val));
                value = lowest + "/" + median + "/" + highest;
            } else {
                value = stringArrayListEntry.getValue().toString();
            }
            calculatedMap.put(stringArrayListEntry.getKey(), value);
        }
        /*var sortedMap = calculatedMap.entrySet()
                .stream()
                .sorted(Map.Entry.<String, String>comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new
                ));
        System.out.println(sortedMap);*/
    }

    public static Double calculateMedian(ArrayList<Double> values) {
        int size = values.size();
        if (size % 2 == 0) {
            return  (values.get(size / 2 -1) + values.get(size / 2)) / 2;
        } else {
            return values.get(size / 2);
        }
    }
}
