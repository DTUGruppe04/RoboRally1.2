/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.model;

import com.google.gson.annotations.Expose;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    @Expose
    public final int width;

    @Expose
    public final int height;

    @Expose
    public final String boardName;

    @Expose
    private Integer gameId;

    private final Space[][] spaces;



    private ArrayList<Space> spawnSpaces;

    @Expose
    private final List<Player> players = new ArrayList<>();

    @Expose
    private Player current;

    @Expose
    private Player winner = null;

    @Expose
    private Phase phase = INITIALISATION;

    @Expose
    private int amountOfCheckpoints = 0;

    @Expose
    private int step = 0;

    @Expose
    private boolean stepMode;

    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                createSpace(x,y,SpaceType.EMPTY_SPACE);
            }
        }
        this.stepMode = false;
    }

    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }
    public Board(int[][] boardArray, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = boardArray[0].length;
        this.height = boardArray.length;
        this.spaces = new Space[width][height];
        this.spawnSpaces = new ArrayList<>();
        for (int y = 0; y < width; y++) {
            for(int x = 0; x < height; x++) {
                createSpace(x,y,SpaceType.get(boardArray[x][y]));
                if (SpaceType.get(boardArray[x][y]).equals(SpaceType.CHECKPOINT1) || SpaceType.get(boardArray[x][y]).equals(SpaceType.CHECKPOINT2) ||
                        SpaceType.get(boardArray[x][y]).equals(SpaceType.CHECKPOINT3) || SpaceType.get(boardArray[x][y]).equals(SpaceType.CHECKPOINT4) ||
                        SpaceType.get(boardArray[x][y]).equals(SpaceType.CHECKPOINT5)) {
                    amountOfCheckpoints++;
                }
                if (SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER1) || SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER2) ||
                        SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER3) || SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER4) ||
                        SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER5) || SpaceType.get(boardArray[x][y]).equals(SpaceType.SPAWN_SPACE_PLAYER6)) {
                    spawnSpaces.add(this.getSpace(x,y));
                }
            }
        }
    }
    private void createSpace(int x, int y, SpaceType spaceType) {
        Space space = new Space(this, x, y, spaceType);
        this.spaces[x][y] = space;
    }
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public int getAmountOfCheckpoints() {
        return amountOfCheckpoints;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }

        return getSpace(x, y);
    }

    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // XXX: V2 changed the status so that it shows the phase, the player and the step
        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep() +
                ", Next Checkpoint: " + (getCurrentPlayer().getCheckpoints()+1);
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public Space[][] getSpaces() {
        return spaces;
    }
    public ArrayList<Space> getSpawnSpaces() { return spawnSpaces; }


}
