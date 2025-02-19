
package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

import jakarta.servlet.http.HttpSession;

import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;


@Controller

@SessionAttributes({"utilisateurEnSession"})
public class ArticleVenduController {
	
	private final ArticleVenduService articleVenduService;
	private final EnchereService enchereService ;
	private final UtilisateurService utilisateurService ;
	private CategorieService categorieService;
	
	public ArticleVenduController (ArticleVenduService articleVenduService, EnchereService enchereService,UtilisateurService utilisateurService,CategorieService categorieService ) {
		this.articleVenduService = articleVenduService;
		this.enchereService  = enchereService ;
		this.utilisateurService  = utilisateurService ;
		this.categorieService = categorieService;
	}
	

	@GetMapping("/articleVendu/detail/{id}")
	public String detailArticleVendu(@PathVariable("id") int id, Model model) {
	    System.out.println("ID passé à la méthode: " + id);
	    ArticleVendu articleVendu = articleVenduService.findById(id);
	    
	    // Création d'une enchère vide pour le formulaire
	    Enchere enchere = new Enchere();
	    
	    // Pré-remplir le montant de l'enchère avec la meilleure proposition :
	    // On suppose que articleVendu.getEnchere() retourne une liste triée (la plus haute en première position)
	    if (articleVendu.getEnchere() != null && !articleVendu.getEnchere().isEmpty() 
	            && articleVendu.getEnchere().get(0).getMontantEnchere() > 0) {
	        enchere.setMontantEnchere(articleVendu.getEnchere().get(0).getMontantEnchere());
	    } else {
	        enchere.setMontantEnchere(articleVendu.getMiseAPrix());
	    }
	    
	    model.addAttribute("articleVendu", articleVendu);
	    model.addAttribute("enchere", enchere);
	    return "detailArticleVendu";
	}

	@PostMapping("/articleVendu/detail/{id}")
	public String ajouterEnchere(@PathVariable("id") int id,
	                             @ModelAttribute Enchere enchere,
	                             HttpSession session,
	                             Model model) {
	    // Récupérer l'article concerné
	    ArticleVendu articleVendu = articleVenduService.findById(id);
	    
	    
	    
	    // Vérifier si des enchères existent pour cet article
	    Enchere encherePlusHaute = null;
	    if (articleVendu.getEnchere() != null && !articleVendu.getEnchere().isEmpty()) {
	        // Si des enchères existent, récupérer la première, supposée être la plus haute
	        encherePlusHaute = articleVendu.getEnchere().get(0);
	    }
	 // Vérification du montant de l'enchère
	    int enchereMin = (encherePlusHaute != null) ? encherePlusHaute.getMontantEnchere() + 1 : articleVendu.getMiseAPrix();
	    if (enchere.getMontantEnchere() < enchereMin) {
	        model.addAttribute("errorMessage", "Votre enchère doit être supérieure à l'enchère actuelle.");
	        model.addAttribute("articleVendu", articleVendu);
	        model.addAttribute("enchere", enchere);
	        return "detailArticleVendu";
	    }
	    
	    // Si une enchère est présente, récupérer l'enchérisseur
	    Utilisateur ancienEncherisseur = new Utilisateur();
	    if (encherePlusHaute != null) {
	        ancienEncherisseur = encherePlusHaute.getEncherisseur();
	        System.out.println("ENCHERE HAUTE    " + encherePlusHaute.getMontantEnchere());
	        System.out.println("ANCIEN ENCHERISSEUR    " + ancienEncherisseur.getNoUtilisateur());
	    }
	
	    // Récupérer l'utilisateur connecté en session
	    Utilisateur encherisseur = (Utilisateur) session.getAttribute("utilisateurEnSession");
	    System.out.println("NOUVEAU ENCHERISSEUR    " + encherisseur);

	    // Vérifier si l'utilisateur a assez de crédits
	    if (encherisseur.getCredit() < enchere.getMontantEnchere()) {
	        // Crédit insuffisant
	        model.addAttribute("errorMessage", "Crédit insuffisant pour placer cette enchère.");
	        
	        // Préparer l'article et l'enchère à renvoyer dans le modèle
	        model.addAttribute("articleVendu", articleVendu);
	        model.addAttribute("enchere", enchere);  // Garder l'objet enchère pour pré-remplir le formulaire
	        
	        // Retourner à la page de détails avec un message d'erreur
	        return "detailArticleVendu";  // Renvoyer à la page avec le message d'erreur
	    }

	    // Si l'enchère est valide, mettez à jour les informations de l'enchère
	    enchere.setDateEnchere(LocalDateTime.now());
	    enchere.setArticle(articleVendu);
	    enchere.setEncherisseur(encherisseur);

	    // Sauvegarder l'enchère en base de données
	    enchereService.ajouter(enchere);
	    
	    // Mise à jour du prix de vente de l'article
	    articleVendu.setPrixVente(enchere.getMontantEnchere());
	    ArticleVenduService.update(articleVendu);
	    
	    // Mise à jour du crédit de l'ancien enchérisseur (si applicable)
	    if ( encherePlusHaute.getMontantEnchere() != 0) {
	    	System.out.println("ANCIEN ENCHERISSEUR AVANT UPDATE++++++++++++++++++++ " + ancienEncherisseur.getNoUtilisateur());
	        Utilisateur ancien = utilisateurService.findById(ancienEncherisseur.getNoUtilisateur());
	        
	        if (ancien != null) {
	            int ancienCredit = ancien.getCredit() + encherePlusHaute.getMontantEnchere();
	            ancien.setCredit(ancienCredit);
	            System.out.println("ANCIEN ENCHERISSEUR AVANT UPDATE " + ancien);
	            utilisateurService.update(ancien);
	        }
	    }
	    
	    // Mise à jour du crédit du nouvel enchérisseur
	    int nouveauCredit = encherisseur.getCredit() - enchere.getMontantEnchere();
	    encherisseur.setCredit(nouveauCredit);
	    System.out.println("ENCHERISSEUR AVANT UPDATE " + encherisseur);
	    utilisateurService.update(encherisseur);
	    
	    // Retourner à la page de détails après avoir ajouté l'enchère
	    return "redirect:/articleVendu/detail/" + id;
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
	public String creerNouvelleVente(@Valid @ModelAttribute("article") ArticleVendu articleVendu, 
									BindingResult bindingResult,
									Model model,
									@ModelAttribute("utilisateurEnSession") Utilisateur utilisateur) {
		
		 if (!bindingResult.hasErrors()) {
		        try {
		            articleVendu.setVend(utilisateur);
		            this.articleVenduService.creerArticleAVendre(articleVendu);
		            return "redirect:/encheres";
		        } catch (BusinessException e) {
		            e.printStackTrace();
		            e.getClesErreurs().forEach(cle -> {
		                ObjectError error = new ObjectError("globalError", cle);
		                bindingResult.addError(error);
		            });
		            List<Categorie> categories = categorieService.findAll();
		            model.addAttribute("categories", categories);
		            return "view-nouvelle-vente";
		        }
		    } else {
		        List<Categorie> categories = categorieService.findAll();
		        model.addAttribute("categories", categories);
		        return "view-nouvelle-vente";
		    }
		
	}	
}
	
