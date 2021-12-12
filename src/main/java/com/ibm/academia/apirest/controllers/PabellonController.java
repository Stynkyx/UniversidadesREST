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
import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.services.PabellonDAO;

@RestController
@RequestMapping("/pabellon")
public class PabellonController {
	
	@Autowired
	private PabellonDAO pabellonDao;
	/**
	 * Endpoint para crear un objeto pabellon 
	 * @param pabellon Objeto pabellon con la inforamción a crear
	 * @return Retorna un objeto pabellon httpstatus 201
	 * @author SACM - 11-12-2021 
	 */
	@PostMapping
	public ResponseEntity<?> guardarPabellon(@Valid @RequestBody Pabellon pabellon, BindingResult result)
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
		
		Pabellon pabellonGuardado = pabellonDao.guardar(pabellon);
		
		return new ResponseEntity<Pabellon>(pabellonGuardado, HttpStatus.CREATED);
	}
	/**
	 * Endpoint para buscar pabellon por localidad
	 * @param nombre Parametro para buscar pabellones
	 * @return lista de objetos pabellones 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/pabellon-localidad/{localidad}")
	public ResponseEntity<?> obtenerPabellonLocalidad(@PathVariable String localidad)
	{
		List<Pabellon> pabellones = (List<Pabellon>) ((PabellonDAO)pabellonDao).findPabellonByLocalidad(localidad);
		
		if(pabellones.isEmpty())
			throw new NotFoundException("No existen pabellones");
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
	}
	/**
	 * Endpoint para buscar pabellon por nombre
	 * @param nombre Parametro para buscar pabellones
	 * @return lista de objetos pabellones 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/pabellon-nombre/{nombre}")
	public ResponseEntity<?> obtenerPabellonNombre(@PathVariable String nombre)
	{
		List<Pabellon> pabellones = (List<Pabellon>) ((PabellonDAO)pabellonDao).findPabellonByNombre(nombre);
		
		if(pabellones.isEmpty())
			throw new NotFoundException("No existen pabellones");
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
	}
	/**
	 * Endpoint para listar los pabellones
	 * @return lista de objetos pabellones
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/lista/pabellones")
	public ResponseEntity<?> buscarTodas()
	{
		List<Pabellon> pabellones = (List<Pabellon>) pabellonDao.buscarTodos();
		if(pabellones.isEmpty())
			throw new BadRequestException("No existen carreras");
		
		return new ResponseEntity<List<Pabellon>>(pabellones, HttpStatus.OK);
	}
	/**
	 * Endoint para actualizar un objeto de tipo pabellon
	 * @param pabellonId Parámetro para buscar la pabellon
	 * @param pabellon Objeto con la información a modificar
	 * @return Retorna un objeto de tipo pabellon con la información actualizada
	 * @NotFoundException En caso de que falle actualizando el objeto pabellon
	 * @author SACM - 10/12/2021
	 */
	@PutMapping("/upd/pabellonId/{pabellonId}")
	public ResponseEntity<?> actualizarCarrera(@PathVariable Integer pabellonId, @RequestBody Pabellon pabellon)
	{
		Optional<Pabellon> oPabellon = pabellonDao.buscarPorId(pabellonId);
		
		if(!oPabellon.isPresent())
			throw new NotFoundException(String.format("La carrera con ID: %d no existe", pabellonId));
		
		Pabellon pabellonActualizado = pabellonDao.actualizar(oPabellon.get(), pabellon); 
		
		return new ResponseEntity<Pabellon>(pabellonActualizado, HttpStatus.OK); 
	}
	
	
	/**
	 * Endoint para eliminar un objeto de tipo pabellon
	 * @param pabellonId Parámetro para buscar la pabellon
	 * @return Retorna un mensaje de que fue eliminado con exito
	 * @NotFoundException En caso de que falle no encuentre el objeto pabellon
	 * @author SACM - 10/12/2021
	 */
	@DeleteMapping("/del/pabelloniD/{pabellonId}")
	public ResponseEntity<?> eliminarPabellon(@PathVariable Integer pabellonId)
	{
		Optional<Pabellon> oPabellon = pabellonDao.buscarPorId(pabellonId);
		
		if(!oPabellon.isPresent())
			throw new NotFoundException(String.format("El Aula con ID %d no existe", pabellonId));
		
		pabellonDao.eliminarPorId(pabellonId); 
		
		return new ResponseEntity<String>("Pabellon ID: " + pabellonId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
}
