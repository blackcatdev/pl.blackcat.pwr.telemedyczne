package pl.blackcat.pwr.telemedyczne;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Base {
	private Connection connection = null;
	private Statement statement = null;
	private ResultSet resultSet = null;

	Base() {
		// variables


		// Krok 1: Wczytywanie sterownika JDBC
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
		} catch (ClassNotFoundException connectionFailure) {

			System.out.println("Błąd podczas ładowania lub rejestracji sterownika MS Access JDBC driver");
			connectionFailure.printStackTrace();
		}

		// Krok 2: Otwarcie bazy danych
		try {

			String msAccDB = "C:\\Users\\Marcin\\IdeaProjects\\pl.blackcat.pwr.telemedyczne\\src\\pl\\blackcat\\pwr\\telemedyczne\\"
					+ "SystemyTelemedyczne.accdb";
			String dbURL = "jdbc:ucanaccess://"
					+ msAccDB;

			// Step 2.A: Create and
			// get connection using DriverManager class
			connection = DriverManager.getConnection(dbURL);

			// Step 2.B: Creating JDBC Statement
			statement = connection.createStatement();

			// Step 2.C: Executing SQL and
			// retrieve data into ResultSet

		} catch (SQLException connectionError) {
			connectionError.printStackTrace();
		}
	}

	int singleQuery(String sqlQuery) {

		try {

			resultSet = statement.executeQuery(sqlQuery);

			//System.out.println("ID\tName\t\t\tAge\tMatches");
			//System.out.println("==\t================\t===\t=======");

			// processing returned data and printing into console
			//while (resultSet.next()) {
			if (!resultSet.next()) {
				return 1;
			} else {
				return 0;

			}
			//resultSet.next();
			//return resultSet.getString(1);
			//resultSet.getString(2) + "\t" +
			//resultSet.getString(3) + "\t" +
			//resultSet.getString(4) + "\t" +
			//resultSet.getString(5));
			//}
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		}
		return 1;
	}

	void closeConnection() {
		// Step 3: Closing database connection
		try {
			if (null != connection) {
				// cleanup resources, once after processing
				resultSet.close();
				statement.close();

				// and then finally close connection
				connection.close();
			}
		} catch (SQLException sqlex) {
			sqlex.printStackTrace();
		}
	}

	int getIntQuery(String sqlQuery) {
		try {
			resultSet = statement.executeQuery(sqlQuery);
			if (resultSet.next())
				return resultSet.getInt(1);
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		}
		return -1;
	}

	public int showQuery(String sqlQuery, int rows) {
		try {

			resultSet = statement.executeQuery(sqlQuery);

			//System.out.println("ID\tName\t\t\tAge\tMatches");
			//System.out.println("==\t================\t===\t=======");

			// processing returned data and printing into console
			//while (resultSet.next()) {
			if (!resultSet.next()) {
				return -1;
			} else {

				for (int i = 1; i <= rows; i++)
					System.out.print(resultSet.getString(i) + "\t\t\t");

			}
			while (resultSet.next()) {
				System.out.println();
				for (int i = 1; i <= rows; i++)
					System.out.print(resultSet.getString(i) + "\t\t\t");

			}
			//resultSet.next();
			//return resultSet.getString(1);
			//resultSet.getString(2) + "\t" +
			//resultSet.getString(3) + "\t" +
			//resultSet.getString(4) + "\t" +
			//resultSet.getString(5));
			//}
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		}
		return 0;

	}

	public void insertNewObservation(int id_operacji, float temperature, int pain, String reccomendations, int drug) {
		String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		String query = "INSERT INTO Obserwacje (Data, ID_Operacji, Temperatura, Siła_Bólu, Zalecenia, ID_Leku) VALUES (#" + date + "#, " + id_operacji + "," + temperature + "," + pain + "," + reccomendations + ',' + drug + ");";
		try {
			statement.executeUpdate(query);
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		}
		System.out.println("Obserwacja zapisana pomyślnie.");

	}

	public void updateObservation(int id_obserwacji) {
		String query = "UPDATE Obserwacje SET Czy_odebrana = true WHERE ID_Obserwacji = " + id_obserwacji;
		try {
			statement.executeUpdate(query);
		} catch (SQLException sqlError) {
			sqlError.printStackTrace();
		}
	}

}