package dao.tests;

import dao.Connexion;
import dao.DBReset;
import dao.FactoryDAO;

public class GenerationDataTest {
	
	
	public static void main(String[] args) throws Exception {
		 final Object lock = new Object(); // Un objet pour servir de verrou
	        Thread thread2 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                synchronized (lock) {
	                    System.out.println("Thread 2 : En attente pendant 3 secondes...");
	                    try {
	                        
	                        TestDaoMatche m = new TestDaoMatche();
	                		TestDaoPartie pa = new TestDaoPartie();
	                		TestDaoAppartenance app = new TestDaoAppartenance();
	                		TestDaoArbitrage arb = new TestDaoArbitrage();
	                		TestDaoSelection sel = new TestDaoSelection();
	                		TestDaoInscription inscr = new TestDaoInscription();
	                		
	                        m.setup();
	                		m.testInsert();
	                		m.testUpdate();
	                		
	                		pa.setup();
	                		pa.testInsert();
	                		
	                		app.setup();
	                		app.testInsert();
	                		
	                		arb.setup();
	                		arb.testInsert();
	                		
	                		sel.setup();
	                		sel.testInsert();
	                		
	                		inscr.setup();
	                		inscr.testInsert();
	                		
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                    lock.notify(); // Réveille le premier thread après 2 secondes
	                }
	            }
	        });
	        
	        Thread thread1 = new Thread(new Runnable() {
	            @Override
	            public void run() {
	                synchronized (lock) {
	                    try {
	                    	DBReset.main(args);
	                    	TestDaoEquipe e = new TestDaoEquipe();
	                		TestDaoArbitre a = new TestDaoArbitre();
	                		TestDaoJoueur j = new TestDaoJoueur();
	                		TestDaoNiveau n = new TestDaoNiveau();
	                		TestDaoTournoi t = new TestDaoTournoi();
	                		TestDaoSaison s = new TestDaoSaison();
	                		TestDaoPoule po = new TestDaoPoule();
	                		
	                    	e.setup();
	                		e.testInsert();
	                		
	                		j.setup();
	                		j.testInsert();
	                		
	                		a.setup();
	                		a.testInsert();
	                		
	                		s.setup();
	                		s.testInsert();
	                		
	                		n.setup();
	                		n.testInsert();
	                		
	                		t.setup();
	                		t.testInsert();
	                		
	                		po.setup();
	                		po.testInsert();
	                		thread2.start();
	                		lock.wait(); // Le thread 1 attend ici

	                		FactoryDAO.getDaoPartie(Connexion.getConnexion()).visualizeTable();
	                		
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	        });
	        
	        thread1.start();
	        
		
	}
	

}
