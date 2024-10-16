package oit.is.z2626.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
  @Select("SELECT * from users")
  ArrayList<User> selectAllbyUsers();
}
