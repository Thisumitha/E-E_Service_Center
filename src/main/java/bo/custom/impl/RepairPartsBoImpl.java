package bo.custom.impl;

import bo.custom.RepairPartsBo;
import dao.DaoFactory;
import dao.custom.RepairPartsDetailsDao;
import dao.util.DaoType;
import dto.RepairPartDto;

import java.sql.SQLException;
import java.util.List;

public class RepairPartsBoImpl implements RepairPartsBo {
    private RepairPartsDetailsDao repairPartsDetailsDao = DaoFactory.getInstance().getDao(DaoType.REPAIR_PARTS);
    @Override
    public boolean saveItem(RepairPartDto dto) throws SQLException, ClassNotFoundException {
        return repairPartsDetailsDao.save(dto);
    }

    @Override
    public List<RepairPartDto> allItems() throws SQLException, ClassNotFoundException {
        return repairPartsDetailsDao.getAll();
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String lastOrder = repairPartsDetailsDao.lastOrder();

            if (lastOrder!=null){
                int num = Integer.parseInt(lastOrder.split("[P]")[1]);
                num++;
                return String.format("P%03d",num);
            }else{
                return "P001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
