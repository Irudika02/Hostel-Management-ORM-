package lk.ijse.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student {
    @Id
    private String student_Id;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

    public Student(String student_Id, String name, String address, String contact, LocalDate dob, String gender) {
        this.student_Id = student_Id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }

    @OneToMany(mappedBy = "students",cascade = CascadeType.ALL)
    private List <Reserve> reserves = new ArrayList<>();
}
