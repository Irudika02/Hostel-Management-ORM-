package lk.ijse.hibernate.bo.custom;

import lk.ijse.hibernate.bo.SuperBO;
import lk.ijse.hibernate.dto.ReserveDTO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface ReserveBO extends SuperBO {
    List<StudentDTO> searchStudent (String id) throws Exception;
    List<RoomDTO> searchRoom (String id) throws Exception;
    List<StudentDTO> getAll() throws SQLException,ClassNotFoundException;
    List<RoomDTO> getAllRoom() throws SQLException,ClassNotFoundException;
    List<ReserveDTO> getAllReserve() throws SQLException,ClassNotFoundException;
    boolean Save(ReserveDTO dto) throws SQLException,ClassNotFoundException;
    boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException;
    List<ReserveDTO> searchReserved(String id)throws Exception;
    boolean deleteReserved(String id) throws Exception;
}
