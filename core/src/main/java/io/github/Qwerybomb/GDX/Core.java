package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class Core extends Game {


    @Override
    public void create() {
        setScreen(new introScreen(this));
    }

    // disposal to prevent memory leaks
    @Override
    public void dispose() {

    }

    // render game loop
    @Override
    public void render() {
        // required for proper screen rendering
        super.render();
    }

}
