package io.github.Qwerybomb.GDX;
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.model.NodePart;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class introScreen extends ScreenAdapter {
    private Core game;
    private SpriteBatch batch;
    private Texture image;
    PerspectiveCamera camera;
    ModelBatch Mbatch = null;
    ModelInstance orb = null;
    ModelInstance base = null;
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
        camera = new PerspectiveCamera(60f,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight());
        camera.position.set(15f, 5f, 10f);
        camera.lookAt(-40f, -20f, 0f);

        // camera render distances
        camera.near = 0.1f;
        camera.far = 300f;
        camera.update();

        // create orb and base
        ObjLoader loader = new ObjLoader();
        Model model = loader.loadModel(Gdx.files.internal("orb2.obj"), true);
        Model model2 = loader.loadModel(Gdx.files.internal("base.obj"));
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
        Mbatch.render(base, environment);
        Mbatch.end();

        // perform transformations
        orbMovement += 0.03;
        orb.transform.translate(0f, (float) (Math.cos(orbMovement) * 0.005), 0f);
        orb.transform.rotate(0, 1f, 0, 1f);
    }


    // prevents memory leaks
    @Override
    public void dispose() {
      image.dispose();
      batch.dispose();
    }
}
