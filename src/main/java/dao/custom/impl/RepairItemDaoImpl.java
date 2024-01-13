package dao.custom.impl;

import bo.BoFactory;
import bo.custom.CustomerBo;
import dao.custom.RepairItemDao;
import dao.util.BoType;
import dao.util.HibernateUtil;
import dto.RepairItemDto;
import entity.Customer;
import entity.RepairItem;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class RepairItemDaoImpl implements RepairItemDao {

    @Override
    public boolean save(RepairItemDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        RepairItem repairItem = new RepairItem(
                dto.getId(),
                dto.getName(),
                dto.getDate(),
                dto.getCashier(),
                dto.getPrice(),
                dto.getNote()
        );
        repairItem.setCustomer(dto.getCustomer());
        repairItem.setCustomer(session.find(Customer.class,dto.getCustomer().getCode()));
        session.save(repairItem);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(RepairItemDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }



    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<RepairItemDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
