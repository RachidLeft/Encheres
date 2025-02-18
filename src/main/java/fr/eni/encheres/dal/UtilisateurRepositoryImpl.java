package fr.eni.encheres.dal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Utilisateur;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {

    
    private final JdbcTemplate jdbcTemplate;


    public UtilisateurRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    
    private static final String FIND_BY_PSEUDO = "select no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville,mot_de_passe, credit, administrateur from utilisateurs where pseudo = ?";


    @Override
    public void ajouter(Utilisateur utilisateur) {
        String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 100, 0)";

        jdbcTemplate.update(sql, 
            utilisateur.getPseudo(), 
            utilisateur.getNom(), 
            utilisateur.getPrenom(), 
            utilisateur.getEmail(), 
            utilisateur.getTelephone(), 
            utilisateur.getRue(), 
            utilisateur.getCodePostal(), 
            utilisateur.getVille(), 
            utilisateur.getMotDePasse()
        );
    }

    @Override
    public List<Utilisateur> findAll() {
        String sql = "SELECT * FROM UTILISATEURS";
        return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
   public Utilisateur findById(int id) {
    	String sql = "SELECT * FROM UTILISATEURS WHERE no_utilisateur = ?";
    	Utilisateur utilisateur = jdbcTemplate.queryForObject(sql,new UtilisateurRowMapper(),id);
        
        return utilisateur;
    }

    @Override
    public void update(Utilisateur utilisateur) {
        String sql = "UPDATE UTILISATEURS SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?, rue = ?, code_postal = ?, ville = ?,credit = ?, administrateur = ? " +
                     "WHERE no_utilisateur = ?";
System.out.println("je suis la");
        jdbcTemplate.update(sql,
            utilisateur.getPseudo(),
            utilisateur.getNom(),
            utilisateur.getPrenom(),
            utilisateur.getEmail(),
            utilisateur.getTelephone(),
            utilisateur.getRue(),
            utilisateur.getCodePostal(),
            utilisateur.getVille(),
            utilisateur.getCredit(),
            utilisateur.isAdministrateur(),
            utilisateur.getNoUtilisateur()
        );
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
	public Utilisateur read(String pseudo) {
		
		return jdbcTemplate.queryForObject(FIND_BY_PSEUDO, new UtilisateurRowMapper(), pseudo);
	}




    class UtilisateurRowMapper implements RowMapper<Utilisateur> {

        @Override
        public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNoUtilisateur(rs.getInt("no_utilisateur"));
            utilisateur.setPseudo(rs.getString("pseudo"));
            utilisateur.setNom(rs.getString("nom"));
            utilisateur.setPrenom(rs.getString("prenom"));
            utilisateur.setEmail(rs.getString("email"));
            utilisateur.setTelephone(rs.getString("telephone"));
            utilisateur.setRue(rs.getString("rue"));
            utilisateur.setCodePostal(rs.getString("code_postal"));
            utilisateur.setVille(rs.getString("ville"));
            utilisateur.setMotDePasse(rs.getString("mot_de_passe"));
            utilisateur.setCredit(rs.getInt("credit"));
            utilisateur.setAdministrateur(rs.getBoolean("administrateur"));
            return utilisateur;
        }
    }
}
