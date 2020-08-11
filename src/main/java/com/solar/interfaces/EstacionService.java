package com.solar.interfaces;

import java.util.List;

import com.solar.model.Estacion;

public interface EstacionService {
	public Estacion save(Estacion estacion);
	public List<Estacion> list();
	public Estacion findByNombre_estacion(String nombre_estacion);
	public Estacion findById(Integer id);
	public void removeById(Integer id);
	public long getLength();
}
