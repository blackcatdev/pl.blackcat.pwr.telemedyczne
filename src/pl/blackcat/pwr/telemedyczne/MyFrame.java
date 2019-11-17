package pl.blackcat.pwr.telemedyczne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class MyFrame extends JFrame implements ActionListener {

	int x, y;
	int xsize = 800;
	int ysize = 600;
	JLabel chooseMode = new JLabel("Jesteś pacjentem czy lekarzem?");
	JButton patient = new JButton("Pacjentem");
	JButton doctor = new JButton("Lekarzem");
	JLabel enterData = new JLabel("Podaj swój pesel i hasło");
	JLabel enterUsername = new JLabel("Podaj pesel:");
	JLabel enterPassword = new JLabel("Podaj hasło:");
	JTextArea usernameField = new JTextArea();
	JPasswordField passwordField = new JPasswordField();
	JButton login = new JButton("Zaloguj");
	JLabel chooseOperation = new JLabel("Wybierz operację:");
	JButton okayOperation = new JButton("OK");

	//fonts
	Font header = new Font("Noto Serif", Font.BOLD, 26);
	Font dataShow = new Font("Noto Serif", Font.BOLD, 16);
	Font dataAcquire = new Font("Noto Serif", Font.PLAIN, 16);


	public void MainWindow(String title) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
		//Window settings
		setTitle(title);
		getDimensions();
		setLocation(x, y);
		setSize(xsize, ysize);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());


		//chooseMode label
		chooseMode.setFont(header);
		chooseMode.setBounds(180, 0, 600, 40);
		add(chooseMode);

		//patient button
		patient.addActionListener(this);
		patient.setBounds(250, 50, 300, 50);
		patient.setFont(header);
		add(patient);

		//doctor button
		doctor.addActionListener(this);
		doctor.setBounds(250, 120, 300, 50);
		doctor.setFont(header);
		add(doctor);

		//enterData label
		enterData.setFont(header);
		enterData.setBounds(230, 4, 600, 40);

		//Patient data
		enterUsername.setBounds(270, 50, 100, 25);
		enterUsername.setFont(dataShow);

		enterPassword.setBounds(270, 90, 100, 25);
		enterPassword.setFont(dataShow);

		usernameField.setBounds(380, 50, 120, 30);
		usernameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		usernameField.setFont(dataAcquire);

		passwordField.setBounds(380, 90, 120, 30);
		passwordField.setBorder(usernameField.getBorder());
		passwordField.setFont(dataAcquire);
		passwordField.addActionListener(this);

		//login button
		login.setBounds(310, 130, 150, 50);
		login.setFont(header);
		login.addActionListener(this);

		//choosing operation
		chooseOperation.setFont(header);
		chooseOperation.setBounds(180, 0, 600, 40);


		setVisible(true);


	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		//button "patient" click
		if (actionEvent.getSource() == patient) {
			remove(chooseMode);
			remove(patient);
			remove(doctor);
			add(enterData);
			add(enterUsername);
			add(enterPassword);
			add(usernameField);
			add(passwordField);
			add(login);
			repaint();
		}

		//button "zaloguj" click
		if (actionEvent.getSource() == login || actionEvent.getSource() == passwordField) {
			Patient patient = new Patient();
			String pesel, password;
			pesel = usernameField.getText();
			password = passwordField.getText();
			if (patient.CheckData(pesel, password) == false) {
				JOptionPane.showMessageDialog(null, "Podałeś błędne dane lub nie miałeś żadnej operacji!");
			} else {
				JOptionPane.showMessageDialog(null, "Dane poprawne!");
				remove(enterData);
				remove(enterUsername);
				remove(enterPassword);
				remove(usernameField);
				remove(passwordField);
				remove(login);

				Vector listOfOperationsVector = patient.showOperationstoGui();
				Vector listOfOperationsVectorHelper = new Vector<Integer>();
				for (int i = 1; i < listOfOperationsVector.size(); i = i + 2) {
					listOfOperationsVectorHelper.add("Operacja o ID: " + listOfOperationsVector.get(i - 1).toString() + " z dnia: " + listOfOperationsVector.get(i).toString());
				}

				chooseOperation.setFont(header);
				chooseOperation.setBounds(220, 0, 600, 40);


				JList listOfOperations = new JList(listOfOperationsVectorHelper);
				listOfOperations.setFont(dataAcquire);
				listOfOperations.setBounds(150, 50, 400, 200);

				okayOperation.setFont(header);
				okayOperation.setBounds(280, 270, 150, 50);

				add(chooseOperation);
				add(listOfOperations);
				add(okayOperation);
				repaint();
			}
		}


	}


	void getDimensions() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - xsize) / 2);
		y = (int) ((dimension.getHeight() - ysize) / 2);
	}
}