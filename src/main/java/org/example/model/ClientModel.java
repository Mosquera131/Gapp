package org.example.model;

import org.example.database.ConfigDB;
import org.example.entity.Client;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {

    public  static boolean registerClient(String fullname,String email,long phoneNumber,String address)
    {
        Connection objConnection = ConfigDB.openConnection();
        try {
            // Verificar si el email ya está registrado
            String checkEmail = "SELECT * FROM clients WHERE email = ?;";
            PreparedStatement checkEmailStmt = objConnection.prepareStatement(checkEmail);
            checkEmailStmt.setString(1, email);

            ResultSet emailResult = checkEmailStmt.executeQuery();
            if (emailResult.next()) {
                JOptionPane.showMessageDialog(null, "This email is already in our date base.");
                return false;
            }

            //adding the Client
            String insertClient = "INSERT INTO clients (fullname, email, phoneNumber, address) VALUES (?, ?, ?, ?);";
            PreparedStatement insertStmt = objConnection.prepareStatement(insertClient);
            insertStmt.setString(1, fullname);
            insertStmt.setString(2, email);
            insertStmt.setLong(3, phoneNumber);
            insertStmt.setString(4, address);
            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "The client has been sucefully registered");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "There was a mistake doing the registration");
                return false;
            }
         } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en el registro: " + e.getMessage());
            return false;
        } finally {
            ConfigDB.closeConnection();
        }
    }


    public static List<Client> findAll(){
        List<Client> listClients= new ArrayList<>();
        Connection objConnection = ConfigDB.openConnection();
        try {
            String sql = "SELECT * FROM users;";
            PreparedStatement stmt =  objConnection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getInt("id"));
                client.setFullname(resultSet.getString("fullname"));
                client.setEmail (resultSet.getString("email"));

                listClients.add(client);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving users: " + e.getMessage());
        } finally {
            ConfigDB.closeConnection();
        }

        return listClients;

    }

    public void listAllClients() {
        try {
            List<Client> clients = ClientModel.findAll();

            if (clients.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No clients found.");
            } else {
                StringBuilder message = new StringBuilder("Clients List:\n");
                for (Client client : clients) {
                    message.append("ID: ").append(client.getId())
                            .append(", Name: ").append(client.getFullname())
                            .append(", Email: ").append(client.getEmail())
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, message.toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage());
        }
    }
}
