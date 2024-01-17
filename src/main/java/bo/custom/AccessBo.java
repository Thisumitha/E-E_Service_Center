package bo.custom;

import bo.SuperBo;
import dto.CustomerDto;
import entity.Access;

import java.sql.SQLException;

public interface AccessBo extends SuperBo {
    String generateId() throws SQLException, ClassNotFoundException;
    boolean saveAccess(Access entity) throws SQLException, ClassNotFoundException;
}
