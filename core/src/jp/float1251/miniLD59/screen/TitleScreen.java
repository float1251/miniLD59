package jp.float1251.miniLD59.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

import jp.float1251.miniLD59.Constants;

public class TitleScreen implements Screen {

    private final Game game;
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture title;
    private BitmapFont font;

    public TitleScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        viewport = new FitViewport(Constants.WIDTH, Constants.HEIGHT);
        title = new Texture("title.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        viewport.getCamera().update();

        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(title, -title.getWidth() / 2, 10);
        font.draw(batch, "Tap To Start", -40, -10);
        batch.end();

        if (Gdx.input.justTouched() && game.getScreen().equals(this)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        batch.dispose();
        title.dispose();
        font.dispose();
    }
}
