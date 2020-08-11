package com.solar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solar.interfaces.ProvedorService;
import com.solar.model.Provedor;
import com.solar.repository.ProvedorRepository;

@Service
public class ProvedorServiceIMPL implements ProvedorService{

	@Autowired
	private ProvedorRepository provedorRepository;

	@Override
	public Provedor save(Provedor provedor) {
		return provedorRepository.save(provedor);
	}

	@Override
	public List<Provedor> list() {
		return provedorRepository.findAll();
	}

	@Override
	public Provedor findByNombre(String nombre) {
		return provedorRepository.findByNombre(nombre);
	}

	@Override
	public Provedor findById(Long id) {
		return provedorRepository.findById(id).get();
	}

	@Override
	public void removeById(Long id) {
		provedorRepository.deleteById(id);
		
	}

	@Override
	public long getLength() {
		return provedorRepository.count();
	}
	
	
}
