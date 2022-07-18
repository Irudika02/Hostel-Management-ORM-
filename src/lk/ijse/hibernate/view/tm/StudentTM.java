package lk.ijse.hibernate.view.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class StudentTM {
    private String student_Id;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;
}
