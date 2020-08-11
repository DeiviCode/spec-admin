package com.solar.interfaces;


import java.sql.Timestamp;

import com.solar.model.Estacion;
import com.solar.model.Radiacion;;

public interface RadiacionService {

	public Radiacion save(Estacion estacion, Double valor_radiacion, Timestamp fecha);
	public long getLength();
}
