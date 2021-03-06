package co.com.udem.agenciainmobiliaria.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombres;
	private String apellidos;
	private String tipoIdentif;
	private int numeroIdentif;
	private String direccion;
	private String telefono;
	private String email;
	private String password;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Propiedad> propiedades = new ArrayList<>();

	
	public Usuario(Long id, String nombres, String apellidos, String tipoIdentif, int numeroIdentif, String direccion,
			String telefono, String email, String password, List<Propiedad> propiedades) {
		super();
		this.id = id;
		this.nombres = nombres;
		this.apellidos = apellidos;
		this.tipoIdentif = tipoIdentif;
		this.numeroIdentif = numeroIdentif;
		this.direccion = direccion;
		this.telefono = telefono;
		this.email = email;
		this.password = password;
		this.propiedades = propiedades;
	}

	public Usuario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getTipoIdentif() {
		return tipoIdentif;
	}

	public void setTipoIdentif(String tipoIdentif) {
		this.tipoIdentif = tipoIdentif;
	}

	public int getNumeroIdentif() {
		return numeroIdentif;
	}

	public void setNumeroIdentif(int numeroIdentif) {
		this.numeroIdentif = numeroIdentif;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
