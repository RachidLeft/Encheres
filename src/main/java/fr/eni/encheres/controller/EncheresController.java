package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Categorie;

@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class EncheresController {
	
	EnchereService enchereService;
	
	public EncheresController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}

	@GetMapping("/")
	public String afficherEncheres(Model model) { 
		
		List<Categorie> categories = enchereService.finAll();
		model.addAttribute("categories",categories);
		

		return "index";
	}
	
	
}
