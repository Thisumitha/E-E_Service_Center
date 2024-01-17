package bo;

import bo.custom.impl.*;
import dao.util.BoType;

public class BoFactory {
    private static BoFactory boFactory;
    private  BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null? boFactory:(boFactory=new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case ITEM:return (T) new ItemBoImpl();
            case TYPE:return (T) new TypeBoImpl();
            case CUSTOMER:return (T) new CustomerBoImpl();
            case ORDER:return (T)new OrderBoImpl();
            case REPAIR_ITEM:return (T)new RepairItemBoImpl();
            case REPAIR_PARTS:return (T)new RepairPartsBoImpl();
            case EMPLOYER:return (T)new EmployerBoImpl();
            case ACCESS:return (T)new AccessBoImpl();
        }
        return null;

    }
}
