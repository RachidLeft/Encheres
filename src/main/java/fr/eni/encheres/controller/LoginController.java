package fr.eni.encheres.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.contexte.ContexteService;
import fr.eni.encheres.bo.Utilisateur;


/**
 * Contrôleur gérant les opérations de connexion et de gestion de session utilisateur.
 *
 * <p>Ce contrôleur permet aux utilisateurs de se connecter et de maintenir leur session
 * active. Il utilise un service de contexte pour charger les informations de l'utilisateur
 * et les stocker dans un attribut de session.</p>
 */
@Controller
@SessionAttributes ({"utilisateurEnSession"})
public class LoginController {
	

	private ContexteService contexteService;

    public LoginController(ContexteService contexteService) {
        this.contexteService = contexteService;
    }
    
    /**
     * Affiche la page de connexion.
     *
     * @return le nom de la vue "login".
     */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	 /**
     * Gère la connexion de l'utilisateur et met à jour les informations de session.
     *
     * @param utilisateurSession l'objet utilisateur en session à mettre à jour.
     * @param principal l'objet contenant les informations d'authentification de l'utilisateur.
     * @return une redirection vers la page des enchères.
     */
	@GetMapping("/session")
	public String connexion(@ModelAttribute("utilisateurEnSession") Utilisateur utilisateurSession, Principal principal) {

		Utilisateur utilisateur = this.contexteService.charger(principal.getName());
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
		return "redirect:/encheres";

	}
	
	/**
     * Crée un nouvel objet utilisateur pour la session.
     * @return un nouvel objet {@code Utilisateur}.
     */
	@ModelAttribute("utilisateurEnSession")
	 public Utilisateur addMembreEnSession() {
	  return new Utilisateur();
	 }

}
