package oh_heaven.game.cardHolders.Players.NPC;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.cardHolders.*;

import java.util.ArrayList;

public class SmartNPC extends NPC {


    public SmartNPC(int seed, int initScore, int initTrick, int bid, DeckHolder deck){
        super(seed, initScore, initTrick, bid, deck);
    }

    //algorithm to find card to give for round
    @Override
    public Card getSelected(Trick trick, Trump trump) {

        ArrayList<Card> CardList = getHand().getCardList();

        //if trick deck is not empty then finds card to palce
        if(trick.getHand().getNumberOfCards() > 0){
            //if there are cards present that are teh same suit as the lead card, only use that suit
            if(this.getHand().getNumberOfCardsWithSuit(trick.getHand().get(0).getSuit()) > 0){
                CardList = this.getHand().getCardsWithSuit(trick.getHand().get(0).getSuit());
            }
            //else find trump suit cards and use that,
            // however if trick = bid then use worst card that isn't in trump suit
           else{
                if(trump.getHand().getNumberOfCards() > 0) {
                    if (this.getHand().getNumberOfCardsWithSuit(trump.getHand().get(0).getSuit()) > 0) {

                        CardList = this.getHand().getCardsWithSuit(trump.getHand().get(0).getSuit());

                        if (this.trick == this.bid) {
                            ArrayList<Card> removeCards = CardList;
                            CardList = getHand().getCardList();
                            CardList.remove(removeCards);
                        }

                    }
                }
                else{
                    CardList = getHand().getCardList();
                }
            }
        }
        int index;

        //if trick score = bid then tries to lose to get the bonus points
        if(this.trick == this.bid){
            index = getIndexWorstCard(CardList);
            return CardList.get(index);
        }
        //gets best card index
        index = getIndexBestCard(CardList);
        //if there is a card with larger value then best card in hand the places worse card, as not to waste that card
        if(getBestCardValue(trick.getHand().getCardList()) < CardList.get(index).getRankId()){
            index = getIndexWorstCard(CardList);
        }

        return CardList.get(index);

    }

    // gets index of best card within array list
    //since best rank is 1, finds lowest number
    public int getIndexBestCard( ArrayList<Card> CardList){
        int min = 12;
        int index = 0;

        for(int i = 0; i < CardList.size();i++){

            int value = CardList.get(i).getRankId();
            //best rank value is lower value
            if(value < min){
                index = i;
                min = value;
            }
        }
        return index;

    }

    // gets index of worst card within array list
    //since best rank is 1, finds highest number
    public int getIndexWorstCard(ArrayList<Card> CardList){
        int max = 0;
        int index = 0;
        int value;

        for(int i = 0; i < CardList.size();i++){

            value = CardList.get(i).getRankId();
            //best rank value is lower value
            if(value > max){
                index = i;
                max = value;
            }
        }

        return index;
    }

    // gets best card value within array list
    //since best rank is 1, finds lowest number
    public int getBestCardValue( ArrayList<Card> CardList){
        int min = 12;

        for(int i = 0; i < CardList.size();i++){

            int value = CardList.get(i).getRankId();
            //best rank value is lower value
            if(value < min){
                min = value;
            }
        }
        System.out.println(min);
        return min;

    }

}
