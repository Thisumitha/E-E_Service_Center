package bo.custom.impl;

import bo.custom.AccessBo;
import dao.DaoFactory;
import dao.custom.AccessDao;
import dao.util.DaoType;
import dto.AccessDto;
import entity.Access;

import java.sql.SQLException;

public class AccessBoImpl implements AccessBo {
    private AccessDao accessDao= DaoFactory.getInstance().getDao(DaoType.ACCESS);
    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String lastOrder = accessDao.lastOrder();

            if (lastOrder!=null){
                int num = Integer.parseInt(lastOrder.split("[A]")[1]);
                num++;
                return String.format("A%03d",num);
            }else{
                return "A001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean saveAccess(AccessDto entity) throws SQLException, ClassNotFoundException {
        return accessDao.save(entity);
    }
}
