package co.com.udem.agenciainmobiliaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.agenciainmobiliaria.entities.Propiedad;


public interface PropiedadRepository extends CrudRepository<Propiedad, Long> {
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor = ?1 and u.numHabitaciones= ?2 and u.area=?3")
    public List<Propiedad> obtenerPropiedades(Long precio, int numhabitaciones, Long area);
	
	@Query("SELECT u FROM Propiedad u WHERE u.valor <= ?3 and u.numHabitaciones<= ?2 and u.area<=?1")
    public List<Propiedad> obtenerPropiedadesFiltro(Long areaMax, int numhabitacionesMax, Long precioMax);
	
	@Query(value="SELECT * FROM Propiedad WHERE propietario = ?1", nativeQuery=true)
    public List<Propiedad> obtenerPropiedadesUsuario(Long propietario);

}
