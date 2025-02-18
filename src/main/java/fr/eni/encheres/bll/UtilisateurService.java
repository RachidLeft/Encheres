package fr.eni.encheres.bll;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurService {

    void ajouter(Utilisateur utilisateur);

    void update(Utilisateur utilisateur);

    Utilisateur findById(int id);

    List<Utilisateur> findAll = null;

    void deleteById(int id);

    // Déclaration de la méthode pour mettre à jour les crédits
    void mettreAJourCredits(Utilisateur encherisseur, int montantEnchere, ArticleVendu articleVendu);
}
