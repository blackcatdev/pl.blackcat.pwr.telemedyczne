package pl.blackcat.pwr.telemedyczne;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Vector;


class MyFrame extends JFrame implements ActionListener {

	int ID_Alergii;


	private int x, y;
	private int xsize = 800;
	private int ysize = 600;


	private String[] allowedTemperatures = {"33", "34", "35", "36", "37", "38", "39", "40", "41", "42"};
	private String[] digits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

	private JComboBox<String> temperature = new JComboBox<>(allowedTemperatures);
	private JComboBox<String> decimalTemperature = new JComboBox<>(digits);
	private JComboBox<String> painLevelBox = new JComboBox<>(digits);


	private Patient patient;
	private Doctor doctor;

	private Vector listOfOperationsVector = new Vector();
	private Vector listOfOperationsVectorHelper = new Vector();

	private JLabel chooseMode = new JLabel("Jesteś pacjentem czy lekarzem?");
	private JButton patientMode = new JButton("Pacjentem");
	private JButton doctorMode = new JButton("Lekarzem");
	private JLabel enterData = new JLabel("Podaj swój pesel i hasło");
	private JLabel enterUsername = new JLabel("Podaj pesel:");
	private JLabel enterPassword = new JLabel("Podaj hasło:");
	private JTextArea usernameField = new JTextArea();
	private JPasswordField passwordFieldP = new JPasswordField();
	private JPasswordField passwordFieldD = new JPasswordField();
	private JButton loginPatient = new JButton("Zaloguj");
	private JLabel chooseOperation = new JLabel("Wybierz operację:");
	private JButton okayOperation = new JButton("OK");
	private JList listOfOperations = new JList();
	private JLabel enterObservationData = new JLabel("<html>&emsp;Wprowadź dane o obserwacji,<br>wybierając wartości z pól poniżej</html>", SwingConstants.CENTER);
	private JLabel enterTemperature = new JLabel("Podaj temperaturę: ");
	private JLabel enterPainLevel = new JLabel("Podaj stopień bólu: ");
	private JButton sendToDoctor = new JButton("Zapisz i wyślij obserwację do lekarza");

	private JButton loginDoctor = new JButton("Zaloguj");
	private JLabel chooseObservation = new JLabel("Wybierz obserwację:");
	private JList listOfObservations;
	private JButton okayObservation = new JButton("OK");
	private JButton okayRecommendation = new JButton("OK");
	private JLabel patientState = new JLabel("Stan pacjenta:");
	private JLabel patientTemperature1 = new JLabel("Temperatura: ");
	private JLabel patientPainLevel1 = new JLabel("Stopień bólu: ");
	private JLabel chooseMedicineQuestion = new JLabel("Który lek ma wziąć pacjent?");
	private JList listOfMedicine;
	private JLabel legendForDoctor = new JLabel("Pogrubioną czcionką zaznaczono stale zażywany lek przez pacjenta a czerwoną obwódką lek, na który pacjent ma alergię.");
	private JLabel enterRecommendations = new JLabel("Wpisz zalecenia dla pacjenta:");
	private JTextArea recommendationForPatient = new JTextArea();


	//fonts
	private Font header = new Font("Noto Serif", Font.BOLD, 26);
	private Font dataShow = new Font("Noto Serif", Font.BOLD, 16);
	private Font dataAcquire = new Font("Noto Serif", Font.PLAIN, 16);
	private Font patientStateFont = new Font("Noto Serif", Font.BOLD, 22);
	private Font smallInfo = new Font("Noto Serif", Font.ITALIC, 10);


	void MainWindow() {
		//Window settings
		setTitle("Telemedica TEST");
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

		usernameField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					usernameField.transferFocus();
					e.consume();
				}
			}
		});


		passwordFieldP.setBounds(380, 90, 120, 30);
		passwordFieldP.setBorder(usernameField.getBorder());
		passwordFieldP.setFont(dataAcquire);
		passwordFieldP.addActionListener(this);

		//loginPatient button
		loginPatient.setBounds(310, 130, 150, 50);
		loginPatient.setFont(header);
		loginPatient.addActionListener(this);

		//loginDoctor button
		loginDoctor.setBounds(310, 130, 150, 50);
		loginDoctor.setFont(header);
		loginDoctor.addActionListener(this);

		//password data field for doctor
		passwordFieldD.setBounds(380, 90, 120, 30);
		passwordFieldD.setBorder(usernameField.getBorder());
		passwordFieldD.setFont(dataAcquire);
		passwordFieldD.addActionListener(this);

		//choosing operation
		chooseOperation.setFont(header);
		chooseOperation.setBounds(180, 0, 600, 40);


		setVisible(true);


	}


	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		//button "patient" click
		if (actionEvent.getSource() == patientMode) {
			patient = new Patient();
			remove(chooseMode);
			remove(patientMode);
			remove(doctorMode);
			add(enterData);
			add(enterUsername);
			add(enterPassword);
			add(usernameField);
			add(passwordFieldP);
			add(loginPatient);
			repaint();
		}

		//button "zaloguj" click
		if (actionEvent.getSource() == loginPatient || actionEvent.getSource() == passwordFieldP) {
			String pesel, password;
			pesel = usernameField.getText();
			password = passwordFieldP.getText();
			if (!patient.CheckData(pesel, password)) {
				JOptionPane.showMessageDialog(null, "Podałeś błędne dane lub nie miałeś żadnej operacji!");
			} else {
				JOptionPane.showMessageDialog(null, "Dane poprawne!");
				remove(enterData);
				remove(enterUsername);
				remove(enterPassword);
				remove(usernameField);
				remove(passwordFieldP);
				remove(loginPatient);

				listOfOperationsVector = patient.showOperationstoGui();
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
		//button "Doctor" click
		if (actionEvent.getSource() == doctorMode) {
			doctor = new Doctor();
			remove(chooseMode);
			remove(patientMode);
			remove(doctorMode);
			add(enterData);
			add(enterUsername);
			add(enterPassword);
			add(usernameField);

			usernameField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_TAB) {
						usernameField.transferFocus();
						e.consume();
					}
				}
			});

			add(passwordFieldD);
			add(loginDoctor);
			repaint();
		}

		//button "Zaloguj" doctor click
		if (actionEvent.getSource() == loginDoctor || actionEvent.getSource() == passwordFieldD) {
			String pesel = usernameField.getText();
			String password = passwordFieldD.getText();
			if (!doctor.CheckData(pesel, password)) {
				JOptionPane.showMessageDialog(null, "Podałeś błędne dane lub nie miałeś żadnej operacji!");
			} else {
				JOptionPane.showMessageDialog(null, "Dane poprawne!");
				remove(enterData);
				remove(enterUsername);
				remove(enterPassword);
				remove(usernameField);
				remove(passwordFieldD);
				remove(loginDoctor);

				listOfOperationsVector.clear();
				listOfOperationsVectorHelper.clear();

				listOfOperationsVector = doctor.showOperationstoGui();
				listOfOperationsVectorHelper = new Vector();
				for (int i = 2; i < listOfOperationsVector.size(); i = i + 3) {
					listOfOperationsVectorHelper.add("Obserwacja o ID " + listOfOperationsVector.get(i - 2).toString() + " dotycząca operacji o ID " + listOfOperationsVector.get(i - 1).toString() + " z dnia: " + listOfOperationsVector.get(i).toString());
				}

				chooseObservation.setFont(header);
				chooseObservation.setBounds(220, 0, 600, 40);

				listOfObservations = new JList(listOfOperationsVectorHelper);
				listOfObservations.setFont(dataAcquire);
				listOfObservations.setBounds(50, 50, 650, 400);

				okayObservation.setFont(header);
				okayObservation.setBounds(280, 470, 150, 50);
				okayObservation.addActionListener(this);

				add(chooseObservation);
				add(listOfObservations);
				add(okayObservation);
				repaint();

			}
		}

		if (actionEvent.getSource() == okayObservation) {
			System.out.println(listOfObservations.getSelectedIndex() * 3);
			System.out.println(listOfOperationsVector.get(listOfObservations.getSelectedIndex() * 3));
			System.out.println("\n");


			int ID_Obserwacji = Integer.parseInt((listOfOperationsVector.get(listOfObservations.getSelectedIndex() * 3)).toString());
			doctor.setObservationID(ID_Obserwacji);

			remove(chooseObservation);
			remove(listOfObservations);
			remove(okayObservation);

			JLabel observationDetails = new JLabel("Zapoznaj się z obserwacją.");
			observationDetails.setBounds(220, 0, 600, 40);
			observationDetails.setFont(header);

			patientState.setBounds(300, 50, 200, 40);
			patientState.setFont(patientStateFont);

			patientTemperature1.setBounds(320, 100, 120, 25);
			patientTemperature1.setFont(dataAcquire);

			JLabel patientTemperature2 = new JLabel(doctor.getTemperature());
			patientTemperature2.setBounds(440, 100, 40, 25);
			patientTemperature2.setFont(dataShow);

			patientPainLevel1.setBounds(320, 135, 120, 25);
			patientPainLevel1.setFont(dataAcquire);

			JLabel patientPainLevel2 = new JLabel(doctor.getPainLevel());
			patientPainLevel2.setBounds(440, 135, 40, 25);
			patientPainLevel2.setFont(dataShow);


			chooseMedicineQuestion.setBounds(230, 170, 350, 40);
			chooseMedicineQuestion.setFont(patientStateFont);

			int ID_Stalego_Leku = doctor.getPatientMedicine();
			ID_Alergii = doctor.getPatientAllergy();

			Medicine.ID_Alergii = ID_Alergii;
			Medicine.ID_Stalego_Leku = ID_Stalego_Leku;

			listOfMedicine = new JList(doctor.showMedicine());
			listOfMedicine.setBounds(230, 220, 330, 160);
			listOfMedicine.setFont(dataAcquire);
			listOfMedicine.setSelectedIndex(ID_Stalego_Leku - 1);
			ListOfMedicineRenderer medicineRenderer = new ListOfMedicineRenderer();
			listOfMedicine.setCellRenderer(medicineRenderer);

			legendForDoctor.setBounds(100, 380, 600, 20);
			legendForDoctor.setFont(smallInfo);

			enterRecommendations.setBounds(270, 410, 340, 25);
			enterRecommendations.setFont(dataShow);

			recommendationForPatient.setBounds(100, 440, 600, 25);
			recommendationForPatient.setFont(dataAcquire);

			okayRecommendation.setBounds(325, 475, 150, 50);
			okayRecommendation.setFont(header);
			okayRecommendation.addActionListener(this);


			add(observationDetails);
			add(patientState);
			add(patientTemperature1);
			add(patientTemperature2);
			add(patientPainLevel1);
			add(patientPainLevel2);
			add(chooseMedicineQuestion);
			add(listOfMedicine);
			add(legendForDoctor);
			add(enterRecommendations);
			add(recommendationForPatient);
			add(okayRecommendation);

			repaint();


		}

		if (actionEvent.getSource() == okayRecommendation) {
			String zalecenia = recommendationForPatient.getText();
			int ID_Leku = listOfMedicine.getSelectedIndex() + 1;
			int orly = 0;
			if (ID_Leku == ID_Alergii) {
				UIManager.put("OptionPane.yesButtonText", "Tak");
				UIManager.put("OptionPane.noButtonText", "Nie");
				UIManager.put("OptionPane.cancelButtonText", "Anuluj");
				orly = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz przepisać pacjentowi lek, na który jest uczulony?");
			}
			if (orly == 0) {
				doctor.acceptObservationsGUI(zalecenia, ID_Leku);
				JOptionPane.showMessageDialog(null, "Zalecenia zostały zapisane pomyślnie. Do zobaczenia!");
				System.exit(0);
			}

		}

	}


	private void getDimensions() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		x = (int) ((dimension.getWidth() - xsize) / 2);
		y = (int) ((dimension.getHeight() - ysize) / 2);
	}

	private void closeProgram() {
		patient.closeDatabase();
		JOptionPane.showMessageDialog(null, "Zadania wykonane pomyślnie. Życzymy dużo zdrowia!");
		System.exit(0);
	}


}