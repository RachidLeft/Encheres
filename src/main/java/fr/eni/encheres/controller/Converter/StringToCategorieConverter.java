package fr.eni.encheres.controller.Converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;



@Component
public class StringToCategorieConverter implements Converter<String, Categorie> {

	private CategorieService categorieService;

	public StringToCategorieConverter(CategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public Categorie convert(String idCategorie) {
		return this.categorieService.read(Integer.parseInt(idCategorie));
	}
	
}
