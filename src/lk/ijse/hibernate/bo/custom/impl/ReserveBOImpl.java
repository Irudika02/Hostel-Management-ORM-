package lk.ijse.hibernate.bo.custom.impl;

import lk.ijse.hibernate.bo.custom.ReserveBO;
import lk.ijse.hibernate.dao.DAOFactory;
import lk.ijse.hibernate.dao.custom.ReserveDAO;
import lk.ijse.hibernate.dao.custom.RoomDAO;
import lk.ijse.hibernate.dao.custom.StudentDAO;
import lk.ijse.hibernate.dto.ReserveDTO;
import lk.ijse.hibernate.dto.RoomDTO;
import lk.ijse.hibernate.dto.StudentDTO;
import lk.ijse.hibernate.entity.Reserve;
import lk.ijse.hibernate.entity.Room;
import lk.ijse.hibernate.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReserveBOImpl implements ReserveBO {
    private final StudentDAO studentDAO = (StudentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);
    private final RoomDAO roomDAO = (RoomDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ROOM);
    private final ReserveDAO reserveDAO = (ReserveDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.RESERVE);
    @Override
    public List<StudentDTO> searchStudent(String id) throws Exception {
        List<Student> student = studentDAO.search(id);
        List<StudentDTO> studentDTOS = new ArrayList<>();
        for (Student st : student){
             studentDTOS.add(new StudentDTO(st.getStudent_Id(), st.getName(), st.getAddress(), st.getContact(), st.getDob(), st.getGender()));
        }
        return studentDTOS;
    }

    @Override
    public List<RoomDTO> searchRoom(String id) throws Exception {
        List<Room> room = roomDAO.search(id);
        List<RoomDTO> roomDTOS = new ArrayList<>();
        for (Room r : room){
            roomDTOS.add(new RoomDTO(r.getRoom_type_Id(),r.getType(),r.getKey_money(),r.getQty()));
        }
        return roomDTOS;
    }

    @Override
    public List<StudentDTO> getAll() throws SQLException, ClassNotFoundException {
        List<Student> all = studentDAO.getAll();
        List<StudentDTO> allDTO = new ArrayList<>();
        for (Student s : all){
            allDTO.add(new StudentDTO(s.getStudent_Id(),s.getName(),s.getAddress(),s.getContact(),s.getDob(),s.getGender()));
        }
        return allDTO;
    }

    @Override
    public List<RoomDTO> getAllRoom() throws SQLException, ClassNotFoundException {
        List<Room> all = roomDAO.getAll();
        List<RoomDTO> allDTO = new ArrayList<>();
        for (Room r : all){
            allDTO.add(new RoomDTO(r.getRoom_type_Id(),r.getType(),r.getKey_money(),r.getQty()));
        }
        return allDTO;
    }

    @Override
    public List<ReserveDTO> getAllReserve() throws SQLException, ClassNotFoundException {
        List<Reserve> all = reserveDAO.getAllReserve();
        List<ReserveDTO> allDTO = new ArrayList<>();
        for (Reserve r : all){
            allDTO.add(new ReserveDTO(r.getRes_Id(),r.getDate(),r.getStatus(),r.getStudents(),r.getRooms(),r.getRes_qty()));
        }
        return allDTO;
    }

    @Override
    public boolean Save(ReserveDTO dto) throws SQLException, ClassNotFoundException {
        return reserveDAO.save(new Reserve(dto.getRes_Id(), dto.getDate(), dto.getStatus(), dto.getStudents(), dto.getRooms(), dto.getRes_qty()));
    }

    @Override
    public boolean updateRoom(RoomDTO dto) throws SQLException, ClassNotFoundException {
        return roomDAO.update(new Room(dto.getRoom_type_Id(), dto.getType(), dto.getKey_money(), dto.getQty()));
    }

    @Override
    public List<ReserveDTO> searchReserved(String id) throws Exception {
        List<Reserve> reserve = reserveDAO.searchReserved(id);
        List<ReserveDTO> reserveDTOS = new ArrayList<>();
        for (Reserve r : reserve){
            reserveDTOS.add(new ReserveDTO(r.getRes_Id(),r.getDate(),r.getStatus(),r.getStudents(),r.getRooms(),r.getRes_qty()));
        }
        return reserveDTOS;
    }

    @Override
    public boolean deleteReserved(String id) throws Exception {
        return  reserveDAO.deleteReserved(id);
    }

}
