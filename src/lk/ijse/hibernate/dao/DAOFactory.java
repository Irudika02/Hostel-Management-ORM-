package lk.ijse.hibernate.dao;

import lk.ijse.hibernate.dao.custom.impl.LoginDAOImpl;
import lk.ijse.hibernate.dao.custom.impl.ReserveDAOImpl;
import lk.ijse.hibernate.dao.custom.impl.RoomDAOImpl;
import lk.ijse.hibernate.dao.custom.impl.StudentDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory(){

    }

    public static DAOFactory getDaoFactory(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        LOGIN,STUDENT,ROOM,RESERVE;
    }

    public SuperDAO getDAO(DAOType daoType){
        switch (daoType){
            case LOGIN:
                return new LoginDAOImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVE:
                return new ReserveDAOImpl();
            default:
                return null;
        }
    }
}
