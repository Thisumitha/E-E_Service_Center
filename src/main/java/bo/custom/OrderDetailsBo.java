package bo.custom;

import bo.SuperBo;
import dto.OrderDetailsDto;

import java.util.List;

public interface OrderDetailsBo extends SuperBo {


    void printBill(String oId);
}
