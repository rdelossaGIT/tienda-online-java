package tiendaonline.clases;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

/**
 * @author Rafael de los Santos Guirado
 *
 */
@Entity
public class Producto implements Serializable, Comparable<Producto> {

	private static final long serialVersionUID = -5945135721837447454L;
	private Key id;
	private double precio;
	private Date fecha;
	private String nombre;
	private int cantidad;
	private String urlImagen;
	private String categoriaString;
	private Long idFabricante;
	private String descripcion;

	public Producto(Key id, double precio, Date fecha, String nombre,
			int cantidad, String urlImagen,
			String categoriaString, Long idFabricante, String descripcion) {
		super();
		this.id = id;
		this.precio = precio;
		this.fecha = fecha;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.urlImagen = urlImagen;
		this.categoriaString = categoriaString;
		this.idFabricante = idFabricante;
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCategoriaString() {
		return categoriaString;
	}

	public void setCategoriaString(String categoriaString) {
		this.categoriaString = categoriaString;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public Producto() {
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "Producto [id=" + id + ", precio=" + precio + ", fecha=" + fecha
				+ ", nombre=" + nombre + ", cantidad=" + cantidad
				+ ", urlImagen=" + urlImagen
				+ ", categoriaString=" + categoriaString + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Producto other = (Producto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getIdFabricante() {
		return idFabricante;
	}

	public void setIdFabricante(Long idFabricante) {
		this.idFabricante = idFabricante;
	}

	@Override
	public int compareTo(Producto p) {

		if (this.fecha.before(p.getFecha())) {
			return 1;
		} else if (this.fecha.after(p.getFecha())) {
			return -1;
		} else {
			return 0;
		}

	}

}
