package oit.is.z2626.kaizi.janken.controller; //大事。//

import java.util.Random;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2626.kaizi.janken.model.Entry;
import oit.is.z2626.kaizi.janken.model.Match;
import oit.is.z2626.kaizi.janken.model.MatchInfo;
import oit.is.z2626.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2626.kaizi.janken.model.User;
import oit.is.z2626.kaizi.janken.service.AsyncKekka;
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
  private MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka gameInfo;

  String loginUserName = "hogehoge";

  @GetMapping("/janken")
  public String sample38(Principal prin, ModelMap model) {

    String user = prin.getName();// 名前を取得
    this.loginUserName = user; // グローバル変数にログインユーザー名を格納
    this.entry.addUser(user); // entryにログインユーザーを登録

    model.addAttribute("entry", this.entry);
    model.addAttribute("sizeMessage", this.entry.sizeUsersMessage());

    ArrayList<Match> matches = gameInfo.syncShowMatchesList();// 勝負ログを取得
    model.addAttribute("matches", matches);
    ArrayList<User> users = gameInfo.syncShowUsersList();// 登録ユーザ情報を取得
    model.addAttribute("users", users);

    ArrayList<MatchInfo> matchesInfo = matchInfoMapper.selectAlltrueActivebyMatchInfo(); // アクティブな試合情報を取得
    model.addAttribute("activeMatchs", matchesInfo);

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

    return "janken.html";
  }

  @GetMapping("/match")
  public String match(@RequestParam Integer id, ModelMap model) {
    Janken event = new Janken();
    int num = id;
    String enemyname = event.nameSerch(id);

    model.addAttribute("id", num);
    model.addAttribute("enemyname", enemyname);
    return "match.html";
  }

  @GetMapping("/fight")
  public String jankenEvent2(@RequestParam Integer id, @RequestParam String hand, Model model) {
    Janken event = new Janken();
    int playerid = gameInfo.syncShowUserId(loginUserName);// 自身のidを取得する
    MatchInfo matchInfo = new MatchInfo(playerid, id, hand, true);// 自身の手の情報をinfoに格納する
    String enemyname = event.nameSerch(id);

    if (enemyname == "CPU") {// CPU戦
      int playerhand = event.translateHandsbyString(hand);
      Random rand = new Random();
      int cpuhand = rand.nextInt(3);
      String resultmsg = event.judge(playerhand, cpuhand);
      String cpuhandmsg = event.translateHandsbyInteger(cpuhand);
      Match match = new Match(playerid, id, hand, cpuhandmsg, false);
      gameInfo.syncInsertMatch(match);
      model.addAttribute("playerhand", "あなたの手" + hand);
      model.addAttribute("cpuhand", "相手の手" + cpuhandmsg);
      model.addAttribute("resultmsg", "結果" + resultmsg);

      model.addAttribute("id", id);
      return "match.html";
    }

    // Player戦

    if (matchInfoMapper.checkActive(playerid, id)) {
      int targetrecode = matchInfoMapper.selectIdActive(playerid, id);
      String player2hand = matchInfoMapper.selectUser1Hand(targetrecode);// 相手が出した手を取得する 必ず１つ分のデータしかないためStringにしている。
      Match match = new Match(playerid, id, hand, player2hand, true);
      gameInfo.syncInsertMatch(match);// 結果を格納する処理
      matchInfoMapper.updateActive(targetrecode);// FALSEに更新
    } else {
      matchInfoMapper.insertMatcheInfo(matchInfo);
    }

    ArrayList<Match> matches = gameInfo
        .syncShowMatchesList();
    model.addAttribute("matches", matches);
    model.addAttribute("id", id);
    model.addAttribute("enemyname", enemyname);

    return "wait.html"; // 相手を選び手を選んだ際にDBにINSERTするより変更

  }

  @GetMapping("/step9")
  public SseEmitter sample59() {// htmlが読み込まれた時に呼び出される。
    final SseEmitter sseEmitter = new SseEmitter();
    this.gameInfo.asyncJankenKekka(sseEmitter);
    return sseEmitter;
  }

}
