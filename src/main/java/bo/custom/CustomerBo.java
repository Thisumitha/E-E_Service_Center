package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.TypeDto;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBo extends SuperBo {

    boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException;
    List<CustomerDto> allICustomers() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
    boolean updateItem(CustomerDto dto) throws SQLException, ClassNotFoundException;


}

