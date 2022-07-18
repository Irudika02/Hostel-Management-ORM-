package lk.ijse.hibernate.bo;

import lk.ijse.hibernate.bo.custom.impl.LoginBOImpl;
import lk.ijse.hibernate.bo.custom.impl.ReserveBOImpl;
import lk.ijse.hibernate.bo.custom.impl.RoomBOImpl;
import lk.ijse.hibernate.bo.custom.impl.StudentBOImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        return boFactory == null ? boFactory = new BOFactory(): boFactory;
    }

    public enum BOType{
        LOGIN,STUDENT,ROOM,RESERVE;
    }

    public SuperBO getBO(BOType boType){
        switch (boType){
            case LOGIN:
                return new LoginBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case RESERVE:
                return new ReserveBOImpl();
            default:
                return null;
        }
    }
}
