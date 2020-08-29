package co.com.udem.agenciainmobiliaria.rest.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.agenciainmobiliaria.dto.PropiedadDTO;
import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.entities.Propiedad;
import co.com.udem.agenciainmobiliaria.entities.Usuario;
import co.com.udem.agenciainmobiliaria.exception.ResourceNotFoundException;
import co.com.udem.agenciainmobiliaria.repositories.PropiedadRepository;
import co.com.udem.agenciainmobiliaria.repositories.UsuarioRepository;
import co.com.udem.agenciainmobiliaria.util.Constantes;
import co.com.udem.agenciainmobiliaria.util.ConvertPropiedad;
import co.com.udem.agenciainmobiliaria.util.ConvertUsuario;


@RestController
public class PropiedadRestController {

	@Autowired
	private PropiedadRepository propiedadRepository;

	@Autowired
	private ConvertPropiedad convertPropiedad;

	@Autowired
	private ConvertUsuario convertUsuario;

	@Autowired
	private UsuarioRepository usuarioRepository;

	//este procedimiento dejo de funcionar cuando cambiamos el OneToOne a ManyToOne 
	@PostMapping("/propiedad/adicionarPropiedad")
	public Map<String, String> adicionarPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
		Map<String, String> response = new HashMap<>();
		try {
			Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
			propiedadRepository.save(propiedad);
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
			return response;
		} catch (ParseException e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
			return response;
		}

	}

	@PostMapping("/usuarios/{username}/propiedad/adicionarPropiedad")
	public Map<String, String> adicionarPropiedadv2(@PathVariable(value = "username") String username,
			@RequestBody PropiedadDTO propiedadDTO) throws ResourceNotFoundException {

		Map<String, String> response = new HashMap<>();

		try {
			Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
			usuarioRepository.findByNumeroIdentif(Integer.parseInt(username)).map(usuario -> {
				propiedad.setUsuario(usuario);
				return propiedad;
			}).orElseThrow(() -> new ResourceNotFoundException("Error con usuario"));
			propiedadRepository.save(propiedad);
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
			return response;
		} catch (ParseException e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
			return response;
		}

	}

	@GetMapping("/propiedades")
	public List<PropiedadDTO> obtenerPropiedades() throws ResourceNotFoundException {
		Iterable<Propiedad> iPropiedad = propiedadRepository.findAll();
		List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
		List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<PropiedadDTO>();
		iPropiedad.iterator().forEachRemaining(listaPropiedades::add);
		for (int i = 0; i < listaPropiedades.size(); i++) {
			UsuarioDTO usuarioDTO2 = null;
			try {
				PropiedadDTO propiedadDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
				usuarioDTO2 = usuarioRepository.findById(listaPropiedades.get(i).getUsuario().getId()).map(usuario -> {
					UsuarioDTO usuarioDTO = null;
					try {
						usuarioDTO = convertUsuario.convertToDTO(usuario);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return usuarioDTO;
				}).orElseThrow(() -> new ResourceNotFoundException("Error con usuario"));

				propiedadDTO.setUsuarioDTO(usuarioDTO2);
				listaPropiedadesDTO.add(propiedadDTO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return listaPropiedadesDTO;
	}
	
	@GetMapping("/propiedades/usuario/{username}")
	public List<PropiedadDTO> obtenerPropiedadesUsuario(@PathVariable String username) throws ResourceNotFoundException {
		Optional<Usuario> usuarioLogin = usuarioRepository.findByNumeroIdentif(Integer.parseInt(username));
		Iterable<Propiedad> iPropiedad = propiedadRepository.obtenerPropiedadesUsuario(usuarioLogin.get().getId());
		List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
		List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<PropiedadDTO>();
		iPropiedad.iterator().forEachRemaining(listaPropiedades::add);
		for (int i = 0; i < listaPropiedades.size(); i++) {
			UsuarioDTO usuarioDTO2 = null;
			try {
				PropiedadDTO propiedadDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
				usuarioDTO2 = usuarioRepository.findById(listaPropiedades.get(i).getUsuario().getId()).map(usuario -> {
					UsuarioDTO usuarioDTO = null;
					try {
						usuarioDTO = convertUsuario.convertToDTO(usuario);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return usuarioDTO;
				}).orElseThrow(() -> new ResourceNotFoundException("Error con usuario"));

				propiedadDTO.setUsuarioDTO(usuarioDTO2);
				listaPropiedadesDTO.add(propiedadDTO);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return listaPropiedadesDTO;
	}

	@GetMapping("/propiedades2")
	public List<Propiedad> obtenerPropiedades2() {
		Iterable<Propiedad> iPropiedad = propiedadRepository.findAll();
		List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
		iPropiedad.iterator().forEachRemaining(listaPropiedades::add);
		return listaPropiedades;
	}
	
	@GetMapping("/propiedad/{id}")
	public Propiedad buscarPropiedad(@PathVariable Long id) {
		return propiedadRepository.findById(id).get();
	}
	
	@GetMapping("/propiedad/filtro")
	public List<PropiedadDTO> filtrarPropiedades2(@RequestBody PropiedadDTO propiedadDTO) throws ResourceNotFoundException, ParseException {

			Propiedad propiedad = convertPropiedad.convertToEntity(propiedadDTO);
			Iterable<Propiedad> iPropiedad = propiedadRepository.obtenerPropiedades(propiedad.getValor(), propiedad.getNumHabitaciones(), propiedad.getArea());
			
			List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
			List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<PropiedadDTO>();
			iPropiedad.iterator().forEachRemaining(listaPropiedades::add);
			for (int i = 0; i < listaPropiedades.size(); i++) {
				try {
					PropiedadDTO propiedadFiltroDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
					listaPropiedadesDTO.add(propiedadFiltroDTO);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			return listaPropiedadesDTO;
		

	}
	
	@GetMapping("/propiedades/filtro")
	public List<PropiedadDTO> filtrarPropiedades(@RequestParam Map<String, String> customQuery) {
		
			Iterable<Propiedad> iPropiedad = propiedadRepository.obtenerPropiedadesFiltro(Long.parseLong(customQuery.get("area")),
					Integer.parseInt(customQuery.get("hab")),
					Long.parseLong(customQuery.get("valor")));
			
			List<Propiedad> listaPropiedades = new ArrayList<Propiedad>();
			List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<PropiedadDTO>();
			iPropiedad.iterator().forEachRemaining(listaPropiedades::add);
			for (int i = 0; i < listaPropiedades.size(); i++) {
				try {
					PropiedadDTO propiedadFiltroDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
					listaPropiedadesDTO.add(propiedadFiltroDTO);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			return listaPropiedadesDTO;
		

	}

}
