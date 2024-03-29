package dao.custom;

import dao.CrudDao;
import dto.OrderDetailsDto;
import dto.OrderDto;
import entity.OrderDetail;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsDao extends CrudDao<OrderDetail> {
   List<OrderDetail> getlastOrderDetails(String id);
}
