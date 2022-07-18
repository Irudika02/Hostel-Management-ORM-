package lk.ijse.hibernate.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.ReserveBO;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.RoomDAO;
import lk.ijse.hibernate.dto.ReserveDTO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.dto.StudentDTO;
import lk.ijse.hibernate.entity.Room;
import lk.ijse.hibernate.entity.Student;
import lk.ijse.hibernate.view.tm.ReserveTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ManageReserveFormController {
    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.RESERVE);
    public Label lblDate;
    public Label lblTime;
    public ComboBox<String> cmbStudent;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtResId;
    public ComboBox<String> cmbRoom;
    public TextField txtType;
    public TextField txtRoomQty;
    public TextField txtKeyMoney;
    public TextField txtQty;
    public TableView<ReserveTM>tblCart;
    public JFXButton btnAdd;
    public AnchorPane reservationAnchor;
    public ComboBox<String> cmbStatus;

    public void initialize() {

        tblCart.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("res_Id"));
        tblCart.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblCart.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("students"));
        tblCart.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("rooms"));
        tblCart.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("res_qty"));
        TableColumn<ReserveTM, Button> lastCol = (TableColumn<ReserveTM, Button>) tblCart.getColumns().get(5);
        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(event -> {
                tblCart.getItems().remove(param.getValue());
                tblCart.getSelectionModel().clearSelection();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });


        loadDateAndTime();
        cmbStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    List<StudentDTO> all = reserveBO.searchStudent(newValue);
                    for (StudentDTO dto : all) {
                        txtName.setText(dto.getName());
                        txtAddress.setText(dto.getAddress());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txtName.clear();
                txtAddress.clear();
            }
        });

        cmbRoom.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    List<RoomDTO> all = reserveBO.searchRoom(newValue);
                    for (RoomDTO dto : all) {
                        txtType.setText(dto.getType());
                        txtKeyMoney.setText(dto.getKey_money().setScale(2).toString());
                        Optional<ReserveTM> optOrderDetail = tblCart.getItems().stream().filter(detail -> detail.getRooms().equals(newValue)).findFirst();
                        txtRoomQty.setText((optOrderDetail.isPresent() ? dto.getQty() - optOrderDetail.get().getRes_qty() : dto.getQty()) + "");

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                txtType.clear();
                txtKeyMoney.clear();
                txtRoomQty.clear();
                txtQty.clear();
            }
        });

        cmbStatus.setItems(FXCollections.observableArrayList("Full Pay","Half Pay"));

        loadAllID();
        loadAllRoomID();
    }




    private void loadDateAndTime() {
        lblDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(currentTime.getHour() + ":" +
                    currentTime.getMinute() + ":" +
                    currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void loadAllID() {
        try {
            List<StudentDTO> all = reserveBO.getAll();
            for (StudentDTO dto : all) {
                cmbStudent.getItems().add(dto.getStudent_Id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void loadAllRoomID() {
        try {
            List<RoomDTO> all = reserveBO.getAllRoom();
            for (RoomDTO dto : all) {
                cmbRoom.getItems().add(dto.getRoom_type_Id());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void AddOnAction(ActionEvent actionEvent) {

        String sId = cmbStudent.getSelectionModel().getSelectedItem();
        String rId = cmbRoom.getSelectionModel().getSelectedItem();
        String resId = txtResId.getText();
        String status  = cmbStatus.getSelectionModel().getSelectedItem();
        int qty = Integer.parseInt(txtQty.getText());
        LocalDate date = LocalDate.now();

        if (!txtQty.getText().matches("^[0-1]$") || Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtRoomQty.getText())) {
            new Alert(Alert.AlertType.ERROR, "Only one room can be reserved for one person").show();
            return;
        }

        if (!resId.matches("^(RES00-)[0-9]{3,5}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid Reservation Id").show();
            return;
        }
        if (btnAdd.getText().equals("Add")){
            boolean exists = tblCart.getItems().stream().anyMatch(detail -> detail.getRooms().equals(rId));
            if (exists) {
                ReserveTM tm = tblCart.getItems().stream().filter(detail -> detail.getRooms().equals(rId)).findFirst().get();
                tm.setRes_qty(tm.getRes_qty() + qty);
                tblCart.refresh();
            }
        }
        tblCart.getItems().add(new ReserveTM(resId,date,status,sId,rId,qty));
        cmbRoom.getSelectionModel().clearSelection();
        cmbRoom.requestFocus();



    }

    public void ReservedOnAction(ActionEvent actionEvent) {
        String resId = tblCart.getSelectionModel().getSelectedItem().getRes_Id();
        LocalDate date = LocalDate.now();
        String status = tblCart.getSelectionModel().getSelectedItem().getStatus();
        String sId = tblCart.getSelectionModel().getSelectedItem().getStudents();
        String rId = tblCart.getSelectionModel().getSelectedItem().getRooms();
        int qty = tblCart.getSelectionModel().getSelectedItem().getRes_qty();
        Student student = new Student();
        student.setStudent_Id(sId);

        Room room = new Room();
        room.setRoom_type_Id(rId);

        try {
           boolean b = reserveBO.Save(new ReserveDTO(resId,date,status,student,room,qty));
            if (b){
                new Alert(Alert.AlertType.CONFIRMATION,"Reserved").show();
            }else {
                new Alert(Alert.AlertType.ERROR,"Nor reserved").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        cmbStudent.getSelectionModel().clearSelection();
        cmbRoom.getSelectionModel().clearSelection();
        txtQty.clear();
        tblCart.getItems().clear();
        txtResId.clear();

        try {
            List<RoomDTO> dto = reserveBO.searchRoom(rId);
            for (RoomDTO roomDTO : dto){
                roomDTO.setQty(roomDTO.getQty() - qty);
                reserveBO.updateRoom(new RoomDTO(roomDTO.getRoom_type_Id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/hibernate/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.reservationAnchor.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
