package oit.is.z2626.kaizi.janken.controller; //大事。//

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * JankenController
 *
 * クラスの前に@Controllerをつけていると，HTTPリクエスト（GET/POSTなど）があったときに，このクラスが呼び出される
 */

@Controller
public class JankenController {

  @PostMapping("/janken")
  public String enterEvent(@RequestParam String username, ModelMap model) {
    if (username != "") {
      String tmp = "Hi,";
      tmp += username;
      tmp += "!";
      username = tmp;
    }
    model.addAttribute("username", username);
    return "janken.html";
  }

  @GetMapping("/janken")
  public String janken_noname() {
    return "janken.html";
  }

}
