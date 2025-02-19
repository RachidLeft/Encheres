package fr.eni.encheres.controller;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes({"utilisateurEnSession"})
public class UtilisateurController {

    private final UtilisateurService utilisateurService;


    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;

    }

    @GetMapping("/ajout")
    public String ajouterUtilisateur(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "form-ajout-utilisateur";
    }

    @PostMapping("/ajout")
    public String ajouterUtilisateur(@Valid @ModelAttribute Utilisateur utilisateur,  
                                     BindingResult resultat, 
                                     @RequestParam("confirmMotDePasse") String confirmMotDePasse) {

 
        // Vérification de la confirmation du mot de passe
        if (!utilisateur.getMotDePasse().equals(confirmMotDePasse)) {
            resultat.rejectValue("motDePasse", "error.motDePasse", "Les mots de passe ne correspondent pas.");
            return "form-ajout-utilisateur"; 
        }

        if (!resultat.hasErrors()) {
        	try {
    			utilisateurService.ajouter(utilisateur);
    			return "redirect:/";
    		} catch (BusinessException e) {
    			e.printStackTrace();
    			e.getClesErreurs().forEach(cle->{
					ObjectError error = new ObjectError("globalError", cle);
					resultat.addError(error);
				});
    			return "form-ajout-utilisateur";
    		}
        }else {
			return "form-ajout-utilisateur";
		  }
     
    }
    @GetMapping("/modif-mdp/{id}")
    public String modifMdp(@PathVariable("id") int id, Model model) {
        model.addAttribute("utilisateur", utilisateurService.findById(id));
        return "form-modif-mdp";
    }

    @PostMapping("/modif-mdp/{id}")
    public String modifMdp(@PathVariable("id") int id, @ModelAttribute Utilisateur utilisateur,
                           BindingResult resultat,
                           @RequestParam("confirmMotDePasse") String confirmMotDePasse) {
        

        // Vérification de la confirmation du mot de passe
        if (!utilisateur.getMotDePasse().equals(confirmMotDePasse)) {
            resultat.rejectValue("motDePasse", "error.motDePasse", "Les mots de passe ne correspondent pas.");
            return "form-modif-mdp"; 
        }

        // Vérification de la validité du mot de passe
        String motDePasse = utilisateur.getMotDePasse();
        if (!isMotDePasseValide(motDePasse)) {
            resultat.rejectValue("motDePasse", "error.motDePasse", "Le mot de passe doit contenir au moins 8 caractères, une majuscule, un chiffre et un caractère spécial.");
            return "form-modif-mdp"; 
        }

        utilisateur.setMotDePasse(confirmMotDePasse);
        utilisateur.setNoUtilisateur(id);
        utilisateurService.updateMdp(utilisateur);
        return "redirect:/utilisateurs/detail/" + utilisateur.getNoUtilisateur();
    }

    // Méthode pour vérifier si le mot de passe est valide
    private boolean isMotDePasseValide(String motDePasse) {
        if (motDePasse.length() < 8) return false;
        if (!motDePasse.matches(".*[A-Z].*")) return false; // Vérifie la présence d'une majuscule
        if (!motDePasse.matches(".*[0-9].*")) return false; // Vérifie la présence d'un chiffre
        if (!motDePasse.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) return false; // Vérifie la présence d'un caractère spécial
        return true;
    }


    @GetMapping("/detail/{id}")
    public String detailUtilisateur(@PathVariable("id") int id, Model model) {
        System.out.println("ID passé à la méthode: " + id);
        Utilisateur utilisateur = utilisateurService.findById(id);
        model.addAttribute("utilisateur", utilisateur);
        return "detailUtilisateur";
    }

    @GetMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable("id") int id) {
        utilisateurService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/modif/{id}")
    public String modifUtilisateur(@PathVariable("id") int id, Model model) {
        model.addAttribute("utilisateur", utilisateurService.findById(id));
        return "form-modif-utilisateur";
    }

    @PostMapping("/modif/{id}")
    public String modifUtilisateur(@PathVariable("id") int id, @ModelAttribute Utilisateur utilisateur) {
        utilisateur.setNoUtilisateur(id);
        utilisateurService.update(utilisateur);
        return "detailUtilisateur";
    }
    
    
}
