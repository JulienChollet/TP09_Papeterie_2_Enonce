package fr.eni.papeterie.dal.jdbc;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import fr.eni.papeterie.bo.Settings;
import fr.eni.papeterie.dal.DALException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public  class BddJdbc {

        /*public static String url  = "jdbc:sqlserver://127.0.0.1:1433;databasename=papeterie_db";
        public static String user = "sa";
        public static String mdp ="Pa$$w0rd";*/

    public static Connection getConnexion() throws DALException {
        try {
            DriverManager.registerDriver(new SQLServerDriver());
            return DriverManager.getConnection(Settings.getProperty("fr.eni.papeterie.dal.jdbc.url"), Settings.getProperty("fr.eni.papeterie.dal.jdbc.user"), Settings.getProperty("fr.eni.papeterie.dal.jdbc.password"));
        } catch (SQLException e) {
           throw new DALException("Erreur durant la connection!",e);
        }


    }
    public static void closeConnexion(Connection c) throws SQLException
    {
        c.close();
    }


}
