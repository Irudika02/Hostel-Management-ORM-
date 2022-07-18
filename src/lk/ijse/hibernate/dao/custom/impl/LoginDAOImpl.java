package lk.ijse.hibernate.dao.custom.impl;

import lk.ijse.hibernate.dao.custom.LoginDAO;
import lk.ijse.hibernate.entity.Login;
import lk.ijse.hibernate.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {
    @Override
    public boolean setUsernamePassword(Login entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean updateUsernamePassword(Login entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Login> findAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        String sql = "FROM Login";
        Query query = session.createQuery(sql);
        List<Login> loginList = query.getResultList();
        return loginList;

    }
}
