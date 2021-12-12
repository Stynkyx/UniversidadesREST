package com.ibm.academia.apirest.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ibm.academia.apirest.models.entities.Aula;

@Repository("repositorioAulas")
public interface AulaRepository extends CrudRepository<Aula, Integer>{
	
	public Aula findAulaByNumeroAula(Integer numero);
	
	@Query("select a from Aula a join fetch a.pabellon p where p.nombre = ?1")
	public Iterable<Aula> buscarAulasPorPabellon(String nombre);
	
	public Iterable<Aula> findAulaByPizarron(String pizarron);

}
