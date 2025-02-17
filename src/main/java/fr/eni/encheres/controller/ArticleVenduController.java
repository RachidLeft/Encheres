package fr.eni.encheres.controller;


import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;

@SessionAttributes ({"utilisateurEnSession"})
@Controller
public class ArticleVenduController {
	
	ArticleVenduService articleVenduService;
	CategorieService categorieService;
	
	public ArticleVenduController(ArticleVenduService articleVenduService, CategorieService categorieService) {
		this.articleVenduService = articleVenduService;
		this.categorieService = categorieService;
	}
	
	 @GetMapping("/articleVendu/detail/{id}")
	 public String detailArticleVendu(@PathVariable("id") int id, Model model) {
	        System.out.println("ID passé à la méthode: " + id);
	        ArticleVendu articleVendu = articleVenduService.findById(id);
	        model.addAttribute("articleVendu", articleVendu);
	        return "detailArticleVendu";
	    }
	
	@GetMapping("/encheres/nouvellevente")
	public String afficherCreationNouvelleVente(Model model, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateur ) {
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
	
	
	@PostMapping("/encheres/nouvellevente")
	public String creerNouvelleVente(@Valid @ModelAttribute("article") ArticleVendu article, 
									BindingResult bindingResult,
									Model model,
									@ModelAttribute("utilisateurEnSession") Utilisateur utilisateur) {
		System.out.println("Article à vendre " + article);
		
		if (bindingResult.hasErrors()) {
			List<Categorie> categories = categorieService.findAll();
			model.addAttribute("categories",categories);
			return "view-nouvelle-vente";
		} else {			
			try {
				article.setVend(utilisateur);
				this.articleVenduService.creerArticleAVendre(article);
				return "redirect:/encheres";
			} catch (BusinessException e) {
				e.printStackTrace();
				e.getClesErreurs().forEach(cle -> {
					ObjectError error = new ObjectError("globalError", cle);
					bindingResult.addError(error);
				});
				List<Categorie> categories = categorieService.findAll();
				model.addAttribute("categories",categories);
				return "view-nouvelle-vente";
			}
			
		}
		
	
	
	
	}	
}
	
