package pl.blackcat.pwr.telemedyczne;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Human {

	Scanner scanner = new Scanner(System.in);
	String pesel;
	String password;

	//ustanów połączenie z bazą
	Base patientBase = new Base();

	float getFloat(Scanner scanner) {
		while (true) {
			try {
				return scanner.nextFloat();
			} catch (InputMismatchException e) {
				System.out.print("Niepoprawny wartość. Spróbuj jeszcze raz: ");
				scanner.next();
			}
		}
	}

	int getInteger(Scanner scanner) {
		while (true) {
			try {
				return scanner.nextInt();
			} catch (InputMismatchException e) {
				System.out.print("Niepoprawny wartość. Spróbuj jeszcze raz: ");
				scanner.next();
			}
		}
	}

	boolean checkValue(float value, float min, float max) {
		if (value >= min && value <= max) {
			return true;
		} else
			System.out.println("Niepoprawna wartość. Spróbuj jeszcze raz.");
		return false;
	}

	boolean checkValue(int value, int min, int max) {
		if (value >= min && value <= max) {
			return true;
		} else
			System.out.println("Niepoprawna wartość. Spróbuj jeszcze raz.");
		return false;
	}

}
