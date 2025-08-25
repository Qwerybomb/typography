package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class introScreen extends ScreenAdapter {
    private Core game;
    private SpriteBatch batch;
    PerspectiveCamera camera;
    ModelBatch Mbatch = null;
    ModelInstance orb = null;
    ModelInstance base = null;
    Environment environment;
    float orbMovement = 0;
    private TextureAtlas textureAtlas;
    private Sprite playButton;
    Skin buttonSkin;
    Button button;
    Stage stage;

    introScreen(Core game) {
        this.game = game;
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        // draw 3d elements
        Mbatch.begin(camera);
        Mbatch.render(orb, environment);
        Mbatch.render(base, environment);
        Mbatch.end();

        // draw Ui elements
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (button.isPressed()) {
            game.setScreen(new mainGame(this.game));
        }

        // perform transformations
        orbMovement += 0.03;
        orb.transform.translate(0f, (float) (Math.cos(orbMovement) * 0.005), 0f);
        orb.transform.rotate(0, 1f, 0, 1f);
    }

    @Override
    public void show() {

        // set up batches
        batch = new SpriteBatch();
        Mbatch = new ModelBatch();

        // set up basic camera
        camera = new PerspectiveCamera(60f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        camera.position.set(15f, 5f, 10f);
        camera.lookAt(-40f, -20f, 0f);

        // camera render distances
        camera.near = 0.1f;
        camera.far = 300f;
        camera.update();

        // create button
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        textureAtlas = new TextureAtlas("textures/playButtons.atlas");
        buttonSkin = new Skin();
        buttonSkin.addRegions(textureAtlas);
        Button.ButtonStyle buttonConfig = new Button.ButtonStyle();
        Drawable buttonUp = new TextureRegionDrawable(textureAtlas.findRegion("play"));
        Drawable buttonDown = new TextureRegionDrawable(textureAtlas.findRegion("play2"));
        buttonConfig.up = buttonUp;
        buttonConfig.down = buttonDown;
        buttonConfig.over = buttonDown;
        button = new Button(buttonConfig);
        button.setTransform(true);
        button.setScale(0.15f, 0.15f);
        button.setPosition(25, 100);
        button.setTouchable(Touchable.enabled);
        stage.addActor(button);

        // create orb and base
        ObjLoader loader = new ObjLoader();
        Model model = loader.loadModel(Gdx.files.internal("orb/orb2.obj"), true);
        Model model2 = loader.loadModel(Gdx.files.internal("orbBase/base.obj"), true);
        orb = new ModelInstance(model);
        base = new ModelInstance(model2);
        base.transform.setToTranslation(0f, -6f, 0f);
        base.transform.scale(5f, 5f, 5f);
        orb.transform.scale(5f, 5f, 5f);
        base.transform.rotate(0, 1f, 0, 5f);

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -0.8f, -0.8f));
    }



    // prevents memory leaks
    @Override
    public void dispose() {

      batch.dispose();
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
