package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exception.BusinessException;

import java.util.List;

public interface UtilisateurService {

    void ajouter(Utilisateur utilisateur) throws BusinessException;

    void update(Utilisateur utilisateur);

    Utilisateur findById(int id);

    List<Utilisateur> findAll = null;

    void deleteById(int id);

	void updateMdp(Utilisateur utilisateur);

  
}
