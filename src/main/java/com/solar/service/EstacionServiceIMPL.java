package com.solar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solar.interfaces.EstacionService;
import com.solar.model.Estacion;
import com.solar.repository.EstacionRepository;

@Service
public class EstacionServiceIMPL implements EstacionService {

	@Autowired
	private EstacionRepository estacionRepository;

	@Override
	public Estacion save(Estacion estacion) {
		return estacionRepository.save(estacion);
	}

	@Override
	public List<Estacion> list() {
		return estacionRepository.findAll();
	}

	@Override
	public Estacion findById(Integer id) {
		Estacion estacion = estacionRepository.findById(id).get();
		return estacion;
	}

	@Override
	public Estacion findByNombre_estacion(String nombre) {
		return estacionRepository.findByNombre(nombre);
	}


	@Override
	public void removeById(Integer id) {
		 estacionRepository.deleteById(id);
		
	}

	@Override
	public long getLength() {
		return estacionRepository.count();
	}


}
