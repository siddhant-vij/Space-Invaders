package com.spaceinvaders.gameobjects;

import com.spaceinvaders.Direction;
import com.spaceinvaders.ShapeMatrix;

public class EnemyShip extends Ship {
  public int score = 15;

  public EnemyShip(double x, double y) {
    super(x, y);
    super.setStaticView(ShapeMatrix.ENEMY);
  }

  public void move(Direction direction, double speed) {
    if (direction == Direction.RIGHT) {
      this.x += speed;
    } else if (direction == Direction.LEFT) {
      this.x -= speed;
    } else if (direction == Direction.DOWN) {
      this.y += 2;
    }
  }

  @Override
  public Bullet fire() {
    return new Bullet(x + 1, y + height, Direction.DOWN);
  }

  @Override
  public void kill() {
    if (!isAlive) {
      return;
    }
    isAlive = false;
    super.setAnimatedView(false,
        ShapeMatrix.KILL_ENEMY_ANIMATION_FIRST,
        ShapeMatrix.KILL_ENEMY_ANIMATION_SECOND,
        ShapeMatrix.KILL_ENEMY_ANIMATION_THIRD);
  }
}
