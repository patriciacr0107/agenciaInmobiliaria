package co.com.udem.agenciainmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.util.ConvertPropiedad;
import co.com.udem.agenciainmobiliaria.util.ConvertUsuario;


@SpringBootApplication
public class AgenciainmobiliariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgenciainmobiliariaApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ConvertUsuario convertUsuario() {
		return new ConvertUsuario();
	}
	
	@Bean
	public ConvertPropiedad convertPropiedad() {
		return new ConvertPropiedad();
	}
	
	@Bean
	public UsuarioDTO usuarioDTO() {
		return new UsuarioDTO();
	}
	
	@Bean
    PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
