package dao.custom.impl;

import dao.custom.RepairPartsDetailsDao;
import dao.util.HibernateUtil;
import dto.RepairItemDto;
import dto.RepairPartDto;
import entity.Customer;
import entity.RepairItem;
import entity.RepairaPartsDetails;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepairPartsDetailsDaoImpl implements RepairPartsDetailsDao {
    @Override
    public boolean save(RepairPartDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        RepairaPartsDetails repairaPartsDetails = new RepairaPartsDetails(
                dto.getId(),
                dto.getName(),
                dto.getQty(),
                dto.getPrice()
        );
        repairaPartsDetails.setItem(session.find(RepairItem.class,dto.getItemcode()));
        session.save(repairaPartsDetails);
        transaction.commit();
        session.close();
        return true;

    }

    @Override
    public boolean update(RepairPartDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<RepairPartDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        List<RepairPartDto>orderDtos=new ArrayList<>();
        org.hibernate.query.Query query = session.createQuery("FROM RepairaPartsDetails ");
        List<RepairaPartsDetails> list = query.list();
        for (RepairaPartsDetails repairItem:list){
            orderDtos.add(new RepairPartDto(
                    repairItem.getId(),
                    repairItem.getName(),
                    repairItem.getQty(),
                    repairItem.getPrice(),
                    repairItem.getItem().getId()

            ));
        }

        session.close();
        return orderDtos;
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