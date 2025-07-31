package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.utils.ScreenUtils;

public class mainGame implements Screen {

    // creates main game elements
    Core game;
    ModelBatch batch = null;
    Model floor = null;
    ModelInstance mod = null;
    PerspectiveCamera camera = new PerspectiveCamera(90f,
        Gdx.graphics.getWidth(),
        Gdx.graphics.getHeight());

    // adds in the main core class
    mainGame(Core game) {
        this.game = game;
    }

    @Override
    public void show() {
       batch = new ModelBatch();
        System.out.println("shown");
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(1f, 0.15f, 0.2f, 1f);
       System.out.println("running");
    }

    @Override
    public void resize(int i, int i1) {

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
      floor.dispose();
    }
}
