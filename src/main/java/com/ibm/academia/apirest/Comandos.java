package com.ibm.academia.apirest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ibm.academia.apirest.services.ProfesorDAO;

@Component
public class Comandos implements CommandLineRunner 
{
	/*@Autowired
	private CarreraDAO carreraDao;*/
	
	@Autowired
	private ProfesorDAO profesorDAO;
	
	@Override
	public void run(String... args) throws Exception 
	{
		
		System.out.println(profesorDAO.findProfesoresByCarrera("Medicina"));
		
	}
}