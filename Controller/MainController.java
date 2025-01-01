package Controller;

import Model.*;
import View.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainController {
    private MainView view;
    private ParcelMap parcelMap;
    private QueueOfCustomers customerQueue;
    private Worker worker;

    public MainController(MainView view, ParcelMap parcelMap, QueueOfCustomers customerQueue) {
        this.view = view;
        this.parcelMap = parcelMap;
        this.customerQueue = customerQueue;
        this.worker = new Worker();

        // Initialize UI
        updateParcelsDisplay();
        updateCustomersDisplay();

        // Add event listeners
        view.getProcessCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processCustomer();
            }
        });

        view.getAddCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewCustomer();
            }
        });

        view.getAddParcelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewParcel();
            }
        });

        view.getSearchParcelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchParcel();
            }
        });

        view.getUpdateParcelStatusButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateParcelStatus();
            }
        });

        view.getRemoveCustomerButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCustomer();
            }
        });

        // Add sorting functionality
        view.getSortOrderComboBox().addActionListener(e -> sortParcels());


    }

    private void sortParcels() {
        // Get selected sort order
        String sortOrder = (String) view.getSortOrderComboBox().getSelectedItem();

        // Retrieve and sort parcels
        List<Parcel> sortedParcels = new ArrayList<>(parcelMap.getAllParcels().values());
        if ("Ascending".equals(sortOrder)) {
            sortedParcels.sort(Comparator.comparingDouble(Parcel::getDaysInDepot));
        } else if ("Descending".equals(sortOrder)) {
            sortedParcels.sort(Comparator.comparingDouble(Parcel::getDaysInDepot).reversed());
        }

        // Update display with sorted parcels
        StringBuilder builder = new StringBuilder();
        for (Parcel parcel : sortedParcels) {
            builder.append(parcel.toString()).append("\n");
        }
        view.getParcelTextArea().setText(builder.toString());
    }

    private void updateParcelsDisplay() {
        StringBuilder builder = new StringBuilder();
        for (Parcel parcel : parcelMap.getAllParcels().values()) {
            builder.append(parcel.toString()).append("\n");
        }
        view.getParcelTextArea().setText(builder.toString());
    }

    private void updateCustomersDisplay() {
        StringBuilder builder = new StringBuilder();
        for (Customer customer : customerQueue.getAllCustomers()) {
            builder.append(customer.toString()).append("\n");
        }
        view.getCustomerTextArea().setText(builder.toString());
    }

    private void processCustomer() {
        if (customerQueue.isEmpty()) {
            JOptionPane.showMessageDialog(view, "No customers to process.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Display a list of customers for selection
        Object[] customers = customerQueue.getAllCustomers().toArray();
        Customer selectedCustomer = (Customer) JOptionPane.showInputDialog(
                view,
                "Select a customer to process:",
                "Process Customer",
                JOptionPane.PLAIN_MESSAGE,
                null,
                customers,
                customers.length > 0 ? customers[0] : null
        );

        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(view, "No customer selected.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Process the selected customer
        worker.processCustomer(selectedCustomer, parcelMap);
        customerQueue.removeCustomer(selectedCustomer.getName(), selectedCustomer.getParcelID());


        // Automatically remove parcel if collected
        Parcel parcel = parcelMap.getParcelByID(selectedCustomer.getParcelID());

        // Mark the parcel as collected
        parcel.setCollected(true);

        if (parcel != null && parcel.isCollected()) {
            parcelMap.collectParcel(parcel.getParcelID());
        }

        // Use the Worker class to calculate the fee
        double fee = worker.calculateFee(parcel);

        JOptionPane.showMessageDialog(view, "Customer processed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        // Display the fee
        JOptionPane.showMessageDialog(
                view,
                "Processing Customer: " + selectedCustomer.getName() +
                        "\nParcel ID: " + parcel.getParcelID() +
                        "\nFee: $" + String.format("%.2f", fee),
                "Customer Fees",
                JOptionPane.INFORMATION_MESSAGE
        );

        updateParcelsDisplay();
        updateCustomersDisplay();
    }


    private void addNewCustomer() {
        String name = JOptionPane.showInputDialog("Enter Customer Name:");
        String parcelId = JOptionPane.showInputDialog("Enter Parcel ID:");
        customerQueue.addNewCustomer(name, parcelId);
        updateCustomersDisplay();
    }

    private void addNewParcel() {
        String id = JOptionPane.showInputDialog("Enter Parcel ID:");
        double length = Double.parseDouble(JOptionPane.showInputDialog("Enter Length:"));
        double width = Double.parseDouble(JOptionPane.showInputDialog("Enter Width:"));
        double height = Double.parseDouble(JOptionPane.showInputDialog("Enter Height:"));
        int daysInDepot = Integer.parseInt(JOptionPane.showInputDialog("Enter Days in Depot:"));
        double weight = Double.parseDouble(JOptionPane.showInputDialog("Enter Weight:"));

        parcelMap.addNewParcel(id, length, width, height, daysInDepot, weight);
        updateParcelsDisplay();
    }


    private void searchParcel() {
        String id = view.getSearchParcelField().getText().trim(); // Remove any leading/trailing whitespace
        System.out.println("Searching for Parcel ID: " + id); // Debugging line
        Parcel parcel = parcelMap.getParcelByID(id);
        if (parcel != null) {
            JOptionPane.showMessageDialog(view, parcel.toString(), "Parcel Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Parcel not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateParcelStatus() {
        String id = JOptionPane.showInputDialog(view, "Enter Parcel ID to Update Status:");
        Parcel parcel = parcelMap.getParcelByID(id);

        if (parcel != null) {
            // Allow the user to choose the status
            String[] options = {"Collected", "Not Collected"};
            int choice = JOptionPane.showOptionDialog(
                    view,
                    "Choose the new status for the parcel:",
                    "Update Parcel Status",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (choice == 0) { // Collected
                parcel.setCollected(true);
                parcelMap.collectParcel(id); // Remove the parcel permanently if collected
                JOptionPane.showMessageDialog(view, "Parcel marked as collected and removed from the list.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else if (choice == 1) { // Not Collected
                parcel.setCollected(false);
                Log.getInstance().addLog("Parcel not collected: " + parcel.getParcelID());
                JOptionPane.showMessageDialog(view, "Parcel status updated to 'Not Collected'.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(view, "No status change made.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Parcel not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        updateParcelsDisplay();
    }


    private void removeCustomer() {
        String name = JOptionPane.showInputDialog(view, "Enter Customer Name:");
        String parcelId = JOptionPane.showInputDialog(view, "Enter Parcel ID:");

        boolean removed = customerQueue.removeCustomer(name, parcelId);
        if (removed) {
            Parcel parcel = parcelMap.getParcelByID(parcelId);
            if (parcel != null) {
                parcel.setCollected(true); // Mark as collected
            }
            JOptionPane.showMessageDialog(view, "Customer removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(view, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        updateCustomersDisplay();
    }



}