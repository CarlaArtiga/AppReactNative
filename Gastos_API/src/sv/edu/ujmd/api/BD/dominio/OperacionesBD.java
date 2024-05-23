/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ujmd.api.BD.dominio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LENOVO
 */
public class OperacionesBD {
      //Atributos privados
    private ConexionBD cn;
    
    //constructor
    public OperacionesBD(){
        try{  
            //conexionBD cn = new conexionBD();
            this.cn = new ConexionBD();
        }
        catch (Exception e ){
            System.out.println("Error op" + e.getMessage());
        }
    }
    
    public List<Object[]> consultar (String sql){
        List<Object[]> lst = new ArrayList();
        Statement sentencia;
        ResultSet rs = null;
        int cols;
        Object[] fila;
        try{
            sentencia = this.cn.getConexion().createStatement();
            rs = sentencia.executeQuery(sql);
            cols = rs.getMetaData().getColumnCount();
            while(rs.next()){
                fila = new Object[cols];
                for (int i=0;i<cols;i++){
                    fila[i] = rs.getObject(i+1);
                }
                lst.add(fila);
            }
            this.cn.cerrar();
        }
        catch (SQLException | NullPointerException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return lst;
    }
    public int ejecutar(String sql){
        int nr=0;
        try{
            PreparedStatement ps = this.cn.getConexion().prepareStatement(sql);
            nr = ps.executeUpdate();
            this.cn.cerrar();
        }
        catch (SQLException ex){
            System.out.println("Error: " + ex.getMessage());
        }
        return nr;
    }
}
