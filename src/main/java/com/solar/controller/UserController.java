package com.solar.controller;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.solar.model.Estacion;
import com.solar.model.IsantandService;
import com.solar.model.Municipio;
import com.solar.model.Provedor;
import com.solar.model.RadiacionInfo;
import com.solar.service.EstacionServiceIMPL;
import com.solar.service.MunicipioServiceIMPL;
import com.solar.service.ProvedorServiceIMPL;
import com.solar.service.RadiacionServiceIMPL;
import com.solar.service.UserServiceImpl;
import com.solar.task.WundergroundService;


@Controller
public class UserController {

	@Autowired
	private EntityManager em;
	

	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private EstacionServiceIMPL estacionServiceIMPL;
	
	@Autowired
	private MunicipioServiceIMPL municipioServiceIMPL;
	
	@Autowired
	private ProvedorServiceIMPL provedorServiceIMPL;
	
	@Autowired
	private RadiacionServiceIMPL radiacionServiceIMPL;
	
	@GetMapping("/login")
	public String login() {
		return "admin/login";
	}
	
	@PostMapping("/import")
	public String importFile(@RequestParam(name = "file") MultipartFile file, @RequestParam(name = "estacion") String estacion,
			RedirectAttributes ra, @RequestParam(name = "format") String format) {
		
		try {
			Estacion existEstacion = estacionServiceIMPL.findByNombre_estacion(estacion);
			
			if(existEstacion != null) {
				if (userServiceImpl.saveRadiacion(file, existEstacion, format)) {
					ra.addFlashAttribute("ok", "Datos importados correctamente");
					
				}else {
					ra.addFlashAttribute("error", "Ocurrio un error al guardar, asegurese de tener un formato de fecha valido e intente"
							+ " nuevamente.");
				}
				
			}else {
				ra.addFlashAttribute("error", "Estación no valida.");
			}
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ra.addFlashAttribute("error", e.getMessage());
		}
		
		return "redirect:/";
	}
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("estaciones", this.getEstaciones());
		model.addAttribute("municipios", this.getMunicipios());
		model.addAttribute("origenes", this.getProvedores());
		
		String uri_30 = "https://api.weather.com/v2/pws/observations/all/1day?stationId=ISANTAND30&format=json&units=e&apiKey=b3bc2e2c48a644b6bc2e2c48a614b691";
		String uri_31 = "https://api.weather.com/v2/pws/observations/all/1day?stationId=ISANTAND31&format=json&units=e&apiKey=b3bc2e2c48a644b6bc2e2c48a614b691";
		RestTemplate restTemplate = new RestTemplate();
		
		HttpStatus status_30 = restTemplate.exchange(uri_30, HttpMethod.GET,null, WundergroundService.class).getStatusCode();
		HttpStatus status_31 = restTemplate.exchange(uri_31, HttpMethod.GET,null, WundergroundService.class).getStatusCode();
		
		model.addAttribute("status_is30", (status_30.equals(HttpStatus.OK)) ? "Online":"Offline");
		model.addAttribute("status_is31",  (status_31.equals(HttpStatus.OK)) ? "Online":"Offline");
		model.addAttribute("registros",  radiacionServiceIMPL.getRegistros());
		
		return "admin/inicio";
	}
	
	@GetMapping("/download")
	public void  downloadData(HttpServletResponse response, @RequestParam(name = "estacion") String estacion) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		System.out.println("Estacion -> " + estacion);
		String filename = "radiación solar - SPEC.csv";
		response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        
        ColumnPositionMappingStrategy<RadiacionInfo> mappingStrategy=  new ColumnPositionMappingStrategy<RadiacionInfo>(); 
        mappingStrategy.setType(RadiacionInfo.class); 
        
        String[] columns = new String[]  
                {"id","estacion","municipio","origen","lat","lon","radiacion","fecha"}; 
        mappingStrategy.setColumnMapping(columns);
        
      
     // Createing StatefulBeanToCsv object 
        StatefulBeanToCsvBuilder<RadiacionInfo> builder = new StatefulBeanToCsvBuilder<RadiacionInfo>(response.getWriter()); 
        
       StatefulBeanToCsv<RadiacionInfo> beanWriter = builder.withMappingStrategy(mappingStrategy)
    		   .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
               .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
               .withOrderedResults(false)
               .build();
        // Write list to StatefulBeanToCsv object 
       Query query = em.createNativeQuery("select * from GET_RADIACION_FROM_ESTACION(:estacion);", RadiacionInfo.class);
       if(estacion.equals("all_stations")) {
    	   query = em.createNativeQuery("select * from radiacion;", RadiacionInfo.class);
       }else {
    	   query.setParameter("estacion", estacion);
       }
       @SuppressWarnings("unchecked")
       List<RadiacionInfo> radiacion = (List<RadiacionInfo>) query.getResultList();
       beanWriter.write(radiacion);
        // closing the writer object 
        response.getWriter().close();
	}
	
	
	
	
	
	
	private List<Estacion> getEstaciones() {
		return estacionServiceIMPL.list();
	}
	
	private List<Municipio> getMunicipios() {
		return municipioServiceIMPL.list();
	}
	
	private List<Provedor> getProvedores() {
		return provedorServiceIMPL.list();
	}
	
	

	


	
}
