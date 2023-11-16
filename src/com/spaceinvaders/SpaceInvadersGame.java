package com.spaceinvaders;

import java.util.ArrayList;
import java.util.List;

import com.codegym.engine.cell.*;
import com.spaceinvaders.gameobjects.*;

public class SpaceInvadersGame extends Game {
  public static final int WIDTH = 64;
  public static final int HEIGHT = 64;
  private List<Star> stars;
  private EnemyFleet enemyFleet;
  public static final int DIFFICULTY = 5;
  private List<Bullet> enemyBullets;
  private PlayerShip playerShip;
  private boolean isGameStopped;
  private int animationsCount;
  private List<Bullet> playerBullets;
  private static final int PLAYER_BULLETS_MAX = 1;
  private int score;

  @Override
  public void initialize() {
    setScreenSize(WIDTH, HEIGHT);
    createGame();
  }

  private void createGame() {
    createStars();
    enemyFleet = new EnemyFleet();
    enemyBullets = new ArrayList<>();
    playerShip = new PlayerShip();
    isGameStopped = false;
    animationsCount = 0;
    playerBullets = new ArrayList<>();
    drawScene();
    setTurnTimer(40);
    score = 0;
  }

  private void drawScene() {
    drawField();
    playerShip.draw(this);
    enemyFleet.draw(this);
    for (Bullet enemyBullet : enemyBullets) {
      enemyBullet.draw(this);
    }
    for (Bullet playerBullet : playerBullets) {
      playerBullet.draw(this);
    }
  }

  private void drawField() {
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        setCellValueEx(x, y, Color.BLACK, "");
      }
    }
    for (Star star : stars) {
      star.draw(this);
    }
  }

  private void createStars() {
    stars = new ArrayList<>();
    for (int i = 0; i < 8; i++) {
      stars.add(new Star(Math.random() * WIDTH, Math.random() * HEIGHT));
    }
  }

  @Override
  public void onTurn(int step) {
    moveSpaceObjects();
    check();
    Bullet firedBullet = enemyFleet.fire(this);
    if (firedBullet != null) {
      enemyBullets.add(firedBullet);
    }
    setScore(score);
    drawScene();
  }

  private void moveSpaceObjects() {
    enemyFleet.move();
    for (Bullet enemyBullet : enemyBullets) {
      enemyBullet.move();
    }
    playerShip.move();
    playerBullets.forEach(Bullet::move);
  }

  private void removeDeadBullets() {
    for (Bullet bullet : new ArrayList<>(enemyBullets)) {
      if (!bullet.isAlive || bullet.y >= HEIGHT - 1) {
        enemyBullets.remove(bullet);
      }
    }
    for (Bullet bullet : new ArrayList<>(playerBullets)) {
      if (!bullet.isAlive || bullet.y + bullet.height < 0) {
        playerBullets.remove(bullet);
      }
    }
  }

  private void check() {
    playerShip.checkHit(enemyBullets);
    score += enemyFleet.checkHit(playerBullets);
    enemyFleet.deleteHiddenShips();
    removeDeadBullets();
    if (!playerShip.isAlive) {
      stopGameWithDelay();
    }
    if (enemyFleet.getBottomBorder() >= playerShip.y) {
      playerShip.kill();
    }
    if (enemyFleet.getShipCount() == 0) {
      playerShip.win();
      stopGameWithDelay();
    }
  }

  private void stopGame(boolean isWin) {
    isGameStopped = true;
    stopTurnTimer();
    if (isWin) {
      showMessageDialog(Color.BLACK, "YOU WIN", Color.GREEN, 100);
    } else {
      showMessageDialog(Color.BLACK, "YOU LOSE", Color.RED, 100);
    }
  }

  private void stopGameWithDelay() {
    animationsCount++;
    if (animationsCount >= 10) {
      stopGame(playerShip.isAlive);
    }
  }

  @Override
  public void onKeyPress(Key key) {
    if (key == Key.SPACE && isGameStopped) {
      createGame();
      return;
    } else if (key == Key.LEFT) {
      playerShip.setDirection(Direction.LEFT);
    } else if (key == Key.RIGHT) {
      playerShip.setDirection(Direction.RIGHT);
    } else if (key == Key.SPACE) {
      Bullet bullet = playerShip.fire();
      if (bullet != null && playerBullets.size() < PLAYER_BULLETS_MAX) {
        playerBullets.add(bullet);
      }
    }
  }

  @Override
  public void onKeyReleased(Key key) {
    if (key == Key.LEFT && playerShip.getDirection() == Direction.LEFT) {
      playerShip.setDirection(Direction.UP);
    } else if (key == Key.RIGHT && playerShip.getDirection() == Direction.RIGHT) {
      playerShip.setDirection(Direction.UP);
    }
  }

  @Override
  public void setCellValueEx(int x, int y, Color color, String text) {
    if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
      return;
    }
    super.setCellValueEx(x, y, color, text);
  }
}
