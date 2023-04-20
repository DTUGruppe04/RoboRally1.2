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
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.control.ChoiceDialog;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved

        if (space != null && space.board == board) {
            Player currentPlayer = board.getCurrentPlayer();
            if (currentPlayer != null && space.getPlayer() == null) {
                currentPlayer.setSpace(space);
                int playerNumber = (board.getPlayerNumber(currentPlayer) + 1) % board.getPlayersNumber();
                board.setCurrentPlayer(board.getPlayer(playerNumber));
            }
        }
    }

    /**
     * Sets up the programming phase of the game by initializing the board state
     * and generating random command cards for each player's card field. Also clears
     * the program fields for each player.
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    /**
     * Generates a new CommandCard with a randomly selected Command value.
     *
     * @return a new CommandCard with a randomly selected Command value.
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * Finishes the programming phase of the game by making the program fields
     * invisible, making the program field for the current player visible, and
     * updating the board state for the activation phase.
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * Makes the program field with the specified register index visible for all players.
     *
     * @param register the index of the program field to make visible.
     */
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Makes all program fields invisible for all players.
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * Executes all program fields in normal mode (i.e., not step-by-step mode).
     * Uses continuePrograms() method to actually execute the programs.
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * Executes the program fields one step at a time, using the executeNextStep() method.
     * Uses continuePrograms() method to actually execute the programs.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Continues executing the program fields by calling the executeNextStep() method repeatedly,
     * until the board's phase is not ACTIVATION or the board is in step mode.
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Moves the game to the next player's turn, updating the current player and the step.
     * If there are still players left to play, sets the current player to the next one.
     * Otherwise, if all players have played all their cards, starts the programming phase.
     */
    public void nextPlayer() {
        Player currentPlayer = board.getCurrentPlayer();
        int step = board.getStep();
        int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
        } else {
            step++;
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.setCurrentPlayer(board.getPlayer(0));
            } else {
                startProgrammingPhase();
            }
        }
    }

    /**
     * Executes the next step of the game. Gets the current player from the game board and the current step.
     * If the game phase is in activation, and there is a current player, and the current step is within bounds,
     * the method will retrieve the command card from the current player's program field, execute the command,
     * execute the board elements, and move on to the next player unless the game phase is in player interaction.
     * If a winner is found, the game phase is set to winner.
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        int step = board.getStep();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null && step >= 0 && step < Player.NO_REGISTERS) {
            CommandCard card = currentPlayer.getProgramField(step).getCard();
            if (card != null) {
                Command command = card.command;
                executeCommand(currentPlayer, command);
            }
            System.out.println(board.getPlayersNumber() + " " + board.getPlayerNumber(currentPlayer));
            if ((board.getPlayerNumber(currentPlayer)+1) == board.getPlayersNumber()) {
                executeBoardElements();
            }
            if (board.getPhase() != Phase.PLAYER_INTERACTION) {
                nextPlayer();
            }

        } else {
            // this should not happen
            assert false;
        }
        if (checkForWinner()) {
            this.board.setPhase(Phase.WINNER);
        }
    }

    /**
     * Executes all doAction methods for spaces with players on it
     */
    private void executeBoardElements() {
        //execute space that has players
        for (Player player : board.getPlayers()) {
            player.getSpace().executeFieldAction(this);
        }

        //Shoot lasers
    }

    /**
     * Executes a command for a player on the board.
     * @param player the player executing the command (not null)
     * @param command the command to be executed (not null)
     * @throws NullPointerException if either player or command is null
     */
    public void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).
            if (!command.isInteractive()) {
                switch (command) {
                    case FORWARD:
                        this.moveForward(player);
                        break;
                    case RIGHT:
                        this.turnRight(player);
                        break;
                    case LEFT:
                        this.turnLeft(player);
                        break;
                    case FAST_FORWARD:
                        this.fastForward(player);
                        break;
                    default:
                        // DO NOTHING (for now)
                }
            } else {
                this.board.setPhase(Phase.PLAYER_INTERACTION);
            }

        }
    }

    /**
     * Checks for a winner, by checking each players checkpoints, if its the same as the boards amount of checkpoint
     * the play is set as the winner
     * @return true if a winner is found false otherwise
     */
    private boolean checkForWinner() {
        for (Player player : board.getPlayers()) {
            if (player.getCheckpoints() >= board.getAmountOfCheckpoints()) {
                board.setWinner(player);
                return true;
            }
        }
        return false;
    }

    /**
     * Moves the player forward one space in the direction of their heading.
     * If the target space is occupied by another player, that player is pushed
     * to the next space in the same direction if possible.
     *
     * @param player the player to move
     */
    public void moveForward(@NotNull Player player) {
        Space space = player.getSpace();
        Heading heading = player.getHeading();
        Space target = board.getNeighbour(space, heading);
        // Check that the target space is valid, the player is valid and on the board,
        // and the movement is allowed based on the borders of the spaces involved.
        if (target != null && player != null && player.board == board && space != null &&
                !space.getType().BorderHeadings.contains(heading) &&
                !target.getType().BorderHeadings.contains(reverseHeading(heading))) {
            Player targetPlayer = target.getPlayer();
            if (targetPlayer == null) {
                // If the target space is empty, move the player to it.
                target.setPlayer(player);
            } else {
                // If the target space is occupied, try to push the other player
                // to the next space in the same direction if possible.
                Space pushSpace = board.getNeighbour(target, heading);
                if (pushSpace != null && !target.getType().BorderHeadings.contains(heading) &&
                        !pushSpace.getType().BorderHeadings.contains(reverseHeading(heading))) {
                    pushSpace.setPlayer(targetPlayer);
                    target.setPlayer(player);
                }
            }
        }
    }

    /**
     * Returns the opposite heading to the given heading.
     *
     * @param heading the heading to reverse
     * @return the opposite heading
     */
    public Heading reverseHeading(Heading heading) {
        return switch (heading) {
            case NORTH -> Heading.SOUTH;
            case SOUTH -> Heading.NORTH;
            case EAST -> Heading.WEST;
            case WEST -> Heading.EAST;
        };
    }


    /**
     * Moves the player 2 spaces forward
     * @param player to be moved
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }


    /**
     * Changes the header direction for the player one to the right
     * @param player to turn right
     */
    public void turnRight(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
        board.setPhase(Phase.ACTIVATION);
    }

    /**
     * Changes the header direction for the player one to the left
     * @param player to turn left
     */
    public void turnLeft(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().prev());
        }
        board.setPhase(Phase.ACTIVATION);
    }


    /**
     * Moves a command card from a source field to a target field.
     * @param source the source command card field
     * @param target the target command card field
     * @return true if the move was successful, false otherwise
     * @throws NullPointerException if either source or target are null
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }


    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
