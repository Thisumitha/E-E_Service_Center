package bo.custom;

import bo.SuperBo;
import dto.EmployerDto;

import java.sql.SQLException;
import java.util.List;

public interface EmployerBo extends SuperBo {
    boolean saveEmployer(EmployerDto dto) throws SQLException, ClassNotFoundException;
    List<EmployerDto> allIEmployers() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
    boolean updateEmployer(EmployerDto dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployerPw(EmployerDto dto) throws SQLException, ClassNotFoundException;
    String generateOTP();
    String generatePassword();
    boolean checkPassword(String enteredPassword, String storedHashedPassword);
    String hashPassword(String password);
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
}
