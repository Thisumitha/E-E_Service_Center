package bo.custom;

import bo.SuperBo;
import dto.RepairItemDto;

import java.sql.SQLException;
import java.util.List;

public interface RepairItemBo extends SuperBo {
    boolean saveItem(RepairItemDto dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(RepairItemDto dto) ;

    boolean deleteItem(String id);

    List<RepairItemDto> allItems() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
}
