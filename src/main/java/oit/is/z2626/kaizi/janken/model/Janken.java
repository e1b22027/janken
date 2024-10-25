package oit.is.z2626.kaizi.janken.model;

public class Janken {
  String playerHand = "hogehoge";

  public Janken(String hand) {
    this.playerHand = hand;
  }

  public Janken() {

  }

  public String judge(int player1, int player2) {
    String msg = "hogehoge";
    if ((player1 - player2 + 3) % 3 == 0) {
      msg = "Draw";
    } else if ((player1 - player2 + 3) % 3 == 2) {
      msg = "You Win!";
    } else {
      msg = "You Lose....";
    }
    return msg;
  }

  public String translateHandsbyInteger(int hand) {
    String msg = "hogehoge";
    switch (hand) {
      case 0:
        msg = "Gu";
        break;
      case 1:
        msg = "Choki";
        break;
      case 2:
        msg = "Pa";
        break;
    }
    return msg;
  }

  public int translateHandsbyString(String hand) {
    int num = -1;
    switch (hand) {
      case "Gu":
        num = 0;
        break;
      case "Choki":
        num = 1;
        break;
      case "Pa":
        num = 2;
        break;
    }
    return num;
  }

  public String nameSerch(int id) {
    String name = "hogehoge";
    switch (id) {
      case 1:
        name = "CPU";
        break;
      case 2:
        name = "ほんだ";
        break;
      case 3:
        name = "いがき";
        break;
    }
    return name;
  }
}
