package fr.eni.encheres.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bo.ArticleVendu;

@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class EncheresController {
	
	private EnchereService enchereService;
	
	public EncheresController(EnchereService enchereService) {
		this.enchereService = enchereService;
	}
	
	
	/*@GetMapping("/encheres")
	public String afficherEncheres(Model model) {
		
			
		return "index";
	}*/
	
	
		

	
	
	
}
