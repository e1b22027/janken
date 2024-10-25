package oit.is.z2626.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchinfo (user1,user2,user1Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{isActive});")
  void insertMatcheInfo(MatchInfo match);

  @Select("SELECT * FROM matchinfo WHERE isActive=TRUE;")
  ArrayList<MatchInfo> selectAlltrueActivebyMatchInfo();

  @Select("SELECT id FROM matchinfo WHERE isActive =TRUE AND (user1=#{enemyid} AND user2 =#{playerid});")
  int selectIdActive(int playerid, int enemyid);

  @Update("UPDATE matchinfo SET isActive = TRUE WHERE isActive =TRUE AND(user1=#{enemyid} AND user2 =#{playerid});")
  boolean checkActive(int playerid, int enemyid);

  @Select("SELECT user1Hand FROM matchinfo WHERE id=#{id}")
  String selectUser1Hand(int id);

  @Update("UPDATE matchinfo SET isActive =FALSE where id =#{id}")
  void updateActive(int id);
}
