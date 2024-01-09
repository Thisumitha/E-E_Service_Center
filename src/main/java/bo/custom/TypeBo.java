package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import dto.TypeDto;

import java.sql.SQLException;
import java.util.List;

public interface TypeBo extends SuperBo {
    boolean saveItem(TypeDto dto) throws SQLException, ClassNotFoundException;
    List<TypeDto> allItems() throws SQLException, ClassNotFoundException;
     int generateId() throws SQLException, ClassNotFoundException;
    boolean updateItem(TypeDto dto) throws SQLException, ClassNotFoundException;
}
