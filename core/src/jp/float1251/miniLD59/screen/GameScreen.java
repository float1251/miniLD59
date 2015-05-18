package jp.float1251.miniLD59.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;

import jp.float1251.miniLD59.Constants;
import jp.float1251.miniLD59.Enemy;
import jp.float1251.miniLD59.Stage;
import jp.float1251.miniLD59.sprite.SpriteDownloader;

public class GameScreen implements Screen {
    SpriteBatch batch;
    private FitViewport viewport;
    private TextureRegion[] texRegions = new TextureRegion[64];
    private Texture img;
    private Stage stage;

    private Vector2 player = new Vector2(1, 1);
    private Enemy[] enemies;
    private BitmapFont font;
    private float total;

    @Override
    public void show() {
        batch = new SpriteBatch();

        viewport = new FitViewport(240, 160);

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        init();

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                handleInput(keycode);
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }

            private void handleInput(int keycode) {
                // 移動量を保持し、壁にあたったら移動を無効にする
                Vector2 vec = new Vector2();
                switch (keycode) {
                    case Input.Keys.LEFT:
                        vec.add(-1, 0);
                        break;
                    case Input.Keys.RIGHT:
                        vec.add(1, 0);
                        break;
                    case Input.Keys.UP:
                        vec.add(0, 1);
                        break;
                    case Input.Keys.DOWN:
                        vec.add(0, -1);
                        break;
                }
                player.add(vec);
                if (stage.checkCollision(player)) {
                    player.sub(vec);
                }

                if (stage.checkGoal(player)) {
                    // TODO goal演出
                    init();
                }
            }
        });
    }

    private void init() {
        total = 0;
        img = SpriteDownloader.loadSprite();
        texRegions = createTexRegions(img);
        stage = new Stage(51, 31);
        // playerの位置は 1, 1 startで固定
        player.set(1, 1);
        viewport.getCamera().position.set(player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE, 0);

        // 敵作成
        enemies = new Enemy[3];
        for (int i = 0; i < 3; i++) {
            boolean canCreate = false;
            Vector2 pos = new Vector2();
            while (!canCreate) {
                int x = (int) (Math.random() * 100000) % 51;
                int y = (int) (Math.random() * 100000) % 31;
                pos.set(x, y);
                if (!stage.checkCollision(pos)) {
                    canCreate = true;
                }
            }
            enemies[i] = new Enemy(pos, 0.5f, stage);
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // playerの中心位置にカメラを移動させる
        viewport.getCamera().position.set(player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE, 0);

        // TODO 敵とplayerの衝突判定

        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        stage.render(batch, texRegions);

        for (Enemy enemy : enemies) {
            enemy.update(Gdx.graphics.getDeltaTime());
            enemy.render(batch, texRegions);
        }

        // player render
        batch.draw(texRegions[0], player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE);

        total += delta;

        Vector3 pos = viewport.getCamera().unproject(new Vector3(40, 30, 0));
        font.draw(batch, "time:" + total, pos.x, pos.y);

        batch.end();

        if (Gdx.input.justTouched()) {
            img.dispose();
            img = SpriteDownloader.loadSprite();
            texRegions = createTexRegions(img);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private TextureRegion[] createTexRegions(Texture tex) {
        TextureRegion[] texRegions = new TextureRegion[64];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                texRegions[y * 8 + x] = new TextureRegion(tex, x * Constants.SPRITE_SIZE, y * Constants.SPRITE_SIZE,
                        Constants.SPRITE_SIZE, Constants.SPRITE_SIZE);
            }
        }
        return texRegions;
    }
}
