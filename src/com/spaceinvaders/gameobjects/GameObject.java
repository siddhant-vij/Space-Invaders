package com.spaceinvaders.gameobjects;

import com.codegym.engine.cell.*;

public class GameObject {
  public double x;
  public double y;
  public int[][] matrix;
  public int width;
  public int height;

  public GameObject(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void setMatrix(int[][] matrix) {
    this.matrix = matrix;
    width = matrix[0].length;
    height = matrix.length;
  }

  public void draw(Game game) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        game.setCellValueEx((int) this.x + x, (int) this.y + y, Color.values()[matrix[y][x]], "");
      }
    }
  }

  public boolean isCollision(GameObject gameObject) {
    for (int gameObjectX = 0; gameObjectX < gameObject.width; gameObjectX++) {
      for (int gameObjectY = 0; gameObjectY < gameObject.height; gameObjectY++) {
        if (gameObject.matrix[gameObjectY][gameObjectX] > 0) {
          if (isCollision(gameObjectX + gameObject.x, gameObjectY + gameObject.y)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private boolean isCollision(double x, double y) {
    for (int matrixX = 0; matrixX < width; matrixX++) {
      for (int matrixY = 0; matrixY < height; matrixY++) {
        if (matrix[matrixY][matrixX] > 0
            && matrixX + (int) this.x == (int) x
            && matrixY + (int) this.y == (int) y) {
          return true;
        }
      }
    }
    return false;
  }
}
