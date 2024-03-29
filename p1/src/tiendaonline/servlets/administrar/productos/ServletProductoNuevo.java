package tiendaonline.servlets.administrar.productos;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tiendaonline.clases.Categoria;
import tiendaonline.clases.Producto;
import tiendaonline.clases.Puntuacion;
import tiendaonline.clases.Usuario;
import tiendaonline.enumerados.MisAtributos;
import tiendaonline.metodos.MisMetodos;

/**
 * @author Rafael de los Santos Guirado
 *
 */
public class ServletProductoNuevo extends HttpServlet {
	// public static String url;

	private String dirUploadFiles; // directorio donde se guardara los archivos

	private static final long serialVersionUID = 865732587598002069L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		double precio = Double.parseDouble(request.getParameter("precio"));
		int cantidad = Integer.parseInt(request.getParameter("cantidad"));
		String urlImagen = request.getParameter("url");
		String tituloCategoria = request.getParameter("categoria");
		Long idFabricante = Long.parseLong(request
				.getParameter("fabricanteProducto"));
		String descripcion = request.getParameter("descripcion");

		Producto producto = new Producto();
		Categoria categoria = new Categoria();

		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request
				.getSession().getServletContext().getAttribute("emf");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		categoria.setTitulo(tituloCategoria);
		producto.setCantidad(cantidad);
		producto.setFecha(new Date());
		producto.setNombre(nombre);
		producto.setPrecio(precio);
		producto.setUrlImagen(urlImagen);
		producto.setCategoriaString(tituloCategoria);
		producto.setIdFabricante(idFabricante);
		producto.setDescripcion(descripcion);

		transaction.begin();
		entityManager.persist(producto);
		transaction.commit();

		entityManager.close();

		introducirPuntuacion(request, nombre);

		response.sendRedirect("Administrar");
	}

	public void introducirPuntuacion(HttpServletRequest request, String nombre) {
		Long idProducto = MisMetodos.obtenerIdProducto(request, nombre);

		EntityManagerFactory entityManagerFactory = (EntityManagerFactory) request
				.getSession().getServletContext().getAttribute("emf");
		EntityManager entityManager = entityManagerFactory
				.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		// Metemos una puntuaci�n a 0 del administrador para que no haya
		// problemas a la hora de insertar otro usuario otra puntuaci�n

		Usuario usuario = (Usuario) request.getSession().getAttribute(
				MisAtributos.usuario.toString());
		Long idUsuario = usuario.getId();

		Puntuacion puntuacion = new Puntuacion();
		puntuacion.setIdProducto(idProducto);
		puntuacion.setIdUsuario(idUsuario);
		puntuacion.setPuntos(0);
		puntuacion.setFecha(new Date());

		transaction.begin();
		entityManager.persist(puntuacion);
		transaction.commit();

		entityManager.close();

	}

	public String getDirUploadFiles() {
		return dirUploadFiles;
	}

	public void setDirUploadFiles(String dirUploadFiles) {
		this.dirUploadFiles = dirUploadFiles;
	}

}
