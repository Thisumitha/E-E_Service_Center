package dao;


import bo.custom.impl.RepairItemBoImpl;
import dao.custom.impl.*;
import dao.util.DaoType;
import entity.Customer;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){

    }
    public static DaoFactory getInstance(){
        return daoFactory!=null? daoFactory:(daoFactory=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){

            case ITEM:return (T)  new ItemDaoImpl();
            case TYPE:return (T)  new TypeDaoImpl();
            case CUSTOMER:return (T)  new CustomerDaoImpl();
            case ORDER:return (T)  new OrderDaoImpl();
            case REPAIR_ITEM:return (T)new RepairItemDaoImpl();
        }
        return null;
    }
}
