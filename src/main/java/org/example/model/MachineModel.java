package org.example.model;

import org.example.database.ConfigDB;
import org.example.entity.Machine;
import org.example.helper.StatusMachine;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MachineModel {
    public boolean registerMachine(String name, String model, String serialNumber, String status) {
        Connection objConnection = ConfigDB.openConnection();
        try {
            // Verificar si el email ya está registrado
            String checkserialNumber = "SELECT * FROM machines WHERE serialNumber = ?;";
            PreparedStatement checkserialNumberStmt = objConnection.prepareStatement(checkserialNumber);
            checkserialNumberStmt.setString(1, serialNumber);

            ResultSet emailResult = checkserialNumberStmt.executeQuery();
            if (emailResult.next()) {
                JOptionPane.showMessageDialog(null, "This machine is already in our date base.");
                return false;
            }

            //adding a machine
            String insertClient = "INSERT INTO machines (fullname, email, phoneNumber, address) VALUES (?, ?, ?, ?);";
            PreparedStatement insertStmt = objConnection.prepareStatement(insertClient);
            insertStmt.setString(1, name);
            insertStmt.setString(2, model);
            insertStmt.setString(3, serialNumber);
            insertStmt.setString(4, status);
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "The machine has been sucefully registered");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "There was a mistake doing the registration");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error in the register: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }

    public List<Machine> listMachinesWithPagination(int pageNumber, int pageSize) {
        Connection objConnection = ConfigDB.openConnection();
        List<Machine> machines = new ArrayList<>();
        String query = "SELECT * FROM machines ORDER BY id ASC LIMIT ? OFFSET ?";

        try {
            PreparedStatement stmt = objConnection.prepareStatement(query);
            stmt.setInt(1, pageSize);
            stmt.setInt(2, (pageNumber - 1) * pageSize);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Machine machine = new Machine();
                machine.setId(rs.getInt("id"));
                machine.setName(rs.getString("name"));
                machine.setModel(rs.getString("model"));
                machine.setSerialNumber(rs.getString("serialNumber"));
                StatusMachine.fromString(rs.getString("status"));
                machines.add(machine);
                machines.add(machine);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving machines: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }
        return machines;
    }



}
