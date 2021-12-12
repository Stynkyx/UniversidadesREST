package com.ibm.academia.apirest.services;

import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Pabellon;

@Repository("repositorioPabellones")
public interface PabellonDAO extends GenericoDAO<Pabellon> {
	
	public Iterable <Pabellon> findPabellonByLocalidad(String localidad);
	public Iterable <Pabellon> findPabellonByNombre(String nombre);

	public Pabellon actualizar(Pabellon pabellonEncontrado, Pabellon pabellon);
}
