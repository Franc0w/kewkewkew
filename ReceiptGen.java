import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

class ShoppingListItem {
    String itemName;
    String itemDescription;
    double itemPrice;

    ShoppingListItem(String itemName, String itemDescription, double itemPrice) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }
}

class ShoppingList {
    List<ShoppingListItem> items;
    Queue<ShoppingListItem> cart;

    ShoppingList() {
        items = new ArrayList<>();
        cart = new LinkedList<>();
    }

    void addList(String itemName, String itemDescription, double itemPrice) {
        items.add(new ShoppingListItem(itemName, itemDescription, itemPrice));
    }

    void addToCart(ShoppingListItem item) {
        cart.offer(item); // Use offer to add to the end (FIFO)
    }

    ShoppingListItem removeFromCart() {
        return cart.poll(); // Remove and return the first item (FIFO)
    }

    void displayCart() {
        if (cart.isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }

        double total = 0;
        System.out.println("Items in Cart:");
        ShoppingListItem firstItem = removeFromCart(); // Remove the first item
        System.out.println("[" + 1 + "] Item: " + firstItem.itemName);
        System.out.println("Price: $" + firstItem.itemPrice);
        System.out.println("==========================");
        total += firstItem.itemPrice;

        int count = 2; // Start with 2 as the first item has already been printed
        for (ShoppingListItem item : cart) {
            System.out.println("[" + count + "] Item: " + item.itemName);
            System.out.println("Price: $" + item.itemPrice);
            System.out.println("==========================");
            total += item.itemPrice;
            count++;
        }

        System.out.println("Total: $" + total);
    }
}

public class ReceiptGen {
    public static void main(String[] args) {
        ShoppingList groceryList = new ShoppingList();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Grocery Store!");

        // Predefined items in categories
        addSnacks(groceryList);
        addFrozenGoods(groceryList);
        addFruits(groceryList);
        addHydration(groceryList);
        addDeliFood(groceryList);

        while (true) {
            System.out.println("Select which section you would like to shop in:");
            System.out.println("[1] Snacks\n[2] Frozen Goods\n[3] Fruits\n[4] Hydration\n[5] Deli Foods");
            System.out.println("[6] View Cart\n[0] Checkout");
            System.out.print("Enter your choice: ");
            int sectionChoice = scanner.nextInt();

            if (sectionChoice == 0) {
                groceryList.displayCart();
                break;
            } else if (sectionChoice == 6) {
                groceryList.displayCart();
            } else if (sectionChoice >= 1 && sectionChoice <= 5) {
                displaySectionItems(groceryList, sectionChoice, scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        
        // Ask if the user wants to add more items or proceed to checkout
        System.out.print("Do you want to add more items (1) or proceed to checkout (0): ");
        int checkoutChoice = scanner.nextInt();
        if (checkoutChoice == 1) {
            continueShopping(groceryList, scanner);
        } else if (checkoutChoice == 0) {
            groceryList.displayCart();
        } else {
            System.out.println("Invalid choice. Proceeding to checkout.");
            groceryList.displayCart();
        }
    }

    private static void displaySectionItems(ShoppingList groceryList, int sectionChoice, Scanner scanner) {
        List<ShoppingListItem> displayedItems = new ArrayList<>();

        for (ShoppingListItem item : groceryList.items) {
            if (isSectionMatch(item, sectionChoice)) {
                displayedItems.add(item);
            }
        }

        if (displayedItems.isEmpty()) {
            System.out.println("No items found in this section.");
        } else {
            displayItemsWithIndexes(displayedItems);
            System.out.print("Enter the index of the item to add to the cart, or '0' to go back: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                // User chose to go back
            } else if (choice >= 1 && choice <= displayedItems.size()) {
                ShoppingListItem selectedItem = displayedItems.get(choice - 1);
                groceryList.addToCart(selectedItem);
                System.out.println(selectedItem.itemName + " added to cart.");
            } else {
                System.out.println("Invalid index. Please try again.");
            }
        }
    }

    private static void displayItemsWithIndexes(List<ShoppingListItem> items) {
        int index = 1;
        for (ShoppingListItem item : items) {
            System.out.println("[" + index + "] Item: " + item.itemName);
            System.out.println("    Description: " + item.itemDescription);
            System.out.println("    Price: $" + item.itemPrice);
            index++;
        }
    }

    private static boolean isSectionMatch(ShoppingListItem item, int sectionChoice) {
        switch (sectionChoice) {
            case 1:
                return item.itemDescription.equals("Snacks");
            case 2:
                return item.itemDescription.equals("Frozen Goods");
            case 3:
                return item.itemDescription.equals("Fruits");
            case 4:
                return item.itemDescription.equals("Hydration");
            case 5:
                return item.itemDescription.equals("Deli Foods");
            default:
                return false;
        }
    }

    // Predefined items in categories
    private static void addSnacks(ShoppingList groceryList) {
        groceryList.addList("Doritos", "Snacks", 6.00);
        groceryList.addList("Chezzy", "Snacks", 4.00);
        groceryList.addList("Piattos", "Snacks", 5.00);
    }

    private static void addFrozenGoods(ShoppingList groceryList) {
        groceryList.addList("Frozen Pizza", "Frozen Goods", 5.00);
        groceryList.addList("Frozen Vegetables", "Frozen Goods", 2.00);
        groceryList.addList("Frozen Whole Chicken", "Frozen Goods", 5.00);
    }

    private static void addFruits(ShoppingList groceryList) {
        groceryList.addList("Apples", "Fruits", 1.00);
        groceryList.addList("Bananas", "Fruits", 1.00);
        groceryList.addList("Orange", "Fruits", 1.00);
    }

    private static void addHydration(ShoppingList groceryList) {
        groceryList.addList("Bottled Water", "Hydration", 1.00);
        groceryList.addList("Coca-cola", "Hydration", 2.00);
        groceryList.addList("Sprite", "Hydration", 2.00);
    }

    private static void addDeliFood(ShoppingList groceryList) {
        groceryList.addList("Sandwich", "Deli Foods", 4.00);
        groceryList.addList("Salad", "Deli Foods", 4.00);
        groceryList.addList("Beef Pattie", "Deli Foods", 5.00);
    }
    
    private static void continueShopping(ShoppingList groceryList, Scanner scanner) {
        while (true) {
            System.out.println("Select which section you would like to shop in:");
            System.out.println("[1] Snacks\n[2] Frozen Goods\n[3] Fruits\n[4] Hydration\n[5] Deli Foods");
            System.out.println("[6] View Cart\n[0] Checkout");
            System.out.print("Enter your choice: ");
            int sectionChoice = scanner.nextInt();

            if (sectionChoice == 0) {
                groceryList.displayCart();
                break;
            } else if (sectionChoice == 6) {
                groceryList.displayCart();
            } else if (sectionChoice >= 1 && sectionChoice <= 5) {
                displaySectionItems(groceryList, sectionChoice, scanner);
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
        
        // Ask if the user wants to add more items or proceed to checkout
        System.out.print("Do you want to add more items (1) or proceed to checkout (0): ");
        int checkoutChoice = scanner.nextInt();
        if (checkoutChoice == 1) {
            continueShopping(groceryList, scanner);
        } else if (checkoutChoice == 0) {
            groceryList.displayCart();
        } else {
            System.out.println("Invalid choice. Proceeding to checkout.");
            groceryList.displayCart();
        }
    }
}
