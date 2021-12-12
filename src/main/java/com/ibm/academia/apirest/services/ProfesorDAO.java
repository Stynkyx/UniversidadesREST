package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Carrera;
import com.ibm.academia.apirest.models.entities.Persona;

public interface ProfesorDAO extends PersonaDAO{

	public Iterable<Persona> findProfesoresByCarrera(String nombre);
	public Persona actualizar(Persona profesorEncontrado, Persona profesor);
	public Persona asociarCarreraProfesor(Persona profesor, Carrera carrera);
	
}
