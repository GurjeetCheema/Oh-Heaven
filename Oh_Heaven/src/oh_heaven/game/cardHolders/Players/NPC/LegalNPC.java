package oh_heaven.game.cardHolders.Players.NPC;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.cardHolders.*;

import java.util.ArrayList;

public class LegalNPC extends NPC {

    public LegalNPC(int seed, int initScore, int initTrick, int bid, DeckHolder deck){
        super(seed ,initScore, initTrick, bid, deck);
    }

    //gets card from player NPC for round
    @Override
    public Card getSelected(Trick trick, Trump trump) {
        //Put legal here
        if(trick.getHand().getNumberOfCards() == 0){
            return this.randomCard(this.getHand().getCardList());
        }
        ArrayList<Card> sameSuit = this.getHand().getCardsWithSuit(trick.getHand().get(0).getSuit());
        if(sameSuit.size() > 0){
            return this.randomCard(sameSuit);
        }
        return this.randomCard(this.getHand().getCardList());
    }

}
