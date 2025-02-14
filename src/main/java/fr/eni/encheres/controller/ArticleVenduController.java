package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.ArticleVendu;

@Controller
@RequestMapping("/articleVendu")
@SessionAttributes({"utilisateurEnSession"})
public class ArticleVenduController {
	
	private final ArticleVenduService articleVenduService;
	
	public ArticleVenduController (ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;
	}
	
	 @GetMapping("/detail/{id}")
	 public String detailArticleVendu(@PathVariable("id") int id, Model model) {
	        System.out.println("ID passé à la méthode: " + id);
	        ArticleVendu articleVendu = articleVenduService.findById(id);
	        model.addAttribute("articleVendu", articleVendu);
	        return "detailArticleVendu";
	    }
}
