package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Game;

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
