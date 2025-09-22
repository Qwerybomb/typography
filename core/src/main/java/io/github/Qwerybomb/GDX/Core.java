package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import io.github.Qwerybomb.GDX.Screens.introScreen;

public class Core extends Game {


    @Override
    public void create() {
        setScreen(new introScreen(this));
    }

    // disposal to prevent memory leaks
    @Override
    public void dispose() {
        super.dispose();
        Gdx.app.exit();
    }

    // render game loop
    @Override
    public void render() {
        // required for proper screen rendering
        super.render();
    }

}
