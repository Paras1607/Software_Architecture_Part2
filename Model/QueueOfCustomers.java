package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class QueueOfCustomers {
    private Queue<Customer> customerQueue;

    public QueueOfCustomers() {
        customerQueue = new LinkedList<>();
    }

    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
    }

    public Customer getNextCustomer() {
        return customerQueue.poll();
    }

    public boolean isEmpty() {
        return customerQueue.isEmpty();
    }

    public List<Customer> getAllCustomers() {
        return new LinkedList<>(customerQueue); // Return a copy for safety
    }

    public void addNewCustomer(String name, String parcelId) {
        addCustomer(new Customer(name, parcelId));
        Log.getInstance().addLog("New customer added: " + name + " | Parcel ID: " + parcelId);
    }


    public void loadCustomersFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    String parcelId = parts[1].trim();
                    addCustomer(new Customer(name, parcelId));
                }
            }
        }
    }

    public boolean removeCustomer(String name, String parcelId) {
        for (Customer customer : customerQueue) {
            if (customer.getName().equalsIgnoreCase(name) && customer.getParcelID().equalsIgnoreCase(parcelId)) {
                customerQueue.remove(customer);
                Log.getInstance().addLog("Customer removed: " + name + " | Parcel ID: " + parcelId);
                return true;
            }
        }
        return false; // Customer not found
    }

}
