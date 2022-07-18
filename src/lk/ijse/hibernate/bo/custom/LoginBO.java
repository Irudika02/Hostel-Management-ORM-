package lk.ijse.hibernate.bo.custom;

import lk.ijse.hibernate.bo.SuperBO;
import lk.ijse.hibernate.dto.LoginDTO;

import java.util.List;

public interface LoginBO extends SuperBO {
    boolean save(LoginDTO dto) throws Exception;
    boolean update(LoginDTO dto) throws Exception;
    List<LoginDTO> findAll() throws Exception;
}
