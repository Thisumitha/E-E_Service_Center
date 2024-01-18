package bo.custom.impl;

import bo.BoFactory;
import bo.custom.AccessBo;
import bo.custom.EmployerBo;
import dao.DaoFactory;
import dao.custom.EmployerDao;
import dao.custom.ItemDao;
import dao.util.BoType;
import dao.util.DaoType;
import dto.AccessDto;
import dto.EmployerDto;
import entity.Access;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class EmployerBoImpl implements EmployerBo {
    private AccessBo accessBo = BoFactory.getInstance().getBo(BoType.ACCESS);
    private EmployerDao employerDao= DaoFactory.getInstance().getDao(DaoType.EMPLOYER);
    @Override
    public boolean saveEmployer(EmployerDto dto) throws SQLException, ClassNotFoundException {
        boolean saveAccess =employerDao.save(dto);
        if (saveAccess) {
            return  accessBo.saveAccess(new AccessDto(
                    dto.getAccess().getId(),
                    dto.getAccess().isStoreAccess(),
                    dto.getAccess().isInventoryAccess(),
                    dto.getAccess().isCustomerAccess(),
                    dto.getAccess().isReportAccess(),
                    dto.getAccess().isRepairAccess(),
                    dto.getCode()
            ));
        }

       return false;
    }

    @Override
    public List<EmployerDto> allIEmployers() throws SQLException, ClassNotFoundException {
        return employerDao.getAll();
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        try {
            String lastOrder = employerDao.lastOrder();

            if (lastOrder!=null){
                int num = Integer.parseInt(lastOrder.split("[E]")[1]);
                num++;
                return String.format("E%03d",num);
            }else{
                return "E001";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateItem(EmployerDto dto) throws SQLException, ClassNotFoundException {
        return employerDao.update(dto);
    }
    public  String generateOTP() {
        int randomPin   =(int)(Math.random()*9000)+1000;
        String otp  =String.valueOf(randomPin);
        return otp;
    }
    public  String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }
}
