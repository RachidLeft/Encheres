package fr.eni.encheres.bll.contexte;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;

@Service
public class ContexteServiceImpl implements ContexteService{

	UtilisateurRepository utilisateurRepository;
	
	
	public ContexteServiceImpl(UtilisateurRepository utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}


	@Override
	public Utilisateur charger(String pseudo) {
		return this.utilisateurRepository.read(pseudo);
	}
	
}
