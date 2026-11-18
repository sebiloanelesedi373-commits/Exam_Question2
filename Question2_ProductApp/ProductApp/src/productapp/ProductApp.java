package productapp;

import javax.swing.*;
import java.awt.GridLayout;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * GUI application that captures a customer sale, calculates VAT,
 * commission and total, shows the result and logs it to data.txt.
 */
public class ProductApp extends JFrame {

    // Q.2.2 - the three mattress products for the combo box
    private static final String[] PRODUCTS =
            {"Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"};

    private static final String FILE_NAME = "data.txt";

    private final JTextField txtName = new JTextField(15);
    private final JComboBox<String> cboProduct = new JComboBox<>(PRODUCTS);
    private final JTextField txtPrice = new JTextField(15);

    public ProductApp() {
        super("Product App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 5, 10));

        // Q.2.2 - labels and input controls
        add(new JLabel("Customer Name:"));
        add(txtName);
        add(new JLabel("Select Product:"));
        add(cboProduct);
        add(new JLabel("Enter Price:"));
        add(txtPrice);
        add(new JLabel());                 // empty cell so the button sits on the right
        JButton btnProcess = new JButton("PROCESS");
        add(btnProcess);

        // Q.2.3 - the Process button runs the shared processing method
        btnProcess.addActionListener(e -> processSale());

        setJMenuBar(createMenuBar());
        pack();
        setLocationRelativeTo(null);
    }

    // Q.2.4 - File > Exit and Tools > Process / Clear
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        processItem.addActionListener(e -> processSale()); // same logic as the button
        clearItem.addActionListener(e -> clearForm());
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);

        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        return menuBar;
    }

    // Shared logic for the Process button and the Process menu item
    private void processSale() {
        String name = txtName.getText().trim();
        String product = (String) cboProduct.getSelectedItem();

        // Validate the input before calculating
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the customer name.",
                    "Missing Data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(txtPrice.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric price.",
                    "Invalid Price", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Calculate using the ProductCalculations class
        IProductCalculations calc = new ProductCalculations(price);
        double vat = calc.GetVat();
        double commission = calc.GetCommission();
        double total = calc.GetTotal();

        // Display the details to the user
        String message = "Customer Name: " + name
                + "\nProduct: " + product
                + "\nVAT: R " + vat
                + "\nCOMMISSION: R " + commission
                + "\nTOTAL: R " + total;
        JOptionPane.showMessageDialog(this, message, "Message",
                JOptionPane.INFORMATION_MESSAGE);

        // Q.2.3 - store the details in data.txt
        saveToFile(name, product, vat, commission, total);
    }

    // Appends the sale details to data.txt
    private void saveToFile(String name, String product,
                            double vat, double commission, double total) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println("DATA LOG");
            writer.println("*".repeat(40));
            writer.println("Customer Name: " + name);
            writer.println("Product: " + product);
            writer.println("VAT: R " + vat);
            writer.println("COMMISSION: R " + commission);
            writer.println("TOTAL: R " + total);
            writer.println("*".repeat(40));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save to " + FILE_NAME,
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Clear menu: empty the text boxes and reset the combo box to index 0
    private void clearForm() {
        txtName.setText("");
        txtPrice.setText("");
        cboProduct.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductApp().setVisible(true));
    }
}
