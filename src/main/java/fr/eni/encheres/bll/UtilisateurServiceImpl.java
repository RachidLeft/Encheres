package fr.eni.encheres.bll;


import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;

import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurRepository utilisateurRepository;
	public UtilisateurServiceImpl ( UtilisateurRepository utilisateurRepository) {
	   this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public void ajouter(Utilisateur utilisateur) {
    	utilisateurRepository.ajouter(utilisateur);
    }

    @Override
    public void update(Utilisateur utilisateur) {

    }

    @Override
    public Utilisateur findById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}
