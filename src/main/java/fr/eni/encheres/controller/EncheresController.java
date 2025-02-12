package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.Categorie;

@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class EncheresController {
	
	CategorieService categorieService;

	public EncheresController(CategorieService categorieService) {
		this.categorieService = categorieService;
	}



	@GetMapping("/")
	public String afficherEncheres(Model model) { 
		
		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories",categories);
		

		return "index";
	}
	
	
}
