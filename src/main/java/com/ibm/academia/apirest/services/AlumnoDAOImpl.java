package com.ibm.academia.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.models.entities.Alumno;
import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.repositories.AlumnoRepository;
import com.ibm.academia.apirest.repositories.PersonaRepository;

@Service
public class AlumnoDAOImpl extends PersonaDAOImpl implements AlumnoDAO 
{
	@Autowired
	public AlumnoDAOImpl(@Qualifier("repositorioAlumnos")PersonaRepository repository) 
	{
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Persona> buscarAlumnoPorNombreCarrera(String nombre) 
	{
		return ((AlumnoRepository)repository).buscarAlumnoPorNombreCarrera(nombre);
	}
	
	@Override
	@Transactional
	public Persona actualizar(Persona alumnoEncontrado, Persona alumno) 
	{
		Persona alumnoActualizado = null;
		alumnoEncontrado.setNombre(alumno.getNombre());
		alumnoEncontrado.setApellido(alumno.getApellido());
		alumnoEncontrado.setDireccion(alumno.getDireccion());
		alumnoActualizado = repository.save(alumnoEncontrado);
		return alumnoActualizado;
	}

	@Override
	@Transactional
	public Persona asociarCarreraAlumno(Persona alumno, Carrera carrera) 
	{
		((Alumno)alumno).setCarrera(carrera);
		return repository.save(alumno);
	}
	
	
}