package org.example;

import org.example.controlllers.ClientController;
import org.example.controlllers.MachineController;
import org.example.controlllers.RentController;
import org.example.database.ConfigDB;

import javax.swing.*;

public class Main {

    private static ClientController clientController = new ClientController();
    private static MachineController machineController = new MachineController();
    private static RentController  rentController = new RentController();



    public static void main(String[] args) {
        ConfigDB.openConnection();
        ConfigDB.closeConnection();

        while (true) {

            String menu = """
                --- What would you love to do ? ---
                1.Register a Client. 
                2. Display all the Clients that has been registered. 
                3. Register a Machine. 
                4.Display all the Machine that has been registered
                5. Register a new Rent
                6. Display all the rents that has been registered. 
                0. Exit
                Ingrese el número de la opción deseada:""";

            String input = JOptionPane.showInputDialog(null, menu, "Main Menu", JOptionPane.QUESTION_MESSAGE);


            // Validar si el usuario cancela o no ingresa nada
            if (input == null || input.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No ingresó ninguna opción. Saliendo del sistema...");
                break;
            }

            try {

                int option = Integer.parseInt(input);

                switch (option) {
                    case 1:
                         clientController.registerClient();
                        break;

                    case 2:
                        clientController.listAllClients();
                        break;

                    case 3:
                        machineController.createMachine();
                        break;


                    case 4:

                        return;
                    case 5:
                        rentController.createRent();
                        return;
                    case 6:
                        rentController.displayAllRents(true);
                        return;
                    case 0:
                        JOptionPane.showMessageDialog(null, "Exiting the system. Goodbye!", "Exit", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                        return;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida. Por favor, seleccione un número entre 1 y 4.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, ingrese un número.");
            }
        }
    }


}