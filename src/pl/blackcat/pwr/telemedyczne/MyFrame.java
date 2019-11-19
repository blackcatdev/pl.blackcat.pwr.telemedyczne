package pl.blackcat.pwr.telemedyczne;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Vector;

class MyFrame extends JFrame implements ActionListener {


	int x, y;
	int xsize = 800;
	int ysize = 600;

	String[] allowedTemperatures = {"33", "34", "35", "36", "37", "38", "39", "40", "41", "42"};
	String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	JComboBox<String> temperature = new JComboBox<>(allowedTemperatures);
	JComboBox<String> decimalTemperature = new JComboBox<>(digits);
	JComboBox<String> painLevelBox = new JComboBox<>(digits);


	Patient patient = new Patient();

	Vector listOfOperationsVector;
	Vector listOfOperationsVectorHelper;

	JLabel chooseMode = new JLabel("Jesteś pacjentem czy lekarzem?");
	JButton patientMode = new JButton("Pacjentem");
	JButton doctorMode = new JButton("Lekarzem");
	JLabel enterData = new JLabel("Podaj swój pesel i hasło");
	JLabel enterUsername = new JLabel("Podaj pesel:");
	JLabel enterPassword = new JLabel("Podaj hasło:");
	JTextArea usernameField = new JTextArea();
	JPasswordField passwordField = new JPasswordField();
	JButton login = new JButton("Zaloguj");
	JLabel chooseOperation = new JLabel("Wybierz operację:");
	JButton okayOperation = new JButton("OK");
	JList listOfOperations = new JList();
	JLabel enterObservationData = new JLabel("<html>&emsp;Wprowadź dane o obserwacji,<br>wybierając wartości z pól poniżej</html>", SwingConstants.CENTER);
	JLabel enterTemperature = new JLabel("Podaj temperaturę: ");
	JLabel enterPainLevel = new JLabel("Podaj stopień bólu: ");
	JButton sendToDoctor = new JButton("Zapisz i wyślij obserwację do lekarza");

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
		patientMode.addActionListener(this);
		patientMode.setBounds(250, 50, 300, 50);
		patientMode.setFont(header);
		add(patientMode);

		//doctor button
		doctorMode.addActionListener(this);
		doctorMode.setBounds(250, 120, 300, 50);
		doctorMode.setFont(header);
		add(doctorMode);

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
		if (actionEvent.getSource() == patientMode) {
			remove(chooseMode);
			remove(patientMode);
			remove(doctorMode);
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

				listOfOperationsVector = patient.showOperationstoGui();
				listOfOperationsVectorHelper = new Vector<Integer>();
				for (int i = 1; i < listOfOperationsVector.size(); i = i + 2) {
					listOfOperationsVectorHelper.add("Operacja o ID: " + listOfOperationsVector.get(i - 1).toString() + " z dnia: " + listOfOperationsVector.get(i).toString());
				}

				chooseOperation.setFont(header);
				chooseOperation.setBounds(220, 0, 600, 40);


				listOfOperations = new JList(listOfOperationsVectorHelper);
				listOfOperations.setFont(dataAcquire);
				listOfOperations.setBounds(150, 50, 400, 200);


				okayOperation.setFont(header);
				okayOperation.setBounds(280, 270, 150, 50);
				okayOperation.addActionListener(this);

				add(chooseOperation);
				add(listOfOperations);
				add(okayOperation);
				repaint();
			}
		}

		if (actionEvent.getSource() == okayOperation) {
			int ID_Operacji = Integer.parseInt((listOfOperationsVector.get(listOfOperations.getSelectedIndex() * 2)).toString());
			patient.setOperationID(ID_Operacji);

			while (patient.recommendationExistsGUI()) {
				JOptionPane.showMessageDialog(null, patient.getRecommendations());
			}
			UIManager.put("OptionPane.yesButtonText", "Tak");
			UIManager.put("OptionPane.noButtonText", "Nie");
			UIManager.put("OptionPane.cancelButtonText", "Anuluj");
			JOptionPane.showMessageDialog(null, "Nie masz żadnych oczekujących zaleceń.");
			int response = JOptionPane.showConfirmDialog(null, "Czy chcesz dodać nową obserwację dla lekarza?", "Pytanie", JOptionPane.YES_NO_CANCEL_OPTION);

			if (response == 0) {
				remove(chooseOperation);
				remove(listOfOperations);
				remove(okayOperation);

				enterObservationData.setFont(header);
				enterObservationData.setBounds(0, 0, 800, 80);

				enterTemperature.setBounds(250, 100, 180, 25);
				enterTemperature.setFont(dataShow);

				enterPainLevel.setBounds(250, 140, 180, 25);
				enterPainLevel.setFont(dataShow);


				temperature.setBounds(440, 100, 40, 25);
				temperature.setFont(dataShow);

				JLabel dot = new JLabel(".");
				dot.setBounds(480, 100, 5, 25);
				dot.setFont(dataShow);

				decimalTemperature.setBounds(485, 100, 40, 25);
				decimalTemperature.setFont(dataShow);

				painLevelBox.setBounds(440, 140, 40, 25);
				painLevelBox.setFont(dataShow);


				sendToDoctor.setBounds(200, 180, 400, 50);
				sendToDoctor.setFont(dataShow);
				sendToDoctor.addActionListener(this);

				add(enterObservationData);
				add(enterTemperature);
				add(enterPainLevel);
				add(temperature);
				add(dot);
				add(decimalTemperature);
				add(painLevelBox);
				add(sendToDoctor);
				repaint();


			} else {
				closeProgram();

			}
		}

		if (actionEvent.getSource() == sendToDoctor) {
			float temperatureLevel = Float.parseFloat(temperature.getSelectedItem() + "." + decimalTemperature.getSelectedItem());
			int painLevel = Integer.parseInt((String) Objects.requireNonNull(painLevelBox.getSelectedItem()));
			patient.saveNewObservationGUI(temperatureLevel, painLevel);
			closeProgram();

		}


	}


	void getDimensions() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - xsize) / 2);
		y = (int) ((dimension.getHeight() - ysize) / 2);
	}

	void closeProgram() {
		patient.closeDatabase();
		JOptionPane.showMessageDialog(null, "Zadania wykonane pomyślnie. Życzymy dużo zdrowia!");
		System.exit(0);
	}
}