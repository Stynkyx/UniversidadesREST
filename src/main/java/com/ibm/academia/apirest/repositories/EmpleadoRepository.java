package com.ibm.academia.apirest.repositories;

import org.springframework.stereotype.Repository;

@Repository("repositorioEmpleados")
public interface EmpleadoRepository extends PersonaRepository 
{	

	//@Query("select p from Profesor p join fetch p.carreras c where c.nombre = ?1")
	//@Query("select e from Empleado e join fetch e.empleados where tipo_empleado = ?1")
	//public Iterable<Persona> findEmpleadoByTipoEmpleado(TipoEmpleado tipoEmpleado);
}