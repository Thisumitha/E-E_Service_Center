package dao.custom;

import dao.CrudDao;
import entity.Customer;

import java.sql.SQLException;

public interface CustomerDao  extends CrudDao<Customer> {

    int lastOrder() throws SQLException, ClassNotFoundException;

}
