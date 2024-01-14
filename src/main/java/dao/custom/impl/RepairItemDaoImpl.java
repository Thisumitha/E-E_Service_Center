package dao.custom.impl;

import bo.BoFactory;
import bo.custom.CustomerBo;
import dao.custom.RepairItemDao;
import dao.util.BoType;
import dao.util.HibernateUtil;
import dto.OrderDetailsDto;
import dto.RepairItemDto;
import entity.Customer;
import entity.OrderDetail;
import entity.RepairItem;
import entity.Type;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
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
        Session session = HibernateUtil.getSession();
        List<RepairItemDto>orderDtos=new ArrayList<>();
        org.hibernate.query.Query query = session.createQuery("FROM RepairItem ");
        List<RepairItem> list = query.list();
        for (RepairItem repairItem:list){
            orderDtos.add(new RepairItemDto(
                    repairItem.getId(),
                    repairItem.getName(),
                    repairItem.getDate(),
                    repairItem.getCashier(),
                    repairItem.getPrice(),
                    repairItem.getNote(),
                    repairItem.getCustomer(),
                    repairItem.getRepairParts()
            ));
        }

        session.close();
        return orderDtos;
    }

    @Override
    public String lastOrder() throws SQLException, ClassNotFoundException {
        RepairItem repairItem =null;
        String last=null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM RepairItem ORDER BY id desc");
        query.setMaxResults(1);
        List list = query.list();
        if(!list.isEmpty()) {
            repairItem= (RepairItem) list.get(0);
            last= repairItem.getId();
        }

        session.close();
        return last;
    }
}
