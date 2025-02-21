package fr.eni.encheres.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

@SessionAttributes({ "utilisateurEnSession", "categorieSession" })
@Controller
public class EncheresController {

	private ArticleVenduService articleVenduService;
	private CategorieService categorieService;

	public EncheresController(CategorieService categorieService, ArticleVenduService articleVenduService) {
		this.categorieService = categorieService;
		this.articleVenduService = articleVenduService;
	}

	
	/*
	 * Cette méthode est prise en compte si aucun Utilisateur est connecté 
	 * Permet d'envoyer sur la page d'acceuil
	 * par défaut la liste des enchères en cours
	 * On peut filtrer par mot clé et/ou par catégorie
	 * avec le nomArticle, le prix en cours,
	 * la date de fin d'enchère et le nom du vendeur
	 */
	@GetMapping({"/encheres", "/"})
	public String afficherEncheres(@RequestParam(name="motCle", required = false) String nomArticle, @RequestParam(name="categorie", required = false) String idCategorie, 
			@ModelAttribute("utilisateurEnSession") Utilisateur utilisateur, Model model) {

		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories", categories);
		
		int idCategorieInt = 0;
		
		if (idCategorie != null && !idCategorie.isBlank()) {
			idCategorieInt = Integer.parseInt(idCategorie);
		}
		
		int noUtilisateur = 0;
		
		if (utilisateur != null) {
			noUtilisateur = utilisateur.getNoUtilisateur();
		}

		List<ArticleVendu> listeArticleVendu = this.articleVenduService.consulterLesArticles(nomArticle, idCategorieInt, noUtilisateur);
		model.addAttribute("article_vendu", listeArticleVendu);
		
		System.out.println("article" + listeArticleVendu);

		return "index";
	}

	/*
	 * Cette méthode est prise en compte si un Utilisateur est connecté 
	 * Permet d'envoyer sur la page d'acceuil
	 * par défaut la liste des enchères en cours
	 * On peut filtrer par mot clé et/ou par catégorie
	 * et/ou avec les checkbox Achat ou Vente
	 * avec le nomArticle, le prix en cours,
	 * la date de fin d'enchère et le nom du vendeur
	 */
	
	@PostMapping("/encheres/filtre")
	public String filtreEncheresCategorie(@RequestParam("categorie") String idCategorie,
			@RequestParam("motCle") String nomArticle, @ModelAttribute("utilisateurEnSession") Utilisateur utilisateur,
			@RequestParam(name="check", required = false) List<String> check, Model model) {

		List<Categorie> categories = categorieService.findAll();
		model.addAttribute("categories", categories);
		
		int idCategorieInt = 0;
		
		if (idCategorie != null && !idCategorie.isBlank()) {
			idCategorieInt = Integer.parseInt(idCategorie);
		}
		
		int noUtilisateur = 0;
		
		if (utilisateur != null) {
			noUtilisateur = utilisateur.getNoUtilisateur();
		}

		if (check == null) {

			List<ArticleVendu> filtreListArticleVendu = this.articleVenduService
					.consulterLesArticles(nomArticle, idCategorieInt, noUtilisateur);
			model.addAttribute("article_vendu", filtreListArticleVendu);
		}

		if (check != null) {

			List<ArticleVendu> filtreListAchatVente = this.articleVenduService
					.flitrerLesArticles(idCategorieInt, nomArticle, noUtilisateur, check);
			model.addAttribute("article_vendu", filtreListAchatVente);
		}
		
		return "index";

	}
	
	@ModelAttribute("categorieSession")
	public List<Categorie> chargerCategorieSession(){
		return this.categorieService.findAll();
	}
	
	@ModelAttribute("utilisateurEnSession")
	 public Utilisateur addMembreEnSession() {
	  return new Utilisateur();
	 }

}
