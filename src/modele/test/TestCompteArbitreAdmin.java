package modele.test;

import modele.Arbitre;
import modele.CompteAdmin;
import modele.CompteArbitre;
import modele.CustomDate;
import modele.Niveau;
import modele.Saison;
import modele.Tournoi;
import org.junit.Before;
import org.junit.Test;

public class TestCompteArbitreAdmin {
	private CompteArbitre compteArbitre;
	private Arbitre a;
	private Tournoi tournoi;
	private Saison s;

	@Before
	public void setUp() throws Exception {
		a = new Arbitre("Michel", "Jean", "1234567890");
		CustomDate d1 = new CustomDate(2022, 11, 13);
		CustomDate d2 = new CustomDate(2022, 10, 10);
		s = new Saison(2022);
		compteArbitre = new CompteArbitre("arbitre1", "123");
		tournoi = new Tournoi(s, "RLCS", d1, d2, Niveau.INTERNATIONAL, compteArbitre);

	}


	@Test
	public void testCompteAdmin() {
		CompteAdmin ca = new CompteAdmin("admin", "123fd4");
	}
}