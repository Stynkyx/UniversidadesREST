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
import com.ibm.academia.apirest.services.AlumnoDAO;
import com.ibm.academia.apirest.services.CarreraDAO;
import com.ibm.academia.apirest.services.PersonaDAO;

@RestController
@RequestMapping("/alumno")
public class AlumnoController 
{
	@Autowired
	@Qualifier("alumnoDAOImpl")
	private PersonaDAO alumnoDao;
	
	@Autowired
	private CarreraDAO carreraDao;
	
	/**
	 * Endpoint para crear un objeto Persona de tipo alumno
	 * @param alumno Objeto alumno con la inforamción a crear
	 * @return Retorna un objeto Persona de tipo alumno con código httpstatus 201
	 * @author NDSC - 06-12-2021 
	 */
	@PostMapping
	public ResponseEntity<?> crearAlumno(@RequestBody Persona alumno)
	{
		Persona alumnoGuardado = alumnoDao.guardar(alumno);
		return new ResponseEntity<Persona>(alumnoGuardado, HttpStatus.CREATED);
	}
	
	/**
	 * Endpoint para listar los alumnos
	 * @return lista de objetos alumnos
	 * @author NDSC - 06-21-2021
	 */
	@GetMapping("/alumnos/lista")
	public ResponseEntity<?> obtenerTodos()
	{
		List<Persona> alumnos = (List<Persona>) alumnoDao.buscarTodos();
		
		if(alumnos.isEmpty())
			throw new NotFoundException("No existen alumnos");
		return new ResponseEntity<List<Persona>>(alumnos, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param alumnoId
	 * @return
	 */
	@GetMapping("/alumnoId/{alumnoId}")
    public ResponseEntity<?> obtenerAlumnoPorId(@PathVariable Integer alumnoId)
    {
        Optional<Persona> oAlumno = alumnoDao.buscarPorId(alumnoId);
        
        if(!oAlumno.isPresent()) 
            throw new NotFoundException(String.format("Alumno con id %d no existe", alumnoId));
        
        return new ResponseEntity<Persona>(oAlumno.get(), HttpStatus.OK);
    }
	
	/**
	 * Endoint para actualizar un objeto de tipo alumno
	 * @param alumnoId Parámetro para buscar la alumno
	 * @param alumno Objeto con la información a modificar
	 * @return Retorna un objeto de tipo Alumno con la información actualizada
	 * @NotFoundException En caso de que falle actualizando el objeto alumno
	 * @author SACM - 10/12/2021
	 */
	@PutMapping("/upd/alumnoId/{alumnoId}")
	public ResponseEntity<?> actualizarAlumno(@PathVariable Integer alumnoId, @RequestBody Persona alumno)
	{
		Optional<Persona> oAlumno = alumnoDao.buscarPorId(alumnoId);
		
		if(!oAlumno.isPresent())
			throw new NotFoundException(String.format("El alumno con ID %d no existe", alumnoId));
		
		Persona alumnoActualizado = ((AlumnoDAO)alumnoDao).actualizar(oAlumno.get(), alumno);
		return new ResponseEntity<Persona>(alumnoActualizado, HttpStatus.OK);
	}
	
	/**
	 * Endpoint para eliminar un objeto de tipo alumno
	 * @param alumnoId Parámetro para buscar la alumno
	 * @return Retorna un mensaje de que fue eliminado con exito
	 * @NotFoundException En caso de que falle no encuentre el objeto alumno
	 * @author SACM - 10/12/2021
	 */
	@DeleteMapping("/alumnoId/{alumnoId}")
	public ResponseEntity<?> eliminarAlumno(@PathVariable Integer alumnoId)
	{
		Optional<Persona> oAlumno = alumnoDao.buscarPorId(alumnoId);
		
		if(!oAlumno.isPresent())
			throw new NotFoundException(String.format("El alumno con ID %d no existe", alumnoId));
		
		alumnoDao.eliminarPorId(alumnoId); 
		
		return new ResponseEntity<String>("Alumno ID: " + alumnoId + " se elimino satisfactoriamente",  HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Endpoint para asiganarle una carrera a un alumno
	 * @param carreraId Parametro de carrera para asignar
	 * @param alumnoId Parametro para el alumno al que se le asignara la carrera
	 * @return Un objeto alumno con la carrera asignada con código httpstatus 200
	 * @author NDSC - 06-21-2021
	 */
	@PutMapping("/alumnoId/{alumnoId}/carrera/{carreraId}")
	public ResponseEntity<?> asignarCarreraAlumno(@PathVariable Integer carreraId, @PathVariable Integer alumnoId)
	{
		Optional<Persona> oAlumno = alumnoDao.buscarPorId(alumnoId);
        if(!oAlumno.isPresent()) 
            throw new NotFoundException(String.format("Alumno con id %d no existe", alumnoId));
        
        Optional<Carrera> oCarrera = carreraDao.buscarPorId(carreraId);
        if(!oCarrera.isPresent())
            throw new NotFoundException(String.format("Carrera con id %d no existe", carreraId));
        
        Persona alumno = ((AlumnoDAO)alumnoDao).asociarCarreraAlumno(oAlumno.get(), oCarrera.get());
        
        return new ResponseEntity<Persona>(alumno, HttpStatus.OK);
	}
}
