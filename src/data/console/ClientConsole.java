package data.console;

import data.dao.*;
import data.models.Client;
import data.models.Order;
import data.models.OrderStatus;
import data.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientConsole {

//    ManagerDAO managerDAO;
//
//    private static ClientConsole instance;
//
//    public static ClientConsole getInstance(ManagerDAO managerDAO) {
//        if (instance == null)
//            instance = new ClientConsole(managerDAO);
//        return instance;
//    }
//
//    private ClientConsole(ManagerDAO managerDao) {
//        managerDAO = managerDao;
//    }

    public static void userInterface() {
        ClientDAO clientDao = new ClientDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        managerDAO.readProducts("products.csv");
        clientDao.readClientsFile("clients.csv");
        managerDAO.readOrders("orders.csv");
        Scanner userInput = new Scanner(System.in);
        String username;
        Client loadedClient;
        Product tempProduct;
        System.out.println("Client interface chosen. \n\n Write your username:");
        username = userInput.nextLine();
        if (clientDao.checkClientExist(username)) {
            loadedClient = clientDao.loadClientData(username);
        } else {
            System.out.println("Username not exist, creating new account");
            clientDao.add(new Client(username));
            System.out.println("Your account has been added.");
            loadedClient = clientDao.loadClientData(username);
        }
        List<Product> clientBasket = loadedClient.getBasket();
        String productName;
        Scanner userInput2 = new Scanner(System.in);
        List<Order> clientOrders = managerDAO.getOrders();
        boolean exit = false;
        while (!exit) {
            System.out.println("What you want to do? \n 1.See products \n 2. See basket \n 3.See orders \n 4.Account options \n5. exit");
            int action = userInput.nextInt();
            switch (action) {
                case 1:
                    for (Product products : managerDAO.getAll()) {
                        System.out.println(products.getName() + " " + products.getValue() + " " + products.getUnit() + " " + products.getCost());
                    }
                    System.out.println("\n 1.add product to basket \n 2.cancel");
                    action = userInput.nextInt();
                    switch (action) {
                        case 1:
                            System.out.print("\n Write product name from list:");
                            productName = userInput2.nextLine();
                            tempProduct = managerDAO.loadProductData(productName);
                            clientBasket.add(tempProduct);
                            System.out.println("Product added to basket");
                            clientDao.updateClientsFile("clients.csv");
                            break;
                        case 2:
                            break;
                    }
                    break;
                case 2:
                    if (!loadedClient.getBasket().isEmpty()) {
                        clientBasket = loadedClient.getBasket();
                        for (Product products : clientBasket) {
                            System.out.println(products.getName() + " " + products.getValue() + " " + products.getUnit() + " " + products.getCost());
                        }
                        System.out.println("Order products from basket? \n 1.Yes \n 2.No");
                        action = userInput.nextInt();
                        switch (action) {
                            case 1:
                                clientOrders.add(managerDAO.createOrder(clientBasket, loadedClient.getUUID()));
                                loadedClient.setBasket(new ArrayList<>());
                                clientDao.updateClientsFile("clients.csv");
                                System.out.println("Order created.");
                                break;
                            case 2:
                                break;
                        }
                    } else
                        System.out.println("Empty basket");
                    break;
                case 3:

                    clientOrders = managerDAO.getUserOrders(loadedClient.getUUID());
                    if (!clientOrders.isEmpty()) {
                        int orderNumber = 1;
                        for (Order order : clientOrders) {
                            if (order.getClient().equals(loadedClient.getUUID())) {
                                System.out.println("Nr. " + orderNumber + "\n date: " + order.getDate() + " " + "\n Cost: " + order.getCost() + " \nPaid: " + order.isPaid() + "\nStatus: " + order.getStatus());
                                for (Product product : order.getProducts()) {
                                    System.out.print(product.getName() + " ");
                                }
                                System.out.println("\n");
                            }
                            orderNumber++;
                        }
                        System.out.println("What you want to do? \n 1. Pay for order \n 2. cancel");
                        action = userInput.nextInt();
                        switch (action) {
                            case 1:
                                System.out.println("Choose order numer");
                                action = userInput.nextInt();
                                if (!clientOrders.get(action - 1).isPaid()) {
                                    clientOrders.get(action - 1).setPaid(true);
                                    clientOrders.get(action - 1).setStatus(OrderStatus.AWAITING);
                                    managerDAO.setOrders(clientOrders);
                                    managerDAO.updateFileOrders("orders.csv");
                                    System.out.println("You paid for " + action + " order.");
                                } else {
                                    System.out.println("You already paid for this order.");
                                }
                                break;
//                            case 2:
//                                System.out.println("Choose order numer");
//                                action = userInput.nextInt();
//                                clientOrders.remove(action - 1);
//                                managerDAO.removeOrder(action - 1);
//                                System.out.println("Order have been canceled.");
//                                break;
                            case 2:
                                break;
                        }
                    } else {
                        System.out.println("You dont have any orders.");
                    }
                    break;
                case 4:
                    System.out.println("UUID: " + loadedClient.getUUID());
                    System.out.println("Username: " + loadedClient.getName());
                    System.out.println("Account options: \n 1.Change username \n 2.Delete account \n 3.cancel");
                    action = userInput.nextInt();
                    switch (action) {
                        case 1:
                            userInput2 = new Scanner(System.in);
                            System.out.println("Write new username");
                            String newUsername = userInput2.nextLine();
                            while (clientDao.checkClientExist(newUsername)) {
                                System.out.println("Username already exist, choose other one");
                                newUsername = userInput2.nextLine();
                            }
                            clientDao.update(loadedClient, new String[]{newUsername});
                            System.out.println("Username changed.");
                            break;
                        case 2:
                            clientDao.delete(loadedClient);
                            System.out.println("Account deleted");
                            break;
                        case 3:
                            System.out.println("Canceled");
                            break;
                    }
                case 5:
                    exit = true;
                    break;

            }
        }
    }

    public static void main(String[] args) {
        userInterface();
    }
}
