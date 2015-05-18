package jp.float1251.miniLD59.sprite;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SpriteDownloader {

    public static Texture loadSprite(){
		Texture img = null;
        try {
			InputStream stream = new URL("http://swapshop.pixelsyntax.com/api/randomImage").openStream();
			Gdx2DPixmap pixmap = Gdx2DPixmap.newPixmap(stream, Gdx2DPixmap.GDX2D_FORMAT_RGB888);
            img = new Texture(new Pixmap(pixmap));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
    }
}
