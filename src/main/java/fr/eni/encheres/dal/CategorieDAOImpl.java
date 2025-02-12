package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO{
	
	private static final String SELECT_BY_ID = "select no_categorie, libelle from categories where no_categorie = :idCategorie";
	private static final String SELECT_ALL = "select no_categorie, libelle from categories"; 

	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate; 
	 
	public CategorieDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) { 
	    this.namedParameterJdbcTemplate = namedParameterJdbcTemplate; 
	} 

	
	@Override
	public Categorie read(int idCategorie) {
		 MapSqlParameterSource namedParameters = new MapSqlParameterSource(); 
		    namedParameters.addValue("idCategorie", idCategorie); 
		    return namedParameterJdbcTemplate.queryForObject(SELECT_BY_ID, namedParameters, new BeanPropertyRowMapper<>(Categorie.class));
	}

	@Override
	public List<Categorie> finAll() {
		return namedParameterJdbcTemplate.query(SELECT_ALL, new CategorieRowMapper());
		
	}
	
	 
	class CategorieRowMapper implements RowMapper<Categorie> { 
		@Override 
		public Categorie mapRow(ResultSet rs, int rowNum) throws SQLException { 
			Categorie c = new Categorie(); 
			c.setNoCategorie(rs.getInt("NO_CATEGORIE")); 
			c.setLibelle(rs.getString("LIBELLE")); 
				return c; 

			} 

	}  

}
