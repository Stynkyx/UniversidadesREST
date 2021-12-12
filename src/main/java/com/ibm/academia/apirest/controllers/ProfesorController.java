package com.ibm.academia.apirest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibm.academia.apirest.exceptions.NotFoundException;
import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.services.CarreraDAO;
import com.ibm.academia.apirest.services.PersonaDAO;
import com.ibm.academia.apirest.services.ProfesorDAO;

@RestController
@RequestMapping("/profesor")
public class ProfesorController {
	
	@Autowired
	@Qualifier("profesorDAOImpl")
	private PersonaDAO profesorDao;

	@Autowired
	private CarreraDAO carreraDao;
	/**
	 * Endpoint para crear un objeto profesor 
	 * @param profesor Objeto profesor con la inforamción a crear
	 * @return Retorna un objeto profesor httpstatus 201
	 * @author SACM - 11-12-2021 
	 */
	@PostMapping
	public ResponseEntity<?> crearProfesor(@RequestBody Persona profesor)
	{
		Persona profesorGuardado = profesorDao.guardar(profesor);
		return new ResponseEntity<Persona>(profesorGuardado, HttpStatus.CREATED);
	}
	/**
	 * Endpoint para buscar profesores por localidad
	 * @return lista de objetos profesores 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/profesor/lista")
	public ResponseEntity<?> obtenerTodos()
	{
		List<Persona> profesores = (List<Persona>) profesorDao.buscarTodos();
		
		if(profesores.isEmpty())
			throw new NotFoundException("No existen profesores");
		return new ResponseEntity<List<Persona>>(profesores, HttpStatus.OK);
	}
	/**
	 * Endpoint para buscar profesores por id
	 * @param profesorId Parametro para buscar profesores
	 * @return lista de objetos profesores 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/profesorId/{profesorId}")
    public ResponseEntity<?> obtenerProfesorPorId(@PathVariable Integer profesorId)
    {
        Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
        
        if(!oProfesor.isPresent()) 
            throw new NotFoundException(String.format("Profesor con id %d no existe", profesorId));
        
        return new ResponseEntity<Persona>(oProfesor.get(), HttpStatus.OK);
    }
	/**
	 * Endpoint para buscar profesores por carrera
	 * @param carrera Parametro para buscar profesores
	 * @return lista de objetos profesores 
	 * @author SACM - 10-21-2021
	 */
	@GetMapping("/profesor-carrera/{carrera}")
	public ResponseEntity<?> obtenerProfesorCarrera(@PathVariable String carrera)
	{
		List<Persona> profesores = (List<Persona>) ((ProfesorDAO)profesorDao).findProfesoresByCarrera(carrera);
		
		if(profesores.isEmpty())
			throw new NotFoundException("No existen profesores");
		return new ResponseEntity<List<Persona>>(profesores, HttpStatus.OK);
	}
	
	/**
	 * Endoint para actualizar un objeto de tipo profesor
	 * @param profesorId Parámetro para buscar la profesor
	 * @param profesor Objeto con la información a modificar
	 * @return Retorna un objeto de tipo profesor con la información actualizada
	 * @NotFoundException En caso de que falle actualizando el objeto profesor
	 * @author SACM - 10/12/2021
	 */
	@PutMapping("/upd/profesorId/{alumnoId}")
	public ResponseEntity<?> actualizarProfesor(@PathVariable Integer profesorId, @RequestBody Persona profesor)
	{
		Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
		
		if(!oProfesor.isPresent())
			throw new NotFoundException(String.format("El profesor con ID %d no existe", profesorId));
		
		Persona profesorActualizado = ((ProfesorDAO)profesorDao).actualizar(oProfesor.get(), profesor);
		return new ResponseEntity<Persona>(profesorActualizado, HttpStatus.OK);
	}
	
	/**
	 * Endoint para eliminar un objeto de tipo profesor
	 * @param profesorId Parámetro para buscar la profesor
	 * @return Retorna un mensaje de que fue eliminado con exito
	 * @NotFoundException En caso de que falle no encuentre el objeto profesor
	 * @author SACM - 10/12/2021
	 */
	@DeleteMapping("/del/profesorId/{profesorId}")
	public ResponseEntity<?> eliminarProfesor(@PathVariable Integer profesorId)
	{
		Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
		
		if(!oProfesor.isPresent())
			throw new NotFoundException(String.format("El profesor con ID %d no existe", profesorId));
		
		profesorDao.eliminarPorId(oProfesor.get().getId()); 
		
		return new ResponseEntity<String>("Profesor ID: " + profesorId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
	/**
	 * Endpoint para asiganarle una carrera a un profesor
	 * @param carreraId Parametro de carrera para asignar
	 * @param profesorId Parametro para el profesor al que se le asignara la carrera
	 * @return Un objeto profesor con la carrera asignada con código httpstatus 200
	 * @author SACM - 10-21-2021
	 */
	@PutMapping("/profesorId/{profesorId}/carreraId/{carreraId}")
	public ResponseEntity<?> asignarCarreraProfesor(@PathVariable Integer carreraId, @PathVariable Integer profesorId)
	{
		Optional<Persona> oProfesor = profesorDao.buscarPorId(profesorId);
        if(!oProfesor.isPresent()) 
            throw new NotFoundException(String.format("Profesor con id %d no existe", profesorId));
        
        Optional<Carrera> oCarrera = carreraDao.buscarPorId(carreraId);
        if(!oCarrera.isPresent())
            throw new NotFoundException(String.format("Carrera con id %d no existe", carreraId));
        
        Persona profesor = ((ProfesorDAO)profesorDao).asociarCarreraProfesor(oProfesor.get(), oCarrera.get());
        
        return new ResponseEntity<Persona>(profesor, HttpStatus.OK);
	}
}
