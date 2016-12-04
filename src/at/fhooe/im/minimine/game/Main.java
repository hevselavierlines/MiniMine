package at.fhooe.im.minimine.game;

import at.fhooe.im.minimine.engine.GameEngine;
import at.fhooe.im.minimine.engine.IGameLogic;
 
public class Main {
 
	// Hello World
	
    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = new DummyGame();
            GameEngine gameEng = new GameEngine("GAME", 800, 600, vSync, gameLogic);
            gameEng.start();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }
}