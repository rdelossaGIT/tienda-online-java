package tiendaonline.servlets.sesiones;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiendaonline.clases.Categoria;
import tiendaonline.clases.Fabricante;
import tiendaonline.clases.Producto;
import tiendaonline.clases.Usuario;
import tiendaonline.enumerados.MisAtributos;
import tiendaonline.metodos.MisMetodos;

/**
 * @author Rafael de los Santos Guirado
 * 
 */
public class ServletLogin extends HttpServlet {

	private static final long serialVersionUID = 279769906444387986L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nick = request.getParameter("nombre");
		String pass = request.getParameter("pass");

		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request
				.getSession().getServletContext().getAttribute("emf");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();

		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = (List<Usuario>) entityManager.createQuery(
				"select usuario from Usuario usuario").getResultList();

		boolean login = false;

		for (Usuario usuario : usuarios) {
			if (usuario.getNick().equals(nick)
					&& usuario.getPass().equals(pass)) {
				login = true;
			}
		}
		if (login) {
			boolean admin = false;
			if (nick.equals("admin") && pass.equals("admin")) {
				admin = true;
			}

			MisMetodos.introducirUsuarioSesion(request, nick, pass, admin);

			entityManager.close();

			response.sendRedirect("Index");
		} else {

			int productosPorPagina = 6;
			int numPaginas = 1;
			int paginaActual = 1;
			try {
				paginaActual = Integer.parseInt(request.getParameter("pag"));
			} catch (NumberFormatException e) {

			}
			int start = productosPorPagina * (paginaActual - 1);

			// No ha podido loguearse, usuario o contraseľa incorrectos
			request.setAttribute(MisAtributos.error.toString(), true);
			List<Categoria> categorias = MisMetodos.obtenerCategorias(request);
			List<Fabricante> fabricantes = MisMetodos
					.obtenerFabricantes(request);

			List<Producto> productos = MisMetodos.paginacion(request, start,
					productosPorPagina);

			Collections.sort(productos);

			MisMetodos.asignarRequest(request, categorias, fabricantes);
			request.setAttribute(MisAtributos.productos.toString(), productos);
			request.setAttribute(MisAtributos.paginaSiguiente.toString(),
					paginaActual + 1);
			request.setAttribute(MisAtributos.paginaAnterior.toString(),
					paginaActual - 1);
			request.setAttribute(MisAtributos.paginaActual.toString(),
					paginaActual);
			request.setAttribute(MisAtributos.numPaginas.toString(), numPaginas);

			request.getRequestDispatcher("index.jsp")
					.forward(request, response);
		}

	}

}
