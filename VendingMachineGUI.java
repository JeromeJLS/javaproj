import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class represents a graphical user interface for a vending machine.
 * It allows users to interact with the vending machine, purchase items, and perform maintenance tasks.
 */
public class VendingMachineGUI extends JFrame {
    private VendingMachine vendingMachine;
    private JTextField coinInputField;
    private Set<Integer> validCoinDenominations;
    private double accumulatedAmount;
    private double internalBalance;
    private List<String> transactionHistory;

    /**
     * Constructs a new VendingMachineGUI instance and initializes the GUI components.
     */

    public VendingMachineGUI() {
        vendingMachine = new VendingMachine();
        vendingMachine.initializeItems();
        validCoinDenominations = new HashSet<>();
        validCoinDenominations.add(1);
        validCoinDenominations.add(5);
        validCoinDenominations.add(10);
        validCoinDenominations.add(20);
        validCoinDenominations.add(50);
        validCoinDenominations.add(100);
        validCoinDenominations.add(200); // Add 200 coin denomination
        validCoinDenominations.add(500); // Add 500 coin denomination
        validCoinDenominations.add(1000); // Add 1000 coin denomination
        internalBalance = 200.0;
        accumulatedAmount = 0.0;
        transactionHistory = new ArrayList<>(); // Initialize transactionHistory
        createGUI();
    }

    /**
     * Initializes the vending machine GUI and creates the main user interface.
     */
    private void createGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Vending Machine");
        setLayout(new BorderLayout());

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(1, 2));

        JButton specialButton = new JButton("Special");
        specialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySpecialVendingFeatures(); // Show special vending machine features
            }
        });
        menuPanel.add(specialButton);

        JButton regularButton = new JButton("Regular");
        regularButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayRegularVendingFeatures();
            }
        });
        menuPanel.add(regularButton);

        add(menuPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Displays the regular vending machine features, including items, prices, and purchasing.
     */
    private void displayRegularVendingFeatures() {
        JFrame regularVendingFrame = new JFrame("Regular Vending Machine Features");
        regularVendingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        regularVendingFrame.setLayout(new BorderLayout());

        JPanel regularVendingPanel = new JPanel();
        regularVendingPanel.setLayout(new GridLayout(1, 2));

        JButton vendingButton = new JButton("Vending Features");
        vendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayVendingFeatures();
            }
        });
        regularVendingPanel.add(vendingButton);

        JButton maintenanceButton = new JButton("Maintenance Features");
        maintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayMaintenanceFeatures();
            }
        });
        regularVendingPanel.add(maintenanceButton);

        regularVendingFrame.add(regularVendingPanel, BorderLayout.CENTER);
        regularVendingFrame.pack();
        regularVendingFrame.setLocationRelativeTo(null);
        regularVendingFrame.setVisible(true);
    }

    private void displayVendingFeatures() {
        JFrame vendingFrame = new JFrame("Vending Features");
        vendingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vendingFrame.setLayout(new BorderLayout());

        JPanel vendingPanel = new JPanel();
        vendingPanel.setLayout(new GridLayout(4, 2));

        String[] items = vendingMachine.getItems();
        double[] prices = vendingMachine.getPrices();
        int[] calories = vendingMachine.getCalories();
        int[] quantities = vendingMachine.getQuantities();

        for (int i = 0; i < items.length; i++) {
            final int itemIndex = i;
            String itemName = items[i];
            double itemPrice = prices[i];
            int itemCalories = calories[i];
            int itemQuantity = quantities[i];

            JButton itemButton = new JButton();
            if (itemQuantity > 0) {
                itemButton.setText(itemName + " - \u20B1" + itemPrice + " - Calories: " + itemCalories + " - Quantity: "
                        + itemQuantity);
                itemButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handlePurchase(itemName, itemPrice, itemIndex);
                    }
                });
            } else {
                itemButton.setEnabled(false); // Disable the button if quantity is zero
                itemButton.setText("Out of Stock");
            }
            vendingPanel.add(itemButton);
        }

        // Add the coin input field
        coinInputField = new JTextField(20);
        JButton insertCoinButton = new JButton("Insert Coin");
        insertCoinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double coinAmount = getAmountPaidFromCoins(coinInputField);
                accumulatedAmount += coinAmount;
                JOptionPane.showMessageDialog(null, "Amount inserted: \u20B1" + coinAmount, "Coin Inserted",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        vendingPanel.add(new JLabel("Enter Coins (1, 5, 10, 20, 50, 100, 200, 500, 1000):"));
        vendingPanel.add(coinInputField);
        vendingPanel.add(insertCoinButton);

        JButton payButton = new JButton("Accumulated Money");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (accumulatedAmount > 0) {
                    JOptionPane.showMessageDialog(null, "Accumulated amount: \u20B1" + accumulatedAmount,
                            "Payment Summary", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Please insert coins before clicking Pay.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        vendingPanel.add(payButton);

        vendingFrame.add(vendingPanel, BorderLayout.CENTER);
        vendingFrame.pack();
        vendingFrame.setLocationRelativeTo(null);
        vendingFrame.setVisible(true);
    }

    /**
     * Handles the purchase of a regular item from the vending machine.
     *
     * @param itemName   The name of the item being purchased.
     * @param itemPrice  The price of the item being purchased.
     * @param itemIndex  The index of the item being purchased.
     */

    private void handlePurchase(String itemName, double itemPrice, int itemIndex) {
        double requiredAmount = itemPrice;

        if (accumulatedAmount < requiredAmount) {
            JOptionPane.showMessageDialog(null, "Please insert the required amount before purchasing.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int choice = JOptionPane.showConfirmDialog(null,
                "Do you want to use your accumulated amount for this purchase?", "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            if (itemIndex != -1) {
                int itemQuantity = vendingMachine.getQuantities()[itemIndex];
                if (itemQuantity == 0) {
                    JOptionPane.showMessageDialog(null, "Item out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double change = accumulatedAmount - requiredAmount;

                if (change <= internalBalance) {
                    vendingMachine.purchaseItem(itemName);
                    internalBalance -= change;
                    JOptionPane.showMessageDialog(null, "Item purchased: " + itemName + "\nChange: ₱" + change,
                            "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
                    vendingMachine.updateItemQuantity(itemIndex, itemQuantity - 1);

                    // Add the purchase to the transaction history
                    String purchaseInfo = "Item purchased: " + itemName + " - Amount paid: ₱" + accumulatedAmount
                            + " - Change: ₱" + change;
                    transactionHistory.add(purchaseInfo);
                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient change in the machine.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                accumulatedAmount = 0.0;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Purchase canceled.", "Purchase Canceled",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Handles the insertion of coins and calculates the accumulated amount.
     *
     * @param coinInputField The input field containing coin denominations.
     * @return The accumulated amount from the inserted coins.
     */    
    private double getAmountPaidFromCoins(JTextField coinInputField) {
        String coinInput = coinInputField.getText();
        if (coinInput.isEmpty()) {
            return 0.0;
        }

        String[] coinTokens = coinInput.split("\\s+");
        double amountPaid = 0.0;

        for (String coinToken : coinTokens) {
            try {
                int coin = Integer.parseInt(coinToken);
                if (validCoinDenominations.contains(coin)) {
                    amountPaid += coin;
                } else {
                    // Invalid coin denomination entered, show an error message
                    JOptionPane.showMessageDialog(null, "Invalid coin denomination: " + coin, "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                // Handle invalid input (non-numeric coins)
                JOptionPane.showMessageDialog(null, "Invalid coin denomination: " + coinToken, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        return amountPaid;
    }

    private void handleSpecialItemPurchase(String itemName, double itemPrice) {
        int choice = JOptionPane.showConfirmDialog(null, "Purchase " + itemName + "?", "Purchase Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            int specialItemIndex = vendingMachine.getSpecialPurchaseItemIndex(itemName);
            int currentQuantity = vendingMachine.getSpecialPurchaseQuantities()[specialItemIndex];

            if (currentQuantity > 0) {
                double remainingAmount = itemPrice - accumulatedAmount;

                if (remainingAmount <= 0) {
                    // Sufficient amount has been paid, deduct the item's quantity
                    vendingMachine.updateSpecialPurchaseItemQuantity(specialItemIndex, currentQuantity - 1);

                    // Update transaction history
                    String purchaseInfo = "Special Purchase: " + itemName + " - \u20B1" + itemPrice;
                    transactionHistory.add(purchaseInfo);

                    JOptionPane.showMessageDialog(null, "Purchase successful! Enjoy your " + itemName + ".",
                            "Purchase Success", JOptionPane.INFORMATION_MESSAGE);
                    accumulatedAmount = Math.abs(remainingAmount); // Set accumulated amount to the change
                } else {
                    // Insufficient amount has been paid, ask the user to insert more coins
                    JOptionPane.showMessageDialog(null,
                            "Please insert more coins. Amount remaining: \u20B1" + remainingAmount,
                            "Insufficient Amount", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                // Special item is out of stock
                JOptionPane.showMessageDialog(null, "Sorry, " + itemName + " is out of stock.", "Out of Stock",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * Displays the maintenance features for the vending machine.
     */    
    private void displayMaintenanceFeatures() {
        JFrame maintenanceFrame = new JFrame("Maintenance Features");
        maintenanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        maintenanceFrame.setLayout(new BorderLayout());

        JPanel maintenancePanel = new JPanel();
        maintenancePanel.setLayout(new GridLayout(5, 1));

        JButton restockButton = new JButton("Restock Items");
        restockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vendingMachine.restockAllItems();
                JOptionPane.showMessageDialog(null, "All items restocked.", "Maintenance",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        maintenancePanel.add(restockButton);

        JButton stockingSpecificButton = new JButton("Stock Specific Items");
        stockingSpecificButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockSpecificItems();
            }
        });
        maintenancePanel.add(stockingSpecificButton);

        JButton setPriceButton = new JButton("Set Price of Item");
        setPriceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPriceOfItem();
            }
        });
        maintenancePanel.add(setPriceButton);

        JButton collectPaymentButton = new JButton("Collect Payment/Money");
        collectPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectPayment();
            }
        });
        maintenancePanel.add(collectPaymentButton);

        JButton replenishMoneyButton = new JButton("Replenish Money");
        replenishMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replenishMoney();
            }
        });
        maintenancePanel.add(replenishMoneyButton);

        JButton printSummaryButton = new JButton("Print Transaction Summary");
        printSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTransactionSummary();
            }
        });
        maintenancePanel.add(printSummaryButton);

        maintenanceFrame.add(maintenancePanel, BorderLayout.CENTER);
        maintenanceFrame.pack();
        maintenanceFrame.setLocationRelativeTo(null);
        maintenanceFrame.setVisible(true);
    }
    /**
     * Prints a summary of transaction history to a dialog box.
     */
    private void printTransactionSummary() {
        StringBuilder summaryText = new StringBuilder("Transaction Summary:\n\n");

        for (String purchaseInfo : transactionHistory) {
            summaryText.append(purchaseInfo).append("\n");
        }

        JTextArea textArea = new JTextArea(15, 40);
        textArea.setText(summaryText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Transaction Summary",
                JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * Allows the user to stock specific items with additional quantities.
     */
    private void stockSpecificItems() {
        // Get the list of available items
        String[] availableItems = vendingMachine.getItems();

        // Create a list of available item names for the dialog
        String[] itemNames = new String[availableItems.length];
        for (int i = 0; i < availableItems.length; i++) {
            itemNames[i] = availableItems[i];
        }

        // Show a dialog to select the item to stock
        String selectedItem = (String) JOptionPane.showInputDialog(
                this,
                "Select the item to stock:",
                "Stock Specific Items",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemNames,
                itemNames[0]);

        if (selectedItem != null) {
            int itemIndex = vendingMachine.getItemIndex(selectedItem);
            int currentQuantity = vendingMachine.getQuantities()[itemIndex];
            int defaultQuantity = 10; // Set the default quantity here

            // Show a dialog to enter the quantity
            JTextField quantityField = new JTextField(5);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(1, 2));
            inputPanel.add(new JLabel("Enter quantity:"));
            inputPanel.add(quantityField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    inputPanel,
                    "Stock Item",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int additionalQuantity = Integer.parseInt(quantityField.getText());

                    if (additionalQuantity <= 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid quantity entered. Please enter a valid positive value.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int totalQuantity = currentQuantity + additionalQuantity;
                        if (totalQuantity > defaultQuantity) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Total quantity cannot exceed the default quantity (" + defaultQuantity + ").",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            vendingMachine.updateItemQuantity(itemIndex, totalQuantity);
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Item '" + selectedItem + "' has been restocked with an additional quantity of "
                                            + additionalQuantity,
                                    "Stock Successful",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid input format. Please enter a valid numeric value.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Allows the user to set the price of a specific item.
     */
    private void setPriceOfItem() {
        // Get the list of available items
        String[] availableItems = vendingMachine.getItems();

        // Create a list of available item names for the dialog
        String[] itemNames = new String[availableItems.length];
        for (int i = 0; i < availableItems.length; i++) {
            itemNames[i] = availableItems[i];
        }

        // Show a dialog to select the item to set the price
        String selectedItem = (String) JOptionPane.showInputDialog(
                this,
                "Select the item to set the price:",
                "Set Price of Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                itemNames,
                itemNames[0]);

        if (selectedItem != null) {
            int itemIndex = vendingMachine.getItemIndex(selectedItem);

            // Show a dialog to enter the new price
            JTextField priceField = new JTextField(5);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(1, 2));
            inputPanel.add(new JLabel("Enter new price:"));
            inputPanel.add(priceField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    inputPanel,
                    "Set Price",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    double newPrice = Double.parseDouble(priceField.getText());

                    if (newPrice <= 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid price entered. Please enter a valid positive value.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        vendingMachine.getPrices()[itemIndex] = newPrice;
                        JOptionPane.showMessageDialog(
                                this,
                                "Price of item '" + selectedItem + "' has been set to ₱" + newPrice,
                                "Price Set",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid input format. Please enter a valid numeric value.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Displays the special vending machine features, including special items and purchasing.
     */
    private void displaySpecialVendingFeatures() {
        JFrame specialVendingFeaturesFrame = new JFrame("Special Vending Features");
        specialVendingFeaturesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        specialVendingFeaturesFrame.setLayout(new BorderLayout());

        JPanel specialVendingPanel = new JPanel();
        specialVendingPanel.setLayout(new GridLayout(1, 2));

        JButton specialVendingButton = new JButton("Special Vending Features");
        specialVendingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySpecialItems();
            }
        });
        specialVendingPanel.add(specialVendingButton);

        JButton specialMaintenanceButton = new JButton("Special Maintenance Features");
        specialMaintenanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySpecialMaintenanceFeatures();
            }
        });
        specialVendingPanel.add(specialMaintenanceButton);

        specialVendingFeaturesFrame.add(specialVendingPanel, BorderLayout.CENTER);
        specialVendingFeaturesFrame.pack();
        specialVendingFeaturesFrame.setLocationRelativeTo(null);
        specialVendingFeaturesFrame.setVisible(true);
    }
    /**
     * Displays the special items available for purchase in the vending machine.
     */
    private void displaySpecialItems() {
        JFrame specialItemsFrame = new JFrame("Special Items");
        specialItemsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        specialItemsFrame.setLayout(new BorderLayout());

        JPanel specialItemsPanel = new JPanel();
        specialItemsPanel.setLayout(new GridLayout(3, 3)); // Adjust the number of columns based on your preference

        String[] specialItems = vendingMachine.getSpecialItems();
        double[] specialPrices = vendingMachine.getSpecialPrices();
        int[] specialCalories = vendingMachine.getCalories(); // Get special item calories
        int[] specialQuantities = vendingMachine.getQuantities(); // Get special item quantities

        for (int i = 0; i < specialItems.length; i++) {
            String itemName = specialItems[i];
            double itemPrice = specialPrices[i];
            int itemCalories = specialCalories[i];
            int itemQuantity = specialQuantities[i];

            JButton itemButton = new JButton(itemName + " - \u20B1" + itemPrice + " - Calories: " + itemCalories
                    + " - Quantity: " + itemQuantity);
            itemButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleSpecialPurchase(itemName, itemPrice);
                }
            });
            specialItemsPanel.add(itemButton);
        }

        specialItemsFrame.add(specialItemsPanel, BorderLayout.CENTER);
        specialItemsFrame.pack();
        specialItemsFrame.setLocationRelativeTo(null);
        specialItemsFrame.setVisible(true);
    }
    /**
     * Handles the purchase of a special item from the vending machine.
     *
     * @param itemName   The name of the special item being purchased.
     * @param itemPrice  The price of the special item being purchased.
     */
    private void handleSpecialPurchase(String itemName, double itemPrice) {
        int choice = JOptionPane.showConfirmDialog(null, "Continue Purchasing Special Item?", "Continue",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            JFrame specialPurchaseFrame = new JFrame("Special Purchase Items");
            specialPurchaseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            specialPurchaseFrame.setLayout(new BorderLayout());

            JPanel specialPurchasePanel = new JPanel();
            specialPurchasePanel.setLayout(new GridLayout(3, 3)); // Adjust the number of columns based on your
                                                                  // preference

            String[] specialPurchaseItems = vendingMachine.getSpecialPurchaseItems();
            double[] specialPurchasePrices = vendingMachine.getSpecialPurchasePrices();
            int[] specialPurchaseCalories = vendingMachine.getSpecialPurchaseCalories(); // Get special purchase item
                                                                                         // calories
            int[] specialPurchaseQuantities = vendingMachine.getSpecialPurchaseQuantities(); // Get special purchase
                                                                                             // item quantities

            for (int i = 0; i < specialPurchaseItems.length; i++) {
                String purchaseItemName = specialPurchaseItems[i];
                double purchaseItemPrice = specialPurchasePrices[i];
                int purchaseItemCalories = specialPurchaseCalories[i];
                int purchaseItemQuantity = specialPurchaseQuantities[i];

                JButton purchaseItemButton = new JButton(purchaseItemName + " - \u20B1" + purchaseItemPrice
                        + " - Calories: " + purchaseItemCalories + " - Quantity: " + purchaseItemQuantity);
                purchaseItemButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Handle the purchase of special purchase items here
                        handleSpecialItemPurchase(purchaseItemName, purchaseItemPrice);
                    }
                });
                specialPurchasePanel.add(purchaseItemButton);
            }

            // Add the coin input field and insert coin button for special purchase
            JTextField coinInputField = new JTextField(20);
            JButton insertCoinButton = new JButton("Insert Coin");
            insertCoinButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double coinAmount = getAmountPaidFromCoins(coinInputField); // Pass the coinInputField here
                    accumulatedAmount += coinAmount;
                    JOptionPane.showMessageDialog(null, "Amount inserted: \u20B1" + coinAmount, "Coin Inserted",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            });
            specialPurchasePanel.add(new JLabel("Enter Coins (1, 5, 10, 20, 50, 100, 200, 500, 1000):"));
            specialPurchasePanel.add(coinInputField);
            specialPurchasePanel.add(insertCoinButton);

            // Add the accumulated amount display for special purchase
            JButton accumulatedAmountButton = new JButton("Accumulated Money");
            accumulatedAmountButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (accumulatedAmount > 0) {
                        JOptionPane.showMessageDialog(null, "Accumulated amount: \u20B1" + accumulatedAmount,
                                "Payment Summary", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "Please insert coins before checking accumulated amount.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            specialPurchasePanel.add(accumulatedAmountButton);

            specialPurchaseFrame.add(specialPurchasePanel, BorderLayout.CENTER);
            specialPurchaseFrame.pack();
            specialPurchaseFrame.setLocationRelativeTo(null);
            specialPurchaseFrame.setVisible(true);
        }
    }
    /**
     * Displays the special maintenance features of the vending machine.
     * Provides options for restocking special items, setting their prices, and performing other maintenance tasks.
     * Special maintenance tasks are specific to special vending items.
     */
    private void displaySpecialMaintenanceFeatures() {
        JFrame specialMaintenanceFrame = new JFrame("Special Maintenance Features");
        specialMaintenanceFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        specialMaintenanceFrame.setLayout(new BorderLayout());

        JPanel specialMaintenancePanel = new JPanel();
        specialMaintenancePanel.setLayout(new GridLayout(5, 1));

        JButton restockSpecialButton = new JButton("Restock Special Items");
        restockSpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vendingMachine.restockSpecialItems(); // Call the restockSpecialItems method
                JOptionPane.showMessageDialog(null, "Special items restocked.", "Special Maintenance",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        specialMaintenancePanel.add(restockSpecialButton);

        JButton stockSpecificSpecialButton = new JButton("Stock Specific Special Items");
        stockSpecificSpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stockSpecificSpecialItems();
            }
        });
        specialMaintenancePanel.add(stockSpecificSpecialButton);

        JButton setPriceSpecialButton = new JButton("Set Price of Special Item");
        setPriceSpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setPriceOfSpecialItem(); // Call the setPriceOfSpecialItem method
            }
        });
        specialMaintenancePanel.add(setPriceSpecialButton);

        JButton collectPaymentButton = new JButton("Collect Payment/Money");
        collectPaymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                collectPayment(); // Call the collectPayment method
            }
        });
        specialMaintenancePanel.add(collectPaymentButton);

        JButton replenishMoneyButton = new JButton("Replenish Money");
        replenishMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                replenishMoney(); // Call the replenishMoney method
            }
        });
        specialMaintenancePanel.add(replenishMoneyButton);

        JButton printSummarySpecialButton = new JButton("Print Transaction Summary for Special Vending");
        printSummarySpecialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTransactionSummaryForSpecial();
            }
        });
        specialMaintenancePanel.add(printSummarySpecialButton);

        specialMaintenanceFrame.add(specialMaintenancePanel, BorderLayout.CENTER);
        specialMaintenanceFrame.pack();
        specialMaintenanceFrame.setLocationRelativeTo(null);
        specialMaintenanceFrame.setVisible(true);
    }
    /**
     * Stocks specific special items in the vending machine.
     * Allows the user to select a special item and increase its quantity.
     * The additional quantity is parsed from user input and added to the selected special item.
     */
    private void stockSpecificSpecialItems() {
        String[] specialItems = vendingMachine.getSpecialPurchaseItems();
        String selectedItem = (String) JOptionPane.showInputDialog(
                null,
                "Select the special item to stock:",
                "Stock Specific Special Items",
                JOptionPane.PLAIN_MESSAGE,
                null,
                specialItems,
                specialItems[0]);

        if (selectedItem != null) {
            int specialItemIndex = vendingMachine.getSpecialPurchaseItemIndex(selectedItem);
            int currentQuantity = vendingMachine.getSpecialPurchaseQuantities()[specialItemIndex];
            int defaultQuantity = 10; // Set the default quantity here

            JTextField quantityField = new JTextField(5);
            JPanel inputPanel = new JPanel();
            inputPanel.setLayout(new GridLayout(1, 2));
            inputPanel.add(new JLabel("Enter quantity:"));
            inputPanel.add(quantityField);

            int result = JOptionPane.showConfirmDialog(
                    null,
                    inputPanel,
                    "Stock Special Item",
                    JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    int additionalQuantity = Integer.parseInt(quantityField.getText());

                    if (additionalQuantity <= 0) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Invalid quantity entered. Please enter a valid positive value.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        int totalQuantity = currentQuantity + additionalQuantity;
                        if (totalQuantity > defaultQuantity) {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Total quantity cannot exceed the default quantity (" + defaultQuantity + ").",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            vendingMachine.updateSpecialPurchaseItemQuantity(specialItemIndex, totalQuantity);
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Special item '" + selectedItem
                                            + "' has been restocked with an additional quantity of "
                                            + additionalQuantity,
                                    "Stock Successful",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid input format. Please enter a valid numeric value.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
    /**
     * Prints a transaction summary for special vending items.
     * Displays a summary of transactions related to special vending items.
     * The summary includes purchase information and other relevant details.
     */
    private void printTransactionSummaryForSpecial() {
        StringBuilder summaryText = new StringBuilder("Special Transaction Summary:\n\n");

        for (String purchaseInfo : transactionHistory) {
            summaryText.append(purchaseInfo).append("\n");
        }

        JTextArea textArea = new JTextArea(15, 40);
        textArea.setText(summaryText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Special Transaction Summary",
                JOptionPane.PLAIN_MESSAGE);
    }
    /**
     * Sets the price of a special item in the vending machine.
     * Allows the user to select a special item and set a new price for it.
     * The new price is parsed from user input and applied to the selected special item.
     */

    private void setPriceOfSpecialItem() {
        String[] specialPurchaseItems = vendingMachine.getSpecialPurchaseItems();
        String selectedItem = (String) JOptionPane.showInputDialog(
                null,
                "Select the special item to set the price:",
                "Set Price of Special Item",
                JOptionPane.PLAIN_MESSAGE,
                null,
                specialPurchaseItems,
                specialPurchaseItems[0]);

        if (selectedItem != null) {
            int specialItemIndex = vendingMachine.getSpecialPurchaseItemIndex(selectedItem);

            if (specialItemIndex != -1) {
                String newPriceInput = JOptionPane.showInputDialog(
                        null,
                        "Enter the new price for " + selectedItem + ":",
                        "Set Price of Special Item",
                        JOptionPane.PLAIN_MESSAGE);

                if (newPriceInput != null && !newPriceInput.isEmpty()) {
                    try {
                        double newPrice = Double.parseDouble(newPriceInput);
                        vendingMachine.updateSpecialPurchaseItemPrice(specialItemIndex, newPrice);
                        JOptionPane.showMessageDialog(null, "Price for " + selectedItem + " updated.",
                                "Special Maintenance", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid input format. Please enter a valid numeric value.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Special item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * Replenishes the internal balance of the vending machine by adding money.
     * The replenished amount is calculated based on logic specific to the coin denominations.
     * If the replenished amount is greater than 0, the internal balance is updated.
     * @see #internalBalance
     */

    private void replenishMoney() {
        // Implement logic to replenish money for different coin denominations here

        int replenishedAmount = 0; // Implement logic to calculate replenished amount

        if (replenishedAmount > 0) {
            internalBalance += replenishedAmount; // Update the internal balance
            JOptionPane.showMessageDialog(
                    null,
                    "Replenished amount: ₱" + replenishedAmount,
                    "Money Replenishment",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No money replenished.",
                    "Money Replenishment",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Collects payment from the vending machine, updating the internal balance.
     * The collected amount is calculated based on logic specific to payment collection.
     * If the collected amount is greater than 0, the internal balance is updated and accumulated amount is reset.
     * @see #internalBalance
     * @see #accumulatedAmount
     */

    private void collectPayment() {
        double collectedAmount = 0.0; // Implement logic to collect payments here

        if (collectedAmount > 0) {
            internalBalance += collectedAmount; // Update the internal balance
            accumulatedAmount = 0.0; // Reset accumulated amount
            JOptionPane.showMessageDialog(
                    null,
                    "Collected amount: ₱" + collectedAmount,
                    "Payment Collection",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(
                    null,
                    "No payments collected.",
                    "Payment Collection",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * The main method that starts the application by creating an instance of VendingMachineGUI.
     * @param args Command-line arguments (not used).
     */

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendingMachineGUI();
            }
        });
    }
}