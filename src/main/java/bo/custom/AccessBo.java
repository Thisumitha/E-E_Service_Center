package bo.custom;

import bo.SuperBo;
import dto.AccessDto;
import dto.CustomerDto;
import entity.Access;

import java.sql.SQLException;

public interface AccessBo extends SuperBo {
    String generateId() throws SQLException, ClassNotFoundException;
    boolean saveAccess(AccessDto entity) throws SQLException, ClassNotFoundException;
}
