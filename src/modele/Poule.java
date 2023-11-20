package modele;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import exceptions.PouleComplete;

public class Poule {

	private Tournoi tournoi;
	private String libelle;
	private Set<Equipe> equipes;
	
	public Poule(Tournoi tournoi, String libelle) {
		this.tournoi = tournoi;
		this.libelle = libelle;
		this.equipes = new HashSet<Equipe>();
	}

	public Tournoi getTournoi() {
		return tournoi;
	}

	public void setTournoi(Tournoi tournoi) {
		this.tournoi = tournoi;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public void addEquipe(Equipe equipe) throws PouleComplete {
		if (this.equipes.size()==8) {
			throw new PouleComplete("La poule est complète");
		}
		this.equipes.add(equipe);
	}
	
	public void deleteEquipe(Equipe equipe) {
		this.equipes.remove(equipe);
	}

	public Set<Equipe> getEquipes() {
		return equipes;
	}
}
