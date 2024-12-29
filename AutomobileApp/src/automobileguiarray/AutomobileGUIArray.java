package automobileguiarray;

import javax.swing.*;

import autocustomer.AutoCustomer;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class AutomobileGUIArray extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtCarPrice, txtCustomerType, txtDiscountedPrice, txtTotalInterest;
    private ArrayList<AutoCustomer> autoCustomersList = new ArrayList<>();

    public AutomobileGUIArray() {
        // Set up the JFrame
        setTitle("Automobile Purchase Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Labels and text fields for user input
        JLabel lblCarPrice = new JLabel("Car Price:");
        txtCarPrice = new JTextField(15);

        JLabel lblCustomerType = new JLabel("Customer Type (employee/manager):");
        txtCustomerType = new JTextField(15);

        JLabel lblDiscountedPrice = new JLabel("Discounted Price:");
        txtDiscountedPrice = new JTextField(15);
        txtDiscountedPrice.setEditable(false); // Read-only

        JLabel lblTotalInterest = new JLabel("Total Interest:");
        txtTotalInterest = new JTextField(15);
        txtTotalInterest.setEditable(false); // Read-only

        // Buttons
        JButton btnCalculate = new JButton("Calculate");
        JButton btnSubmit = new JButton("Submit to DB");
        JButton btnExit = new JButton("Exit");

        // Add components to frame
        add(lblCarPrice);
        add(txtCarPrice);
        add(lblCustomerType);
        add(txtCustomerType);
        add(lblDiscountedPrice);
        add(txtDiscountedPrice);
        add(lblTotalInterest);
        add(txtTotalInterest);
        add(btnCalculate);
        add(btnSubmit);
        add(btnExit);

        // Button Action Listeners
        btnCalculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double carPrice = Double.parseDouble(txtCarPrice.getText());
                    String customerType = txtCustomerType.getText();

                    // Create AutoCustomer object and calculate discount and interest
                    AutoCustomer autoCustomer = new AutoCustomer(carPrice, customerType);
                    autoCustomer.calculateDiscount();
                    autoCustomer.calculateInterest();

                    // Store AutoCustomer object in the list
                    autoCustomersList.add(autoCustomer);

                    // Update the discounted price and total interest fields
                    txtDiscountedPrice.setText(String.format("%.2f", autoCustomer.discountedPrice));
                    txtTotalInterest.setText(String.format("%.2f", autoCustomer.totalInterest));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.");
                }
            }
        }); 

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Insert all records into the database
                try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=loanaccounts;", "nadeem", "password")) {
                    String sql = "INSERT INTO automobile (carprice, customerType, discountedPrice, totalInterest) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);

                    for (AutoCustomer autoCustomer : autoCustomersList) {
                        stmt.setDouble(1, autoCustomer.carPrice);
                        stmt.setString(2, autoCustomer.customerType);
                        stmt.setDouble(3, autoCustomer.discountedPrice);
                        stmt.setDouble(4, autoCustomer.totalInterest);
                        stmt.addBatch();
                    }

                    stmt.executeBatch();
                    JOptionPane.showMessageDialog(null, "Data submitted to the database.");
                    autoCustomersList.clear(); // Clear the list after submission
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error submitting to the database: " + ex.getMessage());
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Exit the application
            }
        });
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AutomobileGUIArray().setVisible(true);
            }
        });
    }
}
