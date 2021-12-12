package com.ibm.academia.apirest.services;

import com.ibm.academia.apirest.models.entities.Aula;

public interface AulaDAO extends GenericoDAO<Aula>{

	public Aula findAulaByNumeroAula(Integer numero);
	public Iterable<Aula> buscarAulasPorPabellon(String nombre);
	public Iterable<Aula> findAulaByPizarron(String pizarron);

}
