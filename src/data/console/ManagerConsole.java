package data.console;

import data.dao.ManagerDAO;
import data.dao.WorkerDAO;
import data.models.Jobs;
import data.models.Order;
import data.models.Product;
import data.models.Worker;

import java.util.Scanner;

public class ManagerConsole {

    public static void managerInterface() {
        ManagerDAO managerDAO = new ManagerDAO();
        WorkerDAO workerDAO = new WorkerDAO();
        managerDAO.readProducts("products.csv");
        managerDAO.readOrders("orders.csv");
        workerDAO.readWorkersFile("workers.csv");
        managerDAO.readJobs("jobs.csv");
        Scanner userInput = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose what you want to do? \n 1.Add product \n 2.Update product \n 3.Remove product \n 4.See products \n 5. see orders \n 6.Close interface");
            int action = userInput.nextInt();
            switch (action) {
                case 1:
                    Scanner productInput = new Scanner(System.in);
                    System.out.print("Product name: ");
                    String productName = productInput.nextLine();
                    while (managerDAO.checkProductExist(productName)) {
                        System.out.println("Product exist, write different name");
                        System.out.print("Product name: ");
                        productName = productInput.nextLine();
                    }
                    System.out.print("\nProduct value: ");
                    int productValue = Integer.parseInt(productInput.nextLine());
                    System.out.print("\nProduct unit: ");
                    String productUnit = productInput.nextLine();
                    System.out.print("\nProduct cost: ");
                    float productCost = Float.parseFloat(productInput.nextLine());
                    managerDAO.add(new Product(productName, productValue, productUnit, productCost));
                    break;
                case 2:
                    System.out.println("Write product name:");
                    Scanner userInput2 = new Scanner(System.in);
                    String loadedProduct = userInput2.nextLine();
                    System.out.println("What you want to edit? \n 1.Name \n 2.Value \n 3.Unit \n 4.Cost");
                    action = userInput.nextByte();
                    Product updatedProduct = managerDAO.loadProductData(loadedProduct);
                    String[] dataUpdate = new String[4];
                    switch (action) {
                        case 1:
                            System.out.println("Write new name:");
                            dataUpdate[0] = userInput2.nextLine();
                            managerDAO.update(updatedProduct, dataUpdate);
                            System.out.println("Product name updated");
                            break;
                        case 2:
                            System.out.println("Write new value:");
                            dataUpdate[1] = userInput.nextLine();
                            managerDAO.update(updatedProduct, dataUpdate);
                            System.out.println("Product value updated");
                            break;
                        case 3:
                            System.out.println("Write new unit:");
                            dataUpdate[2] = userInput.nextLine();
                            managerDAO.update(updatedProduct, dataUpdate);
                            System.out.println("Product unit updated");
                            break;
                        case 5:
                            System.out.println("Write new cost:");
                            dataUpdate[3] = userInput.nextLine();
                            managerDAO.update(updatedProduct, dataUpdate);
                            System.out.println("Product cost updated");
                            break;
                    }
                    break;
                case 3:
                    System.out.print("Write product name to remove: ");
                    productInput = new Scanner(System.in);
                    productName = productInput.nextLine();
                    managerDAO.delete(managerDAO.loadProductData(productName));
                    System.out.println("Product removed.");
                    break;
                case 4:
                    for (Product products : managerDAO.getAll()) {
                        System.out.println(products.getName() + " " + products.getValue() + " " + products.getUnit() + " " + products.getCost());
                    }
                    break;
                case 5:
                    managerDAO.updateFileOrders("orders.csv");
                    int orderNumber = 1;
                    for (Order order : managerDAO.getOrders()) {
                        if (managerDAO.getJobs().stream().noneMatch(jobs -> jobs.getUuid().equals(order.getId()))) {
                            System.out.println("Nr. " +orderNumber + "\n date: " + order.getDate() + " " + "\n Cost: " + order.getCost() + " \nPaid: " + order.isPaid() + "\nStatus: " + order.getStatus());
                            for (Product product : order.getProducts()) {
                                System.out.print(product.getName() + " ");
                            }
                            System.out.println("\n");
                            orderNumber++;
                        }
                    }
                    System.out.println("What you want to do? \n 1.Assign order \n 2. Cancel");
                    action = userInput.nextInt();
                    switch (action) {
                        case 1:
                            System.out.println("Choose order number");
                            action = userInput.nextInt();
                            int counter = 1;
                            for (Worker worker : workerDAO.getWorkers()) {
                                System.out.println(counter + " " + worker.getName());
                                counter++;
                            }
                            System.out.println("Choose worker number");
                            counter = userInput.nextInt();
                            managerDAO.addJob(managerDAO.getOrders().get(action-1).getId(), managerDAO.getOrders().get(action - 1).getClient(), workerDAO.getWorkers().get(counter - 1).getUUID());
                            System.out.println("Work assigned.");
                            break;
                        case 2:
                            break;
                    }
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong option.");
            }
        }
    }
    public static void main(String[] args) {
        managerInterface();
    }
}
