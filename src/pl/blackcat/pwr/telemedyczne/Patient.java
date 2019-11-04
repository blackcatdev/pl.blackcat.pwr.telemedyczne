package pl.blackcat.pwr.telemedyczne;

import pl.blackcat.zadaniajava.pesel.checkPesel;

class Patient extends Human {
	int ID_Operacji;

	void main() {
		//zdobądź dane pacjenta i sprawdź ich poprawność
		acquirePesel();

		//Wyświetl operacje, w których uczestniczył pacjent
		ID_Operacji = showOperations();
		System.out.println("Wybrana operacja: " + ID_Operacji);

		//zamknij bazę
		patientBase.closeConnection();

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
