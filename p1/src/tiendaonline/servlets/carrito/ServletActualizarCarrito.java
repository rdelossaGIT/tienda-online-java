package tiendaonline.servlets.carrito;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiendaonline.clases.Producto;
import tiendaonline.enumerados.MisAtributos;

public class ServletActualizarCarrito extends HttpServlet{

	private static final long serialVersionUID = 3988959730218533300L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		List<Producto> carrito = (List<Producto>) request.getSession().getAttribute(MisAtributos.carrito.toString());
		
		Iterator<Producto> it = carrito.iterator();
		
		int contador = 1;
		while (it.hasNext()){
			Producto producto = it.next();
			String cantidad = request.getParameter("cantidad" + contador);
			producto.setCantidad(Integer.parseInt(cantidad));
			contador++;
		}
		
		response.sendRedirect("Carrito");
	}
	
}
