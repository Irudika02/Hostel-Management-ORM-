package lk.ijse.hibernate.dao;

import lk.ijse.hibernate.entity.Student;

import java.sql.SQLException;
import java.util.List;

public interface CrudDAO <T,Id> extends SuperDAO{
    boolean save(T dto) throws SQLException, ClassNotFoundException;
    boolean delete(Id id) throws SQLException, ClassNotFoundException;
    boolean update(T dto) throws SQLException, ClassNotFoundException;
    List<T> search(Id id)throws SQLException,ClassNotFoundException;
    List<T> getAll() throws SQLException,ClassNotFoundException;
}
