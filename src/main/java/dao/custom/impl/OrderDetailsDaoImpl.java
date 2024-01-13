package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dao.util.HibernateUtil;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.OrderDetail;
import entity.Orders;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {


    @Override
    public boolean save(OrderDetailsDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetailsDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetailsDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        List<OrderDetailsDto>orderDtos=new ArrayList<>();
        org.hibernate.query.Query query = session.createQuery("FROM OrderDetail ");
        List<OrderDetail> list = query.list();
        for (OrderDetail orders:list){
            orderDtos.add(new OrderDetailsDto(
                   orders.getOrders().getOrderId(),
                    orders.getItem().getCode(),
                    orders.getQty(),
                    orders.getPrice()
            ));
        }

        session.close();
        return orderDtos;
    }
}
