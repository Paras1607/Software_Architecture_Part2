package View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private JTextArea parcelTextArea;
    private JTextArea customerTextArea;
    private JButton processCustomerButton;
    private JButton addCustomerButton;
    private JButton addParcelButton;
    private JTextField searchParcelField;
    private JButton searchParcelButton;
    private JButton updateParcelStatusButton;
    private JButton removeCustomerButton;

    private JComboBox<String> sortOrderComboBox; // ComboBox for sort order

    public MainView() {
        setTitle("Depot Management System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Parcels Panel
        JPanel parcelPanel = new JPanel();
        parcelPanel.setLayout(new BorderLayout());
        parcelPanel.setBorder(BorderFactory.createTitledBorder("Parcels"));
        parcelTextArea = new JTextArea();
        parcelTextArea.setEditable(false);
        JScrollPane parcelScrollPane = new JScrollPane(parcelTextArea);
        parcelPanel.add(parcelScrollPane, BorderLayout.CENTER);

        // Customers Panel
        JPanel customerPanel = new JPanel();
        customerPanel.setLayout(new BorderLayout());
        customerPanel.setBorder(BorderFactory.createTitledBorder("Customer Queue"));
        customerTextArea = new JTextArea();
        customerTextArea.setEditable(false);
        JScrollPane customerScrollPane = new JScrollPane(customerTextArea);
        customerPanel.add(customerScrollPane, BorderLayout.CENTER);

        // Buttons
        JPanel buttonPanel = new JPanel();
        processCustomerButton = new JButton("Process Customer");
        addCustomerButton = new JButton("Add Customer");
        addParcelButton = new JButton("Add Parcel");
        updateParcelStatusButton = new JButton("Update Parcel Status");
        removeCustomerButton = new JButton("Remove Customer");
        buttonPanel.add(updateParcelStatusButton);
        buttonPanel.add(removeCustomerButton);
        buttonPanel.add(processCustomerButton);
        buttonPanel.add(addCustomerButton);
        buttonPanel.add(addParcelButton);

        // Search Parcel Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Parcel"));
        searchParcelField = new JTextField(15);
        searchParcelButton = new JButton("Search");
        searchPanel.add(new JLabel("Parcel ID: "));
        searchPanel.add(searchParcelField);
        searchPanel.add(searchParcelButton);

        // Sort Order Panel
        JPanel sortOrderPanel = new JPanel();
        sortOrderPanel.setLayout(new FlowLayout());
        sortOrderPanel.setBorder(BorderFactory.createTitledBorder("Sort Parcels"));
        sortOrderComboBox = new JComboBox<>(new String[]{"Ascending", "Descending"});
        sortOrderPanel.add(new JLabel("Sort by Days in Depot: "));
        sortOrderPanel.add(sortOrderComboBox);
        parcelPanel.add(sortOrderPanel, BorderLayout.SOUTH);


        // Add panels to main layout
        add(parcelPanel, BorderLayout.WEST);
        add(customerPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(searchPanel, BorderLayout.NORTH);
    }

    public JComboBox<String> getSortOrderComboBox() {
        return sortOrderComboBox;
    }

    // Getters for components
    public JTextArea getParcelTextArea() {
        return parcelTextArea;
    }

    public JTextArea getCustomerTextArea() {
        return customerTextArea;
    }

    public JButton getProcessCustomerButton() {
        return processCustomerButton;
    }

    public JButton getAddCustomerButton() {
        return addCustomerButton;
    }

    public JButton getAddParcelButton() {
        return addParcelButton;
    }

    public JTextField getSearchParcelField() {
        return searchParcelField;
    }

    public JButton getSearchParcelButton() {
        return searchParcelButton;
    }

    public JButton getUpdateParcelStatusButton() {
        return updateParcelStatusButton;
    }

    public JButton getRemoveCustomerButton() {
        return removeCustomerButton;
    }

}