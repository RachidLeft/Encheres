package fr.eni.encheres.bll;


import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;

import java.util.Optional;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurRepository utilisateurRepository;
	public UtilisateurServiceImpl ( UtilisateurRepository utilisateurRepository) {
	   this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public void ajouter(Utilisateur utilisateur) {
    	String passwordBcrypt = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMotDePasse());
    	utilisateur.setMotDePasse(passwordBcrypt);
    	utilisateurRepository.ajouter(utilisateur);
    }

    @Override
    public void update(Utilisateur utilisateur) {
    	utilisateurRepository.update(utilisateur);

    }

    @Override
    public Utilisateur findById(int id) {
        return utilisateurRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
    	utilisateurRepository.deleteById(id);
    }
    
    public void mettreAJourCredits(Utilisateur encherisseur, int montantEnchere, ArticleVendu articleVendu) {
        // Réduire les crédits de l'encherisseur actuel
        encherisseur.setCredit(encherisseur.getCredit() - montantEnchere);
        
        utilisateurRepository.update(encherisseur);  // Assurez-vous que vous avez une méthode pour mettre à jour l'utilisateur
        
        // Vérifier s'il y avait déjà une enchère précédente
        if (!articleVendu.getEnchere().isEmpty()) {
            Enchere ancienneEnchere = articleVendu.getEnchere().get(0);  // La plus haute enchère
            Utilisateur ancienEncherisseur = ancienneEnchere.getEncherisseur();
            
            // Ajouter les crédits à l'ancien enchérisseur
            ancienEncherisseur.setCredit(ancienEncherisseur.getCredit() + ancienneEnchere.getMontantEnchere());
            utilisateurRepository.update(ancienEncherisseur);
        }
    }

}
