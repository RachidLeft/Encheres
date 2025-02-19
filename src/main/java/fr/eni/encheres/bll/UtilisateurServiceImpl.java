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


	@Override
	public void updateMdp(Utilisateur utilisateur) {
		String passwordBcrypt = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMotDePasse());
    	utilisateur.setMotDePasse(passwordBcrypt);
    	utilisateurRepository.updateMdp(utilisateur);
	}
    
   

}
