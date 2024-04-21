import javax.swing.*;

public class OrderFormGUI {
    public static void main(String[] args) {
        int confirm;
        do {
            /** DO NOT CHANGE VALUES BELOW **/
            boolean hoodieInStock = true;
            boolean tshirtInStock = false;
            boolean longsleeveInStock = true;
            String item = "";
            int quantity = 0;
            String name = "";
            /** DO NOT CHANGE VALUES ABOVE **/

            String[] options = {"Hoodie", "T-shirt", "Long sleeve"};
            boolean option;
            do {
                item = (String) JOptionPane.showInputDialog(null, "Select item style ", "Order Form",
                        JOptionPane.PLAIN_MESSAGE, null, options, null);
                if (item.equals("Hoodie")) {
                    option = hoodieInStock;
                } else if (item.equals("T-shirt")) {
                    option = tshirtInStock;
                } else {
                    option = longsleeveInStock;
                }
                if (!option) {
                    JOptionPane.showMessageDialog(null, "Out of Stock", "Order Form",
                            JOptionPane.ERROR_MESSAGE);
                }
            } while (!option);

            boolean valid;
            do {
                try {
                    do {
                        quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter quantity", "Order Form",
                                JOptionPane.QUESTION_MESSAGE));
                        if (quantity < 1) {
                            JOptionPane.showMessageDialog(null, "Enter a number greater than 0",
                                    "Order Form", JOptionPane.ERROR_MESSAGE);
                        }
                        valid = true;
                    } while (quantity < 1);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Enter an integer",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                    valid = false;
                }
            } while (!valid);

            boolean hasSpace;
            do {
                name = JOptionPane.showInputDialog(null, "Enter your Name", "Order Form",
                        JOptionPane.QUESTION_MESSAGE);
                hasSpace = name.contains(" ");
                if (!hasSpace) {
                    JOptionPane.showMessageDialog(null, "Enter first and last name",
                            "Order Form", JOptionPane.ERROR_MESSAGE);
                }
            } while (!hasSpace);


            /** Order Confirmation Message **/
            String resultMessage = "Name: " + name + "\nItem: " + item + "\nQuantity: " + quantity;
            JOptionPane.showMessageDialog(null, resultMessage, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);

            confirm = JOptionPane.showConfirmDialog(null, "Would you like to place another order?", "Order Form", JOptionPane.YES_NO_OPTION);
        } while (confirm == JOptionPane.YES_OPTION);

    }
}
