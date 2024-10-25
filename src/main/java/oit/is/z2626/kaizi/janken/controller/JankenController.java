package oit.is.z2626.kaizi.janken.controller; //大事。//

import java.util.Random;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2626.kaizi.janken.model.Entry;
import oit.is.z2626.kaizi.janken.model.Match;
import oit.is.z2626.kaizi.janken.model.MatchMapper;
import oit.is.z2626.kaizi.janken.model.MatchInfo;
import oit.is.z2626.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2626.kaizi.janken.model.User;
import oit.is.z2626.kaizi.janken.model.UserMapper;
import oit.is.z2626.kaizi.janken.model.Janken;

/**
 * JankenController
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */

@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  private MatchMapper matchMapper;
  @Autowired
  private MatchInfoMapper matchInfoMapper;
  @Autowired
  private UserMapper userMapper;

  String loginUserName = "hogehoge";

  @PostMapping("/janken")
  public String enterEvent(@RequestParam String username, ModelMap model) {
    model.addAttribute("username", "Hi " + username);
    return "janken.html";
  }

  // @GetMapping("/janken")
  // public String janken_noname() {
  // return "janken.html";
  // }

  @GetMapping("/janken")
  public String sample38(Principal prin, ModelMap model) {
    String user = prin.getName();
    this.loginUserName = user;
    this.entry.addUser(user);
    model.addAttribute("entry", this.entry);
    model.addAttribute("sizeMessage", this.entry.sizeUsersMessage());

    ArrayList<Match> matches = matchMapper.selectAllbyMatches();
    model.addAttribute("matches", matches);
    ArrayList<User> users = userMapper.selectAllbyUsers();
    model.addAttribute("users", users);

    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankenEvent(@RequestParam String hand, Model model) {
    Janken event = new Janken(hand);
    int playerhand = event.translateHandsbyString(hand);
    Random rand = new Random();
    int cpuhand = rand.nextInt(3);
    String resultmsg = event.judge(playerhand, cpuhand);
    String cpuhandmsg = event.translateHandsbyInteger(cpuhand);
    model.addAttribute("playerhand", "あなたの手" + hand);
    model.addAttribute("cpuhand", "相手の手" + cpuhandmsg);
    model.addAttribute("resultmsg", "結果" + resultmsg);
    // model.addAttribute("entry", this.entry); // 認証のエントリーを追加
    // model.addAttribute("sizeMessage", this.entry.sizeUsersMessage());
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {
    int num = id;
    String enemyname = "hogehoge";
    switch (id) {
      case 1:
        enemyname = "CPU";
        break;
      case 2:
        enemyname = "ほんだ";
        break;
      case 3:
        enemyname = "いがき";
        break;
    }
    model.addAttribute("id", num);
    model.addAttribute("enemyname", enemyname);
    return "match.html";
  }

  @GetMapping("/fight")
  public String jankenEvent2(@RequestParam Integer id, @RequestParam String hand, Model model) {
    // ArrayList<User> player = userMapper.selectIdbyUsers(loginUserName);
    // int cpuid = id; // CPUのDB上のIDをそのまま書いてる。

    // Match match = new Match();

    // Janken event = new Janken(hand);
    // int playerhand = event.translateHandsbyString(hand);
    // Random rand = new Random();
    // // int cpuhand = rand.nextInt(3);
    // String resultmsg = event.judge(playerhand, cpuhand);
    // String cpuhandmsg = event.translateHandsbyInteger(cpuhand);
    ArrayList<User> player = userMapper.selectIdbyUsers(loginUserName);
    int playerid = player.get(0).getId();
    MatchInfo matchInfo = new MatchInfo(playerid, id, hand, true);
    matchInfoMapper.insertMatcheInfo(matchInfo);
    String enemyname = "hogehoge";
    switch (id) {
      case 1:
        enemyname = "CPU";
        break;
      case 2:
        enemyname = "ほんだ";
        break;
      case 3:
        enemyname = "いがき";
        break;
    }
    // match.setUser1(player.get(0).getId());
    // match.setUser2(cpuid);
    // match.setUser1Hand(hand);
    // match.setUser2Hand(cpuhandmsg);

    // matchMapper.insertMatchesInfo(match);

    // model.addAttribute("playerhand", "あなたの手" + hand);
    // model.addAttribute("cpuhand", "相手の手" + cpuhandmsg);
    // model.addAttribute("resultmsg", "結果" + resultmsg);
    // model.addAttribute("entry", this.entry); // 認証のエントリーを追加

    ArrayList<Match> matches = matchMapper.selectAllbyMatches();
    model.addAttribute("matches", matches);
    model.addAttribute("id", id);
    model.addAttribute("enemyname", enemyname);
    // return "match.html";
    return "wait.html"; // 相手を選び手を選んだ際にDBにINSERTするより変更
  }

}
