package Controller;

import Model.*;
import View.MainView;

import java.io.IOException;
import java.util.stream.Collectors;

public class Manager {
    public static void main(String[] args) {
        try {
            // Load parcels from file
            ParcelMap parcelMap = new ParcelMap();
            parcelMap.loadParcelsFromFile("Parcels.csv");

            // Load customers from file
            QueueOfCustomers queueOfCustomers = new QueueOfCustomers();
            queueOfCustomers.loadCustomersFromFile("Custs.csv");

            // Initialize GUI and connect with data
            MainView view = new MainView();
            MainController controller = new MainController(view, parcelMap, queueOfCustomers);

            // Make the GUI visible
            view.setVisible(true);

            // Log initialization events
            Log.getInstance().addLog("Application initialized with loaded data.");

            // Add shutdown hook to print parcels and write log to a file when the program exits
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    // Append to log
                    Log log = Log.getInstance();

                    // Write log to file
                    log.writeLogToFile("Report.txt");
                    System.out.println("Log written to Report.txt");
                } catch (IOException e) {
                    System.err.println("Failed to write log to file: " + e.getMessage());
                }
            }));

        } catch (IOException e) {
            System.err.println("Error loading files: " + e.getMessage());
        }
    }
}
