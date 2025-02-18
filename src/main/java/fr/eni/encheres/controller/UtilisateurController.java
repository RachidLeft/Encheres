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
                                     BindingResult resultat, // 🔴 Doit être immédiatement après @Valid !
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
