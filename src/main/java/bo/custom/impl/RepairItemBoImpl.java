package bo.custom.impl;

import bo.custom.RepairItemBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDao;
import dao.custom.RepairItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import dto.RepairItemDto;
import entity.RepairItem;

import java.sql.SQLException;
import java.util.List;

public class RepairItemBoImpl implements RepairItemBo {
    private RepairItemDao repairItemDao= DaoFactory.getInstance().getDao(DaoType.REPAIR_ITEM);
    private CustomerDao customerDao= DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveItem(RepairItemDto dto) throws SQLException, ClassNotFoundException {
        boolean save = customerDao.save(dto.getCustomer());
        if (save) {
            return repairItemDao.save(dto);
        }
        return false;
    }

    @Override
    public boolean updateItem(RepairItemDto dto) {
        return false;
    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public List<RepairItemDto> allItems() {
        return null;
    }
}
