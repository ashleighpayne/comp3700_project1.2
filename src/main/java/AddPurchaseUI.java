import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPurchaseUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public JFrame view;

    public JButton btnAdd = new JButton("Add");
    public JButton btnCancel = new JButton("Cancel");

    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtProductID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtquantity = new JTextField(20);


    public AddPurchaseUI() {
        this.view = new JFrame();

        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("PurchaseID "));
        line1.add(txtPurchaseID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("ProductID "));
        line2.add(txtProductID);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("CustomerID "));
        line3.add(txtCustomerID);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Quantity "));
        line4.add(txtquantity);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnAdd);
        panelButtons.add(btnCancel);
        view.getContentPane().add(panelButtons);

        btnAdd.addActionListener(new AddButtonListener());

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                JOptionPane.showMessageDialog(null, "Canceling");
                view.setVisible(false);
            }
        });

    }

    public void run() {
        view.setVisible(true);
    }

    class AddButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();

            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }

            String pID = txtProductID.getText();
            if (pID.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be empty!");
                return;
            }

            try {
                purchase.mProductID = Integer.parseInt(pID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            String cID = txtCustomerID.getText();
            if (cID.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be empty!");
                return;
            }

            try {
                purchase.mCustomerID = Integer.parseInt(cID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            String quantity = txtquantity.getText();
            try {
                purchase.mQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            ProductModel product = StoreManager.getInstance().getDataAdapter().loadProduct(Integer.parseInt(pID));
            if (product.mName == null) {
                JOptionPane.showMessageDialog(null, "Aborting: Product not found");
                return;
            }
            CustomerModel customer = StoreManager.getInstance().getDataAdapter().loadCustomer(Integer.parseInt(cID));
            if (customer.mName == null) {
                JOptionPane.showMessageDialog(null, "Aborting: Customer not found");
                return;
            }
            double price = product.mPrice;
            double totalCost = price * Integer.parseInt(quantity);
            double tax = totalCost * .09;
            if (product.mProductID == 5) {
                tax = .5;
            }

            switch (StoreManager.getInstance().getDataAdapter().savePurchase(purchase)) {
                case SQLiteDataAdapter.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate product ID!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
            }

        
            String receipt = "Customer: " + customer.mName + "\nItem purchased: " + product.mName + "\nQuantity purchased: " + quantity + "\nTotal before tax: " + totalCost;
            receipt = receipt + "\nTax due(at 9%): " + tax + "\nPurchase total: " + (tax + totalCost);
            JOptionPane.showMessageDialog(null, receipt);


        }
    }

}
