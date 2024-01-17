package dao.custom.impl;

import dao.custom.AccessDao;
import dao.util.HibernateUtil;
import dto.AccessDto;
import entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AccessDaoImpl implements AccessDao {
    @Override
    public boolean save(AccessDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Access access = new Access(
                dto.getId(),
                dto.isStoreAccess(),
                dto.isInventoryAccess(),
                dto.isCustomerAccess(),
                dto.isReportAccess(),
                dto.isRepairAccess()
        );
        access.setEmployer(session.find(Employers.class,dto.getEmployer()));

        session.save(access);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(AccessDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<AccessDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String lastOrder() throws SQLException, ClassNotFoundException {
        Access access =null;
        String last=null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Access ORDER BY id desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            access= (Access) list.get(0);
            last= access.getId();
        }

        session.close();
        return last;
    }
}
