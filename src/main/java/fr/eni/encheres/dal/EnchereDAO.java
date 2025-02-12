package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {
	
	void creer(Enchere enchere);
	
	List<Enchere> findAll();
	
	Enchere read();

}
