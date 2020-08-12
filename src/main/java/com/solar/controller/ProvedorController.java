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

import com.solar.model.Provedor;
import com.solar.service.ProvedorServiceIMPL;

@Controller
@RequestMapping("/origenes")
public class ProvedorController {

	@Autowired
	private ProvedorServiceIMPL provedorServiceIMPL;
	
	@PostMapping
	public String saveOrUpdateOrigen(@ModelAttribute Provedor origen, RedirectAttributes ra) {
		try {
			if(origen.getId_provedor()!=null) {
				Provedor origenExist = provedorServiceIMPL.findById(origen.getId_provedor());
				origenExist.setNombre(origen.getNombre());
				provedorServiceIMPL.save(origenExist);
			}else {
				provedorServiceIMPL.save(origen);
			}
			
			ra.addFlashAttribute("ok", "Origen registrado");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ra.addFlashAttribute("error", "No se pudo registrar el origen");
		}
		return "redirect:/origenes";
	}
	
	@GetMapping
	public String listProvedores(Model model) {
		model.addAttribute("origen", new Provedor());
		model.addAttribute("origenes", this.getOrigenes());
		return "origenes/list";
	}

	@GetMapping("/edit/{id_origen}")
	public String editarMunicipio(Model model, @PathVariable(name = "id_origen") Long id_origen) {
		try {
			Provedor origen = provedorServiceIMPL.findById(id_origen);
			model.addAttribute("origen", origen);
			model.addAttribute("origenes", this.getOrigenes());
		} catch (Exception e) {
			model.addAttribute("origen", new Provedor());
			model.addAttribute("origenes", this.getOrigenes());
		}
		return "origenes/list";
	}
	
	@GetMapping("/remove/{id_origen}")
	public String eliminarMunicipio(Model model, @PathVariable(name = "id_origen") Long id_origen,
			RedirectAttributes ar) {
		try {
			provedorServiceIMPL.removeById(id_origen);
			ar.addFlashAttribute("ok", "Origen eliminado");
		} catch (Exception e) {
			ar.addFlashAttribute("error", "No se pudo eliminar el origen");
		}
		return "redirect:/origenes";
	}
	
	private List<Provedor> getOrigenes() {
		// TODO Auto-generated method stub
		return provedorServiceIMPL.list();
	}
	
}
