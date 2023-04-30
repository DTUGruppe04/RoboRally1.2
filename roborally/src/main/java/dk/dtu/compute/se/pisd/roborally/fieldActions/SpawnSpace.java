package dk.dtu.compute.se.pisd.roborally.fieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;

public class SpawnSpace extends FieldAction{
    int ownerPlayer;
    public SpawnSpace(int ownerPlayer) { this.ownerPlayer = ownerPlayer; }
    @Override
    public void doAction(GameController gameController, Space space) {
        for (int i = 0; i<gameController.board.getPlayersNumber(); i++) {
            if (i == ownerPlayer) {
                gameController.board.getPlayer(i).setSpace(space);
                //space.setPlayer(gameController.board.getPlayer(i));
            }
        }
    }
}
