package oit.is.z2626.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface MatchMapper {
  @Select("SELECT * from matches")
  ArrayList<Match> selectAllbyMatches();

  @Insert("INSERT INTO matches (user1,user2,user1Hand,user2Hand,isActive) VALUES (#{user1},#{user2},#{user1Hand},#{user2Hand},#{isActive});")
  void insertMatchesInfo(Match match);

  @Select("SELECT * FROM matches where isActive=TRUE")
  ArrayList<Match> selectActivebyMatch();

  @Update("UPDATE  matches SET isActive = FALSE WHERE id =#{id}") // 更新
  void updateActive(int id);

  @Select("SELECT * FROM matches where id=#{id}")
  Match selectMatchfromId(int id);
}
