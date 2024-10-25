package oit.is.z2626.kaizi.janken.model;

public class MatchInfo {
  int id;
  int user1;
  int user2;
  String user1Hand;
  boolean isActive;

  public MatchInfo(int user1, int user2, String user1Hand, boolean isActive) {
    this.user1 = user1;
    this.user2 = user2;
    this.user1Hand = user1Hand;
    this.isActive = isActive;
  }

  public MatchInfo(int id, int user1, int user2, String user1Hand, boolean isActive) {
    this.id = id;
    this.user1 = user1;
    this.user2 = user2;
    this.user1Hand = user1Hand;
    this.isActive = isActive;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUser1() {
    return user1;
  }

  public void setUser1(int user1) {
    this.user1 = user1;
  }

  public int getUser2() {
    return user2;
  }

  public void setUser2(int user2) {
    this.user2 = user2;
  }

  public String getUser1Hand() {
    return user1Hand;
  }

  public void setUser1Hand(String user1Hand) {
    this.user1Hand = user1Hand;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

}
