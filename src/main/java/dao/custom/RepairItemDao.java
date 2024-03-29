package dao.custom;

import dao.CrudDao;
import dto.RepairItemDto;
import entity.RepairItem;
import entity.Type;

import java.sql.SQLException;

public interface RepairItemDao extends CrudDao<RepairItemDto> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
