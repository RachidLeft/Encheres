/**
 * Filtre visuel pour Achat Vente
 */

// Sélection des éléments radio et checkbox
const choix1 = document.getElementById('achats');
const select1 = document.querySelectorAll("#achat1, #achat2, #achat3");
const choix2 = document.getElementById('ventes');
const select2 = document.querySelectorAll("#vente1, #vente2, #vente3");


// Ecoute du click
choix1.addEventListener("click", select);
choix2.addEventListener("click", select);

// Filtre les checkbox selctionnable en fonction du choix
function select() {
	if (choix1.checked) {
		select2.forEach(checkbox => {
			checkbox.disabled = true;
			checkbox.checked = false;
		})
		select1.forEach(checkbox => {
			checkbox.disabled = false;

		})
	}
	if (choix2.checked) {
		select1.forEach(checkbox => {
			checkbox.disabled = true;
			checkbox.checked = false;
		})
		select2.forEach(checkbox => {
			checkbox.disabled = false;

		})
	}
}

