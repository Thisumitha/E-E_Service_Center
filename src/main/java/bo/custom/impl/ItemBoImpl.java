package bo.custom.impl;

import bo.custom.ItemBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    private List<String> categories=new ArrayList<>();
    private List<String> types=new ArrayList<>();


    private ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        return itemDao.save(new Item(
                dto.getCode(),
                dto.getName(),
                dto.getUnitPrice(),
                dto.getQty(),
                dto.getCategory(),
                dto.getType(),
                dto.getImage()
        ));
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {

        return itemDao.update(new Item(
                dto.getCode(),
                dto.getName(),
                dto.getUnitPrice(),
                dto.getQty(),
                dto.getCategory(),
                dto.getType(),
                dto.getImage()

        ));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDao.delete(id);
    }

    @Override
    public List<ItemDto> allItems() throws SQLException, ClassNotFoundException {
        List<Item> itemList = itemDao.getAll();
        List<ItemDto>list=new ArrayList<>();
        for (Item item:itemList){
            list.add(new ItemDto(
                    item.getCode(),
                    item.getName(),
                    item.getUnitPrice(),
                    item.getQtyOnHand(),
                    item.getCategory(),
                    item.getType(),
                    item.getImage()
            ));
        }
        return list;
    }

    @Override
    public List<String> getCategories() {
        categories.add("Electronic");
        categories.add("Electrical");
        return categories;
    }

    @Override
    public List<String> getTypes() {
        types.add("laptops");
        types.add("headphones");
        types.add("television");
        types.add("camera");
        types.add("fridge");

        return types;
    }


}
