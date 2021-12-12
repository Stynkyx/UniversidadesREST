package com.ibm.academia.apirest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Pabellon;

@Repository("repositorioPabellones")
public interface PabellonRepository extends CrudRepository<Pabellon, Integer>{

	@Query("select p from Pabellon p join fetch p.direccion d where d.localidad = ?1")
	public Iterable <Pabellon> findPabellonByLocalidad(String localidad);
	
	public Iterable <Pabellon> findPabellonByNombre(String nombre);
	
}
