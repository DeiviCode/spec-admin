package com.solar.task;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.solar.model.Estacion;
import com.solar.model.IsantandRadiaction;
import com.solar.model.IsantandService;
import com.solar.model.Radiacion;
import com.solar.repository.RadiacionRepository;
import com.solar.service.EstacionServiceIMPL;


@Component
@Profile("!dev")
public class WundergroundService {


	
	@Autowired
	private RadiacionRepository radiacionRepository;
	
	@Autowired
	private EstacionServiceIMPL  estacionServiceIMPL;

	// Run at 8:00 PM
	@Scheduled(cron="0 0 20 * * ?" )
	public void getDataFromISANTAND30() {
		System.out.println("Ejecutando para isantand 30, hora: "+LocalDate.now());
		try {
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String uri = "https://api.weather.com/v2/pws/history/hourly?stationId=ISANTAND30&format=json&units=m&date="+date+"&apiKey=b3bc2e2c48a644b6bc2e2c48a614b691";
			
			RestTemplate restTemplate = new RestTemplate();
			IsantandService isantand = restTemplate.getForObject(uri, IsantandService.class);
			List<IsantandRadiaction> radiaciones = isantand.getObservations();
			List<Radiacion> radiacionesAguardar = new ArrayList<>();
			Estacion Isantand30 = estacionServiceIMPL.findByNombre_estacion("UPB - Piedecuesta");
			
			System.out.println(radiaciones.size() +"datos encontrados");
			
			if(Isantand30 != null) {
				if(radiaciones.size()>0) {
					for(IsantandRadiaction radiacion : radiaciones) {
						try {
							Double valor_radiacion = Double.valueOf(radiacion.getSolarRadiationHigh());
							
							if(valor_radiacion < 0 || valor_radiacion > 1000) {
								continue;
							}
							
							Radiacion currentRadiation = new Radiacion(Isantand30, valor_radiacion, Timestamp.valueOf(radiacion.getObsTimeLocal()));
							radiacionesAguardar.add(currentRadiation);
							
						} catch (Exception e) {
							System.out.println(e.getMessage());
							continue;
						}
					}
					radiacionRepository.saveAll(radiacionesAguardar);
					System.out.println("Se guardaron " + radiacionesAguardar.size() +" datos");
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			System.out.println("Error: " + e);
		}
	}
	
	// Run at 8:05 PM
	@Scheduled(cron="0 5 20 * * ?")
	public void getDataFromISANTAND31() {
		System.out.println("Ejecutando servicio  para isantand 31, hora: "+LocalDate.now());
		try {
			String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String uri = "https://api.weather.com/v2/pws/history/hourly?stationId=ISANTAND31&format=json&units=m&date="+date+"&apiKey=b3bc2e2c48a644b6bc2e2c48a614b691";
			
			RestTemplate restTemplate = new RestTemplate();
			IsantandService isantand = restTemplate.getForObject(uri, IsantandService.class);
			List<IsantandRadiaction> radiaciones = isantand.getObservations();
			List<Radiacion> radiacionesAguardar = new ArrayList<>();
			Estacion Isantand31 = estacionServiceIMPL.findByNombre_estacion("Paralela Bosque");
			
			System.out.println(radiaciones.size() +"datos encontrados");
			
			if(Isantand31 != null ) {
				if(radiaciones.size()>0) {
					for(IsantandRadiaction radiacion : radiaciones) {
						Radiacion currentRadiation = new Radiacion(Isantand31, Double.valueOf(radiacion.getSolarRadiationHigh()), Timestamp.valueOf(radiacion.getObsTimeLocal()));
						radiacionesAguardar.add(currentRadiation);
					}
					radiacionRepository.saveAll(radiacionesAguardar);
					System.out.println("Se guardaron " + radiacionesAguardar.size() +" datos");
				}
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}
}
