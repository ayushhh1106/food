import java.util.*;

class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}

interface Payment {
    void makePayment(double amount);
}

abstract class User {
    private String name;
    private String phone;

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public abstract void displayRole();
}

class Customer extends User {

    public Customer(String name, String phone) {
        super(name, phone);
    }

    @Override
    public void displayRole() {
        System.out.println("Role : Customer");
    }
}

class Restaurant {
    private String restaurantName;

    public Restaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}

class FoodItem {

    private int id;
    private String name;
    private double price;

    public FoodItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void display() {
        System.out.println(id + ". " + name + " - ₹" + price);
    }
}

class Order {

    private final int orderId;
    private Customer customer;
    private ArrayList<FoodItem> items;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        items = new ArrayList<>();
    }

    public void addItem(FoodItem item) {
        items.add(item);
    }

    public double calculateBill() {
        double total = 0;

        for (FoodItem item : items) {
            total += item.getPrice();
        }

        return total;
    }

    public double calculateBill(double discount) {
        return calculateBill() - discount;
    }

    public void displayOrder() {

        StringBuilder bill = new StringBuilder();

        bill.append("\n======= ORDER SUMMARY =======\n");
        bill.append("Order ID : ").append(orderId).append("\n");
        bill.append("Customer : ").append(customer.getName()).append("\n");

        for (FoodItem item : items) {
            bill.append(item.getName())
                .append(" - ₹")
                .append(item.getPrice())
                .append("\n");
        }

        bill.append("Total Bill : ₹")
            .append(calculateBill());

        System.out.println(bill);
    }
}

class UpiPayment implements Payment {

    @Override
    public void makePayment(double amount) {
        System.out.println("UPI Payment Successful : ₹" + amount);
    }
}

class CardPayment implements Payment {

    @Override
    public void makePayment(double amount) {
        System.out.println("Card Payment Successful : ₹" + amount);
    }
}

class DeliveryPerson extends User {

    public DeliveryPerson(String name, String phone) {
        super(name, phone);
    }

    @Override
    public void displayRole() {
        System.out.println("Role : Delivery Partner");
    }
}

public class FoodDeliverySystem {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Restaurant restaurant = new Restaurant("Ayush Foods");

        FoodItem[] menu = {
                new FoodItem(1, "Burger", 120),
                new FoodItem(2, "Pizza", 250),
                new FoodItem(3, "Pasta", 180),
                new FoodItem(4, "Momos", 90),
                new FoodItem(5, "Cold Drink", 50)
        };

        try {

            System.out.println("===== FOOD DELIVERY SYSTEM =====");

            System.out.print("Enter Customer Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Phone Number: ");
            String phone = sc.nextLine();

            Customer customer = new Customer(name, phone);

            customer.displayRole();

            Order order = new Order(1001, customer);

            int choice;

            do {

                System.out.println(
                        "\n--- MENU (" +
                        restaurant.getRestaurantName()
                        + ") ---");

                for (FoodItem item : menu) {
                    item.display();
                }

                System.out.println("0. Finish Order");

                System.out.print("Choose Item: ");
                choice = sc.nextInt();

                if (choice >= 1 && choice <= menu.length) {

                    order.addItem(menu[choice - 1]);

                    System.out.println(
                            menu[choice - 1].getName()
                                    + " Added.");
                }

            } while (choice != 0);

            if (order.calculateBill() == 0) {
                throw new InvalidOrderException(
                        "No food item selected!"
                );
            }

            order.displayOrder();

            System.out.println("\nSelect Payment Method");
            System.out.println("1. UPI");
            System.out.println("2. Card");

            int payChoice = sc.nextInt();

            Payment payment;

            switch (payChoice) {

                case 1:
                    payment = new UpiPayment();
                    break;

                case 2:
                    payment = new CardPayment();
                    break;

                default:
                    throw new InvalidOrderException(
                            "Invalid Payment Method"
                    );
            }

            payment.makePayment(order.calculateBill());

            DeliveryPerson rider =
                    new DeliveryPerson(
                            "Rahul Rider",
                            "9876543210"
                    );

            rider.displayRole();

            System.out.println(
                    "Order Assigned to : "
                            + rider.getName()
            );

            System.out.println(
                    "\nOrder Delivered Successfully!"
            );

        } catch (InvalidOrderException e) {

            System.out.println(
                    "Order Error : "
                            + e.getMessage()
            );

        } catch (Exception e) {

            System.out.println(
                    "Unexpected Error : "
                            + e.getMessage()
            );

        } finally {

            System.out.println(
                    "\nThank You For Using Food Delivery System"
            );

            sc.close();
        }
    }
}
