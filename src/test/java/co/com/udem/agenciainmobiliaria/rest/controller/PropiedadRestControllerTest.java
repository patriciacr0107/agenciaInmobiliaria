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
import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AgenciainmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropiedadRestControllerTest {

	@Autowired
    private TestRestTemplate restTemplate;

 

    @LocalServerPort
    private int port;
    
    
    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    
	
    @Test
	public void adicionarPropiedadTest() {

    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	propiedadDTO.setArea((long) 60);
    	propiedadDTO.setNumBanos(2);
    	propiedadDTO.setNumHabitaciones(3);
    	propiedadDTO.setTipoContrato("venta");
    	propiedadDTO.setValor((long) 300000000);
    	
    	ResponseEntity<PropiedadDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "/usuarios/1/propiedad/adicionarPropiedad", propiedadDTO, PropiedadDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

	}
    
    @Test
	public void consultarPropiedadTest() {

    	PropiedadDTO propiedadDTO = new PropiedadDTO();
    	
    	ResponseEntity<PropiedadDTO> postResponse = restTemplate.postForEntity(getRootUrl() + "propiedades/usuario/123", propiedadDTO, PropiedadDTO.class);
        assertNotNull(postResponse);
        assertNotNull(postResponse.getBody());

	}

}
