package com.solar.controller;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solar.algoritmos.Point;
import com.solar.algoritmos.Polygon;
import com.solar.model.Estacion;
import com.solar.model.Municipio;
import com.solar.model.Provedor;
import com.solar.service.EstacionServiceIMPL;
import com.solar.service.MunicipioServiceIMPL;
import com.solar.service.ProvedorServiceIMPL;



@Controller
@RequestMapping("/estaciones")
public class EstacionController {
	
	
	
	@Autowired
	private EstacionServiceIMPL estacionServiceIMPL;
	
	@Autowired
	private MunicipioServiceIMPL municipioServiceIMPL;
	
	@Autowired
	private ProvedorServiceIMPL provedorServiceIMPL;
	
	
	
	@PostMapping
	public String saveEstacion(@RequestParam(name = "nombre") String nombre, @RequestParam(name = "municipio") String municipio, 
			@RequestParam(name = "origen") String origen, RedirectAttributes ra, @RequestParam(name = "lat") String lat,
			@RequestParam(name = "lon") String lon, @RequestParam(name = "id") String id) {
		try {
			Double latitude = 0.0;
			Double longitude = 0.0;
			
			try {
				latitude = Double.valueOf(lat);
				longitude = Double.valueOf(lon);
			} catch (Exception e) {
				ra.addFlashAttribute("error", "Las coordenadas deben ser numericas.");
				return "redirect:/estaciones";
			}
			
			// si las coordernadas son validas
			if(this.locationIsValid(new Point(longitude,latitude )) == 1) {
				// es una estacioón nueva
				if (id.equals("")) {
					Municipio municipioExist = municipioServiceIMPL.findByNombre(municipio);
					Provedor provedorExist = provedorServiceIMPL.findByNombre(origen);
					estacionServiceIMPL.save(new Estacion(nombre, municipioExist, provedorExist, latitude, longitude));
					ra.addFlashAttribute("ok", "Estacion registrada");
					
				}else {// se actualiza la estación
					Estacion estacionExist = estacionServiceIMPL.findById(Integer.parseInt(id));
					estacionExist.setNombre(nombre);
					estacionExist.setLat(Double.valueOf(latitude));
					estacionExist.setLon(Double.valueOf(lon));
					
					Municipio municipioExist = municipioServiceIMPL.findByNombre(municipio);
					Provedor provedorExist = provedorServiceIMPL.findByNombre(origen);
					
					estacionExist.setMunicipio(municipioExist);
					estacionExist.setProveedor(provedorExist);
					
					estacionServiceIMPL.save(estacionExist);
					ra.addFlashAttribute("ok", "Estacion actualizada");
				}
			}
			else {// si las coordenadas no son validas
				ra.addFlashAttribute("error", "Coordernadas no validas. Las coordernadas escritas no pertenecen al Área Metropolitana de Bucaramanga.");
			}
			
		} catch (Exception e) {
			ra.addFlashAttribute("error", "No se pudo registrar la estación "+e.getCause());
		}
		return "redirect:/estaciones";
	}
	
	@GetMapping("/remove/{id_estacion}")
	public String eliminarEstacion(Model model, @PathVariable(name = "id_estacion") Integer id_estacion,
			RedirectAttributes ar) {
		try {
			estacionServiceIMPL.removeById(id_estacion);;
			ar.addFlashAttribute("ok", "Estación eliminada");
		} catch (Exception e) {
			ar.addFlashAttribute("error", "No se pudo eliminar la estación");
		}
		return "redirect:/estaciones";
	}
	
	@GetMapping
	public String listarEstaciones(Model model) {
		model.addAttribute("id", "");
		model.addAttribute("nombre", "");
		model.addAttribute("lat", "");
		model.addAttribute("lon","");
		model.addAttribute("municipio", "");
		model.addAttribute("origen", "");
		model.addAttribute("estaciones", this.getEstaciones());
		model.addAttribute("origenes", this.getOrigenes());
		model.addAttribute("municipios", this.getMunicipios());
		return "estaciones/list";
	}
	
	@GetMapping("/edit/{id_estacion}")
	public String editarMunicipio(Model model, @PathVariable(name = "id_estacion") Integer id_estacion) {
		try {
			Estacion estacion = estacionServiceIMPL.findById(id_estacion);
			model.addAttribute("id", estacion.getId_estacion());
			model.addAttribute("nombre", estacion.getNombre());
			model.addAttribute("lat", estacion.getLat());
			model.addAttribute("lon", estacion.getLon());
			model.addAttribute("municipio", estacion.getMunicipio().getNombre());
			model.addAttribute("origen", estacion.getProveedor().getNombre());
			model.addAttribute("estaciones", this.getEstaciones());
			model.addAttribute("municipios", this.getMunicipios());
			model.addAttribute("origenes", this.getOrigenes());
		} catch (Exception e) {
			model.addAttribute("id", "");
			model.addAttribute("nombre", "");
			model.addAttribute("lat", "");
			model.addAttribute("lon","");
			model.addAttribute("municipio", "");
			model.addAttribute("origen", "");
			model.addAttribute("estaciones", this.getEstaciones());
			model.addAttribute("origenes", this.getOrigenes());
			model.addAttribute("municipios", this.getMunicipios());
		}
		return "estaciones/list";
	}
	
	private List<Estacion> getEstaciones() {
		return estacionServiceIMPL.list();
	}
	
	private List<Provedor> getOrigenes() {
		return provedorServiceIMPL.list();
	}
	
	private List<Municipio> getMunicipios() {
		return municipioServiceIMPL.list();
	}
	
	private int locationIsValid(Point point) throws IOException, URISyntaxException {
		int isValid = 1;
		Polygon windingNumber = new Polygon();
		if(windingNumber.inPolygon(point) == 0) {
			isValid = 0;
		}
		return isValid;
	}
}
