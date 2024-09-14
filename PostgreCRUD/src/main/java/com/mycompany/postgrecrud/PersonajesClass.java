/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.postgrecrud;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
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
    
    public void Show(JTable tablePersonajes){
        
      ConectionClass obj= new ConectionClass();
      DefaultTableModel model= new DefaultTableModel();
      
      String sql="";
      
        model.addColumn("Id");
        model.addColumn("Nombre");
        model.addColumn("Origen");
        model.addColumn("Edad(Años)");
        
        tablePersonajes.setModel(model);
    
        sql="select * from Personajes";
        
        String [] datos = new String[4];
        
        Statement st;
        
        try{
            
            st = obj.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            while(rs.next()){
            datos[0]=rs.getString(1);
            datos[1]=rs.getString(2);
            datos[2]=rs.getString(3);
            datos[3]=rs.getString(4);
            
            model.addRow(datos);
            
            }
            
            tablePersonajes.setModel(model);
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error"+ e.toString());
        }
        
    }
    
    public void insert(JTextField nombre, JTextField origen, JTextField edad){
    
        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));
        
        ConectionClass obj = new ConectionClass();
        ResultSet rs = null;
        String consulta="insert into personajes (nombre, origen, edad) values ('{?}', '{?}', ?)";
        
        try{
            
          
            String query=String.format("insert into personajes (nombre, origen, edad) values('{%s}','{%s}', '%d');",getNombre(),getOrigen(),getEdad());
            Statement ps =obj.establecerConexion().createStatement();
            ps.executeUpdate(query);
            
            String query1=String.format("insert into public.\"Logtable\"( \"Newnombre\", \"Neworigen\", \"Newedad\", \"typeCRUD\", \"idMachine\") values('{%s}','{%s}', '%d', '1', '1');",getNombre(),getOrigen(),getEdad());
            Statement ps1 =obj.establecerConexion().createStatement();
            ps1.executeUpdate(query1);
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR"+e.toString());
        }
       
    }
    
    public List<String> SelectPer(JTable tablepersonajes, JTextField id, JTextField nombre, JTextField origen, JTextField edad){
        List<String> ArrayField = new ArrayList<>();
        try{
        
            int fila = tablepersonajes.getSelectedRow();
            
            if(fila>=0){
                
                id.setText(tablepersonajes.getValueAt(fila, 0).toString());
                nombre.setText(tablepersonajes.getValueAt(fila, 1).toString());
                origen.setText(tablepersonajes.getValueAt(fila, 2).toString());
                edad.setText(tablepersonajes.getValueAt(fila, 3).toString());
                
                
                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 1))); 
                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 2)));
                ArrayField.add(String.valueOf(tablepersonajes.getValueAt(fila, 3))); 
               
            }else{
            
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            
            }
            
        }catch(Exception e){
        
            JOptionPane.showMessageDialog(null, "ERROR"+e.toString());
            
        }
        
        
        return ArrayField;
        
    }
    
    public void Edit(List<String> ArrayField, JTextField id, JTextField nombre, JTextField origen, JTextField edad){
        
        setCodigo(Integer.parseInt(id.getText()));
        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));
        
        ConectionClass obj = new ConectionClass();
       
        String OriNombre = " ";
        OriNombre=ArrayField.getFirst();
        String Oriorigen = " ";
        ArrayField.removeFirst();
        Oriorigen=ArrayField.getFirst();
        String Edad = ArrayField.getLast();
        int Oriedad = Integer.parseInt(Edad);
       
        try{
            
          
            String query=String.format("UPDATE public.personajes SET nombre='{%s}', origen='{%s}', edad=%d WHERE personajes.id=%d;",getNombre(),getOrigen(),getEdad(),getCodigo());
            Statement ps =obj.establecerConexion().createStatement();
            ps.executeUpdate(query);
            
            String query1=String.format("insert into public.\"Logtable\"( \"Orinombre\", \"Oriorigen\", \"Oriedad\",\"Newnombre\", \"Neworigen\", \"Newedad\", \"typeCRUD\", \"idMachine\", \"idper\") values('{%s}','{%s}', '%d','{%s}','{%s}', '%d', '2', '1','%d');",OriNombre, Oriorigen, Oriedad,getNombre(),getOrigen(),getEdad(),getCodigo());
            Statement ps1 =obj.establecerConexion().createStatement();
            ps1.executeUpdate(query1);
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR"+e.toString());
        }
       
    }
    
    public void Delete(List<String> ArrayField, JTextField id, JTextField nombre, JTextField origen, JTextField edad){
        
        setCodigo(Integer.parseInt(id.getText()));
        setNombre(nombre.getText());
        setOrigen(origen.getText());
        setEdad(Integer.parseInt(edad.getText()));
        
        ConectionClass obj = new ConectionClass();
       
        String OriNombre = " ";
        OriNombre=ArrayField.getFirst();
        String Oriorigen = " ";
        ArrayField.removeFirst();
        Oriorigen=ArrayField.getFirst();
        String Edad = ArrayField.getLast();
        int Oriedad = Integer.parseInt(Edad);
       
        try{
            
          
            String query=String.format("DELETE FROM public.personajes WHERE personajes.id = %d;",getCodigo());
            Statement ps =obj.establecerConexion().createStatement();
            ps.executeUpdate(query);
            
            String query1=String.format("insert into public.\"Logtable\"( \"Orinombre\", \"Oriorigen\", \"Oriedad\", \"typeCRUD\", \"idMachine\", \"idper\") values('{%s}','{%s}', '%d', '3', '1','%d');",OriNombre, Oriorigen, Oriedad,getCodigo());
            Statement ps1 =obj.establecerConexion().createStatement();
            ps1.executeUpdate(query1);
            JOptionPane.showMessageDialog(null, "Operacion Exitosa");
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR"+e.toString());
        }
       
    }
}
