package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;

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
