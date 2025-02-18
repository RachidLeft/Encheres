package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurRepository {

    public void ajouter(Utilisateur utilisateur);

    public List<Utilisateur> findAll();

    public Utilisateur findById(int id);

    public void update(Utilisateur utilisateur);

    public void deleteById(int id);
    
    Utilisateur read(String pseudo);
    
    int countByEmail(String email);
    
    int countByPseudo(String pseudo);

}
