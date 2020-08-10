package com.solar.service;


import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solar.interfaces.RadiacionService;
import com.solar.model.Estacion;
import com.solar.model.Radiacion;
import com.solar.repository.RadiacionRepository;

@Service
public class RadiacionServiceIMPL implements RadiacionService {

	@Autowired
	private RadiacionRepository radiacionRepository;

	@Override
	public Radiacion save(Estacion estacion, Double valor_radiacion, Timestamp fecha) {
		return radiacionRepository.save(new Radiacion(estacion, valor_radiacion, fecha));
	}
	
	public long getRegistros() {
		return radiacionRepository.count();
	}
}
