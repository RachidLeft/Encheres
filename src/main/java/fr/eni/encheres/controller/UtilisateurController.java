package fr.eni.encheres.controller;


import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/utilisateurs")
@SessionAttributes ({"utilisateurEnSession"})
public class UtilisateurController {

    private UtilisateurService utilisateurService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        super();
        this.utilisateurService = utilisateurService;
    }

    @GetMapping("/ajout")
    public String ajouterUtilisateur(Model model){
        model.addAttribute("utilisateur", new Utilisateur());
        return "form-ajout-utilisateur";
    }

    @PostMapping("/ajout")
    public String ajouterUtilisateur(@Valid @ModelAttribute Utilisateur utilisateur, 
                                     @RequestParam("confirmMotDePasse") String confirmMotDePasse, 
                                     BindingResult resultat) {
        if (!utilisateur.getMotDePasse().equals(confirmMotDePasse)) {

            resultat.rejectValue("motDePasse", "error.motDePasse", "Les mots de passe ne correspondent pas.");
            return "form-ajout-utilisateur"; 
        }
        if (resultat.hasErrors()) {
            return "form-ajout-utilisateur"; 
        }
        utilisateurService.ajouter(utilisateur); 
        return "redirect:/";
    }




    @GetMapping("/detail/{id}")
    public String  detailUtilisateur(@PathVariable("id")int id, Model model){
        System.out.println("ID passé à la méthode: " + id);

    Utilisateur utilisateur = utilisateurService.findById(id);
    model.addAttribute("utilisateur",utilisateur);
        return "detailUtilisateur";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable("id") int id) {
    	utilisateurService.deleteById(id);
        return "redirect:/";
    }
    @GetMapping("/modif/{id}")
    public String modifUtilisateur(@PathVariable("id") int id, Model model) {
        model.addAttribute("utilisateur",utilisateurService.findById(id));
        return "form-modif-utilisateur";
    }
    @PostMapping("/modif/{id}")
    public String modifUtilisateur(@PathVariable("id") int id,@ModelAttribute Utilisateur utilisateur) {
    	utilisateur.setNoUtilisateur(id);
    	utilisateurService.update(utilisateur);
        return "detailUtilisateur";
    }

}
