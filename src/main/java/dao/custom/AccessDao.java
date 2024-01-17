package dao.custom;

import dao.CrudDao;
import entity.Access;
import entity.Customer;

import java.sql.SQLException;

public interface AccessDao extends CrudDao<Access> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
