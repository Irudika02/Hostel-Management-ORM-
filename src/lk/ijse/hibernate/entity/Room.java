package lk.ijse.hibernate.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Room {
    @Id
    private String room_type_Id;
    private String type;
    private BigDecimal key_money;
    private int qty;
    public Room(String room_type_Id, String type, BigDecimal key_money, int qty) {
        this.room_type_Id = room_type_Id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }
    @OneToMany(mappedBy = "rooms",cascade = CascadeType.ALL)
    private List <Reserve> reserves = new ArrayList<>();

}
