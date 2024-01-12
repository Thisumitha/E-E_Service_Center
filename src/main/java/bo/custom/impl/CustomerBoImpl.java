package bo.custom.impl;

import bo.BoFactory;
import bo.custom.CustomerBo;
import bo.custom.TypeBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.TypeDao;
import dao.util.BoType;
import dao.util.DaoType;
import dto.CustomerDto;
import entity.Customer;

import java.sql.SQLException;
import java.util.List;

public class CustomerBoImpl implements CustomerBo {
    private CustomerDao customerDao= DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) throws SQLException, ClassNotFoundException {
     return customerDao.save(new Customer(
                dto.getCode(),
                dto.getName(),
                dto.getNumber(),
                dto.getEmail()
        ));

    }

    @Override
    public List<CustomerDto> allItems() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        int last= customerDao.lastOrder();
        System.out.println(last);
        if (last>-1){


            ++last;
            return ""+last;
        }else{
            return ""+1;
        }
    }

    @Override
    public boolean updateItem(CustomerDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
