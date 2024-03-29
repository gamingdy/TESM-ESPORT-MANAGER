package modele.test;

import modele.Appartenance;
import modele.CompteArbitre;
import modele.CustomDate;
import modele.Equipe;
import modele.Niveau;
import modele.Pays;
import modele.Poule;
import modele.Tournoi;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAppartenance {
	private Appartenance appartenance;
	private Poule poule;
	private Tournoi tournoi;
	private Equipe e1;
	private CustomDate debut;
	private CustomDate fin;

	@Before
	public void setUp() throws Exception {
		e1 = new Equipe("Faze", Pays.ALGERIE);
		debut = new CustomDate(2022, 10, 10);
		fin = new CustomDate(2022, 10, 22);
		tournoi = new Tournoi(new modele.Saison(2022), "RLCS", debut, fin, Niveau.INTERNATIONAL, new CompteArbitre("admin", "dsdsd00"));
		poule = new Poule(tournoi, 'A');
		appartenance = new Appartenance(e1, poule);
	}

	@Test
	public void setGetEquipe() {
		assertEquals(e1, appartenance.getEquipe());
	}

	@Test
	public void setGetPoule() {
		assertEquals(poule, appartenance.getPoule());
	}
}