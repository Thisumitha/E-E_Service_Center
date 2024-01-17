package dao.custom;

import dao.CrudDao;
import dto.EmployerDto;
import entity.Employers;

import java.sql.SQLException;

public interface EmployerDao extends CrudDao<EmployerDto> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
