package fr.eni.encheres.controller;


import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    @GetMapping("/ajout")
    public String ajouterUtilisateur(Model model){
        model.addAttribute("utilisateur", new Utilisateur)
        return "form-ajout-utilisateur";
    }

    @PostMapping("/ajout")
    public String ajouterUtilisateur(@Valid @ModelAttribute Utilisateur utilisateur, BindingResult resultat) {
        if(resultat.hasErrors()) {
            return "form-ajout-utilisateur";
        }
        clientService.ajouter(client);
        System.out.println(client);
        return "redirect:/utlisateurs";
    }

}
