package vue;

public enum Page {
	LOGIN("Login"),
	ARBITRES("Arbitres"),
	TOURNOIS("Tournois"),
	TOURNOIS_CREATION("Tournoi Creation"),
	TOURNOIS_LISTE("Tournois Liste"),
	EQUIPES("Equipes"),
	EQUIPES_CREATION("Equipes Creation"),
	EQUIPES_LISTE("Equipes Liste"),


	SAISON_PRECEDENTES("Saisons precedentes"),
	ACCUEIL_ADMIN("Admin-Accueil");

	private String nom;

	Page(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
}
