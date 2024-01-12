package dao.custom.impl;

import dao.custom.OrderDetailsDao;
import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {
    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
      return false;
    }

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
        return null;
    }
}
