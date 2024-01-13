package dao.custom.impl;

import dao.custom.CustomerDao;
import dao.util.HibernateUtil;
import dto.RepairItemDto;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer =new Customer(
                entity.getCode(),
                entity.getName(),
                entity.getNumber(),
                entity.getEmail()
        );




        session.save(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        Customer cutomer = session.find(Customer.class, entity.getCode());
        cutomer.setCode(entity.getCode());
        cutomer.setName(entity.getName());
        cutomer.setNumber(entity.getNumber());
        cutomer.setEmail(entity.getEmail());
        session.save(cutomer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer ");
        List<Customer> list = query.list();
        session.close();
        return list;
    }

    @Override
    public int lastOrder() throws SQLException, ClassNotFoundException {
        Customer lastOrder =null;
        int last=0;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer ORDER BY code desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            lastOrder= (Customer) list.get(0);
            last= Integer.parseInt(lastOrder.getCode());
        }

        session.close();
        return last;
    }
}
