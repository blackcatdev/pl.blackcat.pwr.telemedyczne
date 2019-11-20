package pl.blackcat.pwr.telemedyczne;

import javax.swing.*;
import java.awt.*;

class ListOfMedicineRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		Font dataShow = new Font("Noto Serif", Font.BOLD, 16);

		if (index == Medicine.ID_Alergii - 1)
			c.setBackground(Color.red);
		if (index == Medicine.ID_Stalego_Leku - 1)
			c.setFont(dataShow);
		return c;
	}
}

