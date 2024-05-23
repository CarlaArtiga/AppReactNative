/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ujmd.api.BD.dominio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author LENOVO
 */
public class ConexionBD {
     //atributos privados
    private Connection conexion;
    
    //Atributos publicos
    
    //constructor
    public ConexionBD(){
        String driver, usuario, clave, url;
        Configuracion cnf = new Configuracion("datos.txt");
        driver = System.getProperty("driver");
        usuario = System.getProperty("usuario");
        clave = System.getProperty("clave");
        url = System.getProperty("url");
        try{
            Class.forName(driver);
            this.conexion = DriverManager.getConnection(url, usuario, clave);
        }
        catch (ClassNotFoundException | SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public Connection getConexion(){
        return this.conexion;
    }
    
    public void cerrar(){
        try{
            this.conexion.close();
        }
        catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
    }
}
