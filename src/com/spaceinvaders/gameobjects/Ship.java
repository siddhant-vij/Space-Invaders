package com.spaceinvaders.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.codegym.engine.cell.Game;

public class Ship extends GameObject {
  public boolean isAlive = true;
  private List<int[][]> frames;
  private int frameIndex;
  private boolean loopAnimation = false;

  public Ship(double x, double y) {
    super(x, y);
  }

  public void setStaticView(int[][] viewFrame) {
    super.setMatrix(viewFrame);
    frames = new ArrayList<>();
    frames.add(viewFrame);
    frameIndex = 0;
  }

  public Bullet fire() {
    return null;
  }

  public void kill() {
    isAlive = false;
  }

  public void setAnimatedView(boolean isLoopAnimation, int[][]... viewFrames) {
    loopAnimation = isLoopAnimation;
  }

  public void nextFrame() {
    frameIndex++;
    if (frameIndex >= frames.size()) {
      if (loopAnimation) {
        frameIndex = 0;
      } else {
        return;
      }
    }
    matrix = frames.get(frameIndex);
  }

  @Override
  public void draw(Game game) {
    super.draw(game);
    nextFrame();
  }

  public boolean isVisible() {
    if (!isAlive && frameIndex >= frames.size()) {
      return false;
    } else {
      return true;
    }
  }
}
