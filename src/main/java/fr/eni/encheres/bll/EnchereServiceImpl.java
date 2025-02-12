package fr.eni.encheres.bll;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.dal.CategorieDAO;

@Service
public class EnchereServiceImpl implements EnchereService{

	CategorieDAO categorieDao;
	
	@Override
	public List<Categorie> finAll() {
		return categorieDao.finAll();
	}

	public EnchereServiceImpl(CategorieDAO categorieDao) {
		this.categorieDao = categorieDao;
	}

	@Override
	public Categorie read(int idCategorie) {
		
		return categorieDao.read(idCategorie);
	}
	
	
}
