package pl.blackcat.pwr.telemedyczne;

import java.util.Scanner;

class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Czy jesteś (p)acjentem czy (l)ekarzem?: ");
		char wybor = scanner.next().charAt(0);
		switch (wybor) {
			case 'p':
				Patient patient = new Patient();
				patient.main();
				break;
			case 'l':
				Doctor.main();
				break;
			default:
				System.out.println("Błędny wybór. Zamykam program.");
				System.exit(1);


		}
	}
}