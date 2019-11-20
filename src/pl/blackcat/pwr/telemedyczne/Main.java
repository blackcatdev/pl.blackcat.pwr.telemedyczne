package pl.blackcat.pwr.telemedyczne;

import java.awt.*;

class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.MainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("Czy jesteś (p)acjentem czy (l)ekarzem?: ");
//		char wybor = scanner.next().charAt(0);
//		switch (wybor) {
//			case 'p':
//				Patient patient = new Patient();
//				patient.main();
//				break;
//			case 'l':
//				Doctor doctor = new Doctor();
//				doctor.main();
//				break;
//			default:
//				System.out.println("Błędny wybór. Zamykam program.");
//				System.exit(1);
//
//
//		}
	}
}