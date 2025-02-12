package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Categorie;

public interface EnchereService {
	
	List<Categorie> finAll();
	
	Categorie read(int idCategorie);
	
}
