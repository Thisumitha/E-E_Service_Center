package bo.custom.impl;

import bo.custom.RepairItemBo;
import dao.DaoFactory;
import dao.custom.CustomerDao;
import dao.custom.OrderDao;
import dao.custom.RepairItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import dto.OrderDto;
import dto.RepairItemDto;
import entity.RepairItem;

import java.sql.SQLException;
import java.util.List;

public class RepairItemBoImpl implements RepairItemBo {
    private RepairItemDao repairItemDao= DaoFactory.getInstance().getDao(DaoType.REPAIR_ITEM);
    private CustomerDao customerDao= DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveItem(RepairItemDto dto) throws SQLException, ClassNotFoundException {
            return repairItemDao.save(dto);

    }

    @Override
    public boolean updateItem(RepairItemDto dto) throws SQLException, ClassNotFoundException {
        return  repairItemDao.update(dto);

    }

    @Override
    public boolean deleteItem(String id) {
        return false;
    }

    @Override
    public List<RepairItemDto> allItems() throws SQLException, ClassNotFoundException {
        return repairItemDao.getAll();
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String lastOrder = repairItemDao.lastOrder();

            if (lastOrder!=null){
                int num = Integer.parseInt(lastOrder.split("[R]")[1]);
                num++;
                return String.format("R%03d",num);
            }else{
                return "R001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
