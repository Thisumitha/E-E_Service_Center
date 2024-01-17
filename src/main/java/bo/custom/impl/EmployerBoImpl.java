package bo.custom.impl;

import bo.BoFactory;
import bo.custom.AccessBo;
import bo.custom.EmployerBo;
import dao.DaoFactory;
import dao.custom.EmployerDao;
import dao.custom.ItemDao;
import dao.util.BoType;
import dao.util.DaoType;
import dto.EmployerDto;
import entity.Access;

import java.sql.SQLException;
import java.util.List;

public class EmployerBoImpl implements EmployerBo {
    private AccessBo accessBo = BoFactory.getInstance().getBo(BoType.ACCESS);
    private EmployerDao employerDao= DaoFactory.getInstance().getDao(DaoType.EMPLOYER);
    @Override
    public boolean saveEmployer(EmployerDto dto) throws SQLException, ClassNotFoundException {
        accessBo.saveAccess(new Access(
                dto.getAccess().getId(),
                dto.getAccess().isStoreAccess(),
                dto.getAccess().isInventoryAccess(),
                dto.getAccess().isCustomerAccess(),
                dto.getAccess().isReportAccess(),
                dto.getAccess().isRepairAccess()
        ));
        return employerDao.save(dto);
    }

    @Override
    public List<EmployerDto> allIEmployers() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean updateItem(EmployerDto dto) throws SQLException, ClassNotFoundException {
        return false;
    }
}
