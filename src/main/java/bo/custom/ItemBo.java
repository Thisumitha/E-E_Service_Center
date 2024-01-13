package bo.custom;

import bo.SuperBo;
import dto.ItemDto;
import dto.catelog.ItemCatologDto;

import java.sql.SQLException;
import java.util.List;

public interface ItemBo extends SuperBo {
    boolean saveItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    boolean updateItem(ItemDto dto) throws SQLException, ClassNotFoundException;

    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

    List<ItemDto> allItems() throws SQLException, ClassNotFoundException;

    List<String> getCategories();
    void savecart(List<ItemCatologDto> save);
    List<ItemCatologDto> loadCart();

    void updatequantities(List<ItemCatologDto> itemCatologDtos) throws SQLException, ClassNotFoundException;
}



