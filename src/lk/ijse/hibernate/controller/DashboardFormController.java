package lk.ijse.hibernate.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

    public ImageView imgStudent;
    public ImageView imgRoom;
    public Label lblMenu;
    public Label lblDescription;
    public AnchorPane dashboardAnchor;
    public ImageView imgReserve;
    public ImageView imgSetting;
    public ImageView imgReservedCheck;

    public void imgMouseClicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            Parent root = null;
            switch (icon.getId()) {
                case "imgStudent":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hibernate/view/manage-student-form.fxml"));
                    break;
                case "imgRoom":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hibernate/view/manage-room-form.fxml"));
                    break;
                case "imgReserve":
                    root = FXMLLoader.load(this.getClass().getResource("/lk/ijse/hibernate/view/manage-reserve-form.fxml"));
                    break;
                case "imgReservedCheck":
                    root= FXMLLoader.load(this.getClass().getResource("/lk/ijse/hibernate/view/check-reservation-form.fxml"));
                    break;
                case "imgSetting":
                    LoginFormController.anchorPane = dashboardAnchor;
                    URL resorce = getClass().getResource("/lk/ijse/hibernate/view/update-password-form.fxml");
                    Parent parent = FXMLLoader.load(resorce);
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();

                    break;
            }

            if (root != null) {
                Scene subScene = new Scene(root);
                Stage primaryStage = (Stage) this.dashboardAnchor.getScene().getWindow();
                primaryStage.setScene(subScene);
                primaryStage.centerOnScreen();

                TranslateTransition tt = new TranslateTransition(Duration.millis(350), subScene.getRoot());
                tt.setFromX(-subScene.getWidth());
                tt.setToX(0);
                tt.play();
            }
        }
    }

    public void imgMouseEnterAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();

            switch (icon.getId()) {
                case "imgStudent":
                    lblMenu.setText("Manage Student");
                    lblDescription.setText("Click to add, edit, delete, search or view student");
                    break;
                case "imgRoom":
                    lblMenu.setText("Manage Room");
                    lblDescription.setText("Click to add, edit, delete, search or view room");
                    break;
                case "imgReserve":
                    lblMenu.setText("Manage Reserve");
                    lblDescription.setText("Click to add, reserved or view reserved");
                    break;
                case "imgReservedCheck":
                    lblMenu.setText("Check Reserved Details");
                    lblDescription.setText("Click to check, remove or view reserved details");
                    break;
                case "imgSetting":
                    lblMenu.setText("Setting");
                    lblDescription.setText("Click to change your current password");
                    break;

            }

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            glow.setColor(Color.CORNFLOWERBLUE);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    public void imgMouseExitAnimation(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) mouseEvent.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();
            icon.setEffect(null);
        }
    }
}
