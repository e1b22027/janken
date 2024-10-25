package oit.is.z2626.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  void insertMatcheInfo(MatchInfo match);

  @Select("SELECT * FROM matchinfo WHERE isActive=TRUE;")
  ArrayList<MatchInfo> selectAlltrueActivebyMatchInfo();
}
