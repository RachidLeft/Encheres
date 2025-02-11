package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

public interface ContexteService {
	
	Utilisateur charger(String psuedo);
}
