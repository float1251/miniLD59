package jp.float1251.miniLD59.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class GoalScreen implements Screen {

    private final float goalTime;
    private final Game game;
    private Stage stage;

    public GoalScreen(Game game, float goalTime) {
        this.game = game;
        this.goalTime = goalTime;
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(210, 160));
        stage.setDebugAll(true);

        Table table = new Table();
        table.setPosition(100, 100);

        Label label = new Label("GOAL!!!", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        label.setFontScale(1.5f);
        label.setAlignment(Align.center);
        table.add(label).fillX().colspan(2);
        table.row();

        NinePatchDrawable texture = new NinePatchDrawable(new NinePatch(new Texture("button.png"), 10, 10, 10, 10));
        TextButton.TextButtonStyle tbs = new TextButton.TextButtonStyle();
        tbs.font = new BitmapFont();
        tbs.up = texture;
        TextButton textButton = new TextButton("Next", tbs);
        textButton.setTransform(true);
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("test", event.toString());
                actor.addAction(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.1f), Actions.scaleTo(1f, 1f, 0.1f)));
            }
        });

        table.add(textButton).width(80f);

        textButton = new TextButton("Title", tbs);
        textButton.setTransform(true);
        textButton.setOrigin(textButton.getWidth() / 2, textButton.getHeight() / 2);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("test", event.toString());
                actor.addAction(Actions.sequence(Actions.scaleTo(1.1f, 1.1f, 0.1f), Actions.scaleTo(1f, 1f, 0.1f), new Action() {
                    @Override
                    public boolean act(float delta) {
                        game.setScreen(new TitleScreen(game));
                        return false;
                    }
                }));
            }
        });

        table.add(textButton).width(80f);
        table.row();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getViewport().getCamera().update();
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
