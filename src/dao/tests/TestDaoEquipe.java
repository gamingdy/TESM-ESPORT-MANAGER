package dao.tests;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import dao.FactoryDAO;
import modele.Equipe;
import modele.Pays;

public class TestDaoEquipe extends TestDao {

	private Equipe equipe;
	private Equipe equipe2;
	private List<Equipe> ekip = new LinkedList<>();
	
	public TestDaoEquipe() {
		super();
		equipe = new Equipe("Bienveillance",Pays.REPUBLIQUE_DEMOCRATIQUE_DU_CONGO);
		equipe2 = new Equipe("Bonheur",Pays.REPUBLIQUE_DU_CONGO);
		for(int i = 0;i<30;i++) {
			ekip.add(new Equipe(super.randomUsername("NomDequipeSuperCool"),Pays.values()[i]));
		}
		
	}
	
	public void testInsert() throws Exception {
		for(int i = 0;i<30;i++) {
			FactoryDAO.getDaoEquipe(getC()).add(ekip.get(i));
		}
		System.out.println(FactoryDAO.getDaoEquipe(getC()).visualizeTable());
	}
	
	public void testDelete() throws Exception {
		FactoryDAO.getDaoEquipe(getC()).delete(equipe.getNom());
		System.out.println(FactoryDAO.getDaoEquipe(getC()).visualizeTable());
	}
	
	public void testUpdate() throws Exception {
		Optional<Equipe> b = FactoryDAO.getDaoEquipe(getC()).getById(equipe2.getNom());
		b.get().setPays(Pays.FRANCE);
		FactoryDAO.getDaoEquipe(getC()).update(b.get());
		System.out.println(FactoryDAO.getDaoEquipe(getC()).visualizeTable());
	}
	
	public static void main(String[] args) throws Exception {
		TestDaoEquipe x = new TestDaoEquipe();
		x.testInsert();
		x.testDelete();
		//x.testUpdate();
		
	}

	
	
}
