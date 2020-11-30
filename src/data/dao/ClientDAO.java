package data.dao;

import data.models.Client;
import data.models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ClientDAO implements DAO<Client> {

    private final List<Client> clients = new ArrayList<>();
    String fileName = "clients.csv";

    public List<Client> getAll() {
        readClientsFile("clients.csv");
        return clients;
    }

    public void add(Client client) {
        clients.add(client);
        updateClientsFile(fileName);
    }

    public void update(Client client, String[] username) {
        client.setName(username[0]);
        updateClientsFile(fileName);
    }

    public void delete(Client client) {
        clients.remove(client);
        updateClientsFile(fileName);
    }

    public void updateClientsFile(String fileName) {
        FileWriter updatedFile = null;
        try {
            updatedFile = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
        for (Client fClients : clients) {
            try {
                assert updatedFile != null;
                updatedFile.write(fClients.getUUID() + ";" + fClients.getName());
                if (!fClients.getBasket().isEmpty()) {
                    updatedFile.write(";");
                    boolean firstIteration = true;
                    for (Product products : fClients.getBasket()) {
                        if (firstIteration) {
                            updatedFile.write(String.valueOf(products.getId()));
                            firstIteration = false;
                        } else {
                            updatedFile.write("|" + products.getId());
                        }
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

    public void readClientsFile(String fileName) {
        ManagerDAO managerDAO = new ManagerDAO();
        try (Scanner data = new Scanner(new File(fileName))) {
            while (data.hasNext()) {
                List<Product> products = new ArrayList<>();
                String row = data.next();
                String[] strData = row.split(";");
                if (strData.length > 2) {
                    String[] basket = strData[2].split("\\|");
                    for (String s : basket) {

                        products.add(managerDAO.loadProductDataId(UUID.fromString(s)));
                    }
                }
                clients.add(new Client(strData[0], strData[1], products));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public boolean checkClientExist(String username) {
        return clients.stream().anyMatch(client -> client.getName().equals(username));
    }

    public Client loadClientData(String username) {
        var foundClient = clients.stream().filter(client -> client.getName().equals(username)).findFirst();
        return foundClient.orElse(null);
    }
}
