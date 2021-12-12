package com.ibm.academia.apirest.services;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.models.entities.Persona;
import com.ibm.academia.apirest.repositories.PersonaRepository;

public class PersonaDAOImpl extends GenericoDAOImpl<Persona, PersonaRepository> implements PersonaDAO 
{
	public PersonaDAOImpl(PersonaRepository repository) 
	{
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> buscarPorNombreYApellido(String nombre, String apellido) 
	{
		return repository.buscarPorNombreYApellido(nombre, apellido);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Persona> buscarPorDni(String dni) 
	{
		return repository.buscarPorDni(dni);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Persona> buscarPersonaPorApellido(String apellido) 
	{
		return repository.buscarPersonaPorApellido(apellido);
	}
}