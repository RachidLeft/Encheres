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
	
	@GetMapping("/creer")
	public String creerArticle(Model model /*@ModelAttribute("utilisateurEnSession") Utilisateur utilsateur*/) {
		ArticleVendu article = new ArticleVendu();
		model.addAttribute("article", article);
		
		//Affiche liste categorie 
		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories",categories);
		
		//Permet de recuperer l'adresse de l'utilisateur
		Retrait retrait = new Retrait();
		/*article.getLieuRetrait().setRue(utilsateur.getRue());
		article.getLieuRetrait().setCodePostal(utilsateur.getCodePostal());
		article.getLieuRetrait().setVille(utilsateur.getVille());*/
		
		
		
		
		return "view-nouvelle-vente";
	}
	
}
