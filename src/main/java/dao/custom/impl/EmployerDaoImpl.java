package dao.custom.impl;

import dao.custom.EmployerDao;
import dto.EmployerDto;
import entity.Employers;

import java.sql.SQLException;
import java.util.List;

public class EmployerDaoImpl implements EmployerDao {

    @Override
    public boolean save(EmployerDto entity) throws SQLException, ClassNotFoundException {
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
