package com.spaceinvaders.gameobjects;

import com.spaceinvaders.Direction;
import com.spaceinvaders.ShapeMatrix;

public class Bullet extends GameObject {
  private int dy;
  public boolean isAlive = true;

  public Bullet(double x, double y, Direction direction) {
    super(x, y);
    this.dy = direction == Direction.UP ? -1 : 1;
    setMatrix(ShapeMatrix.BULLET);
  }

  public void move() {
    this.y += dy;
  }

  public void kill() {
    isAlive = false;
  }
}
