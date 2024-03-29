package dao.util;

import dto.AccessDto;
import dto.EmployerDto;
import lombok.*;


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
   public void setData(EmployerDto user) {

      this.user = user;
   }
   public EmployerDto getUser(){
      return user ;
   }


}
