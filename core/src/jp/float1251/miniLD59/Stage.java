package jp.float1251.miniLD59;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by takahiroiwatani on 2015/05/17.
 */
public class Stage {

    private int maxLines = 11;
    private int maxRows = 11;


    public static class Type {
        public static final int NONE = 0;
        public static final int WALL = 1;
        public static final int GOAL = 2;
    }

    public int[][] cells;

    public Stage(int lines, int rows) {
        if (lines % 2 == 0 || rows % 2 == 0) {
            new RuntimeException("only odd number");
        }
        maxLines = lines;
        maxRows = rows;

        cells = new int[maxLines][maxRows];

        init();
    }

    /**
     * 壁に衝突するかどうか
     *
     * @param player cellの位置
     * @return
     */
    public boolean checkCollision(Vector2 player) {
        if (cells[((int) player.x)][((int) player.y)] == Type.WALL) {
            return true;
        }
        return false;
    }

    public boolean checkGoal(Vector2 player) {
        if (cells[((int) player.x)][((int) player.y)] == Type.GOAL) {
            return true;
        }
        return false;
    }

    public void render(SpriteBatch batch, TextureRegion[] texRegions) {
        for (int i = 0; i < maxLines; i++) {
            for (int j = 0; j < maxRows; j++) {
                if (cells[i][j] == Type.WALL) {
                    batch.draw(texRegions[40], i * Constants.SPRITE_SIZE, j * Constants.SPRITE_SIZE);
                } else if (cells[i][j] == Type.GOAL) {
                    batch.draw(texRegions[50], i * Constants.SPRITE_SIZE, j * Constants.SPRITE_SIZE);
                }
            }
        }
    }

    /**
     * 壁倒し法
     * cellsを初期化する。
     * 外周を壁で埋め、[偶数][偶数]のときに柱を建てる.
     */
    private void init() {
        for (int i = 0; i < maxLines; i++) {
            for (int j = 0; j < maxRows; j++) {
                if (i == 0 || i == maxLines - 1 || j == 0 || j == maxRows - 1) {
                    cells[i][j] = Type.WALL;
                } else if (i % 2 == 0 && j % 2 == 0) {
                    cells[i][j] = Type.WALL;
                    int val = (int) (Math.random() * 400) % 4;
                    // 0:上, 1:下, 2:右, 3:左に壁を作る
                    switch (val) {
                        case 0:
                            cells[i - 1][j] = Type.WALL;
                            break;
                        case 1:
                            cells[i + 1][j] = Type.WALL;
                            break;
                        case 2:
                            cells[i][j + 1] = Type.WALL;
                            break;
                        case 3:
                            cells[i][j - 1] = Type.WALL;
                            break;
                        default:
                            cells[i - 1][j] = Type.WALL;
                            break;
                    }
                } else {
                    // 棒倒し処理によりすでに設定されている可能性があるため
                    if (cells[i][j] != Type.WALL) {
                        cells[i][j] = Type.NONE;
                    }
                }
            }
        }

        // GOALを設定する.
        boolean set = false;
        while (!set) {
            int x = (int) (Math.random() * 100000) % maxLines;
            int y = (int) (Math.random() * 100000) % maxRows;
            if (cells[x][y] == Type.NONE) {
                if (!isSurroundWall(x, y)) {
                    cells[x][y] = Type.GOAL;
                    set = true;
                }
            }
        }
    }

    private boolean isSurroundWall(int x, int y) {
        if (cells[x - 1][y] == Type.WALL && cells[x + 1][y] == Type.WALL && cells[x][y + 1] == Type.WALL && cells[x][y - 1] == Type.WALL) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < maxLines; i++) {
            for (int j = 0; j < maxRows; j++) {
                if (j != 0) {
                    sb.append(", ");
                }
                sb.append(cells[i][j]);
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

}
