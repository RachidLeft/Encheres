package fr.eni.encheres.bll;



import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.ArticleVendu;
import fr.eni.encheres.dal.ArticleVenduDAO;
import fr.eni.encheres.dal.CategorieDAO;

import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.exception.BusinessException;

/**
 * Implémentation du service pour la gestion des articles vendus.
 */
@Service
public class ArticleVenduServiceImpl implements ArticleVenduService {
	
	
	ArticleVenduDAO articleVenduDAO;
	CategorieDAO categorieDAO;
	RetraitDAO retraitDAO;
	
	public ArticleVenduServiceImpl(ArticleVenduDAO articleVenduDAO, CategorieDAO categorieDAO, RetraitDAO retraitDAO) {
		this.articleVenduDAO = articleVenduDAO;
		this.categorieDAO = categorieDAO;
		this.retraitDAO = retraitDAO;

	}
	
	  /**
     * Consulte les articles vendus en fonction de critères spécifiques.
     *
     * @param nomArticle le nom de l'article à rechercher.
     * @param idCategorie l'identifiant de la catégorie de l'article.
     * @param noUtilisateur le numéro de l'utilisateur associé à l'article.
     * @return une liste d'articles vendus correspondant aux critères.
     */

	@Override
	public List<ArticleVendu> consulterLesArticles(String nomArticle, int idCategorie, int noUtilisateur) {
		
		List<String> listeAchat = new ArrayList<String>();
		
		listeAchat.add("achat1");
		
		
		return this.articleVenduDAO.findAll(idCategorie, nomArticle, noUtilisateur, listeAchat);
	}


	 /**
     * Filtre les articles vendus en fonction de critères spécifiques.
     *
     * @param idCategorie l'identifiant de la catégorie de l'article.
     * @param nomArticle le nom de l'article à rechercher.
     * @param noUtilisateur le numéro de l'utilisateur associé à l'article.
     * @param check une liste de critères supplémentaires pour filtrer les articles.
     * @return une liste d'articles vendus correspondant aux critères.
     */
	@Override
	public List<ArticleVendu> flitrerLesArticles(int idCategorie, String nomArticle, int noUtilisateur, List<String> check) {
				
		return this.articleVenduDAO.findAll(idCategorie, nomArticle, noUtilisateur, check);
		
	}
	
	/**
     * Met à jour le prix de vente d'un article.
     *
     * @param articleVendu l'article dont le prix de vente doit être mis à jour.
     */
	@Override
	public void updatePrixDeVente(ArticleVendu articleVendu) {
		articleVenduDAO.updatePrixDeVente(articleVendu);
		
	}
	
	

	/**
     * Trouve un article vendu par son identifiant.
     *
     * @param id l'identifiant de l'article à rechercher.
     * @return l'article vendu correspondant à l'identifiant.
     */
	public ArticleVendu findById(int id) {
		return articleVenduDAO.findById(id);

	}
	/**
	 * Crée un nouvel article à vendre en validant ses dates d'enchères, puis
	 * le sauvegarde dans la base de données.
	 * <p>Cette méthode valide d'abord les dates de début et de fin des enchères
	 * pour l'article fourni. Si les dates sont valides, l'article est créé et
	 * stocké dans la base de données. Si la validation échoue, une
	 * {@code BusinessException} est levée avec les messages d'erreur pertinents.</p>
	 */
	@Override
	public void creerArticleAVendre(ArticleVendu articleVendu) throws BusinessException{
	BusinessException be = new BusinessException();
	
		boolean valide = validerDateDebutEnchereArticle(articleVendu.getDateDebutEncheres(), be);
				valide &= validerDateFinEnchereArticle(articleVendu.getDateDebutEncheres(),articleVendu.getDateFinEncheres(), be);
		
		if (valide) {
			this.articleVenduDAO.creerArticle(articleVendu);
			this.retraitDAO.create(articleVendu);
		} else {
			throw be;
		}
	}

	//Validation pour créer un Article
		
	/**
	 * Valide la date de début des enchères pour un article.
	 * @param dateDebutEncheres la date de début des enchères à valider.
	 * @param be l'exception de type {@code BusinessException} pour ajouter des erreurs de validation.
	 * @return {@code true} si la date de début est valide, {@code false} sinon.
	 */
		private boolean validerDateDebutEnchereArticle(LocalDateTime dateDebutEncheres, BusinessException be) {
			boolean valide = true;
			
			if(dateDebutEncheres.isBefore(LocalDateTime.now())){
	            be.addCleErreur("validation.articleVendu.dateDebutEncheres");
	            valide = false;
	        }
			
			return valide;
		}
		
		/**
		 * Valide la date de fin des enchères pour un article.
		 * @param dateDebutEncheres la date de début des enchères pour référence.
		 * @param dateFinEncheres la date de fin des enchères à valider.
		 * @param be l'exception de type {@code BusinessException} pour ajouter des erreurs de validation.
		 * @return {@code true} si la date de fin est valide, {@code false} sinon.
		 */
		private boolean validerDateFinEnchereArticle (LocalDateTime dateDebutEncheres, LocalDateTime dateFinEncheres, BusinessException be) {
			boolean valide = true;
			
			if (dateFinEncheres.isBefore(LocalDateTime.now())) {
				be.addCleErreur("validation.articleVendu.dateFinEncheres");
	            valide = false;
			}
			
			if (dateFinEncheres.isEqual(dateDebutEncheres) || dateFinEncheres.isBefore(dateDebutEncheres)) {
		        be.addCleErreur("validation.articleVendu.dateFinAvantDebut");
		        valide = false;
		    }

			
			return valide;
		}



				
}