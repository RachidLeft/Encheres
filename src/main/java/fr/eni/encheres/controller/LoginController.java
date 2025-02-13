package fr.eni.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ContexteService;
import fr.eni.encheres.bo.Utilisateur;



@Controller
@SessionAttributes ({"utilisateurEnSession"})
public class LoginController {
	

	private ContexteService contexteService;

    public LoginController(ContexteService contexteService) {
        this.contexteService = contexteService;
    }
	
	@GetMapping({"/login", "/"})
	public String login() {
		return "login";
	}
	
	@GetMapping("/session")
	public String connexion(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurSession, Principal principal) {

		Utilisateur utilisateur = this.contexteService.charger(principal.getName());
System.out.println("aaaaaaaaaaaaaaaaaaa"+utilisateur);
		if (utilisateur != null) {
			utilisateurSession.setNoUtilisateur(utilisateur.getNoUtilisateur());
			utilisateurSession.setPseudo(utilisateur.getPseudo());
			utilisateurSession.setNom(utilisateur.getNom());
			utilisateurSession.setPrenom(utilisateur.getPrenom());
			utilisateurSession.setEmail(utilisateur.getEmail());
			utilisateurSession.setCredit(utilisateur.getCredit());
			utilisateurSession.setTelephone(utilisateur.getTelephone());
			utilisateurSession.setRue(utilisateur.getRue());
			utilisateurSession.setCodePostal(utilisateur.getCodePostal());
			utilisateurSession.setVille(utilisateur.getVille());
			utilisateurSession.setAdministrateur(utilisateur.isAdministrateur());
		} else {
			utilisateurSession.setNoUtilisateur(0);
			utilisateurSession.setPseudo(null);
			utilisateurSession.setNom(null);
			utilisateurSession.setPrenom(null);
			utilisateurSession.setEmail(null);
			utilisateurSession.setCredit(0);
			utilisateurSession.setTelephone(null);
			utilisateurSession.setRue(null);
			utilisateurSession.setCodePostal(null);
			utilisateurSession.setVille(null);
			utilisateurSession.setAdministrateur(false);
			
		}
		System.out.println("tessssssssssssss"+utilisateurSession);
		return "redirect:/encheres";

	}
	
	
	@ModelAttribute("utilisateurEnSession")
	 public Utilisateur addMembreEnSession() {
	  return new Utilisateur();
	 }

}
