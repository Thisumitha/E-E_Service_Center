package dao.custom.impl;

import dao.custom.TypeDao;
import dao.util.HibernateUtil;
import entity.Item;
import entity.Type;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class TypeDaoImpl implements TypeDao {

    @Override
    public boolean save(Type entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Type entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        Type type = session.find(Type.class,entity.getId());
        type.setType(entity.getType());
        type.setId(entity.getId());
        type.setCategory(entity.getCategory());
        session.save(type);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Type> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Type");
        List<Type> list = query.list();
        session.close();
        return list;
    }

    @Override
    public int lastOrder() throws SQLException, ClassNotFoundException {
        Type lastOrder =null;
        int last=0;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Type ORDER BY id desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            lastOrder= (Type) list.get(0);
            last= lastOrder.getId();
        }

        session.close();
        return last;
    }
}
