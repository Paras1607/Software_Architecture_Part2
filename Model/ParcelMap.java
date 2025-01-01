package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParcelMap {
    private Map<String, Parcel> parcels;

    public ParcelMap() {
        parcels = new HashMap<>();
    }

    public void addParcel(Parcel parcel) {
        parcels.put(parcel.getParcelID(), parcel);
    }

    public void addNewParcel(String id, double length, double width, double height, int daysInDepot, double weight) {
        Parcel parcel = new Parcel(id, length, width, height, daysInDepot, weight);
        addParcel(parcel);
        Log.getInstance().addLog("New parcel added: " + id);
    }

    public Map<String, Parcel> getAllParcels() {
        return parcels;
    }


    public Parcel getParcelByID(String parcelID) {
        return parcels.get(parcelID);
    }

    public boolean containsParcel(String id) {
        return parcels.containsKey(id);
    }

    public void loadParcelsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String id = parts[0].trim();
                    double length = Double.parseDouble(parts[1].trim());
                    double width = Double.parseDouble(parts[2].trim());
                    double height = Double.parseDouble(parts[3].trim());
                    int daysInDepot = Integer.parseInt(parts[4].trim());
                    double weight = Double.parseDouble(parts[5].trim());

                    Parcel parcel = new Parcel(id, length, width, height, daysInDepot, weight);
                    addParcel(parcel);
                    // addParcel(new Parcel(id, length, width, height, daysInDepot, weight));
                    // Debugging - Check if parcels are added correctly
                    System.out.println("Loaded Parcel: " + parcel.toString());
                }
            }
        }
    }
    public Map<String, Parcel> getParcels() {
        return parcels;
    }

    public void removeParcel(String parcelID) {
        parcels.remove(parcelID);
        Log.getInstance().addLog("Parcel removed: " + parcelID);
    }

    public void collectParcel(String parcelID) {
        parcels.remove(parcelID);
        Log.getInstance().addLog("Parcel collected: " + parcelID);
    }

    public Parcel findParcelById(String id) {
        if (parcels.containsKey(id)) {
            return parcels.get(id);
        }
        return null;
    }

}
