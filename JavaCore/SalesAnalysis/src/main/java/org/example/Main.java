package org.example;

import org.example.model.*;
import org.example.service.OrderAnalyzer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderAnalyzer analyzer = new OrderAnalyzer();
        List<Order> demoOrders = createDemoOrders();

        System.out.println("=== SALES ANALYSIS DEMONSTRATION ===\n");

        // 1. Unique cities
        System.out.println("1. Unique cities with orders:");
        analyzer.getUniqueCities(demoOrders).forEach(city -> System.out.println("   - " + city));
        System.out.println();

        // 2. Total income
        double totalIncome = analyzer.getTotalIncome(demoOrders);
        System.out.printf("2. Total income from delivered orders: $%.2f%n", totalIncome);
        System.out.println();

        // 3. Most popular product
        String mostPopularProduct = analyzer.getMostPopularProduct(demoOrders);
        System.out.println("3. Most popular product: " + mostPopularProduct);
        System.out.println();

        // 4. Average order value
        double averageOrderValue = analyzer.getAverageOrderValue(demoOrders);
        System.out.printf("4. Average order value for delivered orders: $%.2f%n", averageOrderValue);
        System.out.println();

        // 5. Customers with more than 5 orders
        System.out.println("5. Customers with more than 5 orders:");
        List<Customer> loyalCustomers = analyzer.getCustomersWithMoreThan5Orders(demoOrders);
        if (loyalCustomers.isEmpty()) {
            System.out.println("   No customers with more than 5 orders");
        } else {
            loyalCustomers.forEach(customer ->
                    System.out.println("   - " + customer.getName() + " from " + customer.getCity())
            );
        }
        System.out.println();

        printAdditionalStats(demoOrders);
    }

    private static List<Order> createDemoOrders() {
        Customer customer1 = new Customer("C001", "John Smith", "john@email.com",
                LocalDateTime.of(2023, 1, 15, 10, 0), 35, "New York");
        Customer customer2 = new Customer("C002", "Maria Garcia", "maria@email.com",
                LocalDateTime.of(2023, 2, 20, 14, 30), 28, "Los Angeles");
        Customer customer3 = new Customer("C003", "Alex Johnson", "alex@email.com",
                LocalDateTime.of(2023, 3, 10, 9, 15), 42, "Chicago");
        Customer customer4 = new Customer("C004", "Elena Davis", "elena@email.com",
                LocalDateTime.of(2023, 4, 5, 16, 45), 31, "New York");
        Customer customer5 = new Customer("C005", "Mike Wilson", "mike@email.com",
                LocalDateTime.of(2023, 5, 12, 11, 20), 29, "Miami");
        Customer customer6 = new Customer("C006", "Sarah Brown", "sarah@email.com",
                LocalDateTime.of(2023, 6, 8, 13, 45), 26, "Seattle");

        OrderItem laptop = new OrderItem("MacBook Pro", 1, 2500.0, Category.ELECTRONICS);
        OrderItem smartphone = new OrderItem("iPhone 15", 2, 999.0, Category.ELECTRONICS);
        OrderItem headphones = new OrderItem("Wireless Headphones", 3, 199.0, Category.ELECTRONICS);
        OrderItem tablet = new OrderItem("iPad Air", 1, 599.0, Category.ELECTRONICS);
        OrderItem smartwatch = new OrderItem("Smart Watch", 2, 299.0, Category.ELECTRONICS);

        OrderItem tshirt = new OrderItem("Cotton T-Shirt", 8, 25.0, Category.CLOTHING);
        OrderItem jeans = new OrderItem("Designer Jeans", 2, 89.0, Category.CLOTHING);
        OrderItem jacket = new OrderItem("Winter Jacket", 1, 120.0, Category.CLOTHING);
        OrderItem sneakers = new OrderItem("Running Shoes", 3, 75.0, Category.CLOTHING);
        OrderItem dress = new OrderItem("Evening Dress", 1, 150.0, Category.CLOTHING);

        OrderItem javaBook = new OrderItem("Java Programming", 4, 45.0, Category.BOOKS);
        OrderItem novel = new OrderItem("Bestseller Novel", 3, 15.0, Category.BOOKS);
        OrderItem cookbook = new OrderItem("Gourmet Cookbook", 2, 35.0, Category.BOOKS);
        OrderItem textbook = new OrderItem("Math Textbook", 1, 80.0, Category.BOOKS);

        OrderItem blender = new OrderItem("Kitchen Blender", 1, 79.0, Category.HOME);
        OrderItem lamp = new OrderItem("Desk Lamp", 4, 45.0, Category.HOME);
        OrderItem pillow = new OrderItem("Memory Foam Pillow", 3, 35.0, Category.HOME);
        OrderItem vacuum = new OrderItem("Robot Vacuum", 1, 299.0, Category.HOME);

        OrderItem perfume = new OrderItem("Luxury Perfume", 1, 120.0, Category.BEAUTY);
        OrderItem skincare = new OrderItem("Skincare Set", 2, 65.0, Category.BEAUTY);
        OrderItem makeup = new OrderItem("Makeup Kit", 1, 85.0, Category.BEAUTY);

        OrderItem lego = new OrderItem("Lego Set", 2, 49.0, Category.TOYS);
        OrderItem doll = new OrderItem("Fashion Doll", 3, 25.0, Category.TOYS);
        OrderItem puzzle = new OrderItem("1000-piece Puzzle", 1, 20.0, Category.TOYS);

        Order order1 = new Order("O001", LocalDateTime.now().minusDays(30), customer1,
                Arrays.asList(laptop, javaBook, tshirt), OrderStatus.DELIVERED);
        Order order2 = new Order("O002", LocalDateTime.now().minusDays(28), customer2,
                Arrays.asList(smartphone, dress, perfume), OrderStatus.DELIVERED);
        Order order3 = new Order("O003", LocalDateTime.now().minusDays(25), customer3,
                Arrays.asList(tablet, jeans, novel), OrderStatus.PROCESSING);
        Order order4 = new Order("O004", LocalDateTime.now().minusDays(22), customer4,
                Arrays.asList(headphones, sneakers, skincare), OrderStatus.SHIPPED);
        Order order5 = new Order("O005", LocalDateTime.now().minusDays(20), customer5,
                Arrays.asList(smartwatch, jacket, makeup), OrderStatus.DELIVERED);
        Order order6 = new Order("O006", LocalDateTime.now().minusDays(18), customer6,
                Arrays.asList(blender, cookbook, lego), OrderStatus.DELIVERED);
        Order order7 = new Order("O007", LocalDateTime.now().minusDays(15), customer1,
                Arrays.asList(lamp, puzzle, doll), OrderStatus.CANCELLED);
        Order order8 = new Order("O008", LocalDateTime.now().minusDays(12), customer2,
                Arrays.asList(pillow, textbook, tshirt), OrderStatus.DELIVERED);
        Order order9 = new Order("O009", LocalDateTime.now().minusDays(10), customer3,
                Arrays.asList(vacuum, novel, jeans), OrderStatus.DELIVERED);
        Order order10 = new Order("O010", LocalDateTime.now().minusDays(8), customer4,
                Arrays.asList(tshirt, sneakers, skincare), OrderStatus.CANCELLED);

        Order order11 = new Order("O011", LocalDateTime.now().minusDays(7), customer1,
                Arrays.asList(headphones, javaBook), OrderStatus.DELIVERED);
        Order order12 = new Order("O012", LocalDateTime.now().minusDays(6), customer1,
                Arrays.asList(lego, cookbook), OrderStatus.DELIVERED);
        Order order13 = new Order("O013", LocalDateTime.now().minusDays(5), customer1,
                Arrays.asList(tablet, novel), OrderStatus.DELIVERED);
        Order order14 = new Order("O014", LocalDateTime.now().minusDays(4), customer1,
                Arrays.asList(tshirt, perfume), OrderStatus.NEW);
        Order order15 = new Order("O015", LocalDateTime.now().minusDays(3), customer1,
                Arrays.asList(makeup, dress), OrderStatus.DELIVERED);
        Order order16 = new Order("O016", LocalDateTime.now().minusDays(2), customer2,
                Arrays.asList(smartphone, headphones), OrderStatus.DELIVERED);
        Order order17 = new Order("O017", LocalDateTime.now().minusDays(1), customer2,
                Arrays.asList(laptop, javaBook), OrderStatus.PROCESSING);
        Order order18 = new Order("O018", LocalDateTime.now(), customer1,
                Arrays.asList(jeans, sneakers, tshirt), OrderStatus.DELIVERED);

        return Arrays.asList(order1, order2, order3, order4, order5, order6, order7, order8,
                order9, order10, order11, order12, order13, order14, order15,
                order16, order17, order18);
    }

    private static void printAdditionalStats(List<Order> orders) {
        System.out.println("=== ADDITIONAL STATISTICS ===");

        long totalOrders = orders.size();
        long deliveredOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .count();
        long cancelledOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.CANCELLED)
                .count();
        long processingOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.PROCESSING)
                .count();
        long shippedOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.SHIPPED)
                .count();
        long newOrders = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.NEW)
                .count();

        System.out.printf("Total orders: %d%n", totalOrders);
        System.out.printf("New orders: %d%n", newOrders);
        System.out.printf("Processing orders: %d%n", processingOrders);
        System.out.printf("Shipped orders: %d%n", shippedOrders);
        System.out.printf("Delivered orders: %d%n", deliveredOrders);
        System.out.printf("Cancelled orders: %d%n", cancelledOrders);
        System.out.printf("Success rate: %.1f%%%n",
                (double) deliveredOrders / totalOrders * 100);

        long uniqueCustomers = orders.stream()
                .map(Order::getCustomer)
                .distinct()
                .count();
        System.out.printf("Unique customers: %d%n", uniqueCustomers);

        // Most active city
        String mostActiveCity = orders.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        order -> order.getCustomer().getCity(),
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse("Not determined");

        System.out.println("Most active city: " + mostActiveCity);

        // Products count
        long totalProductsSold = orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToInt(OrderItem::getQuantity)
                .sum();
        System.out.printf("Total products sold: %d%n", totalProductsSold);

        // Category distribution
        System.out.println("\nCategory distribution in delivered orders:");
        orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .collect(java.util.stream.Collectors.groupingBy(
                        OrderItem::getCategory,
                        java.util.stream.Collectors.summingInt(OrderItem::getQuantity)
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(entry ->
                        System.out.printf("   %-12s: %d items%n", entry.getKey(), entry.getValue())
                );

        // Top customers by order count
        System.out.println("\nTop customers by number of orders:");
        orders.stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Order::getCustomer,
                        java.util.stream.Collectors.counting()
                ))
                .entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(3)
                .forEach(entry ->
                        System.out.printf("   %s: %d orders%n",
                                entry.getKey().getName(), entry.getValue())
                );
    }
}