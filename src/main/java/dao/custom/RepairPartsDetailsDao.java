package dao.custom;

import dao.CrudDao;
import dto.RepairPartDto;

import java.sql.SQLException;

public interface RepairPartsDetailsDao extends CrudDao<RepairPartDto> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
