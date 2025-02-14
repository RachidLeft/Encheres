package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class ArticleVenduController {
	
	ArticleVenduService articleVenduService;
	CategorieService categorieService;
	
	public ArticleVenduController(ArticleVenduService articleVenduService, CategorieService categorieService) {
		this.articleVenduService = articleVenduService;
		this.categorieService = categorieService;
	}
	
	@GetMapping("/encheres/nouvellevente")
	public String creerArticle(Model model, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateur ) {
		ArticleVendu article = new ArticleVendu();
		
		
		//Affiche liste categorie 
		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories",categories);
		
		//TODO récupérer l'adresse de l'utilisateur
		Retrait retrait = new Retrait();
		article.setLieuRetrait(retrait);
		article.getLieuRetrait().setRue(utilisateur.getRue());
		article.getLieuRetrait().setCodePostal(utilisateur.getCodePostal());
		article.getLieuRetrait().setVille(utilisateur.getVille());
		
		model.addAttribute("article", article);
		return "view-nouvelle-vente";
	}
	
}
