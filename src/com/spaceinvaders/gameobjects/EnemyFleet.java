package com.spaceinvaders.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.codegym.engine.cell.Game;
import com.spaceinvaders.Direction;
import com.spaceinvaders.ShapeMatrix;
import com.spaceinvaders.SpaceInvadersGame;

public class EnemyFleet {
  private static final int ROWS_COUNT = 3;
  private static final int COLUMNS_COUNT = 10;
  private static final int STEP = ShapeMatrix.ENEMY.length + 1;
  private List<EnemyShip> ships;
  private Direction direction = Direction.RIGHT;

  public EnemyFleet() {
    createShips();
  }

  private void createShips() {
    ships = new ArrayList<>();
    for (int x = 0; x < COLUMNS_COUNT; x++) {
      for (int y = 0; y < ROWS_COUNT; y++) {
        ships.add(new EnemyShip(x * STEP, y * STEP + 12));
      }
    }
    ships.add(new Boss(STEP * COLUMNS_COUNT / 2 - ShapeMatrix.BOSS_ANIMATION_FIRST.length / 2 - 1, 5));
  }

  public void draw(Game game) {
    for (EnemyShip ship : ships) {
      ship.draw(game);
    }
  }

  private double getLeftBorder() {
    double left = SpaceInvadersGame.WIDTH;
    for (GameObject ship : ships) {
      if (ship.x < left) {
        left = ship.x;
      }
    }
    return left;
  }

  private double getRightBorder() {
    double right = 0;
    for (GameObject ship : ships) {
      if (ship.x + ship.width > right) {
        right = ship.x + ship.width;
      }
    }
    return right;
  }

  private double getSpeed() {
    return Math.min(2.0, 3.0 / ships.size());
  }

  public void move() {
    if (ships.isEmpty()) {
      return;
    }
    if (direction == Direction.LEFT && getLeftBorder() < 0) {
      direction = Direction.RIGHT;
      for (EnemyShip ship : ships) {
        ship.move(Direction.DOWN, getSpeed());
      }
    } else if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH) {
      direction = Direction.LEFT;
      for (EnemyShip ship : ships) {
        ship.move(Direction.DOWN, getSpeed());
      }
    } else {
      for (EnemyShip ship : ships) {
        ship.move(direction, getSpeed());
      }
    }
  }

  public Bullet fire(Game game) {
    if (ships.isEmpty()) {
      return null;
    }
    if (game.getRandomNumber(100 / SpaceInvadersGame.DIFFICULTY) > 0) {
      return null;
    }
    return ships.get(game.getRandomNumber(ships.size())).fire();
  }

  public void deleteHiddenShips() {
    for (Ship ship : new ArrayList<>(ships)) {
      if (!ship.isVisible()) {
        ships.remove(ship);
      }
    }
  }

  public double getBottomBorder() {
    double bottom = 0;
    for (GameObject ship : ships) {
      if (ship.y + ship.height > bottom) {
        bottom = ship.y + ship.height;
      }
    }
    return bottom;
  }

  public int getShipCount() {
    return ships.size();
  }

  public int checkHit(List<Bullet> bullets) {
    if (bullets.isEmpty()) {
      return 0;
    }
    int count = 0;
    for (EnemyShip ship : ships) {
      for (Bullet bullet : bullets) {
        if (ship.isAlive && bullet.isAlive && ship.isCollision(bullet)) {
          count += ship.score;
          ship.kill();
          bullet.kill();
          break;
        }
      }
    }
    return count;
  }
}
