package pl.blackcat.pwr.telemedyczne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFrame extends JFrame implements ActionListener {

	int x, y;
	int xsize = 800;
	int ysize = 600;


	public MyFrame(String title) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		//Window settings
		super(title);
		getDimensions();
		setLocation(x, y);
		setSize(xsize, ysize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		JLabel chooseMode = new JLabel("Jesteś pacjentem czy lekarzem?");
		Font font = new Font("Noto Serif", Font.BOLD, 26);
		chooseMode.setFont(font);
		chooseMode.setBounds(180, 0, 600, 40);
		add(chooseMode);

		JButton patient = new JButton("Pacjentem");
		patient.addActionListener(this);
		patient.setBounds(250, 50, 300, 50);
		patient.setFont(font);
		add(patient);

		JButton doctor = new JButton("Lekarzem");
		doctor.addActionListener(this);
		doctor.setBounds(250, 120, 300, 50);
		doctor.setFont(font);
		add(doctor);

		setVisible(true);


	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		System.out.println("Działam!");
	}

	void getDimensions() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - xsize) / 2);
		y = (int) ((dimension.getHeight() - ysize) / 2);
	}
}