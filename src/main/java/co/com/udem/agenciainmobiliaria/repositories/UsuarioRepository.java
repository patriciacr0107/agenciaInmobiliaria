package co.com.udem.agenciainmobiliaria.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
	
	Optional<Usuario> findByNumeroIdentif(int numeroIdentif);
	
	

}
