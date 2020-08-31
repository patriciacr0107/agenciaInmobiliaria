package co.com.udem.agenciainmobiliaria.rest.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

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
import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenciainmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropiedadRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

	private AutenticationRequestDTO autenticationRequestDTO = new AutenticationRequestDTO();
	private UsuarioDTO usuarioDTO = new UsuarioDTO();
	

    @LocalServerPort
    private int port;
    
    private String token;
    
    
    private String getRootUrl() {
        return "http://localhost:" + port;
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
	public void adicionarPropiedadTest() {

    	System.out.println("Adicionar propiedad");
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
    	
    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	propiedadDTO.setArea((long) 60);
    	propiedadDTO.setNumBanos(2);
    	propiedadDTO.setNumHabitaciones(3);
    	propiedadDTO.setTipoContrato("venta");
    	propiedadDTO.setValor((long) 300000000);
    	
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	System.out.println("El token es: "+this.token); 
    	headers.set("Authorization", "Bearer "+this.token);
    	
    	HttpEntity<PropiedadDTO> entity = new HttpEntity<>(propiedadDTO, headers);
		Map<String, String> vars = new HashMap<>();
		vars.put("username", autenticationRequestDTO.getUsername());
		
    	ResponseEntity<PropiedadDTO> postResponse = restTemplate.exchange(getRootUrl() + "/usuarios/{username}/propiedad/adicionarPropiedad", HttpMethod.POST, entity, PropiedadDTO.class, vars);
        
    	System.out.println(postResponse.getBody());
    	assertEquals(200, postResponse.getStatusCode().value());

	}
    
    @Test
	public void consultarPropiedadTest() {
    	
    	System.out.println("consultar propiedades");
    	usuarioDTO.setNombres("Fulano");
    	usuarioDTO.setApellidos("Perez");
    	usuarioDTO.setNumeroIdentif(999999);
    	usuarioDTO.setDireccion("Cll 20");
    	usuarioDTO.setEmail("f@gmail.com");
    	usuarioDTO.setTelefono("123456789");
    	usuarioDTO.setTipoIdentif("CC");
    	usuarioDTO.setPassword("123");
    	
    	adicionarUsuario(usuarioDTO);
    	
    	autenticationRequestDTO.setUsername("999999");
    	autenticationRequestDTO.setPassword("123");
    	    	
    	authotization(autenticationRequestDTO);

    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	propiedadDTO.setArea((long) 100);
    	propiedadDTO.setNumBanos(2);
    	propiedadDTO.setNumHabitaciones(3);
    	propiedadDTO.setTipoContrato("venta");
    	propiedadDTO.setValor((long) 300000000);
    	
    	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	System.out.println("El token es: "+this.token); 
    	headers.set("Authorization", "Bearer "+this.token);
    	
    	HttpEntity<PropiedadDTO> entity = new HttpEntity<>(propiedadDTO, headers);
		Map<String, String> vars = new HashMap<>();
		vars.put("username", autenticationRequestDTO.getUsername());
		
    	ResponseEntity<PropiedadDTO> postResponse = restTemplate.exchange(getRootUrl() + "/usuarios/{username}/propiedad/adicionarPropiedad", HttpMethod.POST, entity, PropiedadDTO.class, vars);
    	
    	System.out.println(postResponse.getBody());
    	HttpHeaders headers2 = new HttpHeaders();
    	headers2.setContentType(MediaType.APPLICATION_JSON);
    	System.out.println("El token es: "+this.token); 
    	headers2.set("Authorization", "Bearer "+this.token);
    	
    	HttpEntity<String> entity2 = new HttpEntity<String>(headers);
    	Map<String, String> vars2 = new HashMap<>();
		vars2.put("username", autenticationRequestDTO.getUsername());
    	
		ResponseEntity<String> postResponse2 = restTemplate.exchange(
    			getRootUrl() + "/propiedades/usuario/{username}", 
    			HttpMethod.GET, entity2, String.class,vars2);
    	
    	
    	System.out.println(postResponse2.getBody());
    	System.out.println("Respuesta: "+postResponse2.getStatusCode().value());
    	assertEquals(200, postResponse2.getStatusCode().value());


	}

}
