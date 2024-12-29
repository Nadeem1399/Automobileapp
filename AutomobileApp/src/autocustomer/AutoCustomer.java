package autocustomer;

public class AutoCustomer {
    public double carPrice;
    public String customerType; // e.g., "employee", "manager"
    public double discount;
    public double discountedPrice;
    public double totalInterest;

    // Constructor
    public AutoCustomer(double carPrice, String customerType) {
        this.carPrice = carPrice;
        this.customerType = customerType;
    }

    // Method to calculate the discount based on customer type
    public void calculateDiscount() {
        if ("employee".equalsIgnoreCase(customerType)) {
            this.discount = 0.08; // 8% discount for employees
        } else if ("manager".equalsIgnoreCase(customerType)) {
            this.discount = 0.12; // 12% discount for managers
        } else {
            this.discount = 0.0; // No discount for other customer types
        }

        // Calculate the discounted price
        this.discountedPrice = carPrice * (1 - this.discount);
    }

    // Method to calculate total interest (assuming simple interest)
    public void calculateInterest() {
        // For simplicity, assume a fixed interest rate
        double interestRate = 0.05; // 5% interest rate
        this.totalInterest = carPrice * interestRate; // Simple interest
    }

    // Overridden toString() method to display all fields
    @Override
    public String toString() {
        return "AutoCustomer{" +
                "carPrice=" + carPrice +
                ", customerType='" + customerType + '\'' +
                ", discount=" + discount +
                ", discountedPrice=" + discountedPrice +
                ", totalInterest=" + totalInterest +
                '}';
    }
}
