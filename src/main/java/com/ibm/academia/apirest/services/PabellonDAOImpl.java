package com.ibm.academia.apirest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.academia.apirest.models.entities.Pabellon;
import com.ibm.academia.apirest.repositories.PabellonRepository;

@Service
public class PabellonDAOImpl extends GenericoDAOImpl<Pabellon, PabellonRepository> implements PabellonDAO{

	@Autowired
	public PabellonDAOImpl(PabellonRepository repository) {
		super(repository);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Pabellon> findPabellonByLocalidad(String localidad) {
		return repository.findPabellonByLocalidad(localidad);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Pabellon> findPabellonByNombre(String nombre) {
		return repository.findPabellonByNombre(nombre);
	}

	@Override
	public Pabellon actualizar(Pabellon pabellonEncontrado, Pabellon pabellon) {
		Pabellon pabellonActualizado = null;
		pabellonEncontrado.setAulas(pabellon.getAulas());
		pabellonEncontrado.setNombre(pabellon.getNombre());
		pabellonActualizado = repository.save(pabellonEncontrado);
		return pabellonActualizado;
	}

}
