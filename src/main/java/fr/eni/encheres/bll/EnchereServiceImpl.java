package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.EnchereDAO;

@Service
public class EnchereServiceImpl implements EnchereService {

    private final EnchereDAO enchereDAO;
    private final ArticleVenduService articleVenduService;
    private final UtilisateurService utilisateurService;

    public EnchereServiceImpl(EnchereDAO enchereDAO, 
                              ArticleVenduService articleVenduService, 
                              UtilisateurService utilisateurService) {
        this.enchereDAO = enchereDAO;
        this.articleVenduService = articleVenduService;
        this.utilisateurService = utilisateurService;
    }

    @Override
    public List<Enchere> consulterEnchere() {
        // TODO : implémenter la consultation des enchères si nécessaire
        return null;
    }

    @Override
    public ArticleVendu consulterArticleParId(int noArticle) {
        // TODO : implémenter la récupération d'un article par son identifiant si nécessaire
        return null;
    }

    @Override
    public void ajouter(Enchere enchere) {
        // Récupérer l'article complet afin d'obtenir la liste des enchères existantes
        ArticleVendu article = articleVenduService.findById(enchere.getArticle().getNoArticle());
        
        int montant = enchere.getMontantEnchere();
        Utilisateur encherisseur = enchere.getEncherisseur();

        // Vérifier que l'encherisseur a suffisamment de crédit
        if (encherisseur.getCredit() < montant) {
            throw new IllegalStateException("Crédit insuffisant pour placer cette enchère.");
        }
        
        // Sauvegarder l'enchère en base de données
        enchereDAO.ajouter(enchere);
    }
}
