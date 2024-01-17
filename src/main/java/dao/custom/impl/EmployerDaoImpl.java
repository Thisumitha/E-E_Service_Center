package dao.custom.impl;

import dao.custom.EmployerDao;
import dao.util.HibernateUtil;
import dto.EmployerDto;
import entity.Access;
import entity.Employers;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class EmployerDaoImpl implements EmployerDao {

    @Override
    public boolean save(EmployerDto dto) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        new Access(
                dto.getAccess().getId(),
                dto.getAccess().isStoreAccess(),
                dto.getAccess().isInventoryAccess(),
                dto.getAccess().isCustomerAccess(),
                dto.getAccess().isReportAccess(),
                dto.getAccess().isRepairAccess()
        );
        return false;
    }

    @Override
    public boolean update(EmployerDto entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String value) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<EmployerDto> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
