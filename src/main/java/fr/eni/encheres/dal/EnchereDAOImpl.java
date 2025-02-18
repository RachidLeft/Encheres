package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Enchere;

@Repository
public class EnchereDAOImpl implements EnchereDAO{

	private final String INSERT = "INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere)"
			+ " VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";
	
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	@Override
	public void ajouter(Enchere enchere) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("no_utilisateur", enchere.getEncherisseur().getNoUtilisateur());
		mapSqlParameterSource.addValue("no_article", enchere.getArticle().getNoArticle());
		mapSqlParameterSource.addValue("date_enchere", java.sql.Timestamp.valueOf(enchere.getDateEnchere()));
		mapSqlParameterSource.addValue("montant_enchere", enchere.getMontantEnchere());

		
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(INSERT, mapSqlParameterSource, keyHolder);
		
		if(keyHolder != null && keyHolder.getKey() != null) {
			enchere.setNoEnchere(keyHolder.getKey().intValue());
		}
	}
	
}
