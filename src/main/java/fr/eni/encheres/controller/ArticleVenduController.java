package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bo.ArticleVendu;

@Controller
@SessionAttributes({"utilisateurEnSession"})
@RequestMapping("/encheres")
public class ArticleVenduController {
	
	private ArticleVenduService articleVenduService;

	public ArticleVenduController(ArticleVenduService articleVenduService) {
		this.articleVenduService = articleVenduService;
	}
	
	@GetMapping
	public String afficherArticleVendu(Model model) {
		
		List<ArticleVendu> listeArticleVendu = this.articleVenduService.consulterLesArticles();
		model.addAttribute("article_vendu", listeArticleVendu);
		
		return "index";
		
	}

}
