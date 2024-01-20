package bo.custom;

import bo.SuperBo;
import net.sf.jasperreports.engine.JRException;

public interface OrderDetailsBo extends SuperBo {



     void printbill(String id, String email) throws JRException;
}
