package org.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConfigDB {
    //Este atributo tendrá el estado de la conexión
    public static Connection objConnection = null;

    //Método para conectar la BD

    public static Connection openConnection(){

        try{
            //Llamamos el driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Creamos las variables de conexión

            String url = "jdbc:mysql://b9nr8lhpf3xawlgylly6-mysql.services.clever-cloud.com:3306/br7h00chufuasdpogxaq";
            String user = "uw5o9qouakqx6aiv";
            String password = "aC916FXWGVgErPWl4csz";

         //que devuelva la conexión de tipo conncection

            objConnection= (Connection) DriverManager.getConnection(url, user,password);
            System.out.println("My connection is perfect !!");

        }catch (ClassNotFoundException error){
            System.out.println("ERROR >> Driver no Instalado " + error.getMessage());
        }catch (SQLException error){
            System.out.println("ERROR >> error al conectar con la base de datos" + error.getMessage());
        }
        return objConnection;
    }

    public static void closeConnection(){
        try{
            if (objConnection !=null){
                objConnection.close();
                System.out.println("the connection has finished successfully ");
            }
        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
