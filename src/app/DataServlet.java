package app;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import app.dal.DbContext;
import app.model.entities.User;
import app.model.entities.Users;
import app.utils.JsonSerializer;
import app.utils.Utils;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/plain");

		PrintWriter out = response.getWriter();
		out.println("Method : " + request.getMethod());
		out.println("Headers: " + Utils.getHeadersInfo(request));
		out.println("Body   : " + Utils.getRequestBody(request));	
		out.println("==========================================================================");
		out.println("Properties.Numero: " + app.utils.Utils.getProperty("Numero"));
		out.println("Properties.Letras: " + app.utils.Utils.getProperty("Letras"));
		out.println("==========================================================================");
		try(DbContext dbContext = new DbContext()){

			User user = new User(dbContext).load(2);
			out.println("Usuario 2: " + user.nif);	

			Users users = new Users(dbContext).load();
			users.forEach( u -> out.println(u.nif));
			
			out.println("Total usuarios: " + users.size());	
			out.println("Select COUNT(*) usuarios: " + users.Count());

			// var data = users.stream()
			// 								.filter( u -> u.Id > 2)
			// 								.collect(Collectors.toList());

			users.add(new User());

			out.println(JsonSerializer.toJsonString(users));
			out.println(JsonSerializer.toJsonString(user));

			out.println(JsonSerializer.toXmlString(users));
			out.println(JsonSerializer.toXmlString(user));

			// ================================
			// Delete
			// ================================
			user.id = 7;
			user.delete();
			// ================================
			// Update
			// ================================
			user.id = 6;
			user.nif = "Nif1";
			user.nombre = "Rafa";
			user.descripcion = "Hola caracola";
			user.fechaDeAlta = "20201125 21:20:18";
			user.save();
			// ================================
			// Insert
			// ================================
			// user.id = 0;
			// user.nombre = "Rafa nuevo";
			//user.save();


		}
		  
		out.close();		
	}

}