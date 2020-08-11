package com.solar.interfaces;

import java.util.List;
import com.solar.model.Provedor;

public interface ProvedorService {

	public Provedor save(Provedor provedor);
	public List<Provedor> list();
	public Provedor findByNombre(String nombre);
	public Provedor findById(Long id);
	public void removeById(Long id);
	public long getLength();
}
