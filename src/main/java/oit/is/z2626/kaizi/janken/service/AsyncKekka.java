package oit.is.z2626.kaizi.janken.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2626.kaizi.janken.model.Match;

import oit.is.z2626.kaizi.janken.model.MatchMapper;
import oit.is.z2626.kaizi.janken.model.User;
import oit.is.z2626.kaizi.janken.model.UserMapper;

@Service
public class AsyncKekka {
  boolean dbUpdated = false; // ベータベース共有用
  int id = -1; // 対象の行目格納用

  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private UserMapper userMapper;

  /* userMapper処理 */

  public ArrayList<User> syncShowUsersList() {
    return this.userMapper.selectAllbyUsers();
  }

  public int syncShowUserId(String loginUserName) {
    ArrayList<User> player = userMapper.selectIdbyUsers(loginUserName);// 自身は1つしか登録されていない仮定のもとこの操作を有効としている。
    return player.get(0).getId();

  }
  /* matchMapper処理 */

  public ArrayList<Match> syncShowMatchesList() {
    return this.matchMapper.selectAllbyMatches();
  }

  public ArrayList<Match> syncActiveMatch() {
    return this.matchMapper.selectActivebyMatch();
  }

  public int syncActiveRecode() {
    ArrayList<Match> tmp = this.syncActiveMatch();
    return tmp.get(0).getId();
  }

  public void syncInsertMatch(Match match) {
    this.matchMapper.insertMatchesInfo(match);// 試合結果を格納する
    if (match.getUser2() != 1) {// プレイヤー戦いの時のみtrueにする。 理由：Aが待ちの時にBがCPUと戦うとバグる
      this.dbUpdated = true;
      this.id = this.syncActiveRecode();
    }
  }

  public Match syncSelectMatch(int id) {
    return this.matchMapper.selectMatchfromId(id);
  }

  /*      */

  @Async
  public void asyncJankenKekka(SseEmitter emitter) {
    dbUpdated = true;
    try {
      while (true) {
        if (false == dbUpdated) {
          TimeUnit.MILLISECONDS.sleep(50);
          continue;
        }

        TimeUnit.MILLISECONDS.sleep(100);
        dbUpdated = false;

        if (id != -1) {
          Match match = this.syncSelectMatch(this.id);// 該当レコードを出力。
          emitter.send(match);
          matchMapper.updateActive(this.id); // 更新作業
          TimeUnit.MILLISECONDS.sleep(200);
          id = -1;
        }
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncJankenKekka complete");
  }

}
