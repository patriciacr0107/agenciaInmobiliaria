package co.com.udem.agenciainmobiliaria.util;

import java.text.ParseException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.agenciainmobiliaria.dto.UsuarioDTO;
import co.com.udem.agenciainmobiliaria.entities.Usuario;

public class ConvertUsuario {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario convertToEntity(UsuarioDTO usuarioDTO) throws ParseException{
		return modelMapper.map(usuarioDTO, Usuario.class);
	}

	public UsuarioDTO convertToDTO(Usuario usuario) throws ParseException{
		return modelMapper.map(usuario, UsuarioDTO.class);
	}
	
}
