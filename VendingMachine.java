import java.util.Arrays;
import javax.swing.JOptionPane;


/**
 * Represents a vending machine that sells various items and allows for maintenance operations.
 */
public class VendingMachine {
    private String[] items;
    private int[] quantities;
    private double[] prices;
    private double internalBalance;
    private int[] calories;
    private double startingBalance;
    private String[] specialItems;
    private double[] specialPrices;
    private String[] specialPurchaseItems;
    private double[] specialPurchasePrices;
    private int[] specialPurchaseQuantities;
    private int[] specialPurchaseCalories;

    public void setSpecialPurchaseQuantities(int[] quantities) {
        this.specialPurchaseQuantities = quantities;
    }
   /**
     * Constructs a new vending machine with default settings.
     */
    public VendingMachine() {
        items = new String[8];
        quantities = new int[8];
        prices = new double[8];
        internalBalance = 100.0;
        calories = new int[8]; 
        startingBalance = 100.0;
        specialItems = new String[8];
        specialPrices = new double[8];
        specialPurchaseItems = new String[10];
        specialPurchasePrices = new double[10];
        specialPurchaseQuantities = new int[8];
        Arrays.fill(specialPurchaseQuantities, 10);
        specialPurchaseCalories = new int[10];
    }
    /**
     * Initializes the items and their properties in the vending machine.
     */
    public void initializeItems() {
        String[] items = {
                "Tapsilog", "Tocilog", "Chicksilog", "Bangsilog",
                "Longsilog", "Cornsilog", "Malingsilog", "Hotsilog"
        };
        int[] quantities = { 10, 10, 10, 10, 10, 10, 10, 10 };
        double[] prices = { 85, 80, 85, 80, 80, 60, 60, 50 };
        int[] calories = { 300, 350, 400, 250, 200, 150, 250, 180 }; // Initialize calories array

        String[] specialItems = {
                "Tapsilog", "Tocilog", "Chicksilog", "Bangsilog",
                "Longsilog", "Cornsilog", "Malingsilog", "Hotsilog"
        };
        double[] specialPrices = { 85, 80, 85, 80, 80, 60, 60, 50 };

        String[] specialPurchaseItems = {
                "Rice", "Egg", "Hotdog", "Bangus", "Tocino",
                "Tapa", "Chicken", "Maling", "Longganisa", "Corned Beef"
        };
        double[] specialPurchasePrices = { 10, 5, 15, 20, 15, 15, 25, 8, 10, 12 };
        int[] specialPurchaseQuantities = { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };
        int[] specialPurchaseCalories = { 150, 70, 150, 200, 120, 130, 250, 110, 180, 160 };

        this.specialPurchaseItems = Arrays.copyOf(specialPurchaseItems, specialPurchaseItems.length);
        this.specialPurchasePrices = Arrays.copyOf(specialPurchasePrices, specialPurchasePrices.length);
        this.specialPurchaseQuantities = Arrays.copyOf(specialPurchaseQuantities, specialPurchaseQuantities.length);
        this.specialPurchaseCalories = Arrays.copyOf(specialPurchaseCalories, specialPurchaseCalories.length);
        this.items = Arrays.copyOf(items, items.length);
        this.quantities = Arrays.copyOf(quantities, quantities.length);
        this.calories = Arrays.copyOf(calories, calories.length);
        this.prices = Arrays.copyOf(prices, prices.length);
        this.specialItems = Arrays.copyOf(specialItems, specialItems.length);
        this.specialPrices = Arrays.copyOf(specialPrices, specialPrices.length);
    }

    /**
     * Retrieves the list of regular items available in the vending machine.
     *
     * @return The array of regular item names.
     */
    public String[] getItems() {
        return items;
    }

    /**
     * Retrieves the list of special items available in the vending machine.
     *
     * @return The array of special item names.
     */
    public String[] getSpecialItems() {
        return specialItems;
    }

    /**
     * Retrieves the prices of regular items in the vending machine.
     *
     * @return The array of regular item prices.
     */
    public double[] getPrices() {
        return prices;
    }
    /**
     * Retrieves the prices of special items in the vending machine.
     *
     * @return The array of special item prices.
     */
    public double[] getSpecialPrices() {
        return specialPrices;
    }
    /**
     * Retrieves the calories of regular items in the vending machine.
     *
     * @return The array of regular item calories.
     */
    public int[] getCalories() {
        return calories;
    }
    /**
     * Retrieves the quantities of regular items in the vending machine.
     *
     * @return The array of regular item quantities.
     */
    public int[] getQuantities() {
        return quantities;
    }
    /**
     * Processes the purchase of a regular item by deducting its quantity.
     *
     * @param itemName The name of the item to purchase.
     */
    public void purchaseItem(String itemName) {
        int index = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(itemName)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Item not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (quantities[index] == 0) {
            JOptionPane.showMessageDialog(null, "Item out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        quantities[index]--;
    }
    /**
     * Retrieves the index of a regular item by its name.
     *
     * @param itemName The name of the item to find.
     * @return The index of the item, or -1 if not found.
     */
    public int getItemIndex(String itemName) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].equals(itemName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Updates the quantity of a special purchase item.
     *
     * @param index      The index of the item to update.
     * @param newQuantity The new quantity value.
     */    
    public void updateSpecialPurchaseItemQuantity(int index, int newQuantity) {
        if (index >= 0 && index < specialPurchaseQuantities.length) {
            specialPurchaseQuantities[index] = newQuantity;
        }
    }


    /**
     * Updates the price of a special purchase item.
     *
     * @param index    The index of the item to update.
     * @param newPrice The new price value.
     */    
    public void updateSpecialPurchaseItemPrice(int index, double newPrice) {
        if (index >= 0 && index < specialPurchasePrices.length) {
            specialPurchasePrices[index] = newPrice;
        }
    }
    /**
     * Updates the quantity of a regular item.
     *
     * @param index      The index of the item to update.
     * @param newQuantity The new quantity value.
     */
    public void updateItemQuantity(int index, int newQuantity) {
        if (index >= 0 && index < quantities.length) {
            quantities[index] = newQuantity;
        }
    }
    /**
     * Removes a regular item from the vending machine.
     *
     * @param index The index of the item to remove.
     */
    public void removeItem(int index) {
        if (index >= 0 && index < items.length) {
            items[index] = null;
            quantities[index] = 0;
            prices[index] = 0.0;
        }
    }
    /**
     * Retrieves the starting balance of the vending machine.
     *
     * @return The starting balance.
     */
    public double getStartingBalance() {
        return startingBalance;
    }

    /**
     * Restocks all regular items to the default quantity.
     */    
    public void restockAllItems() {
        for (int i = 0; i < quantities.length; i++) {
            quantities[i] = 10; // Restock all items to the default quantity
        }
    }


    /**
     * Retrieves the list of special purchase items available in the vending machine.
     *
     * @return The array of special purchase item names.
     */    
    public String[] getSpecialPurchaseItems() {
        return specialPurchaseItems;
    }
    /**
     * Retrieves the quantities of a special purchase item in the vending machine.
     *
     * @param index The index of the special purchase item.
     * @return The quantity of the special purchase item.
     */
    public int getSpecialPurchaseItemQuantity(int index) {
        if (index >= 0 && index < specialPurchaseQuantities.length) {
            return specialPurchaseQuantities[index];
        }
        return 0; // Return 0 for invalid index
    }
    /**
     * Retrieves the prices of special purchase items in the vending machine.
     *
     * @return The array of special purchase item prices.
     */
    public double[] getSpecialPurchasePrices() {
        return specialPurchasePrices;
    }
/**
 * Sets the quantities of the special purchase items.
 *
 * @param quantities An array of integers representing the new quantities of the special purchase items.
 */
    public int[] getSpecialPurchaseQuantities() {
        return specialPurchaseQuantities;
    }
/**
 * Returns an array of integers representing the calories of the special purchase items.
 *
 * @return An array of integers representing the calories of the special purchase items.
 */
    public int[] getSpecialPurchaseCalories() {
        return specialPurchaseCalories;
    }


    /**
     * Retrieves the index of a special purchase item by its name.
     *
     * @param itemName The name of the item to find.
     * @return The index of the item, or -1 if not found.
     */    
    public int getSpecialPurchaseItemIndex(String itemName) {
        for (int i = 0; i < specialPurchaseItems.length; i++) {
            if (specialPurchaseItems[i] != null && specialPurchaseItems[i].equals(itemName)) {
                return i;
            }
        }
        return -1;
    }
   /**
     * Processes the purchase of a special purchase item by deducting its quantity.
     *
     * @param index The index of the special purchase item to purchase.
     */
    public void purchaseSpecialPurchaseItem(int index) {
        if (index >= 0 && index < specialPurchaseQuantities.length) {
            if (specialPurchaseQuantities[index] > 0) {
                specialPurchaseQuantities[index]--;
            }
        }
    }


    /**
     * Restocks all special purchase items to the default quantity.
     */  
    public void restockSpecialItems() {

        for (int i = 0; i < specialPurchaseQuantities.length; i++) {
            specialPurchaseQuantities[i] = 10;
        }
    }
    /**
     * Retrieves the current internal balance of the vending machine.
     *
     * @return The internal balance.
     */
    public double getInternalBalance() {
        return internalBalance;
    }
    /**
     * Sets the internal balance of the vending machine.
     *
     * @param balance The new internal balance.
     */
    public void setInternalBalance(double balance) {
        internalBalance = balance;
    }

    /**
     * Performs a transaction by deducting the required change from the starting balance.
     *
     * @param requiredChange The amount to deduct.
     * @return True if the transaction is successful, false if insufficient funds.
     */    
    public boolean performTransaction(double requiredChange) {
        if (startingBalance >= requiredChange) {
            startingBalance -= requiredChange;
            return true;
        } else {
            return false;
        }
    }
}