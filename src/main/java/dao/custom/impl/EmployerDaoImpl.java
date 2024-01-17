package dao.custom.impl;

import dao.custom.EmployerDao;
import dao.util.HibernateUtil;
import dto.EmployerDto;
import entity.Access;
import entity.Customer;
import entity.Employers;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class EmployerDaoImpl implements EmployerDao {

    @Override
    public boolean save(EmployerDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Employers employers = new Employers(
                dto.getCode(),
                dto.getName(),
                null,
                dto.getEmail()

        );

        session.save(employers);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean update(EmployerDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<EmployerDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String lastOrder() throws SQLException, ClassNotFoundException {
        Employers access =null;
        String last=null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Employers ORDER BY code desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            access= (Employers) list.get(0);
            last= access.getCode();
        }

        session.close();
        return last;
    }
}
