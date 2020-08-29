package co.com.udem.agenciainmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.entities.User;
import co.com.udem.agenciainmobiliaria.entities.Usuario;
import co.com.udem.agenciainmobiliaria.repositories.UserRepository;
import co.com.udem.agenciainmobiliaria.repositories.UsuarioRepository;
import co.com.udem.agenciainmobiliaria.util.Constantes;
import co.com.udem.agenciainmobiliaria.util.ConvertUsuario;


@RestController
public class UsuarioRestController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ConvertUsuario convertUsuario;
	
	@Autowired
    private UserRepository userRepository;
   
    @Autowired
    PasswordEncoder passwordEncoder;
	
	@PostMapping("/usuario/adicionarUsuario")
    public Map<String, String> adicionarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Map<String, String> response = new HashMap<>();
        try {
        	Usuario usuario = convertUsuario.convertToEntity(usuarioDTO);
        	usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        	usuarioRepository.save(usuario);
            userRepository.save(User.builder()
                    .username(Long.toString(usuario.getNumeroIdentif()))
                    .password(usuario.getPassword())
                    .roles(Arrays.asList( "ROLE_USER"))
                    .build());
        	response.put(Constantes.CODIGO_HTTP, "200");
            response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
            return response;
        } catch (ParseException e) {
            response.put(Constantes.CODIGO_HTTP, "500");
            response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
            return response;
        }
       
    }
	
	@GetMapping("/usuarios")
    public List<UsuarioDTO> obtenerUsuarios(){
        Iterable<Usuario> iUsuario = usuarioRepository.findAll();
        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        List<UsuarioDTO> listaUsuariosDTO = new ArrayList<UsuarioDTO>();
        iUsuario.iterator().forEachRemaining(listaUsuarios::add);
        for(int i = 0; i < listaUsuarios.size(); i++) {
            try {
                UsuarioDTO usuarioDTO = convertUsuario.convertToDTO(listaUsuarios.get(i));
            	listaUsuariosDTO.add(usuarioDTO);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
       
        return listaUsuariosDTO;
    }
	
	@GetMapping("/usuarios/{id}")
	public Usuario buscarUsuario(@PathVariable Long id) {
		return usuarioRepository.findById(id).get();
	}
	
	@GetMapping("/usuariosv2/{numeroIdentif}")
	public Usuario buscarUsuario(@PathVariable int numeroIdentif) {
		return usuarioRepository.findByNumeroIdentif(numeroIdentif).get();
	}
	
}
