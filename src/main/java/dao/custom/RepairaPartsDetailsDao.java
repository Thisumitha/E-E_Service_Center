package dao.custom;

import dao.CrudDao;
import dto.OrderDetailsDto;
import entity.RepairaPartsDetails;

import java.sql.SQLException;

public interface RepairaPartsDetailsDao extends CrudDao<RepairaPartsDetails> {
    String lastOrder() throws SQLException, ClassNotFoundException;
}
