package pl.blackcat.pwr.telemedyczne;

import pl.blackcat.zadaniajava.pesel.checkPesel;

import java.util.Vector;

class Patient extends Human {

	void main() {
		//zdobądź dane pacjenta i sprawdź ich poprawność
		//przeniesiono do GUI
		acquirePesel();

		//Wyświetl operacje, w których uczestniczył pacjent i pozwól mu wybrać jedną
		//przeniesiono do GUI
		ID_Operacji = showOperations();

		//Jeśli istnieją zalecenia dla pacjenta, każ mu się z nimi zapoznać
		//przeniesiono do GUI
		recommendationExist();

		//Zdobądź dane o obserwacji, jeśli zachodzi potrzeba
		//przeniesiono do GUI
		System.out.print("Czy chcesz dodać nową obserwację dla lekarza? (t): ");
		char wantNewObservation = scanner.next().charAt(0);
		if (wantNewObservation == 't') {
			newObservation();

			//Zapisz dane obserwacji w bazie danych
			saveNewObservation();
		}

		//zamknij bazę
		//przeniesiono do GUI
		healthBase.closeConnection();

		//pożegnaj się z pacjentem
		//przeniesiono do GUI
		System.out.println("Zadania wykonane pomyślnie. Życzymy dużo zdrowia!");

	}

	void closeDatabase() {
		healthBase.closeConnection();
	}

	boolean recommendationExistsGUI() {
		return healthBase.getIntQuery("SELECT ID_Obserwacji FROM Obserwacje WHERE ID_Operacji = " + ID_Operacji + " AND Czy_sprawdzona = true AND Czy_odebrana = false") != -1;
	}

	String getRecommendations() {
		int ID_Obserwacji = healthBase.getIntQuery("SELECT ID_Obserwacji FROM Obserwacje WHERE ID_Operacji = " + ID_Operacji + " AND Czy_sprawdzona = true AND Czy_odebrana = false");
		String recommendation = "Lekarz przysłał zalecenia odnośnie obserwacji numer " + ID_Obserwacji + ":\n";
		recommendation = recommendation + healthBase.getStringQuery("SELECT Zalecenia FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji) + "\n";
		int ID_Leku = healthBase.getIntQuery("SELECT ID_Leku FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji);
		if (ID_Leku != 1)
			recommendation = recommendation + "Należy wziąć lek: " + healthBase.getStringQuery("SELECT Nazwa_i_Dawka_Leku FROM Leki WHERE ID_Leku = " + ID_Leku) + "\n";
		recommendation = recommendation + "Zastosuj się do zaleceń i wciśnij klawisz OK";
		healthBase.updateObservation(ID_Obserwacji);
		return recommendation;
	}


	//przeniesiono do GUI
	private void recommendationExist() {
		int ID_Obserwacji = healthBase.getIntQuery("SELECT ID_Obserwacji FROM Obserwacje WHERE ID_Operacji = " + ID_Operacji + " AND Czy_sprawdzona = true AND Czy_odebrana = false");
		if (ID_Obserwacji == -1)
			System.out.println("Nie masz żadnych oczekujących zaleceń.");
		while (ID_Obserwacji != -1) {
			System.out.println("Lekarz przysłał zalecenia odnośnie obserwacji numer " + ID_Obserwacji + ":");
			healthBase.showQuery("SELECT Zalecenia FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji, 1);
			int ID_Leku = healthBase.getIntQuery("SELECT ID_Leku FROM Obserwacje WHERE ID_Obserwacji = " + ID_Obserwacji);
			if (ID_Leku != 1) {
				System.out.println("\nNależy wziąć lek: ");
				healthBase.showQuery("SELECT Nazwa_i_Dawka_Leku FROM Leki WHERE ID_Leku = " + ID_Leku, 1);
			}
			System.out.println("\nZastosuj się do zaleceń i naciśnij ENTER");
			waitForEnter();

			healthBase.updateObservation(ID_Obserwacji);
			ID_Obserwacji = healthBase.getIntQuery("SELECT ID_Obserwacji FROM Obserwacje WHERE ID_Operacji = " + ID_Operacji + " AND Czy_sprawdzona = true AND Czy_odebrana = false");

		}
	}

	void saveNewObservationGUI(float temperatureLevel, int painLevel) {
		healthBase.insertNewObservation(ID_Operacji, temperatureLevel * 10, painLevel, null, 1);
	}

	//przeniesiono do GUI
	private void saveNewObservation() {


	}

	//przeniesiono do GUI
	private void newObservation() {
		System.out.println("\nWybrana operacja: " + ID_Operacji + "\n");

		float temperature;
		float min_temp = 33;
		float max_temp = 43;
		do {
			System.out.print("Podaj swoją obecną temperaturę: ");
			temperature = getFloat(scanner);
		}
		while (!checkValue(temperature, min_temp, max_temp));

		int pain;
		do {
			System.out.print("Podaj swój stopień bólu w skali 1-10: ");
			pain = getInteger(scanner);
		}
		while (!checkValue(pain, 1, 10));

		System.out.println("Twoja temperatura: " + temperature);
		System.out.println("Twój poziom bólu: " + pain);


	}

	//przeniesiono do GUI
	private int showOperations() {
		System.out.println("ID_Operacji\t\tData");
		int chosenOperation = healthBase.showQuery("SELECT ID_Operacji, Data FROM Operacje WHERE ID_Pacjenta = " + pesel, 2);

		if (chosenOperation == -1) {
			System.out.println("Nie miałeś żadnej operacji. Zamykam program.");
			System.exit(3);
			return 0;
		} else {
			System.out.print("\nWybierz operację z listy powyżej: ");
			chosenOperation = scanner.nextInt();
			if (healthBase.singleQuery("SELECT ID_Operacji FROM Operacje WHERE ID_Pacjenta = " + pesel + " AND ID_Operacji = " + chosenOperation) == 0)
				return chosenOperation;
			else {
				System.out.println("Wybrałeś błędny numer operacji. Zamykam program.");
				System.exit(5);
			}

		}

		return -1;

	}

	//przeniesiono do GUI
	private void acquirePesel() {

		System.out.print("Podaj swój pesel: ");
		pesel = scanner.nextLine();
		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = healthBase.singleQuery("SELECT ID_Pacjenta FROM Operacje WHERE ID_Pacjenta = " + pesel);
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

	//przeniesiono do GUI
	private void acquirePassword() {
		int passwordStatus;
		int i;
		for (i = 0; i < 3; i++) {
			System.out.print("Podaj swoje hasło: ");
			password = scanner.nextLine();
			passwordStatus = healthBase.singleQuery("SELECT Hasło FROM Pacjenci WHERE PESEL = " + pesel + " AND HASŁO = \'" + password + "\'");
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

	boolean CheckData(String pesel, String password) {
		this.pesel = pesel;
		this.password = password;

		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = healthBase.singleQuery("SELECT ID_Pacjenta FROM Operacje WHERE ID_Pacjenta = " + pesel);
			if (peselStatus == 0) {
				return checkPassword(password);
			} else {
				return false;
			}

		} else {
			return false;

		}

	}

	Vector showOperationstoGui() {
		listofOperations = healthBase.resultsToVector("SELECT ID_Operacji, Data FROM Operacje WHERE ID_Pacjenta = " + pesel, 2);
		return listofOperations;
	}

	void setOperationID(int Operation_ID) {
		ID_Operacji = Operation_ID;
	}

	private boolean checkPassword(String password) {
		int passwordStatus = healthBase.singleQuery("SELECT Hasło FROM Pacjenci WHERE PESEL = " + pesel + " AND HASŁO = \'" + password + "\'");
		return passwordStatus == 0;
	}
}
