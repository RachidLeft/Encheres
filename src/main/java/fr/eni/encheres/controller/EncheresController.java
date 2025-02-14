package fr.eni.encheres.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;


@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class EncheresController {
	
	private ArticleVenduService articleVenduService;
	private CategorieService categorieService;

	public EncheresController(CategorieService categorieService, ArticleVenduService articleVenduService) {
		this.categorieService = categorieService;
		this.articleVenduService = articleVenduService;
	}


	@GetMapping("/encheres")	 
	public String afficherEncheres(Model model) {
		
		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories",categories);
		
		List<ArticleVendu> listeArticleVendu = this.articleVenduService.consulterLesArticles();		
		model.addAttribute("article_vendu", listeArticleVendu);
 
		return "index";
	}
	
	@PostMapping("/encheres/filtre")
	public String filtreEncheresCategorie(@RequestParam("categorie") int idCategorie, @RequestParam("motCle") String nomArticle, Model model) {
		
		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories",categories);
		
		List<ArticleVendu> filtreListArticleVendu = this.articleVenduService.consulterLesArticlesNomEtCategorie(nomArticle, idCategorie);
		model.addAttribute("article_vendu", filtreListArticleVendu);
		
		return "index";
		
	}
	
	
	
}
