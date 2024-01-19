package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.util.HibernateUtil;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.RepairItemDto;
import entity.OrderDetail;
import entity.Orders;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {



    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        List<OrderDetailsDto>orderDtos=new ArrayList<>();
        org.hibernate.query.Query query = session.createQuery("FROM OrderDetail ");
        List<OrderDetail> list = query.list();


        session.close();
        return list;
    }

    @Override
    public List<OrderDetail> getlastOrderDetails(String id) {


        List<OrderDetail> orderDetailsList = null;
        try (Session session = HibernateUtil.getSession()) {
            Query query = session.createQuery("FROM OrderDetail  WHERE orders.orderId = :orderId");
            query.setParameter("orderId", id);
            orderDetailsList = query.list();
            System.out.println(orderDetailsList.size());
//
//            for (OrderDetail orderDetail : orderDetailsList) {
//                orderDtos.add(new OrderDetailsDto(
//                        orderDetail.getOrders().getOrderId(),
//                        orderDetail.getItem().getCode(),
//                        orderDetail.getQty(),
//                        orderDetail.getPrice()
//                ));
//            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return orderDetailsList;
    }
}