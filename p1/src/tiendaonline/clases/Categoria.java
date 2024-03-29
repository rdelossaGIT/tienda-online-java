package tiendaonline.clases;

import java.io.Serializable;

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
public class Categoria implements Serializable{

	
	private static final long serialVersionUID = 4298918625832569326L;
	private Key id;
	private String titulo;

	public Categoria(String titulo,
			Fabricante fabricante) {
		super();
		this.titulo = titulo;
	}

	public Categoria() {
	}

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
