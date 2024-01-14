package dao.custom.impl;

import dao.custom.RepairaPartsDetailsDao;
import dao.util.HibernateUtil;
import entity.RepairItem;
import entity.RepairaPartsDetails;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class RepairaPartsDetailsDaoImpl implements RepairaPartsDetailsDao {
    @Override
    public boolean save(RepairaPartsDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(RepairaPartsDetails entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<RepairaPartsDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String lastOrder() throws SQLException, ClassNotFoundException {
        RepairaPartsDetails repairaPartsDetails =null;
        String last=null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM RepairaPartsDetails ORDER BY id desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            repairaPartsDetails= (RepairaPartsDetails) list.get(0);
            last= repairaPartsDetails.getId();
        }

        session.close();
        return last;
    }
}
