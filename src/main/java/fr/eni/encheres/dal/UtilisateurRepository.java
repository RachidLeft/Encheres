package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository {

    public void ajouter(Utilisateur utilisateur);


    public List<Utilisateur> findAll();

    public Optional<Utilisateur> findById(int id);

    public void update(Utilisateur utilisateur);

    public void deleteById(int id);
    
    Utilisateur read(String pseudo);

}
