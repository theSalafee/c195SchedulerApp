package sample;

import database.DbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLDataException;
import java.sql.SQLException;

/**
 * FXML Controller class
 *
 * @author Naasir Bush
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException, IOException, Exception {

        //DbConnection.openConnection();
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("WGU C195 Scheduler App | Prolific Studios - Naasir al-Amreekee");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
    }

    /**
     *
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Exception
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
        DbConnection.openConnection();
        launch(args);
        DbConnection.closeConnection();
    }
}
