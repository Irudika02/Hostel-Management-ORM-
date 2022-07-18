package lk.ijse.hibernate.dao.custom;

import lk.ijse.hibernate.dao.SuperDAO;
import lk.ijse.hibernate.entity.Login;

import java.util.List;

public interface LoginDAO extends SuperDAO {
    boolean setUsernamePassword(Login entity) throws Exception;
    boolean updateUsernamePassword(Login entity) throws Exception;
    List<Login> findAll() throws Exception;
}
