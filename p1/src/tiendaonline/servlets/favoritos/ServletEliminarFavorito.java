package tiendaonline.servlets.favoritos;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiendaonline.clases.Usuario;
import tiendaonline.enumerados.MisAtributos;

/**
 * @author Rafael de los Santos Guirado
 *
 */
public class ServletEliminarFavorito extends HttpServlet {

	private static final long serialVersionUID = -2358332216111523723L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Long idProducto = Long.parseLong(request.getParameter("id"));

		String nick = ((Usuario) request.getSession().getAttribute(
				MisAtributos.usuario.toString())).getNick();

		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request
				.getSession().getServletContext().getAttribute("emf");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		String jpql = "select usuario from Usuario usuario where usuario.nick=:n";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("n", nick);

		Usuario usuario = (Usuario) query.getSingleResult();

		Set<Long> prodFavoritos = usuario.getProdFavoritos();
		if (prodFavoritos == null) {
			prodFavoritos = new HashSet<Long>();
		}
		
		prodFavoritos.remove(idProducto);
		usuario.setProdFavoritos(prodFavoritos);

		// Actualizamos el usuario en la BD
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		entityManager.merge(usuario);
		transaction.commit();
		//Actualizamos el usuario en la sesi�n tambi�n
		request.getSession().setAttribute(MisAtributos.usuario.toString(), usuario);
		entityManager.close();

		response.sendRedirect("Detalles?id=" + idProducto);
	}

}
