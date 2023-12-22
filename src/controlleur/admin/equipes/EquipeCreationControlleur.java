package controlleur.admin.equipes;

import controlleur.VueObserver;
import dao.*;
import modele.*;
import vue.Page;
import vue.admin.equipes.creation.PopupPseudo;
import vue.admin.equipes.creation.VueAdminEquipesCreation;
import vue.common.JFramePopup;
import vue.common.JFramePopupEquipe;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;

public class EquipeCreationControlleur implements ActionListener, ItemListener, MouseListener {
	private final VueAdminEquipesCreation vue;
	private final DaoEquipe daoEquipe;
	private DaoJoueur daoJoueur;
	private DaoSaison daoSaison;
	private DaoInscription daoInscription;
	private BufferedImage logo;
	private static Connexion c;
	private PopupPseudo popupPseudo;
	private List<String> listePseudo;
	private int nbJoueurs = 0;

	public EquipeCreationControlleur(VueAdminEquipesCreation newVue) {
		this.vue = newVue;
		this.listePseudo = new ArrayList<>();
		c = Connexion.getConnexion();
		daoEquipe = new DaoEquipe(c);
		daoSaison = new DaoSaison(c);
		daoJoueur = new DaoJoueur(c);
		daoInscription = new DaoInscription(c);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton bouton = (JButton) e.getSource();
		if (Objects.equals(bouton.getText(), "Ajouter")) {
			String nomEquipe = vue.getNomEquipe().trim();
			Pays champPaysEquipe;

			if (vue.getChampPaysEquipe() == null) {
				champPaysEquipe = null;
			} else {
				champPaysEquipe = Pays.trouverPaysParNom(vue.getChampPaysEquipe());
			}
			//if ((nomEquipe.isEmpty()) || (logo == null) || (champPaysEquipe == null)) {
			if (nomEquipe.isEmpty()) {
				new JFramePopup("Erreur", "Nom de l'equipe est vide", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
			} else if (logo == null) {
				new JFramePopup("Erreur", "Le logo de l'equipe est obligatoire", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
			} else if (champPaysEquipe == null) {
				new JFramePopup("Erreur", "Veuillez choisir le pays de l'equipe", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
			} else if (this.nbJoueurs < 5) {
				new JFramePopup("Erreur", "Pas assez de joueurs dans l'equipe", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
			} else if (equipeDejaExistante(nomEquipe)) {
				new JFramePopup("Erreur", "L'equipe existe deja", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
				resetChamps();
			} else {
				Equipe equipeInserer = new Equipe(nomEquipe, champPaysEquipe);

				try {

					new JFramePopupEquipe("Ajouter", " Voulez vous juste créer une equipe ou l'ajouter dans la saison actuelle",
							() -> {
								addEquipe(equipeInserer);
								new JFramePopup("Succès", "L'équipe est insérée",
										() -> {
											VueObserver.getInstance().notifyVue(Page.EQUIPES_CREATION);
											resetChamps();
										}
								);
							},
							() -> {
								addEquipeSaison(equipeInserer);
								new JFramePopup("Succès", "L'équipe est insérée dans la saison",
										() -> {
											VueObserver.getInstance().notifyVue(Page.EQUIPES_CREATION);
											resetChamps();
										}
								);
							}
					);


				} catch (Exception ex) {
					this.logo = null;
					new JFramePopup("Erreur", "Erreur d'insertion", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
					this.vue.clearField();
					throw new RuntimeException(ex);
				}
			}

		} else if ((Objects.equals(bouton.getText(), "Annuler"))) {
			EquipesObserver.getInstance().notifyVue(Page.EQUIPES_LISTE);
			resetChamps();
		}
	}

	private boolean equipeDejaExistante(String nomEquipe) {
		try {
			Equipe equipe = daoEquipe.getById(nomEquipe).get();
			return equipe != null;
		} catch (Exception ignored) {
			return false;
		}

	}

	public void addEquipe(Equipe equipeInserer) {
		try {
			daoEquipe.add(equipeInserer);
			initEquipe(equipeInserer);
			File outputfile = new File("assets/logo-equipes/" + equipeInserer.getNom() + ".jpg");
			ImageIO.write(logo, "jpg", outputfile);
		} catch (Exception e) {
			new JFramePopup("Erreur", "Erreur d'insertion", () -> EquipesObserver.getInstance().notifyVue(Page.EQUIPES_CREATION));
		}
	}

	public void addEquipeSaison(Equipe equipeInserer) {
		try {
			addEquipe(equipeInserer);
			Saison saison = daoSaison.getLastSaison();
			Inscription inscription = new Inscription(saison, equipeInserer);
			daoInscription.add(inscription);
		} catch (SQLException e) {
			new JFramePopup("Erreur", "Erreur d'insertion", () -> EquipesObserver.getInstance().notifyVue(Page.EQUIPES_CREATION));
		} catch (Exception e) {
			new JFramePopup("Erreur", "Erreur d'insertion dans la saison", () -> EquipesObserver.getInstance().notifyVue(Page.EQUIPES_CREATION));
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			Pays item = (Pays) e.getItem();
			vue.setDrapeau(item.getCode());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == vue.getbtnAjoutJoueurs()) {
			if (!equipeComplete()) {
				this.popupPseudo = new PopupPseudo("Veuillez entrer le pseudo du Joueur", () -> {
					EquipesObserver.getInstance().notifyVue(Page.EQUIPES_CREATION);
					this.addJoueur();
				});
			} else {
				new JFramePopup("Erreur", "Equipe déjà complète", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
			}

		}
		if (e.getSource() == vue.getLabelLogo()) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter("JPG Images", "jpg"));
			int returnVal = chooser.showOpenDialog(this.vue);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				//recuperer le fichier choisi
				File fichier = chooser.getSelectedFile();
				try {
					//le transformer en image et la resize
					BufferedImage image = ImageIO.read(fichier);
					BufferedImage image_resized = resizeImage(image, this.vue.getLabelLogo().getWidth(), this.vue.getLabelLogo().getHeight());
					//transformer en icone pour pouvoir l'afficher

					ImageIcon imageIcon = new ImageIcon(image_resized);
					//passer l'image au controleur pour pouvoir la stoquer plus tard
					this.logo = image;
					//affichage
					this.vue.getLabelLogo().setIcon(imageIcon);
					this.vue.getLabelLogo().setText("");
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}
			}
		}
	}

	public void addJoueur() {
		String nomJoueur = this.popupPseudo.getSaisie().trim();
		if (nomJoueur.isEmpty()) {
			new JFramePopup("Erreur", "Le pseudo du joueur est vide", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
		} else if (this.listePseudo.contains(nomJoueur)) {
			new JFramePopup("Erreur", "Le joueur est deja dans l'equipe", () -> VueObserver.getInstance().notifyVue(Page.EQUIPES));
		} else {
			this.vue.setJoueur(nomJoueur, this.nbJoueurs);
			this.listePseudo.add(nomJoueur);
			this.nbJoueurs++;
		}

	}

	public boolean equipeComplete() {
		return this.nbJoueurs == 5;
	}


	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
		Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
		BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}

	public void initEquipe(Equipe equipe) {
		daoJoueur = new DaoJoueur(c);
		Object[] liste = this.vue.getJoueurs();
		try {
			for (Object pseudo : liste) {
				daoJoueur.add(new Joueur(pseudo.toString(), equipe));
			}
		} catch (Exception e) {

		}
	}

	public void resetChamps() {
		this.logo = null;
		this.vue.clearField();
		this.nbJoueurs = 0;
		this.listePseudo.clear();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
}