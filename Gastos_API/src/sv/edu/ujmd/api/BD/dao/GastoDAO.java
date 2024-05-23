package sv.edu.ujmd.api.BD.dao;

import java.util.ArrayList;
import java.util.List;
import sv.edu.ujmd.api.BD.dominio.OperacionesBD;
import sv.edu.ujmd.api.BD.entidad.Gastos;

public class GastoDAO {

    public List<Gastos> getAll() {
        List<Gastos> gastos = new ArrayList<>();
        String sql = "SELECT * FROM gastos";
        OperacionesBD op = new OperacionesBD();
        List<Object[]> lst = op.consultar(sql);
        for (Object[] row : lst) {
            Gastos gasto = new Gastos();
            gasto.setId(Integer.parseInt(row[0].toString()));
            gasto.setDescripcion(row[1].toString());
            gasto.setMonto(Double.parseDouble(row[2].toString()));
            gasto.setFecha(java.sql.Date.valueOf(row[3].toString()));
            gastos.add(gasto);
        }
        return gastos;
    }

    public int insertar(Gastos gasto) {
    String sql = "INSERT INTO gastos (descripcion, monto, fecha) VALUES ('" 
                 + gasto.getDescripcion() + "', " 
                 + gasto.getMonto() + ", '" 
                 + gasto.getFecha() + "')";
    OperacionesBD op = new OperacionesBD();
    return op.ejecutar(sql);
}

    public int modificar(Gastos gasto) {
        String sql = "UPDATE gastos SET descripcion = '" 
                     + gasto.getDescripcion() + "', monto = " 
                     + gasto.getMonto() + ", fecha = '" 
                     + gasto.getFecha() + "' WHERE id = " 
                     + gasto.getId();
        OperacionesBD op = new OperacionesBD();
        return op.ejecutar(sql);
    }

    public int eliminar(int id) {
        String sql = "DELETE FROM gastos WHERE id = " + id;
        OperacionesBD op = new OperacionesBD();
        return op.ejecutar(sql);
    }
}
