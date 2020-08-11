package com.solar.interfaces;

import java.util.List;

import com.solar.model.Municipio;;

public interface MunicipioService {
	public Municipio save(Municipio municipio);
	public List<Municipio> list();
	public Municipio findByNombre(String nombre);
	public Municipio findById(Long id);
	public void removeById(Long nombre);
	public long getLength();
}
