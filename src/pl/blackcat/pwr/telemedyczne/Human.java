package pl.blackcat.pwr.telemedyczne;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

class Human {

	Scanner scanner = new Scanner(System.in);
	String pesel;
	String password;
	int ID_Operacji;
	protected Vector listofOperations = new Vector();


	//ustanów połączenie z bazą
	Base healthBase = new Base();

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

	void waitForEnter() {
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
