package dao.custom;

import dao.CrudDao;

import dto.OrderDto;
import entity.Type;

import java.sql.SQLException;

public interface TypeDao extends CrudDao<Type> {
    int lastOrder() throws SQLException, ClassNotFoundException;

}
