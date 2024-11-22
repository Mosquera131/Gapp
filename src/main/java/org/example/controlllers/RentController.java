package org.example.controlllers;

import org.example.model.RentModel;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class RentController {
    private RentModel rentModel;

    public RentController() {
        rentModel = new RentModel();
    }

    public void createRent() {
        try {
            String clientIdInput = JOptionPane.showInputDialog("Enter the client ID:");

            if (clientIdInput == null || clientIdInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Client ID is required.");
                return;
            }
            int clientId = Integer.parseInt(clientIdInput.trim());

            String machineIdInput = JOptionPane.showInputDialog("Enter the machine ID:");
            if (machineIdInput == null || machineIdInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Machine ID is required.");
                return;
            }
            int machineId = Integer.parseInt(machineIdInput.trim());

            String startDateInput = JOptionPane.showInputDialog("Enter the start date (YYYY-MM-DD):");
            if (startDateInput == null || startDateInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Start date is required.");
                return;
            }
            LocalDate startDate = LocalDate.parse(startDateInput.trim());
            String endDateInput = JOptionPane.showInputDialog("Enter the end date (YYYY-MM-DD):");
            if (endDateInput == null || endDateInput.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "End date is required.");
                return;
            }
            LocalDate endDate = LocalDate.parse(endDateInput.trim());
            if (startDate.isAfter(endDate)) {
                JOptionPane.showMessageDialog(null, "The start date cannot be after the end date.");
                return;
            }


            // registering the rente
            boolean success = rentModel.registerRent(clientId, machineId, startDate, endDate);
            if (success) {
                JOptionPane.showMessageDialog(null, "Rent registered successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to register the rent. Please try again.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid numeric input: " + e.getMessage());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use YYYY-MM-DD.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An unexpected error occurred: " + e.getMessage());
        }
    }

    public void displayAllRents(boolean includeInactive) {
        List<String> rents = rentModel.getAllRents(includeInactive);

        if (rents.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No rents found.");
        } else {
            StringBuilder message = new StringBuilder("Rents:\n");
            for (String rent : rents) {
                message.append(rent).append("\n");
            }
            JOptionPane.showMessageDialog(null, message.toString());
        }
    }


}
