package lk.ijse.hibernate.bo.custom.impl;

import lk.ijse.hibernate.bo.custom.LoginBO;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.LoginDAO;
import lk.ijse.hibernate.dto.LoginDTO;
import lk.ijse.hibernate.entity.Login;

import java.util.ArrayList;
import java.util.List;

public class LoginBOImpl implements LoginBO {

    private final LoginDAO loginDAO = (LoginDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.LOGIN);

    @Override
    public boolean save(LoginDTO dto) throws Exception {
        return loginDAO.setUsernamePassword(new Login(dto.getUsername(), dto.getPassword()));
    }

    @Override
    public boolean update(LoginDTO dto) throws Exception {
        return loginDAO.updateUsernamePassword(new Login(dto.getUsername(),dto.getPassword()));
    }

    @Override
    public List<LoginDTO> findAll() throws Exception {
        List<Login> all = loginDAO.findAll();
        ArrayList<LoginDTO> allData = new ArrayList<>();
        for (Login login : all){
            allData.add(new LoginDTO(login.getUsername(),login.getPassword()));
        }
        return allData;
    }
}
