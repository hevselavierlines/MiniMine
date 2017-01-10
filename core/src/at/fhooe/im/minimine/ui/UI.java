package at.fhooe.im.minimine.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class UI {
	
	Stage stage;
	TextureAtlas textureAtlas;
	Group uiBar;
	float selectionFrameXPos = 0.0f;
	float uiBarWidth;
	float uiBarHeight;
	Image selectionFrame;
	

	
	void setupUI() {
		
		
		uiBarWidth = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/5;
		uiBarHeight = (Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/5)/9;
		textureAtlas = new TextureAtlas("MiniMineItems.txt");
		stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		
		selectionFrame =  new Image(textureAtlas.findRegion("SelectionFrame"));
		selectionFrame.setSize(uiBarWidth/9, uiBarHeight);
		
		TextureRegion uiBarImg = textureAtlas.findRegion("UIBar");
		Image bar = new Image(uiBarImg);
		bar.setPosition(0, 0);
		bar.setSize(uiBarWidth, uiBarHeight);
		uiBar = new Group();
		uiBar.addActor(bar);
		uiBar.setPosition(Gdx.graphics.getWidth()/2 - uiBarWidth/2, -100);
		
		Image helmet = new Image(textureAtlas.findRegion("helmet"));
		helmet.setSize(uiBarHeight - 20, uiBarHeight - 20);
		helmet.setPosition(uiBarHeight/5, uiBarHeight/5);
		uiBar.addActor(helmet);
		
		Image sword = new Image(textureAtlas.findRegion("Sword"));
		sword.setSize(uiBarHeight - 20, uiBarHeight - 20);
		sword.setPosition((uiBarHeight / 5) + selectionFrame.getWidth(), uiBarHeight  / 5);
		uiBar.addActor(sword);
		
		
		
		uiBar.addAction(Actions.moveTo(Gdx.graphics.getWidth()/2 - uiBarWidth/2, 0, 0.5f));
		uiBar.addActor(selectionFrame);

		selectionFrameXPos = uiBar.getOriginX();

		stage.addActor(uiBar);
	}
	
	void dispose() {
		stage.dispose();
		textureAtlas.dispose();
	}

}
