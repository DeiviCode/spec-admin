package com.solar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.solar.model.Municipio;
import com.solar.service.MunicipioServiceIMPL;

@Controller
@RequestMapping("/municipios")
public class MunicipioController {
	
	@Autowired
	private MunicipioServiceIMPL municipioServiceIMPL;

	@PostMapping
	public String saveOrUpdateMunicipio(@ModelAttribute Municipio municipio, RedirectAttributes ra) {
		try {
			if(municipio.getId_municipio()!=null) {
				Municipio municipioExist = municipioServiceIMPL.findById(municipio.getId_municipio());
				municipioExist.setNombre(municipio.getNombre());
				municipioServiceIMPL.save(municipioExist);
			}else {
				municipioServiceIMPL.save(municipio);
			}
			
			ra.addFlashAttribute("ok", "Municipio registrado");
		} catch (Exception e) {
			ra.addFlashAttribute("error", "No se pudo registrar el municipio "+e.getMessage());
		}
		return "redirect:/municipios";
	}
	
	@GetMapping
	public String listarMuncipios(Model model) {
		model.addAttribute("municipio", new Municipio());
		model.addAttribute("municipios", this.getMunicipios());
		return "municipios/list";
	}
	
	@GetMapping("/edit/{id_municipio}")
	public String editarMunicipio(Model model, @PathVariable(name = "id_municipio") Long id_municipio, RedirectAttributes ra) {
		try {
			Municipio municipio = municipioServiceIMPL.findById(id_municipio);
			model.addAttribute("municipio", municipio);
			model.addAttribute("municipios", this.getMunicipios());
		} catch (Exception e) {
			model.addAttribute("municipio", new Municipio());
			model.addAttribute("municipios", this.getMunicipios());
			ra.addFlashAttribute("error", "Es posible que el recurso no exista.");
		}
		return "municipios/list";
	}
	
	@GetMapping("/remove/{id_municipio}")
	public String eliminarMunicipio(Model model, @PathVariable(name = "id_municipio") Long id_municipio,
			RedirectAttributes ar) {
		try {
			municipioServiceIMPL.removeById(id_municipio);
			ar.addFlashAttribute("ok", "Municipio eliminado");
		} catch (Exception e) {
			ar.addFlashAttribute("error", "No se pudo eliminar el municipio");
		}
		return "redirect:/municipios";
	}
	
	private List<Municipio> getMunicipios() {
		return municipioServiceIMPL.list();
	}
}
