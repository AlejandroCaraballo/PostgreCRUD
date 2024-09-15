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
    String contrasenia = "postgres";
    String bd = "postgres";
    String ip = "localhost";
    String puerto = "5432";

    //String cadena = "postgres://postgres:postgres@0.tcp.ngrok.io:18151,8.tcp.ngrok.io:12144/postgres";

    public Connection establecerConexion() {

        try {
            Class.forName("org.postgresql.Driver");
            conectar = DriverManager.getConnection("jdbc:postgresql://0.tcp.ngrok.io:18151,8.tcp.ngrok.io:12144/postgres", usuario, contrasenia);
            //ya se, ve a postgres y haz click derecho hasta la parte de script y busca UPDATE haber como lo hace tu postgres.

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR"+ e.toString());
        }
        return conectar;
    }
    
}
