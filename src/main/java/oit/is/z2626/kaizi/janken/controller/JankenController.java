package oit.is.z2626.kaizi.janken.controller; //大事。//

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2626.kaizi.janken.model.Entry;

/**
 * JankenController
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

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

    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankenEvent(@RequestParam String hand, Model model) {
    int playerhand = 0;
    int cpuhand = 0; // CPUの手は必ずグーであるとしている。
    String resultmsg = "hogehoge";
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
    if ((playerhand - cpuhand + 3) % 3 == 0) {
      resultmsg = "Draw";
    } else if ((playerhand - cpuhand + 3) % 3 == 2) {
      resultmsg = "You Win!";
    } else {
      resultmsg = "You Lose....";
    }
    model.addAttribute("playerhand", "あなたの手" + hand);
    model.addAttribute("cpuhand", "相手の手" + "Gu");// CPUの手は必ずグーであるとしている。
    model.addAttribute("resultmsg", "結果" + resultmsg);
    model.addAttribute("entry", this.entry); // 認証のエントリーを追加
    return "janken.html";
  }

}