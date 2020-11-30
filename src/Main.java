import java.util.Scanner;

import static data.console.ClientConsole.userInterface;
import static data.console.ManagerConsole.managerInterface;
import static data.console.WorkerConsole.workerInterface;

public class Main {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("Choose interface you want to use: \n 1.Client \n 2.Worker \n 3.Manager \n 4. Exit");
            int action = userInput.nextByte();
            switch (action) {
                case 1:
                    userInterface();
                    break;
                case 2:
                    workerInterface();
                    break;
                case 3:
                    managerInterface();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong choice.");
                    break;
            }
        }
    }

}
