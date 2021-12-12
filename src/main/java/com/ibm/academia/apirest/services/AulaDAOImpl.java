package com.ibm.academia.apirest.services;

import org.springframework.stereotype.Service;

import com.ibm.academia.apirest.models.entities.Aula;
import com.ibm.academia.apirest.repositories.AulaRepository;

@Service
public class AulaDAOImpl extends GenericoDAOImpl<Aula, AulaRepository> implements AulaDAO{

	public AulaDAOImpl(AulaRepository repository) {
		super(repository);
	}

	@Override
	public Aula findAulaByNumeroAula(Integer numero) {
		return repository.findAulaByNumeroAula(numero);
	}

	@Override
	public Iterable<Aula> buscarAulasPorPabellon(String nombre) {
		return repository.buscarAulasPorPabellon(nombre);
	}

	@Override
	public Iterable<Aula> findAulaByPizarron(String pizarron) {
		return 	repository.findAulaByPizarron(pizarron);

	}


}
