package vue.arbitre;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import vue.Vue;
import vue.common.CustomColor;
import vue.common.MaFont;

public class CasePartie extends JPanel{

	public JComponent getPanel() {
		setBorder(BorderFactory.createEmptyBorder(20,0,0,20));
		setLayout(new GridBagLayout());
		setOpaque(false);
		JPanel panelE = new JPanel(new GridBagLayout());
		panelE.setOpaque(false);
		GridBagConstraints gbcEquipe = new GridBagConstraints();
		gbcEquipe.fill = GridBagConstraints.BOTH;
		gbcEquipe.weightx = 1;
		gbcEquipe.weighty = 1;
		gbcEquipe.gridy = 1;
		add(panelE,gbcEquipe);
		JPanel equipe1 = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,5));
		equipe1.setOpaque(false);
		equipe1.setBorder(BorderFactory.createLineBorder(CustomColor.ROSE_CONTOURS, 1));
		JLabel logoEquipe1 = new JLabel();
		equipe1.add(logoEquipe1);
		JLabel nomEquipe1 = new JLabel();
		nomEquipe1.setFont(MaFont.getFontTitre4());
		nomEquipe1.setForeground(CustomColor.BLANC);
		equipe1.add(nomEquipe1);
		JButton image1 = new JButton();
		image1.setContentAreaFilled(false);
		image1.setBorder(null);
		image1.setFocusPainted(false);
		equipe1.add(image1);
		equipe1.setPreferredSize(new Dimension(1,1));
		GridBagConstraints gbcEquipe1 = new GridBagConstraints();
		gbcEquipe1.fill = GridBagConstraints.BOTH;
		gbcEquipe1.weighty = 1;
		gbcEquipe1.weightx = 0.50;
		panelE.add(equipe1,gbcEquipe1);

		JPanel equipe2 = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));
		equipe2.setOpaque(false);
		equipe2.setBorder(BorderFactory.createMatteBorder(1,0,1,1,CustomColor.ROSE_CONTOURS));
		JButton image2 = new JButton();
		image2.setContentAreaFilled(false);
		image2.setBorder(null);
		image2.setFocusPainted(false);
		equipe2.add(image2);
		JLabel nomEquipe2 = new JLabel();
		nomEquipe2.setFont(MaFont.getFontTitre4());
		nomEquipe2.setForeground(CustomColor.BLANC);
		equipe2.add(nomEquipe2);
		JLabel logoEquipe2 = new JLabel();
		equipe2.add(logoEquipe2);
		equipe2.setPreferredSize(new Dimension(1,1));
		GridBagConstraints gbcEquipe2 = new GridBagConstraints();
		gbcEquipe2.fill = GridBagConstraints.BOTH;
		gbcEquipe2.gridx = 1;
		gbcEquipe2.weighty = 1;
		gbcEquipe2.weightx = 0.5;
		panelE.add(equipe2,gbcEquipe2);
		logoEquipe1.setIcon(Vue.resize(this.getLogoGauche(),40,40));
		nomEquipe1.setText(this.getNomGauche());
		image1.setIcon(Vue.resize(this.getImageBoutonGauche(),40,40));
		image1.addActionListener(this.getAlGauche());
		logoEquipe2.setIcon(Vue.resize(this.getLogoDroite(),40,40));
		nomEquipe2.setText(this.getNomDroite());
		image2.setIcon(Vue.resize(this.getImageBoutonDroite(),40,40));
		image1.addActionListener(this.getAlDroite());
		return this;
	}
}
