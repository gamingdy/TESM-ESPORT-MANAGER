package vue.admin.tournois.liste;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vue.Vue;
import vue.common.CustomColor;
import vue.common.MaFont;

public class CaseTournoi {
	
	private String nom;
	private String dateDebut;
	private String dateFin;
	/**
	 * @param nom
	 * @param dateDebut
	 * @param dateFin le logo de l'équipe
	 * @param pays l'icone du drapeau du pays
	 */
	public CaseTournoi(String nom, String dateDebut, String dateFin) {
		this.nom = nom;
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
	}
	/**
	 * Permet de render la case de l'équipe
	 * @return un JPanel contentant les infos de l'équipe
	 */
	public JPanel getPanel() {
		JPanel panelItem = new JPanel(new GridBagLayout());
		panelItem.setBackground(CustomColor.BACKGROUND_MAIN);
        panelItem.setBorder(BorderFactory.createCompoundBorder(
        		BorderFactory.createLineBorder(CustomColor.ROSE_CONTOURS,2),
        		BorderFactory.createEmptyBorder(20, 20, 20, 20)));
		
		JLabel nom = new JLabel(getNom());
		nom.setIconTextGap(15);
		nom.setForeground(CustomColor.BLANC);
		nom.setFont(MaFont.getFontTitre2());
		GridBagConstraints gbcNom = new GridBagConstraints();
		gbcNom.fill = GridBagConstraints.HORIZONTAL;
		gbcNom.gridx = 0;
		gbcNom.gridy = 0;
		gbcNom.weighty = 0.2F;
		gbcNom.gridwidth = GridBagConstraints.REMAINDER;
		panelItem.add(nom,gbcNom);
		
		JLabel labelDate = new JLabel(dateDebut +" - "+ dateFin);
		labelDate.setFont(MaFont.getFontTitre3());
		labelDate.setForeground(CustomColor.BLANC.darker());
		labelDate.setHorizontalTextPosition(JLabel.CENTER);
		labelDate.setVerticalTextPosition(JLabel.CENTER);
		GridBagConstraints gbcDate = new GridBagConstraints();
		gbcDate.fill = GridBagConstraints.HORIZONTAL;
		gbcDate.gridx = 0;
		gbcDate.gridy = 1;
		gbcDate.weighty = 0.5F;
		gbcDate.gridwidth = GridBagConstraints.REMAINDER;
		panelItem.add(nom,gbcDate);
		
		JLabel labelModif = new JLabel(Vue.resize(new ImageIcon("assets/modif.png"),30,30));
		labelModif.setHorizontalAlignment(JLabel.RIGHT);
		GridBagConstraints gbcModif = new GridBagConstraints();
		gbcModif.fill = GridBagConstraints.HORIZONTAL;
		gbcModif.gridx = 1;
		gbcModif.gridy = 2;
		gbcModif.weightx = 0.6F;
		gbcModif.weighty = 0.2F;
		panelItem.add(labelModif,gbcModif);
		
		JLabel labelDelete = new JLabel(Vue.resize(new ImageIcon("assets/delete.png"),30,30));
		GridBagConstraints gbcDelete = new GridBagConstraints();
		gbcDelete.fill = GridBagConstraints.HORIZONTAL;
		gbcDelete.gridx = 2;
		gbcDelete.gridy = 2;
		gbcDelete.weightx = 0.2F;
		gbcDelete.weighty = 0.2F;
		panelItem.add(labelDelete,gbcDelete);
		
		return panelItem;
	}
	/**
	 * @return le nom
	 */
	public String getNom() {
		return nom;
	}
	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	/**
	 * @return les joueurs
	 */
	public String getJoueurs() {
		return dateDebut;
	}
	/**
	 * @param joueurs the joueurs to set
	 */
	public void setJoueurs(String joueurs) {
		this.dateDebut = joueurs;
	}
	/**
	 * @return le logo
	 */
	public String getLogo() {
		return dateFin;
	}
	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.dateFin = logo;
	}
}