package com.api.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.api.web.model.Persona;
import com.api.web.service.ConsumoMicroservice;

@Controller
public class RestTemplateController {
	
	@Autowired
    private ConsumoMicroservice consumoMicroservice;

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        List<Persona> personas = consumoMicroservice.obtenerListaDePersonas();
        model.addAttribute("persona", personas);
        return "lista";
    }
   
    @GetMapping("/usuarios/agregar")
    public String mostrarFormularioAgregar(Model model) {
        Persona persona = new Persona();
        model.addAttribute("persona", persona);
        return "crear_estudiante";
    }

    @PostMapping("/usuarios")
    public String agregarUsuario(@ModelAttribute("persona") Persona persona) {
        consumoMicroservice.agregarPersona(persona);
        return "redirect:/usuarios";
    }
    
    @GetMapping("/usuarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Persona persona = new Persona();
        persona.setId(id);
        if (id != null) 
        	 model.addAttribute("persona", persona);
 	        return "editar_estudiante";
    }

    @PatchMapping("/usuarios/{id}")
    public String editarPersona(@PathVariable Long id, @ModelAttribute Persona persona) {
        // Lógica para enviar la persona editada a través del servicio RestTemplate
        ResponseEntity<Persona> responseEntity = consumoMicroservice.editarPersona(id, persona);

        // Lógica para manejar la respuesta
        if (responseEntity.getStatusCode() == HttpStatus.ACCEPTED) {
        	return "redirect:/usuarios"; //
        } else {
            return "error-page";
        }
    }

    
    @PostMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        try {
            consumoMicroservice.eliminarPersona(id);
            return "redirect:/usuarios";
        } catch (Exception e) {
            // Manejar la excepción si la persona no se encuentra
            System.out.println(e);
            return "redirect:/error/403";
        }
    }
    
    @GetMapping("/error/403")
    public String handleAccessDenied() {
        return "redirect:/usuarios";
    }

   
}