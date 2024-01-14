package bo.custom;

import bo.SuperBo;
import dto.RepairItemDto;
import dto.RepairPartDto;

import java.sql.SQLException;
import java.util.List;

public interface RepairPartsBo extends SuperBo{
    boolean saveItem(RepairPartDto dto) throws SQLException, ClassNotFoundException;
    List<RepairPartDto> allItems() throws SQLException, ClassNotFoundException;
    String generateId() throws SQLException, ClassNotFoundException;
}
