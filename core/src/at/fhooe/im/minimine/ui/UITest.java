package at.fhooe.im.minimine.ui;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class UITest extends ApplicationAdapter implements Telegraph {

	private PerspectiveCamera cam;
	private ModelBatch modelBatch;
	private Model model;
	private ModelInstance instance;
	private Environment environment;
	private CameraInputController camController;
	private float rotate;
	private Texture texture;
	private Stage stage;
	UI userInterface = new UI();

	@Override
	public void create() {
		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f,
				0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
				-0.8f, -0.2f));

		modelBatch = new ModelBatch();

		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		cam.position.set(10f, 10f, 10f);
		cam.lookAt(0, 0, 0);
		cam.near = 1f;
		cam.far = 300f;
		cam.update();

		texture = new Texture("badlogic.jpg");

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f, new Material(
				TextureAttribute.createDiffuse(texture)), Usage.Position
				| Usage.Normal | Usage.TextureCoordinates);
		instance = new ModelInstance(model);

		userInterface.setupUI();
		camController = new OwnCameraInputController(cam,
				userInterface.selectionFrame);
		Gdx.input.setInputProcessor(camController);
		MessageManager.getInstance().addListener(this, 1);

	}

	@Override
	public void render() {
		camController.update();

		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		modelBatch.begin(cam);
		modelBatch.render(instance, environment);
		modelBatch.end();

		userInterface.stage.draw();
		userInterface.uiBar.act(Gdx.graphics.getDeltaTime());

		MessageManager.getInstance().update();
	}

	@Override
	public void dispose() {
		modelBatch.dispose();
		model.dispose();
		userInterface.dispose();
	}

	@Override
	public boolean handleMessage(Telegram msg) {
		if (msg.message == 1) {
			System.out.println("Received following message: " + msg.message
					+ " with the extra info: " + msg.extraInfo);
			return true;
		}
		return false;
	}

}
