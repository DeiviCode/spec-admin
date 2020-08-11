package com.solar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solar.interfaces.MunicipioService;
import com.solar.model.Municipio;
import com.solar.repository.MunicipioRepository;

@Service
public class MunicipioServiceIMPL implements MunicipioService {

	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Override
	public Municipio save(Municipio municipio) {
		return municipioRepository.save(municipio);
	}

	@Override
	public List<Municipio> list() {
		return municipioRepository.findAll();
	}

	@Override
	public Municipio findByNombre(String nombre) {
		return municipioRepository.findByNombre(nombre);
	}
	
	@Override
	public Municipio findById(Long id) {
		return municipioRepository.findById(id).get();
	}
	
	@Override
	public void removeById(Long id) {
		 municipioRepository.deleteById(id);
	}

	@Override
	public long getLength() {
		return municipioRepository.count();
	}

}
