package jp.float1251.miniLD59;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by takahiroiwatani on 2015/05/17.
 */
public class Enemy {
    private final Stage stage;
    private float moveInterval;
    public Vector2 position;

    private float time;
    private int index;

    public Enemy(Vector2 pos, float moveInterval, Stage stage) {
        position = pos.cpy();
        this.moveInterval = moveInterval;
        this.stage = stage;
        this.index = 39;
    }

    public void update(float delta) {
        time += delta;
        if (time >= moveInterval) {
            // randomに動かす
            int val = (int) (Math.random() * 100) % 4;
            Vector2 vel = new Vector2();
            switch (val) {
                case 0:
                    vel.add(1, 0);
                    break;
                case 1:
                    vel.add(-1, 0);
                    break;
                case 2:
                    vel.add(0, 1);
                    break;
                case 3:
                    vel.add(0, -1);
                    break;
                default:
                    break;
            }
            position.add(vel);
            if (stage.checkCollision(position)) {
                position.sub(vel);
            } else {
                time = 0;
            }
        }
    }

    public void render(SpriteBatch batch, TextureRegion[] texs) {
        batch.draw(texs[index], position.x * Constants.SPRITE_SIZE, position.y * Constants.SPRITE_SIZE);
    }
}
