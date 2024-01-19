package dao;


import dao.custom.impl.*;
import dao.util.DaoType;

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
            case REPAIR_PARTS:return (T)new RepairPartsDetailsDaoImpl();
            case ACCESS:return (T) new AccessDaoImpl();
            case EMPLOYER:return (T)new EmployerDaoImpl();
            case ORDER_DETAIL:return (T)new OrderDetailsDaoImpl();
        }
        return null;
    }
}
