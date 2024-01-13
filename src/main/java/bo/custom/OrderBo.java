package bo.custom;

import bo.SuperBo;
import dto.OrderDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderBo extends SuperBo {
   boolean saveOreder(OrderDto dto)throws SQLException,ClassNotFoundException;
   String generateId() ;
   List<OrderDto> allOrders() throws SQLException, ClassNotFoundException;

}
