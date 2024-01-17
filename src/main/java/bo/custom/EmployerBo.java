package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import dto.EmployerDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployerBo extends SuperBo {
    boolean saveEmployer(EmployerDto dto) throws SQLException, ClassNotFoundException;
    List<EmployerDto> allIEmployers() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
    boolean updateItem(EmployerDto dto) throws SQLException, ClassNotFoundException;

}
