package com.ibm.academia.apirest.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ibm.academia.apirest.repositories.AlumnoRepository;

@ExtendWith(MockitoExtension.class)
class PersonaDAOImplTest 
{

	PersonaDAO personaDAO;
	@Mock
	AlumnoRepository alumnoRepository;

	@BeforeEach
	void setUp() 
	{
		personaDAO = new PersonaDAOImpl(alumnoRepository);
	}

	@Test
	@DisplayName("Test: Buscar por Nombre y Apellido")
	void buscarPorNombreYApellido() 
	{
		// When
		personaDAO.buscarPorNombreYApellido(anyString(), anyString());

		// Then
		verify(alumnoRepository).buscarPorNombreYApellido(anyString(), anyString());
	}

	@Test
	@DisplayName("Test: Buscar por DNI")
	void buscarPorDni() 
	{
		// When
		personaDAO.buscarPorDni(anyString());

		// Then
		verify(alumnoRepository).buscarPorDni(anyString());
	}

	@Test
	@DisplayName("Test: Buscar por Apellido")
	void buscarPersonaPorApellido() 
	{
		// When
		personaDAO.buscarPersonaPorApellido(anyString());

		// Then
		verify(alumnoRepository).buscarPersonaPorApellido(anyString());
	}
}