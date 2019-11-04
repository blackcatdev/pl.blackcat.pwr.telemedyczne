package pl.blackcat.pwr.telemedyczne;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				System.out.println("ID_Operacji\t\tData");
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
}