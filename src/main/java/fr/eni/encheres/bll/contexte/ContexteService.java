package fr.eni.encheres.bll.contexte;

import fr.eni.encheres.bo.Utilisateur;

public interface ContexteService {
	
	Utilisateur charger(String psuedo);
}
