import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// // File I/O: reading and writing logs to a file
public class FileActions {
    private static final String LOG_FILE = "log.txt";

    public static void saveLog(String logEntry) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry + System.lineSeparator());
        }
    }

    public static ArrayList<String> readLog() throws IOException {
        ArrayList<String> logEntries = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logEntries.add(line);
            }
        }
        return logEntries;
    }

    public static String createLogEntry(String message) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter) + " - " + message;
    }


public static void deleteLog() throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, false))) {
        writer.write("");
    }
}

}


