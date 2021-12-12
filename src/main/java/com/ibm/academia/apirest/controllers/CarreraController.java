package com.ibm.academia.apirest.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.mapper.CarreraMapper;
import com.ibm.academia.apirest.models.dto.CarreraDTO;
import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.services.CarreraDAO;

@RestController
@RequestMapping("/carrera")
public class CarreraController 
{
	@Autowired
	private CarreraDAO carreraDao;
	/**
	 * Endpoint para crear un objeto carrera 
	 * @param carrera Objeto carrera con la inforamción a crear
	 * @return Retorna un objeto carrera httpstatus 201
	 * @author SACM - 11-12-2021 
	 */
	@PostMapping
	public ResponseEntity<?> guardarCarrera(@Valid @RequestBody Carrera carrera, BindingResult result)
	{
		Map<String, Object> validaciones = new HashMap<String, Object>();
		if(result.hasErrors())
		{
			List<String> listaErrores = result.getFieldErrors()
					.stream()
					.map(errores -> "Campo: '" + errores.getField() + "' " + errores.getDefaultMessage())
					.collect(Collectors.toList());
			validaciones.put("Lista Errores", listaErrores);
			return new ResponseEntity<Map<String, Object>>(validaciones, HttpStatus.BAD_REQUEST);
		}
		
		Carrera carreraGuardada = carreraDao.guardar(carrera);
		
		return new ResponseEntity<Carrera>(carreraGuardada, HttpStatus.CREATED);
	}
	/**
	 * Endpoint para listar los carreras
	 * @return lista de objetos carreras
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/lista/carreras")
	public ResponseEntity<?> buscarTodas()
	{
		List<Carrera> carreras = (List<Carrera>) carreraDao.buscarTodos();
		if(carreras.isEmpty())
			throw new BadRequestException("No existen carreras");
		
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
	/**
	 * Endpoint para buscar carrera por Id
	 * @return lista de objetos carreras 
	 * @return carreraId Parametro para buscar la carrera
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/id/{carreraId}")
	public ResponseEntity<?> buscarCarreraPorId(@PathVariable Integer carreraId)
	{
		Carrera carrera = carreraDao.buscarPorId(carreraId).orElse(null);
		if(carrera == null)
			throw new BadRequestException(String.format("La carrera con ID: %d no existe", carreraId));
		
		return new ResponseEntity<Carrera>(carrera, HttpStatus.OK);
	}
	/**
	 * Endpoint para listar las carreras con el nombre y apellido de profesor
	 * @return lista de objetos carreras 
	 * @return nombre Parametro para buscar las aulas
	 * @return apellido Parametro para buscar las aulas
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/carrera-profesor/{nombre}/{apellido}")
	public ResponseEntity<?> obtenerProfesorCarrera(@PathVariable String nombre,@PathVariable String apellido)
	{
		List<Carrera> carreras = (List<Carrera>) ((CarreraDAO)carreraDao).buscarCarrerasPorProfesorNombreYApellido(nombre, apellido);
		
		if(carreras.isEmpty())
			throw new NotFoundException("No existen carreras");
		return new ResponseEntity<List<Carrera>>(carreras, HttpStatus.OK);
	}
		
	/**
	 * Endoint para actualizar un objeto de tipo carrera
	 * @param carreraId Parámetro para buscar la carrera
	 * @param carrera Objeto con la información a modificar
	 * @return Retorna un objeto de tipo Carrera con la información actualizada
	 * @NotFoundException En caso de que falle actualizando el objeto carrera
	 * @author NDSC - 06/12/2021
	 */
	@PutMapping("/upd/carreraId/{carreraId}")
	public ResponseEntity<?> actualizarCarrera(@PathVariable Integer carreraId, @RequestBody Carrera carrera)
	{
		Optional<Carrera> oCarrera = carreraDao.buscarPorId(carreraId);
		
		if(!oCarrera.isPresent())
			throw new NotFoundException(String.format("La carrera con ID: %d no existe", carreraId));
		
		Carrera carreraActualizada = carreraDao.actualizar(oCarrera.get(), carrera); 
		
		return new ResponseEntity<Carrera>(carreraActualizada, HttpStatus.OK); 
	}
	
	/**
	 * Endoint para eliminar un objeto de tipo carrera
	 * @param carreraId Parámetro para buscar la carrera
	 * @return Retorna un mensaje de que fue eliminado con exito
	 * @NotFoundException En caso de que falle no encuentre el objeto carrera
	 * @author SACM - 10/12/2021
	 */
	@DeleteMapping("/carreraId/{carreraId}")
	public ResponseEntity<?> eliminarCarrera(@PathVariable Integer carreraId)
	{
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		Optional<Carrera> carrera = carreraDao.buscarPorId(carreraId);
		
		if(!carrera.isPresent())
			throw new NotFoundException(String.format("La carrera con ID: %d no existe", carreraId));
		
		carreraDao.eliminarPorId(carreraId);
		respuesta.put("OK", "Carrera ID: " + carreraId + " eliminada exitosamente");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.ACCEPTED);
	}
	
	/**
	 * Endpoint para consultar todas las carreras
	 * @return Retorna una lista de carreras en DTO
	 * @NotFoundException En caso de que no encuentre ningun elemento en la base de datos
	 * @author NDSC - 07-12-2021
	 */
	@GetMapping("/carreras/dto")
	public ResponseEntity<?> obtenerCarrerasDTO()
	{
		List<Carrera> carreras = (List<Carrera>) carreraDao.buscarTodos();
		
		if(carreras.isEmpty())
			throw new NotFoundException("No existen carreras en la base de datos.");
		
		List<CarreraDTO> listaCarreras = carreras
				.stream()
				.map(CarreraMapper::mapCarrera)
				.collect(Collectors.toList());
		return new ResponseEntity<List<CarreraDTO>>(listaCarreras, HttpStatus.OK);
	}
}