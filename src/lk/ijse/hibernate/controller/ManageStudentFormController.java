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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.hibernate.bo.BOFactory;
import lk.ijse.hibernate.bo.custom.StudentBO;
import lk.ijse.hibernate.dto.StudentDTO;
import lk.ijse.hibernate.view.tm.StudentTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ManageStudentFormController {
    private final StudentBO studentBO = (StudentBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.STUDENT);
    public TextField txtStudentId;
    public TextField txtName;
    public TextField txtAddress;
    public JFXComboBox cmbGender;
    public DatePicker cmbDate;
    public TextField txtContact;
    public TableView<StudentTM> tblStudent;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public AnchorPane studentAnchor;

    public void initialize() {
        tblStudent.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("student_Id"));
        tblStudent.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblStudent.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblStudent.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblStudent.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("dob"));
        tblStudent.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("gender"));

        clear();
        loadAll();
        cmbGender.setItems(FXCollections.observableArrayList("Male", "Female"));

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtStudentId.setText(newValue.getStudent_Id());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());
                txtContact.setText(newValue.getContact());
                cmbDate.setValue(newValue.getDob());
                cmbGender.setValue(newValue.getGender());

                txtStudentId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);
                txtContact.setDisable(false);
                cmbDate.setDisable(false);
                cmbGender.setDisable(false);
            }
        });
    }

    public void SaveOnAction(ActionEvent actionEvent) {
        String id = txtStudentId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();
        String contact = txtContact.getText();
        LocalDate date = cmbDate.getValue();
        String gender = (String) cmbGender.getValue();

        try {
            if (btnSave.getText().equalsIgnoreCase("Save")) {
                if (studentBO.saveStudent(new StudentDTO(id, name, address, contact, date, gender))) {
                    tblStudent.getItems().add(new StudentTM(id, name, address, contact, date, gender));
                    clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved!").show();

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed!").show();
                }
            } else {
                studentBO.updateStudent(new StudentDTO(id, name, address, contact, date, gender));
                StudentTM selected = tblStudent.getSelectionModel().getSelectedItem();
                selected.setStudent_Id(id);
                selected.setName(name);
                selected.setAddress(address);
                selected.setContact(contact);
                selected.setDob(date);
                selected.setGender(gender);

                tblStudent.refresh();
            }

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }
    }

    public void DeleteOnAction(ActionEvent actionEvent) {
        try {
            String id = tblStudent.getSelectionModel().getSelectedItem().getStudent_Id();
            try {
                studentBO.deleteStudent(id);
                tblStudent.getItems().remove(tblStudent.getSelectionModel().getSelectedItem());
                tblStudent.getSelectionModel().clearSelection();
                clear();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR, "Something Happened. try again carefully!").showAndWait();
        }

    }

    public void loadAll() {
        tblStudent.getItems().clear();
        try {
            List<StudentDTO> all = studentBO.getAll();
            for (StudentDTO dto : all) {
                tblStudent.getItems().add(new StudentTM(dto.getStudent_Id(), dto.getName(), dto.getAddress(), dto.getContact(), dto.getDob(), dto.getGender()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        txtStudentId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        cmbDate.setValue(null);
        cmbGender.getSelectionModel().clearSelection();
        txtStudentId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtContact.setDisable(true);
        cmbDate.setDisable(true);
        cmbGender.setDisable(true);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void AddOnAction(ActionEvent actionEvent) {
        txtStudentId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtContact.setDisable(false);
        cmbDate.setDisable(false);
        cmbGender.setDisable(false);
        txtStudentId.clear();
        txtName.clear();
        txtAddress.clear();
        txtContact.clear();
        cmbDate.setValue(null);
        cmbGender.getSelectionModel().clearSelection();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblStudent.getSelectionModel().clearSelection();


    }

    public void navigateToHome(MouseEvent mouseEvent) throws IOException {
        URL resource = this.getClass().getResource("/lk/ijse/hibernate/view/dashboard_form.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.studentAnchor.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
