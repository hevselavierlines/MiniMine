package at.fhooe.im.minimine.ui;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class OwnCameraInputController extends CameraInputController {

	float selectionFrameXPos = 0.0f;
	Image selectionFrame;

	public OwnCameraInputController(Camera _camera, Image _selectionFrame) {
		super(_camera);
		selectionFrame = _selectionFrame;
	}

	@Override
	public boolean keyDown(int keycode) {
		super.keyDown(keycode);

		if (keycode == Keys.RIGHT
				&& selectionFrameXPos < selectionFrame.getWidth() * 9
						- selectionFrame.getWidth()) {
			selectionFrameXPos += selectionFrame.getWidth();
			selectionFrame.clearActions();
			selectionFrame.addAction(Actions
					.moveTo(selectionFrameXPos, 0, 0.5f));
		}

		if (keycode == Keys.LEFT
				&& selectionFrameXPos > selectionFrame.getOriginX() + 1) {
			selectionFrameXPos -= selectionFrame.getWidth();
			selectionFrame.clearActions();
			selectionFrame.addAction(Actions
					.moveTo(selectionFrameXPos, 0, 0.5f));
		}

		if (keycode == Keys.ENTER) {
			MessageManager.getInstance().dispatchMessage(
					1,
					"Player selected the "
							+ ((int) selectionFrameXPos
									/ (int) (selectionFrame.getWidth()) + 1)
							+ ". Item");
		}

		return false;
	}

}
