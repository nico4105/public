package drugware_v15;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class PharmacieTest {
	private Pharmacie list;
	private List<Prescription> lp = new ArrayList<Prescription>();
	private List<Client> lc = new ArrayList<Client>();
	private List<Medicament> lm = new ArrayList<Medicament>();
	private StubLireClient stc = new StubLireClient();
	private StubLirePrescription stp = new StubLirePrescription() ;
	private StubLireMedicament stm = new StubLireMedicament();
	
	@Rule
	public TestName nomMethode = new TestName();

	@Before
	public void setUp() throws Exception {
		list = new Pharmacie();
		lc = stc.retourListClient();
		lp = stp.retourListPrescription();
		lm = stm.retourListMedicament();
		list.setLesClients(lc);
		list.setLesMedicaments(lm);
		
		
			lc.get(0).getPrescriptions().add(lp.get(0));
			lc.get(1).getPrescriptions().add(lp.get(1));
			lc.get(1).getPrescriptions().add(lp.get(2));
			lc.get(2).getPrescriptions().add(lp.get(3));
		
		
		System.out
				.println("Debut du test de la classe Pharmacie de la méthode "
						+ nomMethode.getMethodName());
	}

	@After
	public void tearDown() throws Exception {
		list = null;
		System.out.println("Fin du test de la classe Pharmacie de la méthode "
				+ nomMethode.getMethodName() + "\n");
	}

	@Test
	public void testPharmacie() {
		assertNotNull(list);
	}

	@Test
	public void testGetLesClients() {
		assertNotNull(list.getLesClients());
	}

	@Test
	public void testSetLesClients() {
		List<Client> c = new ArrayList<>();
		Client b = new Client("NIOU1234", "NI", "OU");
		c.add(b);
		list.setLesClients(c);
		assertEquals(list.getLesClients().get(0).getNAM(), "NIOU1234");
	}

	@Test
	public void testGetLesMedicaments() {
		assertNotNull(list.getLesMedicaments());
	}

	@Test
	public void testSetLesMedicaments() {
		List<Medicament> c = new ArrayList<>();
		Medicament b = new Medicament();
		b.setNomMolecule("AH1N1");
		c.add(b);
		list.setLesMedicaments(c);
		assertEquals(list.getLesMedicaments().get(0).getNomMolecule(), "AH1N1");
	}

	@Test
	public void testSiClientExiste() {
		list.ajouterClient("HOUL2514", "HO", "UL");
		//assertEquals(list.getLesClients().get(0).getNAM(), "HOUL2514");
		assertTrue(list.siClientExiste("HOUL2514"));
		assertFalse(list.siClientExiste("HOUL2515"));

	}

	@Test
	public void testAjouterClient() {
		list.ajouterClient("GAUF03128506", "Gauthier", "Francois");
		
		assertEquals(list.getLesClients().get(5).getNAM(),"GAUF03128506");
	}

	@Test
	public void testGetPrescriptionsClient() {
		list.ajouterClient("NICO12345678", "Ouellet", "Nic");
		Prescription p = new Prescription("AB", 1, 1);
		lp.add(p);
		list.getLesClients().get(5).setPrescriptions(lp);
		//lp = list.getPrescriptionsClient("NICO12345678");
		
		assertSame(list.getPrescriptionsClient("NICO12345678"),lp);
		assertNotSame(list.getPrescriptionsClient("NICO1234567"),lp);
	}

	@Test
	public void testServirPrescription() {
		list.ajouterClient("NICO12345678", "Ouellet", "Nic");
		assertFalse(list.servirPrescription("ASDF12345678", "Nexium"));
		Prescription p = new Prescription("Nexium", 1, 1);
		lp.add(p);
		list.getLesClients().get(5).setPrescriptions(lp);
		assertTrue(list.servirPrescription("NICO12345678", "Nexium"));
		Prescription p1 = new Prescription("AB", 1, 0);
		lp.add(p1);
		list.getLesClients().get(5).setPrescriptions(lp);
		assertFalse(list.servirPrescription("NICO12345678", "AB"));
	}

	@Test
	public void testTrouverInteraction() { 
		
		assertFalse(list.trouverInteraction("","" ));
		assertTrue(list.trouverInteraction("ésoméprazole","clopidogrel" ));
		assertFalse(list.trouverInteraction("ésoméprazole","" ));
		assertTrue(list.trouverInteraction("clopidogrel","ésoméprazole" ));
		assertFalse(list.trouverInteraction("cyclobenzaprine","clopidogrel" ));
	}
	@Test
	public void testAjouterPrescription(){
		assertFalse(list.ajouterPrescription("", "","0","0"));
		assertFalse(list.ajouterPrescription("", "Nexium","0","-1"));
		assertFalse(list.ajouterPrescription("ELHM12345678", "Botox","100","-1"));
		assertTrue(list.ajouterPrescription("ELHM12345678", "Nexium","100","1"));
		assertTrue(list.ajouterPrescription("ELHM12345678", "Botox","100","1"));
	}
	
	public class StubLireClient{
		private List<Client> lc = new ArrayList<Client>();
		public StubLireClient(){
			Client c1 = new Client("ELHM12345678","ElHachem","Maud");
			Client c2 = new Client("DUFO12345678","Dufort","JeanRene");
			Client c3 = new Client("PELA12345678","Peladeau","PK");
			Client c4 = new Client("BRUW12345678","Wayne","Bruce");
			Client c5 = new Client("DOLA12345678","Dolan","Xavier");
			lc.add(c1);
			lc.add(c2);
			lc.add(c3);
			lc.add(c4);
			lc.add(c5);
			
		}
		public List<Client> retourListClient(){
			return lc;
		}
	}
	public class StubLirePrescription{
		private List<Prescription> lp = new ArrayList<Prescription>();
		
		public StubLirePrescription(){
			
			Prescription p1 = new Prescription("Botox", 50.0, 3);
			Prescription p2 = new Prescription("Plavix", 300.0, 3);
			Prescription p3 = new Prescription("Nexium", 40.0, 2);
			Prescription p4 = new Prescription("Nexium", 20.0, 3);
			lp.add(p1);
			lp.add(p2);
			lp.add(p3);
			lp.add(p4);
			
		}
		public List<Prescription> retourListPrescription(){
			return lp;
		}
	}
	public class StubLireMedicament{
		private List<Medicament> lm = new ArrayList<Medicament>();
		public StubLireMedicament(){
			double[] doses1 = {20,40};
			double[] doses2 = {75,300};
			double[] doses3 = {25,50,75,150,225,300};
			double[] doses4 = {50,100,200};
			String[] inter1 = {"clopidogrel","dabigatran","ifosfamide"};
			String[] inter2 = {"fluvoxamine","ibuprofène","naproxène","gemfibrozil"};
			String[] inter3 = {"clonidine","cyclobenzaprine","alcool","diphénhydramine","buprénorphine"};
			String[] inter4 = {"aclidinium","atropine","belladone","benztropine"};
			
			Medicament m1 = new Medicament("ésoméprazole","Nexium",doses1);
			Medicament m2 = new Medicament("clopidogrel","Plavix",doses2);
			Medicament m3 = new Medicament("prégabaline","Lyrica",doses3);
			Medicament m4 = new Medicament("toxine Clostridium  botulique de type A","Botox",doses4);
			m1.setInteractions(inter1);
			m2.setInteractions(inter2);
			m3.setInteractions(inter3);
			m4.setInteractions(inter4);
			lm.add(m1);
			lm.add(m2);
			lm.add(m3);
			lm.add(m4);
		}
		public List<Medicament> retourListMedicament(){
			return lm;
		}
	}

}
