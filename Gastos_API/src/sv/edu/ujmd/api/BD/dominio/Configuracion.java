/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ujmd.api.BD.dominio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author LENOVO
 */
public class Configuracion {
     //Miembros privados
    private Properties propiedades;
    private InputStream entrada;
    
    //Miembros publicos 
    public Configuracion(String archivo){
        this.propiedades = new Properties();
        try {
            String rutaAbsoluta = "C:/Users/LENOVO/Documents/NOVENO CICLO/DSA/PROYECTO FERIA/Gastos_API/" + archivo;
            InputStream entrada = new FileInputStream(rutaAbsoluta);
            if (entrada != null) {
                this.propiedades.load(entrada);
                System.setProperty("driver", this.propiedades.getProperty("driver"));
                System.setProperty("usuario", this.propiedades.getProperty("usuario"));
                System.setProperty("clave", this.propiedades.getProperty("clave"));
                System.setProperty("url", this.propiedades.getProperty("url"));
            } else {
                System.out.println("El archivo " + archivo + " no se encontró en la ruta especificada.");
            }
        } catch (IOException ex) {
            System.out.println("Error al cargar el archivo de configuración: " + ex.getMessage());
        }
    }
    
}
