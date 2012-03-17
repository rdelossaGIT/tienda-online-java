package tiendaonline.filtros;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tiendaonline.clases.Usuario;
import tiendaonline.enumerados.MisAtributos;

public class FiltroAdmin implements Filter {

	private FilterConfig config;
	private String urlLogin;

	@Override
	public void destroy() {
		config = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// Extraer Sesión
		HttpSession session = ((HttpServletRequest) request).getSession();

		if (session.getAttribute(MisAtributos.usuario.toString()) == null) {
			// NO hay una session con ususario
			((HttpServletResponse) response).sendRedirect(urlLogin);

		} else {
			Usuario usuario = (Usuario) session
					.getAttribute(MisAtributos.usuario.toString());
			if (!usuario.getNick().equals("admin")) {
				// el usuario no es administrador
				((HttpServletResponse) response).sendRedirect(urlLogin);
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;

		// Tambien se pueden cargar los parametros
		// Configura url desconexión
		this.urlLogin = config.getInitParameter("urlLogin");
		System.out.println("urlLogin: " + urlLogin);
		if (urlLogin == null || urlLogin.trim().length() == 0) {
			// Error al cargar la url de login
			throw new ServletException("No se ha configurado URL de login");
		}
	}
}