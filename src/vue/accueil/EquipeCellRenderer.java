package vue.accueil;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class EquipeCellRenderer implements ListCellRenderer<Object[]> {


	@Override
	public Component getListCellRendererComponent(JList<? extends Object[]> list, Object[] value, int index,
			boolean isSelected, boolean cellHasFocus) {
	
		PanelEquipeClassement equipe = new PanelEquipeClassement((String) value[0],(String) value[1],(String) value[2],(String) value[3]);
		equipe.setOpaque(false);
		equipe.setBorder(BorderFactory.createCompoundBorder(equipe.getBorder(),
				BorderFactory.createEmptyBorder(0,0,15,0)));
        return equipe;
        
	}

}
