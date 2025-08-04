package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class introScreen extends ScreenAdapter {
    private Core game;
    private SpriteBatch batch;
    private Texture image;
    PerspectiveCamera camera;
    ModelBatch Mbatch = null;
    ModelInstance orb = null;
    Environment environment;
    float orbMovement = 0;

    introScreen(Core game) {
        this.game = game;
    }

    @Override
    public void show() {

        // set up batches
        batch = new SpriteBatch();
        Mbatch = new ModelBatch();

        // set up basic camera
        camera = new PerspectiveCamera(90f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        camera.position.set(10f, 10f, 10f);
        camera.lookAt(0f, 0f, 0f);

        // camera render distances
        camera.near = 0.1f;
        camera.far = 300f;
        camera.update();

        // create orb
        ObjLoader loader = new ObjLoader();
        Model model = loader.loadModel(Gdx.files.internal("orb2.obj"));
        orb = new ModelInstance(model);
        orb.transform.scale(10f, 10f, 10f);

        // set up the enviornment and lighting
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 0.2f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, 1f, -0.8f, -0.8f));
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // draw Ui elements
        batch.begin();
        batch.end();

        // draw 3d elements
        Mbatch.begin(camera);
        Mbatch.render(orb, environment);
        Mbatch.end();

        // perform transformations
        orbMovement += 0.05;
        orb.transform.setToTranslation(orb.transform.getTranslation(new Vector3()).x,
            (float) (orb.transform.getTranslation(new Vector3()).y + (Math.cos(orbMovement) * 0.2)),
            orb.transform.getTranslation(new Vector3()).z);
        orb.transform.scale(5f, 5f, 5f);
    }


    // prevents memory leaks
    @Override
    public void dispose() {
      image.dispose();
      batch.dispose();
    }
}
