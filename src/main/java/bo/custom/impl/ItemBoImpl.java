package bo.custom.impl;

import bo.custom.ItemBo;
import com.jfoenix.controls.JFXButton;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.util.DaoType;
import dto.ItemDto;
import dto.catelog.ItemCatolog;
import entity.Item;
import entity.Type;
import javafx.fxml.FXML;
import javafx.scene.image.Image;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemBoImpl implements ItemBo {
    @FXML
    private JFXButton btn;
    private List<String> categories=new ArrayList<>();
    private List<String> types=new ArrayList<>();


    private ItemDao itemDao= DaoFactory.getInstance().getDao(DaoType.ITEM);

    @Override
    public boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        Type type =new Type(
                dto.getType(),
               null,
                0
        );
       Item item=new Item(
                dto.getCode(),
                dto.getName(),
                dto.getUnitPrice(),
                dto.getQty(),
                dto.getImage()
        );
        item.setType(type);

        return itemDao.save(item);
    }

    @Override
    public boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException {
        Type type =new Type(
                dto.getType(),
             null,
                0
        );

        Item item=new Item(
                dto.getCode(),
                dto.getName(),
                dto.getUnitPrice(),
                dto.getQty(),
                dto.getImage()

        );
        item.setType(type);
        return itemDao.update(item);
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
                    item.getType().getType(),
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






}
