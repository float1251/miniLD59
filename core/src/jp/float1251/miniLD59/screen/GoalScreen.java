package jp.float1251.miniLD59.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;


/**
 * Created by takahiroiwatani on 2015/05/18.
 */
public class GoalScreen implements Screen {

    private final float goalTime;
    private Stage stage;

    public GoalScreen(Game game, float goalTime) {
        this.goalTime = goalTime;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(210, 160));

        NinePatchDrawable texture = new NinePatchDrawable(new NinePatch(new Texture("button.9.png"), 10, 10, 10, 10));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = new BitmapFont();
        tbs.up = texture;
        TextButton textButton = new TextButton("Next", tbs);
        textButton.setWidth(80);
        stage.addActor(textButton);

        textButton = new TextButton("Title", tbs);
        textButton.setWidth(80);
        textButton.setPosition(100, 0);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("test", event.toString());
                actor.addAction(Actions.sizeTo(actor.getWidth() * 1.1f, actor.getHeight() * 1.1f, 0.5f));


            }
        });
        stage.addActor(textButton);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
        stage.dispose();
    }
}
