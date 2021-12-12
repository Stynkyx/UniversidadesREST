package com.ibm.academia.apirest.services;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.ibm.academia.apirest.repositories.AlumnoRepository;

@SpringBootTest
class AlumnoDAOImplTest 
{
	@MockBean
	AlumnoRepository alumnoRepository;
	
	@Autowired
	AlumnoDAO alumnoDAO;

	@Test
	void buscarAlumnosPorNombreCarrera() 
	{
		// When
		alumnoDAO.buscarAlumnoPorNombreCarrera(anyString());

		// Then
		verify(alumnoRepository).buscarAlumnoPorNombreCarrera(anyString());
	}
}