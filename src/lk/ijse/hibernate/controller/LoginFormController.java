package lk.ijse.hibernate.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.LoginBO;
import lk.ijse.hibernate.dto.LoginDTO;
import org.controlsfx.control.Notifications;

import java.util.List;

public class LoginFormController {
    private final LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.LOGIN);
    public AnchorPane loginAnchor;
    public TextField txtUsername;
    public PasswordField txtPassword;
    public CheckBox checkBox;
    public TextField passwordText;
    public static AnchorPane anchorPane;
    public Pane registerPane;
    public TextField txtRegister_Username;
    public PasswordField txtRegister_Password;
    public Pane loginPane;

    public void key_Entered(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            txtPassword.requestFocus();
        }
    }

    public void LoginOnAction(ActionEvent actionEvent) {
        try {
            List<LoginDTO> login = loginBO.findAll();
            for (LoginDTO l : login) {
                String username = l.getUsername();
                String password = l.getPassword();
                try {
                    String Lusername = txtUsername.getText();
                    String Lpassword = txtPassword.getText();

                    if (Lusername.equalsIgnoreCase(username) && Lpassword.equals(password)) {
                        Image image = new Image("/lk/ijse/hibernate/view/image/check.png");
                        Notifications notifications = Notifications.create()
                                .title("Login Success!")
                                .text("You have successful login to the system")
                                .graphic(new ImageView(image))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);

                        notifications.darkStyle();
                        notifications.show();

                        Parent root = FXMLLoader.load(getClass().getResource("../view/dashboard_form.fxml"));
                        if (root != null) {
                            Scene subScene = new Scene(root);
                            Stage primaryStage = (Stage) this.loginAnchor.getScene().getWindow();
                            primaryStage.setScene(subScene);
                            primaryStage.centerOnScreen();

                            TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                            tt.setFromX(-subScene.getWidth());
                            tt.setToX(0);
                            tt.play();

                        }
                    } else {
                        Image image = new Image("/lk/ijse/hibernate/view/image/close.png");
                        Notifications notifications = Notifications.create()
                                .title("Login Failed!")
                                .text("You have Failed login to the system")
                                .graphic(new ImageView(image))
                                .hideAfter(Duration.seconds(4))
                                .position(Pos.BOTTOM_RIGHT);

                        notifications.darkStyle();
                        notifications.show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, "Something Happened").show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void changeVisibility(ActionEvent actionEvent) {
        if (checkBox.isSelected()){
            passwordText.setText(txtPassword.getText());
            passwordText.setVisible(true);
            txtPassword.setVisible(false);
            return;
        }
        txtPassword.setText(passwordText.getText());
        txtPassword.setVisible(true);
        passwordText.setVisible(false);
    }

    public void RegisterOnAction(ActionEvent actionEvent) {
        registerPane.setVisible(true);
    }

    public void RegisteredOnAction(ActionEvent actionEvent) {
        String reg_user = txtRegister_Username.getText();
        String reg_pass = txtRegister_Password.getText();

        try {
            boolean b = loginBO.save(new LoginDTO(reg_user,reg_pass));
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"Register success").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Register Failed").show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void BackLoginOnAction(ActionEvent actionEvent) {
        loginPane.setVisible(true);
        registerPane.setVisible(false);
    }


    public void key_Entered_register(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            txtRegister_Password.requestFocus();
        }
    }
}
