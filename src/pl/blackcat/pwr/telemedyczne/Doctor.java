package pl.blackcat.pwr.telemedyczne;

import pl.blackcat.zadaniajava.pesel.checkPesel;

class Doctor extends Human {
	int ID_Obserwacji;

	void main() {
		//zdobądź dane pacjenta i sprawdź ich poprawność
		acquirePesel();

		//Sprawdź, czy do którejś operacji prowadzonej przez lekarza istnieją niezatwierdzone obserwacje
		ID_Obserwacji = showOperations();

		//Lekarz wprowadza zalecenia do niezatwierdzonej obserwacji a następnie ją zatwierdza
		acceptObservations();


	}

	private void acceptObservations() {

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
			System.out.println("Nie prowadziłeś żadnej operacji. Zamykam program.");
			System.exit(3);
			return 0;
		} else {
			System.out.print("\nWybierz niezatwierdzoną obserwację z listy powyżej: ");
			chosenObservation = scanner.nextInt();
			if (healthBase.singleQuery("SELECT ID_Obserwacji FROM Obserwacje, Operacje WHERE ID_Lekarza = " + pesel + " AND ID_Obserwacji = " + chosenObservation + " AND Obserwacje.ID_Operacji = Operacje.ID_Operacji") == 0) {
				return chosenObservation;
			} else {
				System.out.println("Wybrałeś błędny numer operacji. Zamykam program.");
				System.exit(5);
			}

		}

		return -1;

	}
}

