package com.spaceinvaders.gameobjects;

import java.util.List;

import com.spaceinvaders.Direction;
import com.spaceinvaders.ShapeMatrix;
import com.spaceinvaders.SpaceInvadersGame;

public class PlayerShip extends Ship {
  private Direction direction = Direction.UP;

  public PlayerShip() {
    super(SpaceInvadersGame.WIDTH / 2.0, SpaceInvadersGame.HEIGHT - ShapeMatrix.PLAYER.length - 1);
    super.setStaticView(ShapeMatrix.PLAYER);
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction newDirection) {
    if (newDirection != Direction.DOWN) {
      this.direction = newDirection;
    }
  }

  public void checkHit(List<Bullet> bullets) {
    if (bullets.isEmpty()) {
      return;
    }
    if (isAlive) {
      for (Bullet bullet : bullets) {
        if (bullet.isAlive && isCollision(bullet)) {
          kill();
          bullet.kill();
          break;
        }
      }
    }
  }

  @Override
  public void kill() {
    if (!isAlive) {
      return;
    }
    isAlive = false;
    super.setAnimatedView(false,
        ShapeMatrix.KILL_PLAYER_ANIMATION_FIRST,
        ShapeMatrix.KILL_PLAYER_ANIMATION_SECOND,
        ShapeMatrix.KILL_PLAYER_ANIMATION_THIRD,
        ShapeMatrix.DEAD_PLAYER);
  }

  public void move() {
    if (isAlive) {
      switch (direction) {
        case LEFT:
          x--;
          break;
        case RIGHT:
          x++;
          break;
        default:
          break;
      }
      if( x < 0) {
        x = 0;
      }
      if (x + width > SpaceInvadersGame.WIDTH) {
        x = SpaceInvadersGame.WIDTH - width;
      }
    }
  }

  @Override
  public Bullet fire() {
    if (!isAlive) {
      return null; 
    }
    return new Bullet(x + 2, y - ShapeMatrix.BULLET.length, Direction.UP);
  }

  public void win() {
    this.setStaticView(ShapeMatrix.WIN_PLAYER);
  }
}
