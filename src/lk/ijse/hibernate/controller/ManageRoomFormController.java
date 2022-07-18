package lk.ijse.hibernate.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.RoomBO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.view.tm.RoomTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;

public class ManageRoomFormController {
    private final RoomBO roomBO = (RoomBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ROOM);
    public ImageView imgHome;
    public TextField txtRoomTypeId;
    public JFXComboBox cmbRoomType;
    public TextField txtKeyMoney;
    public TextField txtQty;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public TableView<RoomTM> tblRoom;
    public AnchorPane roomAnchor;

    public void initialize() {
        tblRoom.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("room_type_Id"));
        tblRoom.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("type"));
        tblRoom.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("key_money"));
        tblRoom.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));

        clear();
        loadAll();
        cmbRoomType.setItems(FXCollections.observableArrayList("Non-AC", "Non-AC / Food", "AC", "AC / Food"));

        tblRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtRoomTypeId.setText(newValue.getRoom_type_Id());
                cmbRoomType.setValue(newValue.getType());
                txtKeyMoney.setText(newValue.getKey_money().setScale(2).toString());
                txtQty.setText(newValue.getQty()+"");

                txtRoomTypeId.setDisable(false);
                cmbRoomType.setDisable(false);
                txtKeyMoney.setDisable(false);
                txtQty.setDisable(false);
            }

        });
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        String id = txtRoomTypeId.getText();
        String type = (String) cmbRoomType.getValue();
        BigDecimal keyMoney = new BigDecimal(txtKeyMoney.getText());
        int qty = Integer.parseInt(txtQty.getText());

        try {
            if (btnSave.getText().equalsIgnoreCase("Save")){
                if (roomBO.saveRoom(new RoomDTO(id,type,keyMoney,qty))){
                    tblRoom.getItems().add(new RoomTM(id,type,keyMoney,qty));
                    clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();
                }else {
                    new Alert(Alert.AlertType.ERROR, "Failed!").show();
                }
            }else {
                roomBO.updateRoom(new RoomDTO(id,type,keyMoney,qty));
                RoomTM selected = tblRoom.getSelectionModel().getSelectedItem();
                selected.setRoom_type_Id(id);
                selected.setType(type);
                selected.setKey_money(keyMoney);
                selected.setQty(qty);

                tblRoom.refresh();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        try {
            String id  = tblRoom.getSelectionModel().getSelectedItem().getRoom_type_Id();
            roomBO.deleteRoom(id);
            tblRoom.getItems().remove(tblRoom.getSelectionModel().getSelectedItem());
            tblRoom.getSelectionModel().clearSelection();
            clear();
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
    }

    public void loadAll() {
        tblRoom.getItems().clear();
        try {
            List<RoomDTO> all = roomBO.getAll();
            for (RoomDTO dto : all){
                tblRoom.getItems().add(new RoomTM(dto.getRoom_type_Id(), dto.getType(), dto.getKey_money(), dto.getQty()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddOnAction(ActionEvent actionEvent) {
        txtRoomTypeId.setDisable(false);
        cmbRoomType.setDisable(false);
        txtKeyMoney.setDisable(false);
        txtQty.setDisable(false);
        txtRoomTypeId.clear();
        cmbRoomType.getSelectionModel().clearSelection();
        txtKeyMoney.clear();
        txtQty.clear();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblRoom.getSelectionModel().clearSelection();
    }

    public void clear() {
        txtRoomTypeId.clear();
        cmbRoomType.getSelectionModel().clearSelection();
        txtKeyMoney.clear();
        txtQty.clear();
        txtRoomTypeId.setDisable(true);
        cmbRoomType.setDisable(true);
        txtKeyMoney.setDisable(true);
        txtQty.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/hibernate/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.roomAnchor.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
