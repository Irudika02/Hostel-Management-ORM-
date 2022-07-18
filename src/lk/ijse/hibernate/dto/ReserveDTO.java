package lk.ijse.hibernate.dto;

import lk.ijse.hibernate.entity.Room;
import lk.ijse.hibernate.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ReserveDTO {
    private String res_Id;
    private LocalDate date;
    private String status;
    private Student students;
    private Room rooms;
    private int res_qty;
}
