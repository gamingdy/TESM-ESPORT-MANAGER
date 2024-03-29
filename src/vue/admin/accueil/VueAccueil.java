package vue.admin.accueil;

import controlleur.admin.accueil.AccueilControlleur;
import vue.common.CustomColor;
import vue.common.CustomScrollBarUI;
import vue.common.MaFont;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class VueAccueil extends JPanel {

	private static final long serialVersionUID = 1L;

	private JList<LigneMatche> listeMatches;

	private JList<LigneTournoi> listeTournois;

	private JList<LigneEquipe> listeEquipes;
	private AccueilControlleur controlleur;

	private JButton boutonImprimer;

	/**
	 * Create the panel.
	 *
	 * @param equipes  liste des équipes à afficher
	 * @param tournois liste des tournois à afficher
	 * @param matches  liste des matchs à afficher
	 */
	public VueAccueil(ListModel<LigneEquipe> equipes, ListModel<LigneTournoi> tournois, ListModel<LigneMatche> matches) {
		setOpaque(false);


		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0};
		setLayout(gridBagLayout);
		JPanel panelClassement = new JPanel();
		panelClassement.setPreferredSize(new Dimension(0, 0));
		panelClassement.setBorder(BorderFactory.createLineBorder(CustomColor.ROSE_CONTOURS, 2));
		panelClassement.setBackground(CustomColor.BACKGROUND_MAIN);
		GridBagConstraints gbc_panelClassement = new GridBagConstraints();
		gbc_panelClassement.weighty = 0.3;
		gbc_panelClassement.insets = new Insets(0, 0, 20, 0);
		gbc_panelClassement.fill = GridBagConstraints.BOTH;
		gbc_panelClassement.gridx = 0;
		gbc_panelClassement.gridy = 0;
		add(panelClassement, gbc_panelClassement);
		GridBagLayout gbl_panelClassement = new GridBagLayout();
		gbl_panelClassement.columnWidths = new int[]{0, 0};
		gbl_panelClassement.rowHeights = new int[]{0};
		gbl_panelClassement.columnWeights = new double[]{1.0, 0.0};
		gbl_panelClassement.rowWeights = new double[]{0.0, 1.0};
		panelClassement.setLayout(gbl_panelClassement);

		JLabel labelTitreClassement = new JLabel("Classement des équipes de la saison actuelle");
		labelTitreClassement.setFont(MaFont.getFontTitre1());
		labelTitreClassement.setForeground(CustomColor.BLANC);
		GridBagConstraints gbcLabelTitreClassement = new GridBagConstraints();
		gbcLabelTitreClassement.insets = new Insets(0, 20, 5, 5);
		gbcLabelTitreClassement.weighty = 0.2;
		gbcLabelTitreClassement.weightx = 1.0;
		gbcLabelTitreClassement.fill = GridBagConstraints.BOTH;
		gbcLabelTitreClassement.gridx = 0;
		gbcLabelTitreClassement.gridy = 0;
		panelClassement.add(labelTitreClassement, gbcLabelTitreClassement);

		boutonImprimer = new JButton(new ImageIcon("assets/imprimante.png"));
		boutonImprimer.setFocusPainted(false);
		boutonImprimer.setContentAreaFilled(false);
		boutonImprimer.setBorder(null);
		GridBagConstraints gbcBoutonImprimert = new GridBagConstraints();
		gbcBoutonImprimert.fill = GridBagConstraints.BOTH;
		gbcBoutonImprimert.insets = new Insets(0, 0, 0, 5);
		gbcBoutonImprimert.gridx = 1;
		gbcBoutonImprimert.gridy = 0;
		panelClassement.add(boutonImprimer, gbcBoutonImprimert);

		listeEquipes = new JList<>(equipes);
		listeEquipes.setCellRenderer(new EquipeCellRenderer());
		listeEquipes.setBackground(CustomColor.BACKGROUND_MAIN);

		JScrollPane scrollPaneEquipe = new JScrollPane(listeEquipes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneEquipe.setWheelScrollingEnabled(true);
		scrollPaneEquipe.setBorder(BorderFactory.createMatteBorder(0, 20, 10, 20, CustomColor.BACKGROUND_MAIN));
		GridBagConstraints gbcScrollPaneEquipe = new GridBagConstraints();
		gbcScrollPaneEquipe.insets = new Insets(0, 0, 0, 5);
		gbcScrollPaneEquipe.fill = GridBagConstraints.BOTH;
		gbcScrollPaneEquipe.gridx = 0;
		gbcScrollPaneEquipe.gridy = 1;
		panelClassement.add(scrollPaneEquipe, gbcScrollPaneEquipe);

		JScrollBar scrollBarEquipe = scrollPaneEquipe.getVerticalScrollBar();

		scrollBarEquipe.setForeground(CustomColor.ROSE_CONTOURS);
		scrollBarEquipe.setUI(new CustomScrollBarUI());

		JPanel panelBas = new JPanel();
		panelBas.setOpaque(false);
		GridBagConstraints gbc_panelBas = new GridBagConstraints();
		gbc_panelBas.weighty = 0.7;
		gbc_panelBas.fill = GridBagConstraints.BOTH;
		gbc_panelBas.gridx = 0;
		gbc_panelBas.gridy = 1;
		add(panelBas, gbc_panelBas);
		GridBagLayout gbl_panelBas = new GridBagLayout();
		gbl_panelBas.columnWidths = new int[]{0, 0, 0};
		gbl_panelBas.rowHeights = new int[]{0};
		gbl_panelBas.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panelBas.rowWeights = new double[]{1.0};
		panelBas.setLayout(gbl_panelBas);

		JPanel panelTournois = new JPanel();
		panelTournois.setBackground(CustomColor.BACKGROUND_MAIN);
		panelTournois.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_panelTournois = new GridBagConstraints();
		gbc_panelTournois.insets = new Insets(0, 0, 0, 10);
		gbc_panelTournois.weightx = 0.5;
		gbc_panelTournois.fill = GridBagConstraints.BOTH;
		gbc_panelTournois.gridx = 0;
		gbc_panelTournois.gridy = 0;
		panelBas.add(panelTournois, gbc_panelTournois);
		GridBagLayout gbl_panelTournois = new GridBagLayout();
		gbl_panelTournois.columnWidths = new int[]{0};
		gbl_panelTournois.rowHeights = new int[]{0, 0};
		gbl_panelTournois.columnWeights = new double[]{1.0};
		gbl_panelTournois.rowWeights = new double[]{0.0, 1.0};
		panelTournois.setLayout(gbl_panelTournois);
		panelTournois.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(CustomColor.ROSE_CONTOURS, 2), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		JLabel labelTitreTournois = new JLabel("Tournois");
		labelTitreTournois.setForeground(CustomColor.BLANC);
		labelTitreTournois.setFont(MaFont.getFontTitre1());
		GridBagConstraints gbcLabelTitreTournois = new GridBagConstraints();
		gbcLabelTitreTournois.fill = GridBagConstraints.BOTH;
		gbcLabelTitreTournois.insets = new Insets(5, 20, 0, 0);
		gbcLabelTitreTournois.gridx = 0;
		gbcLabelTitreTournois.gridy = 0;
		panelTournois.add(labelTitreTournois, gbcLabelTitreTournois);

		listeTournois = new JList<LigneTournoi>(tournois);
		listeTournois.setCellRenderer(new TournoiCellRenderer());
		listeTournois.setBackground(CustomColor.TRANSPARENT);


		JScrollPane scrollPaneTournois = new JScrollPane(listeTournois, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTournois.setWheelScrollingEnabled(true);
		scrollPaneTournois.setOpaque(false);
		scrollPaneTournois.setBorder(null);
		GridBagConstraints gbcScrollPaneTournois = new GridBagConstraints();
		gbcScrollPaneTournois.fill = GridBagConstraints.BOTH;
		gbcScrollPaneTournois.insets = new Insets(0, 20, 0, 0);
		gbcScrollPaneTournois.gridx = 0;
		gbcScrollPaneTournois.gridy = 1;
		panelTournois.add(scrollPaneTournois, gbcScrollPaneTournois);

		scrollPaneTournois.getVerticalScrollBar().setUI(new CustomScrollBarUI());

		JPanel panelMatchs = new JPanel();
		panelMatchs.setBackground(CustomColor.BACKGROUND_MAIN);
		panelMatchs.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_panelMatchs = new GridBagConstraints();
		gbc_panelMatchs.insets = new Insets(0, 10, 0, 0);
		gbc_panelMatchs.weightx = 0.5;
		gbc_panelMatchs.fill = GridBagConstraints.BOTH;
		gbc_panelMatchs.gridx = 1;
		gbc_panelMatchs.gridy = 0;
		panelBas.add(panelMatchs, gbc_panelMatchs);
		GridBagLayout gblPanelMatch = new GridBagLayout();
		gblPanelMatch.columnWidths = new int[]{0};
		gblPanelMatch.rowHeights = new int[]{0, 0};
		gblPanelMatch.columnWeights = new double[]{1.0};
		gblPanelMatch.rowWeights = new double[]{0.0, 1.0};
		panelMatchs.setLayout(gblPanelMatch);

		listeMatches = new JList<LigneMatche>(matches);
		listeMatches.setCellRenderer(new MatchCellRenderer());
		listeMatches.setBackground(CustomColor.TRANSPARENT);

		JScrollPane scrollPaneMatch = new JScrollPane(listeMatches, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		GridBagConstraints gbcScrollPaneMatchs = new GridBagConstraints();
		gbcScrollPaneMatchs.fill = GridBagConstraints.BOTH;
		gbcScrollPaneMatchs.insets = new Insets(0, 20, 0, 0);
		gbcScrollPaneMatchs.gridx = 0;
		gbcScrollPaneMatchs.gridy = 1;
		panelMatchs.add(scrollPaneMatch, gbcScrollPaneMatchs);
		scrollPaneMatch.getVerticalScrollBar().setUI(new CustomScrollBarUI());
		scrollPaneMatch.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
		scrollPaneMatch.setOpaque(false);
		scrollPaneMatch.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		JLabel labelTitreMatch = new JLabel("Derniers Matchs");
		labelTitreMatch.setForeground(CustomColor.BLANC);
		labelTitreMatch.setFont(MaFont.getFontTitre1());
		labelTitreMatch.setHorizontalAlignment(SwingConstants.LEADING);
		GridBagConstraints gbcLabelTitreMatch = new GridBagConstraints();
		gbcLabelTitreMatch.fill = GridBagConstraints.BOTH;
		gbcLabelTitreMatch.insets = new Insets(5, 20, 0, 0);
		gbcLabelTitreMatch.gridx = 0;
		gbcLabelTitreMatch.gridy = 0;
		panelMatchs.add(labelTitreMatch, gbcLabelTitreMatch);
		panelMatchs.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(CustomColor.ROSE_CONTOURS, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));

	}

	public VueAccueil() {
		this(new DefaultListModel<LigneEquipe>(), new DefaultListModel<LigneTournoi>(), new DefaultListModel<LigneMatche>());
	}

	public void setListeEquipes(DefaultListModel<LigneEquipe> equipes) {
		this.listeEquipes.setModel(equipes);
	}

	public void setListeTournois(DefaultListModel<LigneTournoi> tournois) {

		this.listeTournois.setModel(tournois);
	}

	public void setControlleur(AccueilControlleur controlleur) {
		this.controlleur = controlleur;
		this.boutonImprimer.addActionListener(controlleur);
	}

	public void setListeMatches(DefaultListModel<LigneMatche> matches) {
		this.listeMatches.setModel(matches);
	}

	public JButton getBoutonImprimer() {
		return this.boutonImprimer;
	}


	public void updateControlleur() {
		this.controlleur.update();
	}

}
