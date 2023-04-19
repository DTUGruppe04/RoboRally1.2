package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Space;

public abstract class FieldAction {
    public abstract void doAction(GameController gameController, Space space);
}
