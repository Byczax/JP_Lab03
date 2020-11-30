package data.console;

import data.dao.ManagerDAO;
import data.dao.WorkerDAO;
import data.models.Jobs;
import data.models.Order;
import data.models.OrderStatus;
import data.models.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorkerConsole {

    public static void workerInterface() {
        WorkerDAO workerDAO = new WorkerDAO();
        ManagerDAO managerDAO = new ManagerDAO();
        managerDAO.readJobs("jobs.csv");
        workerDAO.readWorkersFile("workers.csv");
        managerDAO.readOrders("orders.csv");
        Scanner userInput = new Scanner(System.in);
        List<Jobs> myJobs;
        List<Order> myJobOrders = new ArrayList<>();
        String username;
        Worker loadedWorker;
        Order order;
        int counter;
        System.out.println("Worker interface chosen. \n\n Write your username:");
        username = userInput.nextLine();
        if (workerDAO.checkWorkerExist(username)) {
            loadedWorker = workerDAO.loadWorkerData(username);
        } else {
            System.out.println("Username not exist, creating new account");
            workerDAO.add(new Worker(username));
            System.out.println("Your account has been added.");
            loadedWorker = workerDAO.loadWorkerData(username);
        }
        boolean exit = false;
        while (!exit) {
            System.out.println("What you want to do? \n 1.see jobs \n 2. account options \n 3. exit");
            int action = userInput.nextInt();
            switch (action) {
                case 1:
                    myJobs = managerDAO.getWorkerJobs(loadedWorker.getUUID());
                    if (!myJobs.isEmpty()) {
                        counter = 1;
                        for (Jobs jobs : myJobs) {
                            order = managerDAO.getOrderInfo(jobs.getClient());
                            myJobOrders.add(order);
                            System.out.println(counter + " " + order.getDate() + " " + order.getStatus() + " ");
                            counter++;
                        }
                        System.out.println("What you want to do? \n 1. Update job status \n 2.cancel");
                        action = userInput.nextInt();
                        switch (action) {
                            case 1:
                                System.out.println("Select job number");
                                action = userInput.nextInt();
//                            counter = 1;
//                            for (OrderStatus orderStatus : OrderStatus.values()) {
//                                System.out.println(orderStatus);
//                                counter++;
//                            }
//                            System.out.println("Choose job status");
//                            action = userInput.nextInt();
                                OrderStatus orderStatus = myJobOrders.get(action - 1).getStatus();
                                myJobOrders.get(action - 1).setStatus(orderStatus.incrementSize());
                                break;
                            case 2:
                                break;

                        }
                    }
                    else {
                        System.out.println("You don't have any jobs, good work!");
                    }
                    break;
                case 2:
                    System.out.println("UUID: " + loadedWorker.getUUID());
                    System.out.println("Username: " + loadedWorker.getName());
                    System.out.println("Account options: \n 1.Change username \n 2.Delete account \n 3.cancel");
                    action = userInput.nextInt();
                    switch (action) {
                        case 1:
                            Scanner userInput2 = new Scanner(System.in);
                            System.out.println("Write new username");
                            String newUsername = userInput2.nextLine();
                            while (workerDAO.checkWorkerExist(newUsername)) {
                                System.out.println("Username already exist, choose other one");
                                newUsername = userInput2.nextLine();
                            }
                            workerDAO.update(loadedWorker, new String[]{newUsername});
                            System.out.println("Username changed.");
                            break;
                        case 2:
                            workerDAO.delete(loadedWorker);
                            System.out.println("Account deleted");
                            break;
                        case 3:
                            break;
                    }
                    break;
                case 3:
                    exit = true;
                    break;
            }
        }
    }
    public static void main(String[] args) {
        workerInterface();
    }
}
