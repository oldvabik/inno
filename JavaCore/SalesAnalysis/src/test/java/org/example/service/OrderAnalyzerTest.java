package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderAnalyzerTest {

    private final OrderAnalyzer analyzer = new OrderAnalyzer();

    @Test
    void getUniqueCitiesTest() {
        Customer customer1 = new Customer("1", "John", "john@test.com",
                LocalDateTime.now(), 30, "Moscow");
        Customer customer2 = new Customer("2", "Alice", "alice@test.com",
                LocalDateTime.now(), 25, "Saint Petersburg");
        Customer customer3 = new Customer("3", "Bob", "bob@test.com",
                LocalDateTime.now(), 35, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer1,
                        Arrays.asList(new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS)),
                        OrderStatus.NEW),
                new Order("O2", LocalDateTime.now(), customer2,
                        Arrays.asList(new OrderItem("Phone", 1, 500.0, Category.ELECTRONICS)),
                        OrderStatus.PROCESSING),
                new Order("O3", LocalDateTime.now(), customer3,
                        Arrays.asList(new OrderItem("Book", 1, 20.0, Category.BOOKS)),
                        OrderStatus.DELIVERED)
        );

        var cities = analyzer.getUniqueCities(orders);

        assertEquals(2, cities.size());
        assertTrue(cities.contains("Moscow"));
        assertTrue(cities.contains("Saint Petersburg"));
    }

    @Test
    void getTotalIncomeTest() {
        Customer customer = new Customer("1", "John", "john@test.com",
                LocalDateTime.now(), 30, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS),
                                new OrderItem("Mouse", 2, 50.0, Category.ELECTRONICS)
                        ), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Phone", 1, 500.0, Category.ELECTRONICS)
                        ), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Book", 3, 20.0, Category.BOOKS)
                        ), OrderStatus.CANCELLED),
                new Order("O4", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Headphones", 1, 150.0, Category.ELECTRONICS)
                        ), OrderStatus.SHIPPED)
        );

        double totalIncome = analyzer.getTotalIncome(orders);

        assertEquals(1600.0, totalIncome, 0.01);
    }

    @Test
    void getMostPopularProductTest() {
        Customer customer = new Customer("1", "John", "john@test.com",
                LocalDateTime.now(), 30, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Book", 5, 20.0, Category.BOOKS),
                                new OrderItem("Pen", 3, 5.0, Category.BOOKS)
                        ), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Book", 2, 20.0, Category.BOOKS),
                                new OrderItem("Notebook", 1, 10.0, Category.BOOKS)
                        ), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Pen", 10, 5.0, Category.BOOKS)
                        ), OrderStatus.CANCELLED)
        );

        String mostPopular = analyzer.getMostPopularProduct(orders);

        assertEquals("Book", mostPopular);
    }

    @Test
    void getAverageOrderValueTest() {
        Customer customer = new Customer("1", "John", "john@test.com",
                LocalDateTime.now(), 30, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS)
                        ), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Phone", 1, 500.0, Category.ELECTRONICS),
                                new OrderItem("Case", 1, 50.0, Category.ELECTRONICS)
                        ), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Book", 1, 20.0, Category.BOOKS)
                        ), OrderStatus.CANCELLED),
                new Order("O4", LocalDateTime.now(), customer,
                        Arrays.asList(
                                new OrderItem("Tablet", 1, 300.0, Category.ELECTRONICS)
                        ), OrderStatus.DELIVERED)
        );

        double average = analyzer.getAverageOrderValue(orders);

        assertEquals(616.67, average, 0.01);
    }

    @Test
    void getCustomersWithMoreThan5OrdersTest() {
        Customer customerWithManyOrders = new Customer("1", "Mike", "mike@test.com",
                LocalDateTime.now(), 40, "Kazan");
        Customer customerWithFewOrders = new Customer("2", "Anna", "anna@test.com",
                LocalDateTime.now(), 28, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item1", 1, 10.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item2", 1, 20.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item3", 1, 30.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O4", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item4", 1, 40.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O5", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item5", 1, 50.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O6", LocalDateTime.now(), customerWithManyOrders,
                        Arrays.asList(new OrderItem("Item6", 1, 60.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O7", LocalDateTime.now(), customerWithFewOrders,
                        Arrays.asList(new OrderItem("Item7", 1, 70.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O8", LocalDateTime.now(), customerWithFewOrders,
                        Arrays.asList(new OrderItem("Item8", 1, 80.0, Category.BOOKS)), OrderStatus.DELIVERED)
        );

        var customers = analyzer.getCustomersWithMoreThan5Orders(orders);

        assertEquals(1, customers.size());
        assertEquals("Mike", customers.get(0).getName());
        assertEquals("mike@test.com", customers.get(0).getEmail());
    }

    @Test
    void getCustomersWithMoreThan5Orders_Exactly5OrdersTest() {
        Customer customerWith5Orders = new Customer("1", "Tom", "tom@test.com",
                LocalDateTime.now(), 35, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customerWith5Orders,
                        Arrays.asList(new OrderItem("Item1", 1, 10.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O2", LocalDateTime.now(), customerWith5Orders,
                        Arrays.asList(new OrderItem("Item2", 1, 20.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O3", LocalDateTime.now(), customerWith5Orders,
                        Arrays.asList(new OrderItem("Item3", 1, 30.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O4", LocalDateTime.now(), customerWith5Orders,
                        Arrays.asList(new OrderItem("Item4", 1, 40.0, Category.BOOKS)), OrderStatus.DELIVERED),
                new Order("O5", LocalDateTime.now(), customerWith5Orders,
                        Arrays.asList(new OrderItem("Item5", 1, 50.0, Category.BOOKS)), OrderStatus.DELIVERED)
        );

        var customers = analyzer.getCustomersWithMoreThan5Orders(orders);

        assertTrue(customers.isEmpty());
    }

    @Test
    void emptyOrdersListTest() {
        List<Order> orders = Arrays.asList();

        assertTrue(analyzer.getUniqueCities(orders).isEmpty());
        assertEquals(0.0, analyzer.getTotalIncome(orders));
        assertEquals("No products found", analyzer.getMostPopularProduct(orders));
        assertEquals(0.0, analyzer.getAverageOrderValue(orders));
        assertTrue(analyzer.getCustomersWithMoreThan5Orders(orders).isEmpty());
    }

    @Test
    void noDeliveredOrdersTest() {
        Customer customer = new Customer("1", "John", "john@test.com",
                LocalDateTime.now(), 30, "Moscow");

        List<Order> orders = Arrays.asList(
                new Order("O1", LocalDateTime.now(), customer,
                        Arrays.asList(new OrderItem("Laptop", 1, 1000.0, Category.ELECTRONICS)),
                        OrderStatus.NEW),
                new Order("O2", LocalDateTime.now(), customer,
                        Arrays.asList(new OrderItem("Phone", 1, 500.0, Category.ELECTRONICS)),
                        OrderStatus.CANCELLED)
        );

        assertEquals(0.0, analyzer.getTotalIncome(orders));
        assertEquals("No products found", analyzer.getMostPopularProduct(orders));
        assertEquals(0.0, analyzer.getAverageOrderValue(orders));
    }
}