package data.dao;

import data.models.Client;
import data.models.Order;
import data.models.Product;
import data.models.Worker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class WorkerDAO implements DAO<Worker> {

    private final List<Worker> workers = new ArrayList<>();

    String fileName = "workers.csv";

    public List<Worker> getAll() {
        return workers;
    }

    public void add(Worker worker) {
        workers.add(worker);
        updateFile(fileName);
    }

    public void update(Worker worker, String[] username) {
        worker.setName(username[0]);
        updateFile(fileName);
    }

    public void delete(Worker worker) {
        workers.remove(worker);
        updateFile(fileName);
    }

    private void updateFile(String fileName) {
        FileWriter updatedFile = null;
        try {
            updatedFile = new FileWriter(fileName);
        } catch (IOException e) {
            System.out.println("Unable to create file");
            e.printStackTrace();
        }
        for (Worker worker : workers) {
            try {
                assert updatedFile != null;
                updatedFile.write(worker.getUUID() + ";" + worker.getName() + "\n");
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

    public boolean checkWorkerExist(String username) {
        return workers.stream().anyMatch(worker -> worker.getName().equals(username));
    }

    public Worker loadWorkerData(String username) {
        var foundClient = workers.stream().filter(worker -> worker.getName().equals(username)).findFirst();
        return foundClient.orElse(null);
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void readWorkersFile(String fileName) {
        try (Scanner data = new Scanner(new File(fileName))) {
            while (data.hasNext()) {
                String row = data.next();
                String[] strData = row.split(";");
                workers.add(new Worker(strData[0], strData[1]));
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }
}
