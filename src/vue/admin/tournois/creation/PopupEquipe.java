package vue.admin.tournois.creation;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;

import modele.Equipe;

import javax.swing.JComboBox;

import vue.common.CustomColor;
import vue.common.MaFont;



public class PopupEquipe extends JFrame {

	private JComboBox<Equipe> c;

	public interface ActionHandler {
		void handleAction();
	}

	public PopupEquipe(String title,List<Equipe> equipes, ActionHandler actionHandler) {
		super(title);
		setType(Type.UTILITY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(400, 200));

		ImageIcon icon = new ImageIcon(("assets/logo.png"));
		setIconImage(icon.getImage());

		JPanel panel = createPanel(actionHandler,equipes);
		add(panel);

		pack();
		setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
		setVisible(true);
	}

	private JPanel createPanel(ActionHandler actionHandler, List<Equipe> equipes) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(new Color(15, 3, 25));

		JLabel label = new JLabel("Saisir le pseudo :");
		label.setForeground(Color.white);
		label.setHorizontalAlignment(SwingConstants.CENTER); // Centre le texte
		label.setFont(MaFont.getFontTitre3()); // Agrandir la police
		panel.add(label, BorderLayout.NORTH);

		
		DefaultComboBoxModel<Equipe> model = new DefaultComboBoxModel<Equipe>();
		model.addAll(equipes);
		
		c = new JComboBox<Equipe>(model);
		
		panel.add(c, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setBackground(new Color(15, 3, 25));
		JButton closeButton = new JButton("Annuler");
		closeButton.setFont(MaFont.getFontTitre4());
		closeButton.setForeground(CustomColor.BLANC);
		closeButton.setBackground(CustomColor.TRANSPARENT);
		closeButton.setOpaque(false);
		closeButton.setFocusable(false);
		closeButton.addActionListener(e -> dispose());
		buttonPanel.add(closeButton);

		JButton okButton = new JButton("Valider");
		okButton.setFont(MaFont.getFontTitre4());
		okButton.setForeground(CustomColor.BLANC);
		okButton.setBackground(CustomColor.TRANSPARENT);
		okButton.setOpaque(false);
		okButton.setFocusable(false);
		okButton.addActionListener(e -> {
			if (actionHandler != null) {
				actionHandler.handleAction();
			}
			dispose();
		});
		buttonPanel.add(okButton);

		panel.add(buttonPanel, BorderLayout.SOUTH);

		return panel;
	}

	public Equipe getSaisie() {
		return (Equipe) c.getSelectedItem();
	}
}