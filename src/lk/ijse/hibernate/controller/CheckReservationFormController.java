package lk.ijse.hibernate.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.ReserveBO;
import lk.ijse.hibernate.dto.ReserveDTO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.view.tm.ReserveTM;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

public class CheckReservationFormController {

    private final ReserveBO reserveBO = (ReserveBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.RESERVE);
    public TextField txtReservationId;
    public TableView<ReserveTM> tblReserved;
    public AnchorPane checkReservationAnchor;

    public void initialize(){
        tblReserved.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("res_Id"));
        tblReserved.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblReserved.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblReserved.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("students"));
        tblReserved.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("rooms"));
        tblReserved.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("res_qty"));
    }
    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/hibernate/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.checkReservationAnchor.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }

    public void btnSearchReservation(ActionEvent actionEvent) {
        String id = txtReservationId.getText();
        try {
            List<ReserveDTO> res = reserveBO.searchReserved(id);
            for (ReserveDTO b : res){
                tblReserved.getItems().add(new ReserveTM(b.getRes_Id(),b.getDate(),b.getStatus(),b.getStudents().getStudent_Id(),b.getRooms().getRoom_type_Id(),b.getRes_qty()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkOutOnAction(ActionEvent actionEvent) {
        try {
            String rId = tblReserved.getSelectionModel().getSelectedItem().getRooms();
            int qty = tblReserved.getSelectionModel().getSelectedItem().getRes_qty();
            List<RoomDTO> dto = reserveBO.searchRoom(rId);
            for (RoomDTO roomDTO : dto){
                roomDTO.setQty(roomDTO.getQty() + qty);
                reserveBO.updateRoom(new RoomDTO(roomDTO.getRoom_type_Id(), roomDTO.getType(), roomDTO.getKey_money(), roomDTO.getQty()));
                try {
                    String resId = tblReserved.getSelectionModel().getSelectedItem().getRes_Id();
                    reserveBO.deleteReserved(resId);
                    tblReserved.getItems().remove(tblReserved.getSelectionModel().getSelectedItem());
                    tblReserved.getSelectionModel().clearSelection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
