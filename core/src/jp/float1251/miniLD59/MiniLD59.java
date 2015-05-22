package jp.float1251.miniLD59;

import com.badlogic.gdx.Game;

import jp.float1251.miniLD59.screen.TitleScreen;

public class MiniLD59 extends Game {

    @Override
    public void create() {
        setScreen(new TitleScreen(this));
    }
}
