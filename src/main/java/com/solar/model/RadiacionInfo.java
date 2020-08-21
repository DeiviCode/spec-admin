package com.solar.model;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;


//@SqlResultSetMapping(name = "RadiacionInfo", classes = {
//		@ConstructorResult(targetClass = RadiacionInfo.class, columns = {
//				@ColumnResult(name = "Estacion"),@ColumnResult(name = "Municipio"),@ColumnResult(name = "Origen"),
//				@ColumnResult(name = "Lat"),@ColumnResult(name = "Long"),@ColumnResult(name = "Radiacion"),
//				@ColumnResult(name = "Fecha"), @ColumnResult(name = "Id")
//		})
//})

@Entity
@Immutable
@Table(name="radiacion")
public class RadiacionInfo {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id")
	private Integer id;
	@Column(name = "Estacion")
	private String estacion;
	@Column(name = "Municipio")
	private String municipio;
	@Column(name = "Origen")
	private String origen;
	@Column(name = "Lat")
	private Double lat;
	@Column(name = "Long")
	private Double lon;
	@Column(name = "Radiacion")
	private Double radiacion;
	@Column(name = "Fecha")
	private LocalDateTime fecha;
	


	public RadiacionInfo(Integer id, String estacion, String municipio, String origen, Double lat, Double lon,
			Double radiacion, LocalDateTime fecha) {
		super();
		this.id = id;
		this.estacion = estacion;
		this.municipio = municipio;
		this.origen = origen;
		this.lat = lat;
		this.lon = lon;
		this.radiacion = radiacion;
		this.fecha = fecha;
	}



	public RadiacionInfo() {
		
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getEstacion() {
		return estacion;
	}



	public void setEstacion(String estacion) {
		this.estacion = estacion;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getOrigen() {
		return origen;
	}



	public void setOrigen(String origen) {
		this.origen = origen;
	}



	public Double getLat() {
		return lat;
	}



	public void setLat(Double lat) {
		this.lat = lat;
	}



	public Double getLon() {
		return lon;
	}



	public void setLon(Double lon) {
		this.lon = lon;
	}



	public Double getRadiacion() {
		return radiacion;
	}



	public void setRadiacion(Double radiacion) {
		this.radiacion = radiacion;
	}



	public LocalDateTime getFecha() {
		return fecha;
	}



	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	
	
	
}
