package oh_heaven.game.cardHolders.Players;

import java.util.*;
import ch.aplu.jcardgame.*;
import oh_heaven.game.cardHolders.*;

public abstract class Player extends DeckHolderDecorator {

    protected int score;
    protected int trick;
    protected int bid;
    protected char type = 'P';

    private final int madeBidBonus = 10;

    public Player(int initScore, int initTrick, int bid, DeckHolder deck){
        super(deck);
        this.score = initScore;
        this.trick = initTrick;
        this.bid = bid;
    }
    //decides bid
    public int decideBid(int nbStartCards, Random random) {
        int bid = nbStartCards / 4 + random.nextInt(2);;
        this.setBid(bid);
        return bid;

    }
    // updates scores
    public void updateScore(){
        score += trick;
        if(trick == bid){
            score += madeBidBonus;
        }
    }
    //gets selected
    public Card getSelected(Trick trick, Trump trump){
        return deck.getHand().get(0);
    }
    //getters and setters
    public int getScore() {
        return score;
    }

    public int getTrick() {
        return trick;
    }

    public int getBid() {
        return bid;
    }


    public void setTrick(int trick) {
        this.trick = trick;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public void addBid(int bid){
        this.bid += bid;
    }


    public void addTrick(int trick){
        this.trick += trick;
    }

    public char getType() {
        return type;
    }
}
