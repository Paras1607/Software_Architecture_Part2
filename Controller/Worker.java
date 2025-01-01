package Controller;

import Model.*;

public class Worker {
    private static final double BASE_FEE = 5.0;

    public double calculateFee(Parcel parcel) {
        double baseFee = 10.0;
        double weightFee = parcel.getWeight() * 0.5;
        double dimensionFee = parcel.getLength() * parcel.getWidth() * parcel.getHeight() * 0.1;
        double daysFee = parcel.getDaysInDepot() * 0.2;
        return baseFee + weightFee + dimensionFee + daysFee;
    }


    public void processCustomer(Customer customer, ParcelMap parcelMap) {
        Log log = Log.getInstance();
        Parcel parcel = parcelMap.getParcelByID(customer.getParcelID());

        if (parcel != null) {
            double fee = calculateFee(parcel);
            log.addLog("Processing Customer: " + customer.getName() + " | Parcel ID: " + parcel.getParcelID() + " | Fee: $" + fee);
            parcel.collect();
        } else {
            log.addLog("Parcel not found for Customer: " + customer.getName() + " | Parcel ID: " + customer.getParcelID());
        }
    }

    public void removeCustomer(Customer customer) {
        Log.getInstance().addLog("Customer removed: " + customer.getName());
    }


}
