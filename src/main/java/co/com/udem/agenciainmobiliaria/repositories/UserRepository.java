package co.com.udem.agenciainmobiliaria.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.User;


public interface UserRepository extends CrudRepository<User, Long> {
	
	Optional<User> findByUsername(String username);

}
