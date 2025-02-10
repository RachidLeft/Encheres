package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {

    void ajouter(Utilisateur utilisateur);

    void update(Utilisateur utilisateur);

    Utilisateur findById(int id);

    List<Utilisateur> findAll;

    void deleteById(int id);

}
