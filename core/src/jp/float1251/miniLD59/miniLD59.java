package jp.float1251.miniLD59;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;

import jp.float1251.miniLD59.sprite.SpriteDownloader;

public class miniLD59 extends ApplicationAdapter {
    SpriteBatch batch;
    private FitViewport viewport;
    private TextureRegion[] texRegions = new TextureRegion[64];
    private Texture img;
    private Stage stage;

    private Vector2 player = new Vector2(1, 1);

    @Override
    public void create() {
        batch = new SpriteBatch();

        viewport = new FitViewport(240, 160);

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
        img = SpriteDownloader.loadSprite();
        Gdx.app.log("test", "load sprite");
        texRegions = createTexRegions(img);
        stage = new Stage(51, 31);
        // playerの位置は 1, 1 startで固定
        player.set(1, 1);
        viewport.getCamera().position.set(player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE, 0);
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // playerの中心位置にカメラを移動させる
        viewport.getCamera().position.set(player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE, 0);

        viewport.getCamera().update();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        stage.render(batch, texRegions);

        batch.draw(texRegions[0], player.x * Constants.SPRITE_SIZE, player.y * Constants.SPRITE_SIZE);

        // player render

        batch.end();

        if (Gdx.input.justTouched()) {
            img.dispose();
            img = SpriteDownloader.loadSprite();
            texRegions = createTexRegions(img);
        }
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
