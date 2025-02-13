package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import fr.eni.encheres.bll.EnchereService;

@Controller
public class EncheresController {
	
	private EnchereService enchereService;
	
	public EncheresController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}
	
	
	@GetMapping("/encheres")
	public String afficherEncheres() {
		
			
		return "index";
	}
	
	
	
}
