package co.com.udem.agenciainmobiliaria.rest.controller;

import static org.junit.Assert.assertNotNull;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;


import co.com.udem.agenciainmobiliaria.AgenciainmobiliariaApplication;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenciainmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

 

    @LocalServerPort
    private int port;
    
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    
    @Test
	public void adicionarUsuarioTest() {

    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	usuarioDTO.setNombres("Pepito");
    	usuarioDTO.setApellidos("Perez");
    	usuarioDTO.setDireccion("Cll 46");
    	usuarioDTO.setEmail("pepito@gmail.com");
    	usuarioDTO.setNumeroIdentif(123);
    	usuarioDTO.setPassword("123");
    	usuarioDTO.setTelefono("12345600");
    	usuarioDTO.setTipoIdentif("CC");
    	
    	ResponseEntity<UsuarioDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "usuario/adicionarUsuario", usuarioDTO, UsuarioDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

	}
    
    
    @Test
	public void autenticar() {

    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	usuarioDTO.setNumeroIdentif(123);
    	usuarioDTO.setPassword("123");
    	
    	ResponseEntity<UsuarioDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin", usuarioDTO, UsuarioDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

	}
}
