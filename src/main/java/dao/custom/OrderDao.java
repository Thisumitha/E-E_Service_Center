package dao.custom;


import dao.CrudDao;
import dto.OrderDto;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao  extends CrudDao<OrderDto> {

    OrderDto lastOrder() throws SQLException, ClassNotFoundException;


}