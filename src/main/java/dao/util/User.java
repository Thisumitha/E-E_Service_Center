package dao.util;

import dto.AccessDto;
import dto.EmployerDto;
import lombok.*;

import java.util.Properties;


@NoArgsConstructor
@ToString
@Getter
@Setter
public class User {
   private static EmployerDto user;

   public AccessDto getAccessLevel(){
      return user.getAccess();
   }
   public String getName(){
       return user.getName();
   }
   public void userDeta(EmployerDto user) {
      this.user = user;
   }


}
