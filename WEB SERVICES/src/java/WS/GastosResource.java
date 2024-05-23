package WS;

import io.jsonwebtoken.Jwts;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Base64;
import java.util.List;
import sv.edu.ujmd.api.BD.dao.GastoDAO;
import sv.edu.ujmd.api.BD.entidad.Gastos;

@Path("gastos")
public class GastosResource {

    @Context
    private UriInfo context; 
    private GastoDAO dao = new GastoDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllGastos(@HeaderParam("Authorization") String token) {
        String _token = token.substring(7);
        String[] datos = _token.split("\\.");
        Base64.Decoder decodificador = Base64.getUrlDecoder();
        try {
            Jwts.parser().setSigningKey("miclave").parseClaimsJws(_token);
            System.out.println("Autorizado");
            System.out.println("Payload: " + new String(decodificador.decode(datos[1])));
            List<Gastos> gastos = dao.getAll();
            if (gastos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No se encontraron gastos").build();
            } else {
                return Response.ok(gastos).build();
            }
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener gastos: " + ex.getMessage()).build();
        }
    }

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response createGasto(Gastos gasto) {
    try {
        int result = dao.insertar(gasto);
        if (result > 0) {
            // Obtener el ID del nuevo registro desde la respuesta del método insertar
            int nuevoId = result;

            // Actualizar el ID del objeto gasto con el ID del nuevo registro
            gasto.setId(nuevoId);

            // Crear un objeto JSON con los detalles del gasto creado
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("descripcion", gasto.getDescripcion())
                .add("monto", gasto.getMonto())
                .add("fecha", gasto.getFecha() != null ? gasto.getFecha().toString() : "") // Manejar fecha nula
                .build();
                
            // Retornar el objeto JSON como respuesta
            return Response.status(Response.Status.CREATED)
                .entity(jsonResponse.toString())
                .build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error al crear gasto")
                .build();
        }
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
            .entity("Error al crear gasto: " + e.getMessage())
            .build();
    }
}

@PUT
@Path("{id}")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response updateGasto(@PathParam("id") int id, Gastos gasto) {
    try {
        if (id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("ID de gasto no válido").build();
        }

        gasto.setId(id);  // Asegúrate de que el ID del gasto está correctamente configurado

        int rowsAffected = dao.modificar(gasto);
        if (rowsAffected > 0) {
            return Response.ok().entity(gasto).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Gasto no encontrado").build();
        }
    } catch (Exception e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar el gasto: " + e.getMessage()).build();
    }
}




    @DELETE
    public Response deleteGasto(@QueryParam("id") int id) {
        try {
            int resultado = dao.eliminar(id);
            if (resultado > 0) {
                return Response.ok("Gasto eliminado exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No se encontró el gasto con el ID proporcionado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar el gasto: " + e.getMessage()).build();
        }
    }
}
