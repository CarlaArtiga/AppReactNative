/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package WS;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.util.Date;
import sv.edu.ujmd.api.BD.dao.UsuarioDAO;
import sv.edu.ujmd.api.BD.entidad.Usuario;

/**
 * REST Web Service
 *
 * @author LENOVO
 */
@Path("auth")
public class LoginServices {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of LoginServices
     */
    public LoginServices() {
    }

    /**
     * Retrieves representation of an instance of WS.LoginServices
     * @return an instance of java.lang.String
     */
    
    @POST
    @Consumes(jakarta.ws.rs.core.MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Validar (Usuario us) {
        
        if(us!=null){
           // Instantiate the daoUsuario class
           UsuarioDAO du = new UsuarioDAO();
           // Validate the user
           boolean isValid = du.validar(us);

           if (isValid) { // If the user is valid
               // Create a JWT
               String secretKey = "miclave"; // Clave secreta para firmar el JWT
               long tiempoActual = System.currentTimeMillis();
               String jwt = Jwts.builder()
                   .signWith(SignatureAlgorithm.HS256, secretKey)
                   .setSubject(us.getCorreo()) // Utilizamos el correo como sujeto del JWT
                   .setIssuedAt(new Date(tiempoActual))
                   .setExpiration(new Date(tiempoActual + 900000)) // JWT expira en 15 minutos
                   .claim("usuarioId", us.getId()) // Añadimos el ID del usuario como claim
                   .compact();

               JsonObject json = Json.createObjectBuilder()
                   .add("JWT", jwt)
                   .build();

               // Return the JWT
               return Response.status(Status.CREATED).entity(json).build();
           } else {
               // If the user is not valid, return an unauthorized status
               return Response.status(Status.UNAUTHORIZED).build();
           }
            
        }
        else{
            return Response.serverError().build();
        }
        
       
    }
    
}
