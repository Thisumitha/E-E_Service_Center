package bo.custom.impl;

import bo.BoFactory;
import bo.custom.OrderBo;
import bo.custom.TypeBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.OrderDao;
import dao.util.BoType;
import dao.util.DaoType;
import dto.OrderDto;

import java.sql.SQLException;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao= DaoFactory.getInstance().getDao(DaoType.ORDER);
    @Override
    public boolean saveOreder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return orderDao.save(dto);
    }

    @Override
    public String generateId() {
        try {
            OrderDto dto = orderDao.lastOrder();

            if (dto!=null){
                String id = dto.getOrderId();
                System.out.println("\n"+id);
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                return String.format("D%03d",num);
            }else{
                return "D001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
