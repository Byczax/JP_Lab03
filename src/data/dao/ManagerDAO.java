package data.dao;

import data.models.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class ManagerDAO implements DAO<Product> {
    private final List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private final List<Jobs> jobs = new ArrayList<>();
    String fileName = "products.csv";

    public List<Product> getAll() {
        readProducts(fileName);
        return products;
    }

    public void add(Product product) {
        products.add(product);
        updateFileProducts(fileName);
    }

    public void update(Product product, String[] parameters) {
        if (parameters[0] != null)
            product.setName(parameters[0]);
        if (parameters[1] != null)
            product.setValue(Integer.parseInt(parameters[1]));
        if (parameters[2] != null)
            product.setUnit(parameters[0]);
        if (parameters[3] != null)
            product.setCost(Float.parseFloat(parameters[4]));
        updateFileProducts(fileName);
    }

    @Override
    public void delete(Product product) {
        products.remove(product);
        updateFileProducts(fileName);
    }

    public void readProducts(String fileName) {
        try (Scanner data = new Scanner(new File(fileName))) {
            products.clear();
            while (data.hasNext()) {
                String row = data.next();
                String[] strData = row.split(";");
                products.add(new Product(strData[0], strData[1], Integer.parseInt(strData[2]), strData[3], Float.parseFloat(strData[4])));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateFileProducts(String fileName) {
        FileWriter updatedFile = null;
        try {
            updatedFile = new FileWriter(fileName, false);
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
        for (Product fProduct : products) {
            try {
                assert updatedFile != null;
                updatedFile.write(fProduct.getId() + ";" + fProduct.getName() + ";" + fProduct.getValue() + ";" + fProduct.getUnit() + ";" + fProduct.getCost() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unexpected empty elements in client list.");
            }
        }
        try {
            assert updatedFile != null;
            updatedFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product loadProductDataId(UUID uuid) {
        readProducts(fileName);
        var foundProduct = products.stream().filter(product -> product.getId().equals(uuid)).findFirst();
        return foundProduct.orElse(null);
    }

    public Product loadProductData(String username) {
        var foundProduct = products.stream().filter(product -> product.getName().equals(username)).findFirst();
        return foundProduct.orElse(null);
    }

    public boolean checkProductExist(String username) {
        return products.stream().anyMatch(product -> product.getName().equals(username));
    }

    public Order createOrder(List<Product> clientBasket, UUID client) {
        int cost = 0;
        for (Product products : clientBasket) {
            cost += products.getCost();
        }
        Order createdOrder = new Order(clientBasket, cost, client);
        orders.add(createdOrder);
        updateFileOrders("orders.csv");
//        clientDAO.updateClientsFile("clients.csv");
        return createdOrder;
    }

    public void updateFileOrders(String fileName) {
        FileWriter updatedFile = null;
        try {
            updatedFile = new FileWriter(fileName, false);
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
        for (Order fOrder : orders) {
            boolean isPaid = false;

            try {
                assert updatedFile != null;
                if (fOrder.getStatus().equals(OrderStatus.AWAITING))
                    isPaid = true;
                updatedFile.write(fOrder.getId() + ";" + fOrder.getDate() + ";" + fOrder.getCost() + ";" + fOrder.getClient() + ";" + fOrder.getStatus() + ";" + isPaid + ";");

                boolean firstElement = true;
                for (Product product : fOrder.getProducts()) {
                    if (firstElement) {
                        updatedFile.write(String.valueOf(product.getId()));
                        firstElement = false;
                    } else {
                        updatedFile.write("|" + product.getId());
                    }
                }
                updatedFile.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unexpected empty elements in client list.");
            }
        }
        try {
            assert updatedFile != null;
            updatedFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readOrders(String fileName) {
        try (Scanner data = new Scanner(new File(fileName))) {
            while (data.hasNext()) {
                List<Product> products = new ArrayList<>();
                String row = data.next();
                String[] strData = row.split(";");
                if (strData.length > 6) {
                    String[] basket = strData[6].split("\\|");
                    for (String s : basket) {

                        products.add(loadProductDataId(UUID.fromString(s)));
                    }
                }
                orders.add(new Order(UUID.fromString(strData[0]), LocalDate.parse(strData[1]), Integer.parseInt(strData[2]), UUID.fromString(strData[3]), OrderStatus.valueOf(strData[4]), Boolean.getBoolean(strData[5]), products));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getUserOrders(UUID user) {
        ArrayList<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            if (order.getClient().equals(user)) {
                orderList.add(order);
            }
        }
        return orderList;
    }

    public List<Jobs> getWorkerJobs(UUID worker) {
        List<Jobs> myJobs = new ArrayList<>();
        for (Jobs job : jobs) {
            if (job.getWorker().equals(worker)) {
                myJobs.add(job);
            }
        }
        return myJobs;
    }

    public Order getOrderInfo(UUID orderId) {
        var foundOrder = orders.stream().filter(worker -> worker.getClient().equals(orderId)).findFirst();
        return foundOrder.orElse(null);
    }

    public void readJobs(String fileName) {
        try (Scanner data = new Scanner(new File(fileName))) {
            while (data.hasNext()) {
                String row = data.next();
                String[] strData = row.split(";");
                jobs.add(new Jobs(UUID.fromString(strData[0]), UUID.fromString(strData[1]), UUID.fromString(strData[2])));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public void updateJobs(String fileName) {
        FileWriter updatedFile = null;
        try {
            updatedFile = new FileWriter(fileName, false);
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
        for (Jobs job : jobs) {
            try {
                assert updatedFile != null;
                updatedFile.write(job.getUuid() + ";" + job.getClient() + ";" + job.getWorker() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Unexpected empty elements in client list.");
            }
        }
        try {
            assert updatedFile != null;
            updatedFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOrderToWorker(UUID order, UUID client, UUID worker) {
        jobs.add(new Jobs(order, client, worker));
        updateJobs("jobs.csv");
    }

    public List<Jobs> getJobs() {
        return jobs;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addJob(UUID order, UUID client, UUID worker) {
        jobs.add(new Jobs(order, client, worker));
        updateJobs("jobs.csv");
    }

    public void setOrders(List<Order> orderList) {
        orders = orderList;
    }
//    public void removeOrder(int index){
//        orders.remove(index);
//        updateFileOrders("orders.csv");
//    }
}
