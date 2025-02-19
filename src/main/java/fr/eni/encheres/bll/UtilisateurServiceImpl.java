package fr.eni.encheres.bll;



import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;
import fr.eni.encheres.exception.BusinessException;

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
    public void ajouter(Utilisateur utilisateur) throws BusinessException {
    	String passwordBcrypt = PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(utilisateur.getMotDePasse());
    	utilisateur.setMotDePasse(passwordBcrypt);
    	
    	BusinessException be = new BusinessException();
    	boolean valide = validerEmailInexistant(utilisateur.getEmail(), be);
    			valide &= validerPseudoInexistant(utilisateur.getPseudo(), be);
    			
    	if (valide) {
    		utilisateurRepository.ajouter(utilisateur);
		} else {
			throw be;
		}
    	
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
    
   

    
    private boolean validerEmailInexistant(String email, BusinessException be) {
    	boolean valide = true;
    	
    	int nbEmail = this.utilisateurRepository.countByEmail(email);
    	
    	if (nbEmail == 1) {
			valide = false;
			be.addCleErreur("validation.utilisateur.email.unique");
		}
    	return valide;
    }
    
    private boolean validerPseudoInexistant(String pseudo, BusinessException be) {
    	boolean valide = true;
    	
    	int nbPseudo = this.utilisateurRepository.countByPseudo(pseudo);
    	
    	if (nbPseudo == 1) {
			valide = false;
			be.addCleErreur("validation.utilisateur.pseudo.unique");
		}
    	return valide;
    }
}
