package lk.ijse.hibernate.dao.custom;

import lk.ijse.hibernate.dao.SuperDAO;
import lk.ijse.hibernate.entity.Reserve;

import java.sql.SQLException;
import java.util.List;

public interface ReserveDAO extends SuperDAO {
    boolean save(Reserve entity)throws SQLException,ClassNotFoundException;
    List<Reserve> getAllReserve() throws SQLException,ClassNotFoundException;
    List<Reserve> searchReserved(String id)throws SQLException,ClassNotFoundException;
    boolean deleteReserved(String id) throws Exception;
}
