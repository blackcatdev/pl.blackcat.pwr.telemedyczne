package pl.blackcat.pwr.telemedyczne;

import pl.blackcat.zadaniajava.pesel.checkPesel;

class Patient extends Human {
	int ID_Operacji;
	float min_temp = 33;
	float max_temp = 43;
	float temperature;
	int pain;

	void main() {
		//zdobądź dane pacjenta i sprawdź ich poprawność
		acquirePesel();

		//Wyświetl operacje, w których uczestniczył pacjent i pozwól mu wybrać jedną
		ID_Operacji = showOperations();

		//Sprawdź, czy istnieją niezatwierdzone obserwacje


		//Zdobądź dane o obserwacji
		newObservation();

		//Zapisz dane obserwacji w bazie danych
		saveNewObservation();

		//zamknij bazę
		patientBase.closeConnection();

	}

	private void saveNewObservation() {
		patientBase.insertNewRow(ID_Operacji,temperature,pain);

	}

	private void newObservation() {
		System.out.println("\nWybrana operacja: " + ID_Operacji + "\n");
		do {
			System.out.print("Podaj swoją obecną temperaturę: ");
			temperature = getFloat(scanner);
		}
		while (!checkValue(temperature, min_temp, max_temp));

		do {
			System.out.print("Podaj swój stopień bólu w skali 1-10: ");
			pain = getInteger(scanner);
		}
		while (!checkValue(pain, 1, 10));

		System.out.println("Twoja temperatura: " + temperature);
		System.out.println("Twój poziom bólu: " + pain);


	}

	private int showOperations() {
		int chosenOperation = patientBase.showQuery("SELECT ID_Operacji, Data FROM Operacje WHERE ID_Pacjenta = " + pesel, 2);

		if (chosenOperation == -1) {
			System.out.println("Nie miałeś żadnej operacji. Zamykam program.");
			System.exit(3);
			return 0;
		} else {
			System.out.print("\nWybierz operację z listy powyżej: ");
			chosenOperation = scanner.nextInt();
			if (patientBase.singleQuery("SELECT ID_Operacji FROM Operacje WHERE ID_Pacjenta = " + pesel + " AND ID_Operacji = " + chosenOperation) == 0)
				return chosenOperation;
			else {
				System.out.println("Wybrałeś błędny numer operacji. Zamykam program.");
				System.exit(5);
			}

		}

		return -1;

	}

	private void acquirePesel() {

		System.out.print("Podaj swój pesel: ");
		pesel = scanner.nextLine();
		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = patientBase.singleQuery("SELECT ID_Pacjenta FROM Operacje WHERE ID_Pacjenta = " + pesel);
			if (peselStatus == 0) {
				acquirePassword();
			} else {
				System.out.println("Nie miałeś żadnej operacji. Zamykam program.");
				System.exit(3);
			}

		} else {
			System.out.println("Podano błędny pesel (z definicji). Zamykam program.");
			System.exit(2);

		}


	}

	private void acquirePassword() {
		int passwordStatus;
		int i;
		for (i = 0; i < 3; i++) {
			System.out.print("Podaj swoje hasło: ");
			password = scanner.nextLine();
			passwordStatus = patientBase.singleQuery("SELECT Hasło FROM Pacjenci WHERE PESEL = " + pesel + " AND HASŁO = \'" + password + "\'");
			if (passwordStatus == 0) {
				System.out.println("Hasło poprawne");
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
}
