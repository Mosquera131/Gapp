package org.example.controlllers;

import org.example.entity.Client;
import org.example.model.ClientModel;

import javax.swing.*;
import java.util.List;

public class ClientController {
    public void registerClient() {
        try {

            String fullname = JOptionPane.showInputDialog("Enter the client's full name:");
            if (fullname == null || fullname.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Full name is required.");
                return;
            }

            String email = JOptionPane.showInputDialog("Enter the client's email:");
            if (email == null || email.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Email is required.");
                return;
            }

            String phoneStr = JOptionPane.showInputDialog("Enter the client's phone number:");
            if (phoneStr == null || phoneStr.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Phone number is required.");
                return;
            }

            long phoneNumber;
            try {
                phoneNumber = Long.parseLong(phoneStr.trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid phone number. Please enter a valid number.");
                return;
            }

            String address = JOptionPane.showInputDialog("Enter the client's address:");
            if (address == null || address.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Address is required.");
                return;
            }


            boolean success = ClientModel.registerClient(fullname.trim(), email.trim(), phoneNumber, address.trim());

            if (success) {
                JOptionPane.showMessageDialog(null, "Client registered successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to register the client. Please try again.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage());
        }
    }

    public void listAllClients() {
        try {
            // Llama al modelo para obtener la lista de clientes
            List<Client> clients = ClientModel.findAll();

            // Verifica si la lista está vacía
            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No clients found.", "Clients List", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Construye el mensaje con los datos de los clientes
                StringBuilder message = new StringBuilder("Clients List:\n");
                for (Client client : clients) {
                    message.append("ID: ").append(client.getId())
                            .append(", Name: ").append(client.getFullname())
                            .append(", Email: ").append(client.getEmail())
                            .append("\n");
                }
                // Muestra la lista en un cuadro de diálogo
                JOptionPane.showMessageDialog(null, message.toString(), "Clients List", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            // Maneja errores inesperados
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
