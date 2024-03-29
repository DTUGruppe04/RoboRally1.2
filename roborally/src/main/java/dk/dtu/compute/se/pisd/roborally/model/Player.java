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
import jdk.jfr.Experimental;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {

    @Expose
    final public static int NO_REGISTERS = 5;
    @Expose
    final public static int NO_CARDS = 9;

    final public Board board;
    @Expose
    private String name;
    @Expose
    private boolean ready;
    @Expose
    private String color;
    @Expose
    private boolean isInPit;
    @Expose
    private int checkpoints;
    @Expose
    private Space space;
    @Expose
    private Heading heading = SOUTH;

    @Expose
    private int spamCards = 0;

    @Expose
    private CommandCardField[] program;

    @Expose
    private CommandCardField[] cards;


    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;
        this.checkpoints = 0;
        this.isInPit = true;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
         }
    }

    public void setReady(boolean bool) {
        ready = bool;
    }

    public boolean getReady() {
        return ready;
    }

    public void raiseCheckpoints() {
        checkpoints++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    public Space getSpace() {
        return space;
    }

    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }
    public int getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(int checkpoints) {
        this.checkpoints = checkpoints;
    }
    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public boolean getIsInPit() { return isInPit; }
    public void setIsInPit(boolean inPit) { isInPit = inPit; }
    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    public CommandCardField[] getProgram() {
        return program;
    }

    public CommandCardField[] getCards() {
        return cards;
    }

    public void decSpamCards() {
        if (spamCards>=1) {
            spamCards--;
        }
    }

    public void addSpamCards(int number) {
        for (int i = 0; i < number; i++)
            if (spamCards == 9) {
                break;
            }
            else {
                spamCards++;
            }
    }

    //Public Getter Method for SpamCards
    public int getSpamCards() {
        return spamCards;
    }
    public void setSpamCards(int spamCards) {
        this.spamCards = spamCards;
    }
}
