package com.ibm.academia.apirest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Carrera;

@Repository("repositorioCarreras")
public interface CarreraRepository extends CrudRepository<Carrera, Integer>
{
	public Iterable<Carrera> findCarrerasByCantidadAnios(Integer cantidadAnios);
	
	public Iterable<Carrera> findCarrerasByNombreContains(String nombre);
	
	public Iterable<Carrera> findCarrerasByNombreContainsIgnoreCase(String nombre);
	
	public Iterable<Carrera> findCarrerasByCantidadAniosAfter(Integer cantidadAnios); 
	
	@Query("select c from Carrera c join fetch c.profesores p where p.nombre = ?1 and p.apellido = ?2")
	public Iterable<Carrera> buscarCarrerasPorProfesorNombreYApellido(String nombre, String apellido);
}