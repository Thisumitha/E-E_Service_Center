package dao.custom.impl;

import dao.custom.ItemDao;
import dao.util.HibernateUtil;
import dto.ItemDto;
import entity.Item;
import entity.Type;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class ItemDaoImpl implements ItemDao {

    @Override
    public ItemDto getItem(String code) throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class,code);


            return new ItemDto(
                    item.getCode(),
                    item.getName(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getType().getType(),
                    item.getImage()
            );


    }

    @Override
    public void removeItem(int num ,String code) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class,code);
        item.setQtyOnHand(num);
        session.save(item);
        transaction.commit();
        session.close();

    }



    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Item item =new Item(
                entity.getCode(),
                entity.getName(),
                entity.getUnitPrice(),
                entity.getQtyOnHand(),
                entity.getImage()
        );
        System.out.println(entity.getType().getType());
        item.setType(session.find(Type.class,entity.getType().getType()));



        session.save(item);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();

        Transaction transaction = session.beginTransaction();
        Item item = session.find(Item.class, entity.getCode());
        item.setCode(entity.getCode());
        item.setName(entity.getName());
        item.setQtyOnHand(entity.getQtyOnHand());
        item.setUnitPrice(entity.getUnitPrice());
        item.setImage(entity.getImage());
        item.setType(entity.getType());
        session.save(item);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Item.class,value));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public List<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Item");
        List<Item> list = query.list();
        session.close();
        return list;
    }
}
