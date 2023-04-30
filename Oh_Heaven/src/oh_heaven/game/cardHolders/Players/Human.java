package oh_heaven.game.cardHolders.Players;

import oh_heaven.game.cardHolders.DeckHolder;

public class Human extends Player {
    protected char type = 'H';
    public Human(int initScore, int initTrick, int bid, DeckHolder deck){
        super(initScore, initTrick, bid, deck);
    }

    public char getType() {
        return type;
    }

}
