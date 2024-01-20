package dao.custom.impl;

import dao.custom.OrderDao;
import dao.util.HibernateUtil;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {




    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        Orders lastOrder = null;
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Orders ORDER BY orderId desc ");
        query.setMaxResults(1);
        List list = query.list();
        if (!list.isEmpty()) {
            lastOrder = (Orders) list.get(0);
            System.out.println("\n"+lastOrder.getOrderId()+"\n");
            return new OrderDto(
                    lastOrder.getOrderId(),
                    null,
                    null,
                    null,
                    null,
                    null

            );

        }

        session.close();

        return null;
    }

    @Override
    public boolean save(OrderDto dto) throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Orders order=new Orders(
                dto.getOrderId(),
                dto.getDate(),
                dto.getTime(),
                dto.getCashier()

        );
        order.setCustomer(session.find(Customer.class,dto.getCusCode()));
        session.save(order);
        List<OrderDetailsDto> list = dto.getList();
        List<OrderDetail>details=new ArrayList<>();
        for(OrderDetailsDto detailsDto :list){

            OrderDetail orderDetail = new OrderDetail(
                    new OrderDetailsKey(detailsDto.getOrderId(), detailsDto.getItemId()),
                    session.find(Item.class, detailsDto.getItemId()),
                    order,
                    detailsDto.getQty(),
                    detailsDto.getPrice()
            );
            session.save(orderDetail);
            details.add(orderDetail);
        }


        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(OrderDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDto> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        List<OrderDto>orderDtos=new ArrayList<>();

        org.hibernate.query.Query query = session.createQuery("FROM Orders ");
        List<Orders> list = query.list();

        for (Orders orders:list){
            List<OrderDetailsDto>detailsDtoList=new ArrayList<>();
            for(OrderDetail detailsDto:orders.getOrderDetailsList()){
                detailsDtoList.add( new OrderDetailsDto(
                        detailsDto.getOrders().getOrderId(),
                        detailsDto.getItem().getCode(),
                        detailsDto.getQty(),
                        detailsDto.getPrice()
                ));
            }
            orderDtos.add(new OrderDto(
                    orders.getOrderId(),
                    orders.getDate(),
                    orders.getTime(),
                    orders.getCustomer().getCode(),
                    detailsDtoList,
                    orders.getCashier()

            ));
        }

        session.close();
        return orderDtos;
    }
}