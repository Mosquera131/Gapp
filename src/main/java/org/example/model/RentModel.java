package org.example.model;

import org.example.database.ConfigDB;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RentModel {
    public boolean registerRent(int clientId, int machineId, LocalDate startDate, LocalDate endDate) {
        Connection objConnection = ConfigDB.openConnection();
        try {
            // check machine availability

            String checkMachineAvailability = "SELECT status FROM machines WHERE id = ? AND status = 'Available'";
            PreparedStatement checkMachineStmt = objConnection.prepareStatement(checkMachineAvailability);
            checkMachineStmt.setInt(1, machineId);

            ResultSet machineResult = checkMachineStmt.executeQuery();
            if (!machineResult.next()) {
                JOptionPane.showMessageDialog(null, "This machine is not available for rent.");
                return false;
            }

            // registering the Rent
            String registerRentQuery = "INSERT INTO rents (client_id, machine_id, start_date, end_date, active) VALUES (?, ?, ?, ?, TRUE)";
            PreparedStatement registerRentStmt = objConnection.prepareStatement(registerRentQuery);
            registerRentStmt.setInt(1, clientId);
            registerRentStmt.setInt(2, machineId);
            registerRentStmt.setDate(3, java.sql.Date.valueOf(startDate));
            registerRentStmt.setDate(4, java.sql.Date.valueOf(endDate));

            int rowsInserted = registerRentStmt.executeUpdate();
            if (rowsInserted == 0) {
                JOptionPane.showMessageDialog(null, "Failed to register the rent.");
                return false;
            }

            // changing the Status of the machine fo rented
            String updateMachineStatus = "UPDATE machines SET status = 'Rented' WHERE id = ?";
            PreparedStatement updateMachineStmt = objConnection.prepareStatement(updateMachineStatus);
            updateMachineStmt.setInt(1, machineId);

            int rowsUpdated = updateMachineStmt.executeUpdate();
            if (rowsUpdated == 0) {
                JOptionPane.showMessageDialog(null, "Failed to update the machine status.");
                return false;
            }

            JOptionPane.showMessageDialog(null, "The rent has been successfully registered.");
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in registering the rent: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }

    // getting all the rents that has been done so far.

    public List<String> getAllRents(boolean includeInactive) {
        Connection objConnection = ConfigDB.openConnection();
        List<String> rents = new ArrayList<>();

        String query = includeInactive
                ? "SELECT r.id, c.fullname AS client_name, m.model AS machine_model, r.start_date, r.end_date, r.active " +
                "FROM rents r " +
                "JOIN clients c ON r.client_id = c.id " +
                "JOIN machines m ON r.machine_id = m.id"
                : "SELECT r.id, c.fullname AS client_name, m.model AS machine_model, r.start_date, r.end_date, r.active " +
                "FROM rents r " +
                "JOIN clients c ON r.client_id = c.id " +
                "JOIN machines m ON r.machine_id = m.id " +
                "WHERE r.active = TRUE";

        try {
            PreparedStatement stmt = objConnection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String rent = "ID: " + rs.getInt("id") +
                        ", Client: " + rs.getString("client_name") +
                        ", Machine: " + rs.getString("machine_model") +
                        ", Start Date: " + rs.getDate("start_date") +
                        ", End Date: " + rs.getDate("end_date") +
                        ", Active: " + rs.getBoolean("active");
                rents.add(rent);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving rents: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return rents;
    }

    //doing a sofdelete

    public boolean deactivateRent(int rentId, int machineId) {
        Connection objConnection = ConfigDB.openConnection();

        try {
            objConnection.setAutoCommit(false); // Iniciar la transacción

            // Marcar el alquiler como desactivado
            String deactivateRentQuery = "UPDATE rents SET active = FALSE WHERE id = ?";
            PreparedStatement deactivateRentStmt = objConnection.prepareStatement(deactivateRentQuery);
            deactivateRentStmt.setInt(1, rentId);

            int rowsUpdatedRent = deactivateRentStmt.executeUpdate();
            if (rowsUpdatedRent == 0) {
                JOptionPane.showMessageDialog(null, "Failed to deactivate the rent. It may already be deactivated.");
                return false;
            }

            // Cambiar el estado de la máquina a 'Available'
            String updateMachineStatus = "UPDATE machines SET status = 'Available' WHERE id = ?";
            PreparedStatement updateMachineStmt = objConnection.prepareStatement(updateMachineStatus);
            updateMachineStmt.setInt(1, machineId);

            int rowsUpdatedMachine = updateMachineStmt.executeUpdate();
            if (rowsUpdatedMachine == 0) {
                JOptionPane.showMessageDialog(null, "Failed to update the machine status.");
                return false;
            }

            objConnection.commit(); // Confirmar la transacción
            JOptionPane.showMessageDialog(null, "The rent has been successfully deactivated and the machine is now available.");
            return true;

        } catch (SQLException e) {
            try {
                objConnection.rollback(); // Revertir cambios si algo falla
            } catch (SQLException rollbackEx) {
                JOptionPane.showMessageDialog(null, "Error during rollback: " + rollbackEx.getMessage());
            }
            JOptionPane.showMessageDialog(null, "Error during deactivating the rent: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }


}
