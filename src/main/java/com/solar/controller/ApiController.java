package com.solar.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.solar.exception.RestException;
import com.solar.model.EstacionInfo;
import com.solar.model.RadiacionInfo;

@RestController
@RequestMapping("/api")
public class ApiController {

	@Autowired
	private EntityManager em;
	
	
	// api for estaciones
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getEstaciones")
	public List<EstacionInfo> getEstaciones() throws RestException{
		try {
			Query query = em.createNativeQuery("select * from GET_STATIONS();", EstacionInfo.class);
			return (List<EstacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Error:"+e.getMessage());
		}
		
	}
	
	// api for radiación
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacion")
	public List<RadiacionInfo> getRadiacion() throws RestException {
		try {
			Query query = em.createNativeQuery("select * from radiacion", RadiacionInfo.class);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Error:"+e.getMessage());
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByRangeDate/{date_start}/{date_end}")
	public List<RadiacionInfo> getRadiacionByRangeDate(@PathVariable(name = "date_start") String date_start, 
			@PathVariable(name = "date_end") String date_end) throws RestException {
		try {
			System.out.println("Rango " + date_start + " - " + date_end);
			Query query = em.createNativeQuery("select * from GET_RADIACION_BY_RANGE_DATE(:date_start,:date_end);", RadiacionInfo.class);
			query.setParameter("date_start", Timestamp.valueOf(date_start), TemporalType.DATE);
			query.setParameter("date_end", Timestamp.valueOf(date_end), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByDate/{date}")
	public List<RadiacionInfo> getRadiacionByDate(@PathVariable(name = "date") String date) throws RestException {
		try {
			System.out.println("Espesifica "+ date);
			Query query = em.createNativeQuery("select * from GET_RADIACION_BY_DATE(:date);", RadiacionInfo.class);
			query.setParameter("date", Timestamp.valueOf(date), TemporalType.DATE);
			List<RadiacionInfo> data = (List<RadiacionInfo>) query.getResultList();
			for(RadiacionInfo ri : data) {
				System.out.println(ri.getFecha());
			}
			return data;
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByOrigen/{origen}")
	public List<RadiacionInfo> getRadiacionByOrigen(@PathVariable(name = "origen") String origen) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ORIGEN(:origen);", RadiacionInfo.class);
			query.setParameter("origen", origen);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Error:"+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByMunicipio/{municipio}")
	public List<RadiacionInfo> getRadiacionByMunicipio(@PathVariable(name = "municipio") String municipio) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_MUNICIPIO(:municipio);", RadiacionInfo.class);
			query.setParameter("municipio", municipio);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Error:"+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByEstacion/{estacion}")
	public List<RadiacionInfo> getRadiacionByEstacion(@PathVariable(name = "estacion") String estacion) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ESTACION(:estacion);", RadiacionInfo.class);
			query.setParameter("estacion", estacion);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Error:"+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByEstacionAndDate/{estacion}/{date}")
	public List<RadiacionInfo> getRadiacionByEstacionAndDate(@PathVariable(name = "estacion") String estacion,
			@PathVariable(name = "date") String date) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ESTACION_AND_DATE_WITHOUT_TIME(:estacion, :date);", RadiacionInfo.class);
			query.setParameter("estacion", estacion);
			query.setParameter("date", Timestamp.valueOf(date), TemporalType.DATE);
			System.out.println("Se encontraron " + query.getResultList().size() + " para la fecha " + date);
			return (List<RadiacionInfo>) query.getResultList();
			
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByMunicipioAndDate/{municipio}/{date}")
	public List<RadiacionInfo> getRadiacionByMunicipioAndDate(@PathVariable(name = "municipio") String municipio,
			@PathVariable(name = "date") String date) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_MUNICIPIO_AND_DATE(:municipio, :date);", RadiacionInfo.class);
			query.setParameter("municipio", municipio);
			query.setParameter("date", Timestamp.valueOf(date), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
//	@SuppressWarnings("unchecked")
//	@GetMapping("/getRadiacionByPointAndDate/{lat}/{lon}/{date}")
//	public List<RadiacionInfo> getRadiacionByPointAndDate(@PathVariable(name = "lat") String lat,
//			@PathVariable(name = "lon") String lon,@PathVariable(name = "date") String date) throws RestException {
//		try {
//			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_MUNICIPIO_AND_DATE(:lat, :lon, :date);", RadiacionInfo.class);
//			query.setParameter("lat", lat);
//			query.setParameter("lon", lon);
//			query.setParameter("date", Timestamp.valueOf(date), TemporalType.DATE);
//			return (List<RadiacionInfo>) query.getResultList();
//		} catch (Exception e) {
//			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
//		}
//		
//	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByOrigenAndDate/{origen}/{date}")
	public List<RadiacionInfo> getRadiacionByOrigenAndDate(@PathVariable(name = "origen") String origen,
			@PathVariable(name = "date") String date) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ORIGEN_AND_DATE(:origen, :date);", RadiacionInfo.class);
			query.setParameter("origen", origen);
			query.setParameter("date", Timestamp.valueOf(date), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByEstacionAndRangeDate/{estacion}/{date_start}/{date_end}")
	public List<RadiacionInfo> getRadiacionByEstacionAndRangeDate(@PathVariable(name = "estacion") String estacion,
			@PathVariable(name = "date_start") String date_start, @PathVariable(name = "date_end") String date_end) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ESTACION_AND_RANGE_DATE(:estacion,:date_start,:date_end);", RadiacionInfo.class);
			query.setParameter("estacion", estacion);
			query.setParameter("date_start", Timestamp.valueOf(date_start), TemporalType.DATE);
			query.setParameter("date_end", Timestamp.valueOf(date_end), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByOrigenAndRangeDate/{origen}/{date_start}/{date_end}")
	public List<RadiacionInfo> getRadiacionByOrigenAndRangeDate(@PathVariable(name = "origen") String origen,
			@PathVariable(name = "date_start") String date_start, @PathVariable(name = "date_end") String date_end) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ORIGEN_AND_RANGE_DATE(:origen,:date_start,:date_end);", RadiacionInfo.class);
			query.setParameter("origen", origen);
			query.setParameter("date_start", Timestamp.valueOf(date_start), TemporalType.DATE);
			query.setParameter("date_end", Timestamp.valueOf(date_end), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/getRadiacionByMunicipioAndRangeDate/{municipio}/{date_start}/{date_end}")
	public List<RadiacionInfo> getRadiacionByMunicipioAndRangeDate(@PathVariable(name = "municipio") String municipio,
			@PathVariable(name = "date_start") String date_start, @PathVariable(name = "date_end") String date_end) throws RestException {
		try {
			Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_MUNICIPIO_AND_RANGE_DATE(:municipio,:date_start,:date_end);", RadiacionInfo.class);
			query.setParameter("municipio", municipio);
			query.setParameter("date_start", Timestamp.valueOf(date_start), TemporalType.DATE);
			query.setParameter("date_end", Timestamp.valueOf(date_end), TemporalType.DATE);
			return (List<RadiacionInfo>) query.getResultList();
		} catch (Exception e) {
			throw  new RestException("Asegurese de escribir el siguiente formato: yyyy-mm-dd hh:mm:ss. Error: "+e.getMessage());
		}
		
	}
	
}
