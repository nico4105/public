// auteurs: Maud El-Hachem
// 2015
package drugware_v15;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class Pharmacie {
	private List<Client> lesClients;
	private List<Medicament> lesMedicaments;

	public Pharmacie() {
		this.lesMedicaments = new ArrayList<>();
		this.lesClients = new ArrayList<>();
	}

	/**
	 * @return the lesClients
	 */
	public List<Client> getLesClients() {
		return lesClients;
	}

	/**
	 * @param lesClients
	 *            the lesClients to set
	 */
	public void setLesClients(List<Client> lesClients) {
		this.lesClients = lesClients;
	}

	/**
	 * @return the lesMedicaments
	 */
	public List<Medicament> getLesMedicaments() {
		return lesMedicaments;
	}

	/**
	 * @param lesMedicaments
	 *            the lesMedicaments to set
	 */
	public void setLesMedicaments(List<Medicament> lesMedicaments) {
		this.lesMedicaments = lesMedicaments;
	}

	public void lireClients() {
		Fichiers fichier = new Fichiers();
		fichier.lireClients(lesClients);
	}

	public void lireMedicaments() {
		Fichiers fichier = new Fichiers();
		fichier.lireMedicaments(lesMedicaments);
	}

	public void lirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.lirePrescriptions(lesClients);
	}

	public boolean siClientExiste(String NAM) {
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {
				return true;
			}
		}
		return false;
	}

	public void ajouterClient(String NAM, String nom, String prenom) {
		this.lesClients.add(new Client(NAM, nom, prenom));
	}

	public List<Prescription> getPrescriptionsClient(String NAM) {

		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client c = it.next();
			if (c.getNAM().equals(NAM)) {

				return c.getPrescriptions();
			}
		}
		return null;
	}

	public boolean servirPrescription(String NAM, String medicament) {
		boolean delivree = false;
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM)) {

				for (Iterator<Prescription> it2 = itClient.getPrescriptions()
						.iterator(); it2.hasNext();) {
					Prescription courante = it2.next();
					if (courante.getMedicamentAPrendre().equalsIgnoreCase(
							medicament))

						if (courante.getRenouvellements() >= 1) {

							courante.setRenouvellements(courante
									.getRenouvellements() - 1);

							delivree = true;
						}
				}
			}
		}
		return delivree;
	}

	public boolean ajouterPrescription(String NAM, String medicament,String doses , String renouv) {
		boolean valide = false;
		double dose = Double.parseDouble(doses);
		int renouvellement = Integer.parseInt(renouv);
		Medicament med = new Medicament();
		
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament itMed = it.next();
			if (itMed.getNomMarque().equalsIgnoreCase(medicament)) {
				med = itMed;
				if (dose > 0){
					if(renouvellement > 0){
						valide = true;
					}
				}
			}
		}
		
		
		
		/*String doses, renouv;
		double dose = 0;
		int nbRenouv = 0;
		Medicament med = new Medicament();
		String message = "";
		boolean valide = false;
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament itMed = it.next();
			if (itMed.getNomMarque().equalsIgnoreCase(medicament)) {
				med = itMed;

				while (!valide) {
					doses = JOptionPane.showInputDialog(null,
							"Entrez la dose désiré ", "Prescription",
							JOptionPane.QUESTION_MESSAGE);
					try {
						dose = Double.parseDouble(doses);
						if (dose > 0)
							valide = true;
					} catch (Exception e) {
						valide = false;
					}

				}
				valide = false;
				while (!valide) {
					renouv = JOptionPane.showInputDialog(null,
							"Entrez le nombre de renouvellement ",
							"Renouvellement", JOptionPane.QUESTION_MESSAGE);
					try {
						nbRenouv = Integer.parseInt(renouv);
						if (nbRenouv > 0)
							valide = true;
					} catch (Exception e) {
						valide = false;
					}
				}

			}

		}
*/
		for (Iterator<Client> it = lesClients.iterator(); it.hasNext();) {
			Client itClient = it.next();
			if (itClient.getNAM().equals(NAM) && valide) {

				if (itClient.siPrescriptionExiste(itClient, medicament)) {
					for (Iterator<Prescription> it2 = itClient
							.getPrescriptions().iterator(); it2.hasNext();) {
						Prescription courante = it2.next();
						if (courante.getMedicamentAPrendre().equalsIgnoreCase(
								medicament)) {
							double doseFinale = courante.servirDose(dose, med);
							courante.setDose(doseFinale);
							courante.setMedicamentAPrendre(medicament);
							courante.setRenouvellements(renouvellement);

						}

					}
				} else {
					Prescription p = new Prescription("", 0, 0);
					double doseFinale = p.servirDose(dose, med);
					p = new Prescription(medicament, doseFinale,
							renouvellement);
					itClient.ajoutPrescriptions(p);
					
					
				}
			}
			
		}
		
		return valide;

	}

	public boolean trouverInteraction(String medicament1, String medicament2) {
		for (Iterator<Medicament> it = lesMedicaments.iterator(); it.hasNext();) {
			Medicament courant = it.next();
			if (courant.getNomMolecule().equalsIgnoreCase(medicament1))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i]
							.equalsIgnoreCase(medicament2))
						return true;
			if (courant.getNomMolecule().equalsIgnoreCase(medicament2))
				for (int i = 0; i < courant.getInteractions().length; i++)
					if (courant.getInteractions()[i]
							.equalsIgnoreCase(medicament1)) // //////changer
															// medicament2 pour
															// medicament1
						return true;
		}
		return false; // ///////changer true pour false
	}

	public void ecrireClients() {
		Fichiers fichier = new Fichiers();
		fichier.ecrireClients(lesClients);
	}

	public void ecrirePrescriptions() {
		Fichiers fichier = new Fichiers();
		fichier.ecrirePrescriptions(lesClients);
	}
}
