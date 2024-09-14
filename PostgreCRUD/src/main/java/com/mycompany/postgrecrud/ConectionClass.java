/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.postgrecrud;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Gladys
 */
public class ConectionClass {

    /*Datos para la conexion de la bd de postgre*/
    Connection conectar = null;
    String usuario = "postgres";
    String contrasenia = "12345";
    String bd = "postgres";
    String ip = "localhost";
    String puerto = "5432";

    String cadena = "jdbc:postgresql://" + ip + ":" + puerto + "/" + bd;

    public Connection establecerConexion() {

        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+ e.toString());
        }
        return conectar;
    }
    
}
