package sample;

import database.UserDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Login implements Initializable {
    ResourceBundle rb;
    Locale userLocale;
    Logger userLog = Logger.getLogger("userlog.txt");

    @FXML
    public TextField userName;
    @FXML
    public TextField password;

    @FXML
    public void loginHandler(ActionEvent actionEvent) throws IOException, Exception {
        String user = userName.getText();
        String pass = password.getText();
        loggedUser.setUserName(user);
        loggedUser.setPassword(pass);

        FileHandler userLogFH = new FileHandler("userlog.txt", true);
        SimpleFormatter sf = new SimpleFormatter();
        userLogFH.setFormatter(sf);
        userLog.addHandler(userLogFH);
        userLog.setLevel(Level.INFO);

        try {
            ObservableList<User> userLoginInfo = UserDB.getActiveUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static User loggedUser = new User();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
