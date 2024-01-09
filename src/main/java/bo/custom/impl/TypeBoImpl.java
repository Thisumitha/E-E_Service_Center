package bo.custom.impl;

import bo.custom.TypeBo;
import dao.DaoFactory;
import dao.custom.TypeDao;
import dao.util.DaoType;
import dto.TypeDto;
import entity.Type;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeBoImpl implements TypeBo {
    private TypeDao typeDao= DaoFactory.getInstance().getDao(DaoType.TYPE);
    @Override
    public boolean saveItem(TypeDto dto) throws SQLException, ClassNotFoundException {
        int i = generateId();
        Type type =new Type(
                dto.getType(),
                dto.getCategory(),
                i
        );
        return typeDao.save(type);
    }

    @Override
    public List<TypeDto> allItems() throws SQLException, ClassNotFoundException {
        List<Type> typeList = typeDao.getAll();
        List<TypeDto>list=new ArrayList<>();
        for (Type type:typeList){
            list.add(new TypeDto(
                    type.getType(),
                    type.getCategory(),
                    type.getId()

            ));
        }
        return list;
    }

    @Override
    public int generateId() throws SQLException, ClassNotFoundException {
        int last= typeDao.lastOrder();
        System.out.println(last);
        if (last>-1){


            ++last;
            return last;
        }else{
            return 1;
        }
    }

    @Override
    public boolean updateItem(TypeDto dto) throws SQLException, ClassNotFoundException {
     return typeDao.update( new Type(
                dto.getType(),
                dto.getCategory(),
                dto.getId()
        ));

    }


}
