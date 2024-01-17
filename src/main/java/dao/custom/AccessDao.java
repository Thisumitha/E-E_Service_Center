package dao.custom;

import dao.CrudDao;
import dto.AccessDto;
import entity.Access;
import entity.Customer;

import java.sql.SQLException;

public interface AccessDao extends CrudDao<AccessDto> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
