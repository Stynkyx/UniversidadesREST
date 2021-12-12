package com.ibm.academia.apirest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.exceptions.BadRequestException;
import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.services.AulaDAO;

@RestController
@RequestMapping("/aula")
public class AulaController {

	@Autowired
	private AulaDAO aulaDao;
	
	/**
	 * Endpoint para crear un objeto Aula 
	 * @param aula Objeto aula con la inforamción a crear
	 * @return Retorna un objeto Aula httpstatus 201
	 * @author SACM - 11-12-2021 
	 */
	@PostMapping
	public ResponseEntity<?> crearAlula(@RequestBody Aula aula)
	{
		Aula aulaGuardada = aulaDao.guardar(aula);
		return new ResponseEntity<Aula>(aulaGuardada, HttpStatus.CREATED);
	}
	/**
	 * Endpoint para buscar aula por numero
	 * @param numero Parametro para buscar el aula
	 * @return lista de objetos aulas 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/numero/{numero}")
	public ResponseEntity<?> buscarAulaPorNumero(@PathVariable Integer numero)
	{
		Aula aula = aulaDao.findAulaByNumeroAula(numero);
		if(aula == null)
			throw new BadRequestException(String.format("El aula con numero: %d no existe", numero));
		
		return new ResponseEntity<Aula>(aula, HttpStatus.OK);
	}
	/**
	 * Endpoint para listar las aulas con el nombre un objeto pabellon
	 * @param nombre Parametro para buscar las aulas
	 * @return lista de objetos aulas 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/pabellon/{nombre}")
	public ResponseEntity<?> buscarAulaPorPabellon(@PathVariable String nombre)
	{
		List<Aula> aula = (List<Aula>) aulaDao.buscarAulasPorPabellon(nombre);
		if(aula.isEmpty())
			throw new BadRequestException(String.format("El aulas en el Pabellon de: %s no existe", nombre));
		
		return new ResponseEntity<List<Aula>>(aula, HttpStatus.OK);
	}
	/**
	 * Endpoint para listar las aulas con tipo de pizarron
	 * @param pizarron Parametro para buscar las aulas
	 * @return lista de objetos aulas 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/pizarron/{pizarron}")
	public ResponseEntity<?> buscarAulaPorPizarron(@PathVariable String pizarron)
	{
		List<Aula> aula = (List<Aula>) aulaDao.buscarAulasPorPabellon(pizarron);
		if(aula.isEmpty())
			throw new BadRequestException(String.format("No existen aulas con: %s no existe", pizarron));
		
		return new ResponseEntity<List<Aula>>(aula, HttpStatus.OK);
	}
	/**
	 * Endpoint para listar las aulas
	 * @return lista de objetos aulas
	 * @author SACM - 06-21-2021
	 */
	@GetMapping("/aulas/lista")
	public ResponseEntity<?> obtenerTodos()
	{
		List<Aula> aulas = (List<Aula>) aulaDao.buscarTodos();
		
		if(aulas.isEmpty())
			throw new NotFoundException("No existen aulas");
		return new ResponseEntity<List<Aula>>(aulas, HttpStatus.OK);
	}
	
	/**
	 * Endoint para eliminar un objeto de tipo alumno
	 * @param alumnoId Parámetro para buscar la alumno
	 * @return Retorna un mensaje de que fue eliminado con exito
	 * @NotFoundException En caso de que falle no encuentre el objeto alumno
	 * @author SACM - 10/12/2021
	 */
	@DeleteMapping("/del/aulaId/{aulaId}")
	public ResponseEntity<?> eliminarAula(@PathVariable Integer aulaId)
	{
		Optional<Aula> oAula = aulaDao.buscarPorId(aulaId);
		
		if(!oAula.isPresent())
			throw new NotFoundException(String.format("El Aula con ID %d no existe", aulaId));
		
		aulaDao.eliminarPorId(aulaId); 
		
		return new ResponseEntity<String>("Aula ID: " + aulaId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
}
