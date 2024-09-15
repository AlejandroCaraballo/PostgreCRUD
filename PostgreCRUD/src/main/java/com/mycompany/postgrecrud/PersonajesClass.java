/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.postgrecrud;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gladys
 */
public class PersonajesClass {

    int codigo;
    String nombre;
    String origen;
    int edad;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void Show(JTable tablePersonajes) {

        ConectionClass obj = new ConectionClass();
        DefaultTableModel model = new DefaultTableModel();

        String sql = "";

        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Origen");
        model.addColumn("Edad(Años)");

        tablePersonajes.setModel(model);

        sql = "select * from Personajes";

        String[] datos = new String[4];

        Statement st;

        try {

            st = obj.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = rs.getString(1);
                datos[1] = rs.getString(2);
                datos[2] = rs.getString(3);
                datos[3] = rs.getString(4);

                model.addRow(datos);

            }

            tablePersonajes.setModel(model);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error" + e.toString());
        }

    }

    public void insert(JTextField nombre, JTextField origen, JTextField edad) {

        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));

        ConectionClass obj = new ConectionClass();

        try {

            PreparedStatement ps = obj.establecerConexion().prepareStatement("insert into personajes (nombre, origen, edad) values(?, ?, ?);");
            ps.setString(1, getNombre());
            ps.setString(2, getOrigen());
            ps.setInt(3, getEdad());
            ps.executeUpdate();
            

            PreparedStatement ps1 = obj.establecerConexion().prepareStatement("INSERT INTO public.\"Logtable\"(\n"
                    + "       \"Orinombre\", \"Oriorigen\", \"Oriedad\", \"Newnombre\", \"Neworigen\", \"Newedad\", \"typeCRUD\", \"idMachine\", idper)\n"
                    + "	VALUES (' ', ' ', null, ?, ?, ?, 1, 1, 0);");

            ps1.setString(1, getNombre());
            ps1.setString(2, getOrigen());
            ps1.setInt(3, getEdad());

            ps1.executeUpdate();
            
            //limpia la base de datos para hacer esta ultima prueba. si y gracias por la ayuda 
            
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.toString());
        }

    }

    public List<String> SelectPer(JTable tablepersonajes, JTextField id, JTextField nombre, JTextField origen, JTextField edad) {
        List<String> ArrayField = new ArrayList<>();
        try {

            int fila = tablepersonajes.getSelectedRow();

            if (fila >= 0) {

                id.setText(tablepersonajes.getValueAt(fila, 0).toString());
                nombre.setText(tablepersonajes.getValueAt(fila, 1).toString());
                origen.setText(tablepersonajes.getValueAt(fila, 2).toString());
                edad.setText(tablepersonajes.getValueAt(fila, 3).toString());

                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 1)));
                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 2)));
                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 3)));

            } else {

                JOptionPane.showMessageDialog(null, "Fila no seleccionada");

            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "ERROR" + e.toString());

        }

        return ArrayField;

    }

    public void Edit(List<String> ArrayField, JTextField id, JTextField nombre, JTextField origen, JTextField edad) {

        setCodigo(Integer.parseInt(id.getText()));
        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));

        ConectionClass obj = new ConectionClass();
        
        String OriNombre = ArrayField.removeFirst();
        String Oriorigen = ArrayField.removeFirst();
        String Edad = ArrayField.removeFirst();
        int Oriedad = Integer.parseInt(Edad);

        try {

            String query = String.format("UPDATE public.personajes SET nombre='{%s}', origen='{%s}', edad=%d WHERE personajes.id=%d;", getNombre(), getOrigen(), getEdad(), getCodigo());
            PreparedStatement ps = obj.establecerConexion().prepareStatement("UPDATE public.personajes\n" +
"	SET nombre=?, origen=?, edad=?\n" +
"	WHERE personajes.id= ?;");
            ps.setString(1, getNombre());
            ps.setString(2, getOrigen());
            ps.setInt(3, getEdad());
            ps.setInt(4, getCodigo());
          
            ps.executeUpdate();
            int typeCrud = 2;
            InsertLog(obj, OriNombre, Oriorigen, Oriedad, typeCrud);
            
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.toString());
        }

    }

    public void Delete(List<String> ArrayField, JTextField id, JTextField nombre, JTextField origen, JTextField edad) {

        setCodigo(Integer.parseInt(id.getText()));
        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));

        ConectionClass obj = new ConectionClass();

        String OriNombre = ArrayField.removeFirst();
        String Oriorigen = ArrayField.removeFirst();
        String Edad = ArrayField.removeFirst();
        int Oriedad = Integer.parseInt(Edad);

        try {

            PreparedStatement ps = obj.establecerConexion().prepareStatement("DELETE FROM public.personajes WHERE personajes.id = ?;");
            ps.setInt(1, getCodigo());
            
            ps.executeUpdate();
            int typeCrud = 3;
            InsertLog(obj, OriNombre, Oriorigen, Oriedad, typeCrud);
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.toString());
        }
            //qué aparece en el Log?, paseme un capt para si estan los campos bien
    }

    private void InsertLog(ConectionClass obj, String OriNombre, String Oriorigen, int Oriedad, int typeCrud) throws SQLException {
        PreparedStatement ps1 = obj.establecerConexion().prepareStatement("""
                            INSERT INTO public."Logtable"(
                        "Orinombre", "Oriorigen", "Oriedad", "Newnombre", "Neworigen", "Newedad", "typeCRUD", "idMachine", idper)
                 	VALUES (?, ?, ?, ?, ?, ?, ?, 1, ?);""");
        
        ps1.setString(1, OriNombre);
        ps1.setString(2, Oriorigen);
        ps1.setInt(3, Oriedad);
        ps1.setString(4, getNombre());
        ps1.setString(5, getOrigen());
        ps1.setInt(6, getEdad());
        ps1.setInt(7, typeCrud);
        ps1.setInt(8, getCodigo());
        
        ps1.executeUpdate();
    }
}
