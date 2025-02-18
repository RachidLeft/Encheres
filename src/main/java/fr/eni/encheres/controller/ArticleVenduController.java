package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import fr.eni.encheres.bll.ArticleVenduService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/articleVendu")
@SessionAttributes({"utilisateurEnSession"})
public class ArticleVenduController {
	
	private final ArticleVenduService articleVenduService;
	private final EnchereService enchereService ;
	private final UtilisateurService utilisateurService ;
	
	public ArticleVenduController (ArticleVenduService articleVenduService, EnchereService enchereService,UtilisateurService utilisateurService ) {
		this.articleVenduService = articleVenduService;
		this.enchereService  = enchereService ;
		this.utilisateurService  = utilisateurService ;
	}
	
	@GetMapping("/detail/{id}")
	public String detailArticleVendu(@PathVariable("id") int id, Model model, HttpSession session) {
	    System.out.println("ID passé à la méthode: " + id);

	    ArticleVendu articleVendu = articleVenduService.findById(id);

	    // Création d'une enchère vide pour le formulaire
	    Enchere enchere = new Enchere();

	    // Pré-remplissage du montant de l'enchère avec la meilleure offre ou la mise à prix
	    if (articleVendu.getEnchere() != null && !articleVendu.getEnchere().isEmpty()
	            && articleVendu.getEnchere().get(0).getMontantEnchere() > 0) {
	        enchere.setMontantEnchere(articleVendu.getEnchere().get(0).getMontantEnchere());
	    } else {
	        enchere.setMontantEnchere(articleVendu.getMiseAPrix());
	    }

	    // 📌 Formatage de la date de fin d'enchère
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'à' HH'h'mm", Locale.FRENCH);
	    String dateFinFormatee = articleVendu.getDateFinEncheres().format(formatter);

	    // 📌 Ajouter la date actuelle (pour comparaison uniquement, pas pour affichage)
	    LocalDateTime currentDate = LocalDateTime.now();

	    // Comparer la date actuelle avec la date de fin de l'enchère
	    boolean isEnchereEnCours = currentDate.isBefore(articleVendu.getDateFinEncheres());

	    // Récupérer l'utilisateur connecté depuis la session
	    Utilisateur encherisseur = (Utilisateur) session.getAttribute("utilisateurEnSession");

	    // Vérifier si l'utilisateur connecté est le gagnant
	    boolean isGagnant = false;
	    if (articleVendu.getEnchere() != null && !articleVendu.getEnchere().isEmpty()) {
	        Enchere meilleureEnchere = articleVendu.getEnchere().get(0);
	        // Vérifier si l'utilisateur connecté est celui qui a fait la meilleure enchère
	        if (encherisseur != null && meilleureEnchere.getEncherisseur().getPseudo().equals(encherisseur.getPseudo())) {
	            isGagnant = true;
	        }
	    }

	    // Ajouter les attributs au modèle
	    model.addAttribute("articleVendu", articleVendu);
	    model.addAttribute("enchere", enchere);
	    model.addAttribute("dateFinFormatee", dateFinFormatee);
	    model.addAttribute("isEnchereEnCours", isEnchereEnCours);
	    model.addAttribute("isGagnant", isGagnant);  // Indiquer si l'utilisateur connecté est le gagnant

	    return "detailArticleVendu";
	}





	@PostMapping("/detail/{id}")
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



}
