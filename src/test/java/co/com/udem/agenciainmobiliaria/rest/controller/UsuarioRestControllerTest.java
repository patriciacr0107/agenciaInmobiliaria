package co.com.udem.agenciainmobiliaria.rest.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

import co.com.udem.agenciainmobiliaria.AgenciainmobiliariaApplication;
import co.com.udem.agenciainmobiliaria.dto.AutenticationRequestDTO;
import co.com.udem.agenciainmobiliaria.dto.AutenticationResponseDTO;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenciainmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

	private AutenticationRequestDTO autenticationRequestDTO = new AutenticationRequestDTO();
	private UsuarioDTO usuarioDTO = new UsuarioDTO();

    @LocalServerPort
    private int port;
    
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    private String token;
    
    @Test
	public void adicionarUsuarioTest() {

    	UsuarioDTO usuarioDTO = new UsuarioDTO();
    	usuarioDTO.setNombres("Keana");
    	usuarioDTO.setApellidos("Vasco");
    	usuarioDTO.setDireccion("Cll 46");
    	usuarioDTO.setEmail("keana@gmail.com");
    	usuarioDTO.setNumeroIdentif(333333);
    	usuarioDTO.setPassword("123");
    	usuarioDTO.setTelefono("12345600");
    	usuarioDTO.setTipoIdentif("CC");
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	
    	HttpEntity<UsuarioDTO> entity = new HttpEntity<UsuarioDTO>(usuarioDTO, headers);
    	
    	ResponseEntity<UsuarioDTO> postResponse = restTemplate.exchange(getRootUrl() + "usuario/adicionarUsuario", HttpMethod.POST, entity, UsuarioDTO.class);
        
    	assertEquals(200, postResponse.getStatusCode().value());

	}
    
    public void authotization(AutenticationRequestDTO autenticationRequestDTO) {
    	
    	ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl()+"/auth/signin", autenticationRequestDTO, String.class);
    	Gson g = new Gson();
    	AutenticationResponseDTO autenticationResponseDTO = g.fromJson (postResponse.getBody(),AutenticationResponseDTO.class);
    	this.token = autenticationResponseDTO.getToken();
    }
    
    private void adicionarUsuario(UsuarioDTO usuarioDTO) {
    	ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl()+"/usuario/adicionarUsuario", usuarioDTO, String.class);
    	postResponse.getBody();
    	
    	System.out.println(postResponse.getBody());
    	
    }
    
    @Test
    public void obtenerUsuariosTest(){
    	
    	usuarioDTO.setNombres("Pepito");
    	usuarioDTO.setApellidos("Perez");
    	usuarioDTO.setNumeroIdentif(123456);
    	usuarioDTO.setDireccion("Cll 11");
    	usuarioDTO.setEmail("p@gmail.com");
    	usuarioDTO.setTelefono("123456789");
    	usuarioDTO.setTipoIdentif("CC");
    	usuarioDTO.setPassword("123");
    	
    	adicionarUsuario(usuarioDTO);
    	
    	autenticationRequestDTO.setUsername("123456");
    	autenticationRequestDTO.setPassword("123");
    	    	
    	authotization(autenticationRequestDTO);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	System.out.println("El token es: "+this.token); 
    	headers.set("Authorization", "Bearer "+this.token);
    	
    	HttpEntity<String> entity = new HttpEntity<String>(headers);
    	
    	ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/usuarios", HttpMethod.GET, entity, String.class);
        
    	System.out.println(postResponse.getBody());
    	assertEquals(200, postResponse.getStatusCode().value());
    	
    	
        
    }
    
   
    
}
