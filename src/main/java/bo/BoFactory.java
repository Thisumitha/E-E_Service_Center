package bo;

import bo.custom.impl.CustomerBoImpl;
import bo.custom.impl.ItemBoImpl;
import bo.custom.impl.TypeBoImpl;
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
            //case CUSTOMER:return (T)  new CustomerBoImpl();
             case ITEM:return (T) new ItemBoImpl();
            case TYPE:return (T) new TypeBoImpl();
            case CUSTOMER:return (T) new CustomerBoImpl();
        }
        return null;

    }
}
