package at.mtxframe.mtxframe.database;

import at.mtxframe.mtxframe.MtxFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private Connection connection;
    MtxFrame plugin;

    private final String HOST;
    private final String PORT;
    private final String USER;
    private final String PASSWORD;
    private final String DATABASE_NAME;


    public DatabaseConnection(String host, String port, String user, String password, String databaseName) {
        plugin = MtxFrame.getPlugin();
        HOST = host;
        PORT = port;
        USER = user;
        PASSWORD = password;
        DATABASE_NAME = databaseName;
    }

    // Getter für die Connection
    public Connection getConnection() throws SQLException {
        //Abfrage, ob schon eine Verbindung besteht (Wenn nicht wird die aktuelle connection zurückgegeben)
        if (connection != null) {
            return connection;
        }
            //Database connection Variablen Abgleich
            //Veränderbar in der configDatei

            String url = "jdbc:mysql://" + this.HOST + ":" + this.PORT + "/" + this.DATABASE_NAME;
            Connection connection = DriverManager.getConnection(url, this.USER, this.PASSWORD);
            this.connection = connection;
            if (plugin != null) {
                plugin.cLog("Verbindung zur Datenbank erfolgreich.");
            }
            return this.connection;

    }

    public void initializeDatabase(String iniBuildString) throws SQLException{
        //Kreiren von DataBase Tables mit Statements
        assert connection != null;
        Statement statement = getConnection().createStatement();
        //Der iniBuildString / InitialisierungsModel kommt von der DatabaseHandler Klasse und ist modular
        String sql = iniBuildString;
        statement.execute(sql);

        statement.close();

        plugin.cLog("Der DatabaseTable wurde erfolgreich erstellt");

    }


}
