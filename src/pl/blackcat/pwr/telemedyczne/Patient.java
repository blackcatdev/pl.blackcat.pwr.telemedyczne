package pl.blackcat.pwr.telemedyczne;

import java.util.Scanner;

import pl.blackcat.zadaniajava.pesel.*;

class Patient {
	private static Scanner scanner = new Scanner(System.in);
	private static String pesel;
	private static String password;
	//ustanów połączenie z bazą
	private static Base patientBase = new Base();

	static void main() {
		//zdobądź dane pacjenta i sprawdź ich poprawność
		acquirePesel();


		//patientBase.Query("SELECT * FROM Pacjenci");
		patientBase.closeConnection();

	}

	private static void acquirePesel() {

		System.out.print("Podaj swój pesel: ");
		pesel = scanner.nextLine();
		if (checkPesel.checkPesel(pesel) == 0) {
			int peselStatus = patientBase.singleQuery("SELECT ID_Pacjenta FROM Operacje WHERE ID_Pacjenta = " + pesel);
			if (peselStatus == 0) {
				acquirePassword();
			}
			else {
				System.out.println("Nie miałeś żadnej operacji. Zamykam program.");
				System.exit(3);
			}

		} else {
			System.out.println("Podano błędny pesel (z definicji). Zamykam program.");
			System.exit(2);

		}


	}

	private static void acquirePassword() {
		int passwordStatus;
		int i;
		for (i = 0; i < 3; i++) {
			System.out.print("Podaj swoje hasło: ");
			password = scanner.nextLine();
			passwordStatus = patientBase.singleQuery("SELECT Hasło FROM Pacjenci WHERE PESEL = " + pesel + " AND HASŁO = \'" + password + "\'");
			if (passwordStatus == 0) {
				System.out.println("Hasło poprawne");
				break;
			}
			else {
				System.out.println("Hasło niepoprawne.");
			}

		}
		if (i == 3) {
			System.out.println("Podałeś zbyt dużą ilość razy błędne hasło. Zamykam program.");
			System.exit(4);
		}
	}
}
