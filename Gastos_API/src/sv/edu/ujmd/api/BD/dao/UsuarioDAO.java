/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sv.edu.ujmd.api.BD.dao;

import java.util.List;
import sv.edu.ujmd.api.BD.dominio.OperacionesBD;
import sv.edu.ujmd.api.BD.entidad.Usuario;

/**
 *
 * @author LENOVO
 */
public class UsuarioDAO {
    public boolean validar(Usuario us) {
        boolean isValid = false;
        String sql = "SELECT id FROM usuarios WHERE correo = '" + us.getCorreo() + "' AND contraseña = MD5('" + us.getContraseña() + "')";
        OperacionesBD op = new OperacionesBD();
        List<Object[]> lista = op.consultar(sql);
        if (!lista.isEmpty()) {
            isValid = true;
        }
        return isValid;
    }
    
}
