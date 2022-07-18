package lk.ijse.hibernate.bo.custom;

import lk.ijse.hibernate.bo.SuperBO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface RoomBO extends SuperBO {
    boolean saveRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;

    boolean deleteRoom(String id) throws SQLException, ClassNotFoundException;

    List<RoomDTO> getAll() throws SQLException,ClassNotFoundException;
}
