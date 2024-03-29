package tiendaonline.servlets.sesiones;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiendaonline.ServletIndex;
import tiendaonline.clases.Categoria;
import tiendaonline.clases.Fabricante;
import tiendaonline.clases.Producto;
import tiendaonline.clases.Usuario;
import tiendaonline.enumerados.MisAtributos;
import tiendaonline.listeners.ContextoListener;
import tiendaonline.metodos.MisMetodos;

/**
 * @author Rafael de los Santos Guirado
 *
 */
public class ServletRegistro extends HttpServlet {

	private static final long serialVersionUID = 4872740599949017634L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String usuarioString = request.getParameter("nombre");
		String pass = request.getParameter("pass");

		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request
				.getSession().getServletContext().getAttribute("emf");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		// Comprobamos si existe un usuario con ese mismo nick, si es as� nos
		// informar� de que ya existe y se cancelar� el registro
		boolean existe = MisMetodos.comprobarExisteUsuario(request,
				usuarioString);
		if (!existe) {
			Usuario usuario = new Usuario();
			boolean admin = false;

			if (pass.equals("admin") && (usuarioString.equals("admin"))) {
				admin = true;
			}
			usuario.setPass(pass);
			usuario.setNick(usuarioString);
			usuario.setAdmin(admin);
			// usuario.setFacturas(new ArrayList<Factura>());

			transaction.begin();
			entityManager.persist(usuario);
			transaction.commit();
			
//				List<Categoria> categorias = MisMetodos.obtenerCategorias(request);
//				List<Fabricante> fabricantes = MisMetodos
//						.obtenerFabricantes(request);
//				
//				int productosPorPagina = 6;
//				int paginaActual = 1;
//
//				int start = productosPorPagina * (paginaActual - 1);
//
//				List<Producto> productos = MisMetodos.paginacion(request, start,
//						productosPorPagina);
//
//				Collections.sort(productos);
//				
//				MisMetodos.asignarRequest(request, categorias, fabricantes);
//				
//				ServletIndex.categoria = null;
//				ServletIndex.fabricante = null;
//				ServletIndex.busquedaPer = null;
//				productos = MisMetodos.paginacion(request, start,
//						productosPorPagina);
//				request.setAttribute(MisAtributos.fav.toString(), null);
//				request.setAttribute(MisAtributos.producto.toString(),
//						ContextoListener.productosCabecera.get(0));
//				request.setAttribute(MisAtributos.paginaSiguiente.toString(),
//						paginaActual + 1);
//				request.setAttribute(MisAtributos.paginaAnterior.toString(),
//						paginaActual - 1);
//				request.setAttribute(MisAtributos.paginaActual.toString(), paginaActual);
//				request.setAttribute(MisAtributos.productos.toString(), productos);
//				request.setAttribute(MisAtributos.registrado.toString(), true);
//				
//				request.getRequestDispatcher("index.jsp")
//						.forward(request, response);
			response.sendRedirect("Index?reg=t");
			
		} else {
			request.setAttribute(MisAtributos.error.toString(), true);
			List<Categoria> categorias = MisMetodos.obtenerCategorias(request);
			List<Fabricante> fabricantes = MisMetodos
					.obtenerFabricantes(request);

			MisMetodos.asignarRequest(request, categorias, fabricantes);
			request.getRequestDispatcher("registrarse.jsp").forward(request,
					response);
		}
	}

}
