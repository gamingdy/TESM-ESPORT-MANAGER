package dao.tests;

import dao.FactoryDAO;
import modele.Arbitrage;
import modele.Arbitre;
import modele.Tournoi;

import java.util.LinkedList;
import java.util.List;

public class TestDaoArbitrage extends TestDao {

	private List<Arbitre> a = new LinkedList<>();
	private List<Tournoi> t = new LinkedList<>();
	private List<Arbitrage> ab = new LinkedList<>();

	public TestDaoArbitrage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setup() throws Exception {
		t = FactoryDAO.getDaoTournoi(getC()).getAll();
		a = FactoryDAO.getDaoAbritre(getC()).getAll();
		for (Tournoi tr : t) {
			for (Arbitre arb : a) {
				ab.add(new Arbitrage(arb, tr));
			}
		}
	}

	@Override
	public void testInsert() throws Exception {
		for (Arbitrage arb : ab) {
			FactoryDAO.getDaoArbitrage(getC()).add(arb);
		}
		System.out.println(FactoryDAO.getDaoArbitrage(getC()).visualizeTable());
	}

	@Override
	public void testDelete() throws Exception {
		FactoryDAO.getDaoArbitrage(getC()).delete(ab.get(0).getArbitre().getNom(), ab.get(0).getArbitre().getPrenom(), ab.get(0).getArbitre().getNumeroTelephone(), ab.get(0).getTournoi().getSaison().getAnnee(), ab.get(0).getTournoi().getNom());
		System.out.println(FactoryDAO.getDaoArbitrage(getC()).visualizeTable());

	}

	@Override
	public void testUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

	public void testGetArbitreByTournoi() throws Exception {
		List<Arbitre> ar = FactoryDAO.getDaoArbitrage(getC()).getArbitreByTournoi(ab.get(0).getTournoi().getNom(), ab.get(0).getTournoi().getSaison().getAnnee());
		System.out.println("______________________________________");
		ar.stream().forEach(System.out::println);
	}

	public void testGetTournoiByArbitre() throws Exception {
		List<Tournoi> ar = FactoryDAO.getDaoArbitrage(getC()).getTournoiByArbitre(ab.get(0).getArbitre().getNom(), ab.get(0).getArbitre().getPrenom(), ab.get(0).getArbitre().getNumeroTelephone());
		System.out.println("______________________________________");
		ar.stream().forEach(System.out::println);
	}

	public static void main(String[] args) throws Exception {
		TestDaoArbitrage x = new TestDaoArbitrage();
		x.setup();
		x.testInsert();
		x.testDelete();
		x.testUpdate();
		x.testGetArbitreByTournoi();
		x.testGetTournoiByArbitre();
	}

}
