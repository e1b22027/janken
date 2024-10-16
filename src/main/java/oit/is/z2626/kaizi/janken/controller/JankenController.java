package oit.is.z2626.kaizi.janken.controller; //大事。//

import java.util.Random;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2626.kaizi.janken.model.Entry;
import oit.is.z2626.kaizi.janken.model.Match;
import oit.is.z2626.kaizi.janken.model.MatchMapper;
import oit.is.z2626.kaizi.janken.model.User;
import oit.is.z2626.kaizi.janken.model.UserMapper;

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
  private UserMapper userMapper;

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
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
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
    int playerhand = 0;
    Random rand = new Random();
    int cpuhand = rand.nextInt(3); // CPUの手をランダムに変更。
    String resultmsg = "hogehoge";
    String cpuhandmsg = "hogehoge";
    switch (hand) {
      case "Gu":
        playerhand = 0;
        break;
      case "Choki":
        playerhand = 1;
        break;
      case "Pa":
        playerhand = 2;
        break;
    }
    switch (cpuhand) {
      case 0:
        cpuhandmsg = "Gu";
        break;
      case 1:
        cpuhandmsg = "Choki";
        break;
      case 2:
        cpuhandmsg = "Pa";
        break;
    }
    if ((playerhand - cpuhand + 3) % 3 == 0) {
      resultmsg = "Draw";
    } else if ((playerhand - cpuhand + 3) % 3 == 2) {
      resultmsg = "You Win!";
    } else {
      resultmsg = "You Lose....";
    }
    model.addAttribute("playerhand", "あなたの手" + hand);
    model.addAttribute("cpuhand", "相手の手" + cpuhandmsg);// CPUの手をランダムに変更。
    model.addAttribute("resultmsg", "結果" + resultmsg);
    model.addAttribute("entry", this.entry); // 認証のエントリーを追加
    model.addAttribute("sizeMessage", this.entry.sizeUsersMessage());
    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {
    model.addAttribute("id", id);
    return "match.html";
  }

}
