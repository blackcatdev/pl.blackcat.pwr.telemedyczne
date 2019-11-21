package pl.blackcat.pwr.telemedyczne;

import pl.blackcat.zadaniajava.pesel.checkPesel;

import java.util.Vector;

class Doctor extends Human {
	private int ID_Obserwacji;
	private int ID_Leku;
	private String patientPesel;

	void main() {
		//zdobądź dane lekarza i sprawdź ich poprawność
		//przeniesiono do GUI
		acquirePesel();


		//Sprawdź, czy do którejś operacji prowadzonej przez lekarza istnieją niezatwierdzone obserwacje
		//przeniesiono do GUI
		while (true) {
			System.out.println("\n");
			ID_Obserwacji = showOperations();

			//Lekarz wprowadza zalecenia do niezatwierdzonej obserwacji a następnie ją zatwierdza
			acceptObservations();
		}


	}

	private void acceptObservations() {
		patientPesel = healthBase.getStringQuery("SELECT Operacje.ID_Pacjenta FROM Operacje, Obserwacje WHERE Obserwacje.ID_Operacji = Operacje.ID_Operacji AND Obserwacje.ID_Obserwacji = " + ID_Obserwacji);
		System.out.println("\nWybrałeś obserwację: " + ID_Obserwacji);
		System.out.println("Pacjent zgłosił następujący stan: ");
		System.out.println("ID_Obserwacji\t\tTemperatura\t\t\t\tSiła bólu");
		healthBase.showQuery("SELECT ID_Obserwacji, Temperatura, Siła_Bólu FROM Obserwacje, Operacje WHERE ID_Lekarza = " + pesel + " AND Obserwacje.ID_Operacji = Operacje.ID_Operacji AND Obserwacje.ID_Obserwacji = " + ID_Obserwacji, 3);
		ID_Leku = healthBase.getIntQuery("SELECT ID_Leku FROM Leki, Pacjenci WHERE Pacjenci.ID_Stalego_Leku = Leki.ID_Leku AND Pacjenci.PESEL = " + patientPesel);
		if (ID_Leku != 1) {
			System.out.print("\nPacjent przyjmuje stały lek: ");
			healthBase.showQuery("SELECT NAZWA_I_DAWKA_LEKU FROM Leki, Pacjenci WHERE Pacjenci.ID_Stalego_Leku = Leki.ID_Leku AND Pacjenci.PESEL = " + patientPesel, 1);
			System.out.print("\nCzy nadal powinien go stosować? (t): ");
			if (scanner.next().charAt(0) != 't')
				ID_Leku = 1;
		}
		System.out.print("\nPodaj zalecenia dla pacjenta: ");
		scanner.nextLine();
		String zalecenia = scanner.nextLine();
		if (ID_Leku == 1) {
			chooseMedicine();
		}
		healthBase.acceptObservation(ID_Obserwacji, zalecenia, ID_Leku);


	}

	void acceptObservationsGUI(String zalecenia, int ID_Leku) {
		healthBase.acceptObservation(ID_Obserwacji, zalecenia, ID_Leku);
	}

	private void chooseMedicine() {
		System.out.println("\nDostępne leki dla pacjenta z wykluczeniem leku, na który ma alergię:");
		int ID_Alergii = healthBase.getIntQuery("SELECT ID_Alergii_Na_Lek FROM Pacjenci WHERE PESEL = " + patientPesel);
		System.out.println("ID_Leku\t\t\t\tNazwa i dawka leku");
		healthBase.showQuery("SELECT ID_Leku, Nazwa_i_Dawka_Leku FROM Leki WHERE ID_Leku <> " + Integer.toString(ID_Alergii), 2);

		do {
			System.out.print("\nKtóry lek przepisać pacjentowi: ");
			ID_Leku = getInteger(scanner);
			if (ID_Leku == ID_Alergii) {
				System.out.print("Czy na pewno chcesz przepisać pacjentowi lek, na który ma alergię? (t): ");
				if (scanner.next().charAt(0) != 't')
					ID_Leku = -1;
			}
		}
		while (!checkValue(ID_Leku, 1, 6));


	}

	private void acquirePesel() {

		System.out.print("Podaj swój pesel: ");
		pesel = scanner.nextLine();
		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = healthBase.singleQuery("SELECT ID_Lekarza FROM Operacje WHERE ID_Lekarza = " + pesel);
			if (peselStatus == 0) {
				acquirePassword();
			} else {
				System.out.println("Nie prowadziłeś żadnej operacji. Zamykam program.");
				System.exit(3);
			}

		} else {
			System.out.println("Podano błędny pesel (z definicji). Zamykam program.");
			System.exit(2);

		}


	}

	boolean CheckData(String pesel, String password) {
		this.pesel = pesel;
		this.password = password;

		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = healthBase.singleQuery("SELECT ID_Lekarza FROM Operacje WHERE ID_Lekarza = " + pesel);
			if (peselStatus == 0) {
				return checkPassword(password);
			} else {
				return false;
			}

		} else {
			return false;

		}

	}

	private boolean checkPassword(String password) {
		int passwordStatus = healthBase.singleQuery("SELECT Hasło FROM Lekarze WHERE PESEL = " + pesel + " AND HASŁO = \'" + password + "\'");
		return passwordStatus == 0;
	}

	private void acquirePassword() {
		int passwordStatus;
		int i;
		for (i = 0; i < 3; i++) {
			System.out.print("Podaj swoje hasło: ");
			password = scanner.nextLine();
			passwordStatus = healthBase.singleQuery("SELECT Hasło FROM Lekarze WHERE PESEL = " + pesel + " AND Hasło = \'" + password + "\'");
			if (passwordStatus == 0) {
				System.out.println("Hasło poprawne.");
				break;
			} else {
				System.out.println("Hasło niepoprawne.");
			}

		}
		if (i == 3) {
			System.out.println("Podałeś zbyt dużą ilość razy błędne hasło. Zamykam program.");
			System.exit(4);
		}
	}

	private int showOperations() {
		System.out.println("ID_Obserwacji\t\tID_Operacji\t\t\t\t\t\t\tData");
		int chosenObservation = healthBase.showQuery("SELECT ID_Obserwacji, ID_Operacji, Data FROM Obserwacje, Operacje WHERE ID_Lekarza = " + pesel + " AND Obserwacje.ID_Operacji = Operacje.ID_Operacji AND Obserwacje.Czy_sprawdzona = false", 3);

		if (chosenObservation == -1) {
			System.out.println("Nie masz żadnej obserwacji do zatwierdzenia. Sprawdź później. Zamykam program.");
			System.exit(0);
			return 0;
		} else {
			System.out.print("\nWybierz niezatwierdzoną obserwację z listy powyżej: ");
			chosenObservation = getInteger(scanner);
			if (healthBase.singleQuery("SELECT ID_Obserwacji FROM Obserwacje, Operacje WHERE ID_Lekarza = " + pesel + " AND ID_Obserwacji = " + chosenObservation + " AND Obserwacje.ID_Operacji = Operacje.ID_Operacji") == 0) {
				return chosenObservation;
			} else {
				System.out.println("Wybrałeś błędny numer operacji. Zamykam program.");
				System.exit(5);
			}

		}


		return -1;

	}

	Vector showOperationstoGui() {
		listofOperations = healthBase.resultsToVector("SELECT ID_Obserwacji, ID_Operacji, Data FROM Obserwacje, Operacje WHERE ID_Lekarza = " + pesel + " AND Obserwacje.ID_Operacji = Operacje.ID_Operacji AND Obserwacje.Czy_sprawdzona = false", 3);
		return listofOperations;
	}

	void setObservationID(int ID_Obserwacji) {
		this.ID_Obserwacji = ID_Obserwacji;
		patientPesel = healthBase.getStringQuery("SELECT Operacje.ID_Pacjenta FROM Operacje, Obserwacje WHERE Obserwacje.ID_Operacji = Operacje.ID_Operacji AND Obserwacje.ID_Obserwacji = " + ID_Obserwacji);
	}

	String getTemperature() {
		float temperature = healthBase.getFloatQuery("SELECT Temperatura FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji) / 10;
		return Float.toString(temperature);

	}

	String getPainLevel() {
		return healthBase.getStringQuery("SELECT Siła_bólu FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji);
	}

	int getPatientMedicine() {
		return healthBase.getIntQuery("SELECT ID_Leku FROM Leki, Pacjenci WHERE Pacjenci.ID_Stalego_Leku = Leki.ID_Leku AND Pacjenci.PESEL = " + patientPesel);
	}

	int getPatientAllergy() {
		return healthBase.getIntQuery("SELECT ID_Alergii_Na_Lek FROM Pacjenci WHERE PESEL = " + patientPesel);
	}

	Vector showMedicine() {
		return healthBase.resultsToVector("SELECT Nazwa_i_Dawka_Leku FROM Leki", 1);
	}
}

