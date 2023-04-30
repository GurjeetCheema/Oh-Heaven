package oh_heaven.game.cardHolders.Players.NPC;
import java.util.*;
import ch.aplu.jcardgame.Card;
import oh_heaven.game.cardHolders.*;

public class RandomNPC extends NPC {


    public RandomNPC(int seed, int initScore, int initTrick, int bid, DeckHolder deck){
        super(seed, initScore, initTrick, bid, deck);

    }

    //gets card that npc will give for round
    @Override
    public Card getSelected(Trick trick, Trump trump) {
        Random random = new Random(getSeed());
        int x = random.nextInt(deck.getHand().getNumberOfCards());
        return deck.getHand().get(x);
    }


}
