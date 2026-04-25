import java.util.Scanner;

class FoodItem {
    String itemName;
    double price;

    FoodItem(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
}

class Restaurant {
    String name;
    FoodItem[] menu;

    Restaurant(String name, FoodItem[] menu) {
        this.name = name;
        this.menu = menu;
    }

    public void displayMenu() {
        System.out.println("\n--- Menu ---");
        for (int i = 0; i < menu.length; i++) {
            System.out.println((i + 1) + ". " + menu[i].getName() + " - ₹" + menu[i].getPrice());
        }
    }

    public FoodItem getItem(int index) {
        return menu[index];
    }
}

class Order {
    FoodItem[] items = new FoodItem[10];
    int[] quantities = new int[10];
    int count = 0;

    double subtotal = 0;
    double tax = 0;
    double deliveryCharge = 0;
    double total = 0;

    public void addItem(FoodItem item, int quantity) {
        items[count] = item;
        quantities[count] = quantity;
        count++;
    }

    public void calculateTotal() {
        subtotal = 0;

        for (int i = 0; i < count; i++) {
            subtotal += items[i].getPrice() * quantities[i];
        }

        if (subtotal > 500) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 50;
        }

        tax = subtotal * 0.05;
        total = subtotal + tax + deliveryCharge;
    }

    public void displayOrder() {
        System.out.println("\n===== Order Summary =====");

        for (int i = 0; i < count; i++) {
            System.out.println(items[i].getName() + " x" + quantities[i] +
                    " = ₹" + (items[i].getPrice() * quantities[i]));
        }

        System.out.println("--------------------------");
        System.out.println("Subtotal: ₹" + subtotal);
        System.out.println("Delivery Charge: ₹" + deliveryCharge);
        System.out.println("Tax (5%): ₹" + tax);
        System.out.println("Total Amount: ₹" + total);
    }
}

public class project {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        FoodItem[] menu = {
                new FoodItem("Burgerr", 100),
                new FoodItem("Pizza", 300),
                new FoodItem("Pasta", 200),
                new FoodItem("Sandwich", 150)
        };

        Restaurant restaurant = new Restaurant("Food Hub", menu);
        Order order = new Order();

        int choice;

        do {
            restaurant.displayMenu();
            System.out.println("Select item (1-4) or 0 to finish:");
            choice = sc.nextInt();

            if (choice >= 1 && choice <= 4) {
                System.out.print("Enter quantity: ");
                int qty = sc.nextInt();
                order.addItem(restaurant.getItem(choice - 1), qty);
            }

        } while (choice != 0);

        order.calculateTotal();
        order.displayOrder();

        sc.close();
    }
}
