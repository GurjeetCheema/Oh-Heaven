package oh_heaven.game.cardHolders.Players.NPC;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.cardHolders.DeckHolder;
import oh_heaven.game.cardHolders.Players.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class NPC extends Player {



    private static int seed;
    protected char type = 'N';

    public NPC(int seed, int initScore, int initTrick, int bid, DeckHolder deck){
        super(initScore, initTrick, bid, deck);
        this.seed = seed;
    }

    // return random Card from ArrayList
    public static Card randomCard(ArrayList<Card> list){
        Random random = new Random(seed);
        int x = random.nextInt(list.size());
        return list.get(x);
    }

    public static int getSeed() {
        return seed;
    }
    public char getType() {
        return type;
    }
}
