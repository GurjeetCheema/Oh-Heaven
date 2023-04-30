package oh_heaven.game.cardHolders;
import ch.aplu.jcardgame.*;


public class BaseDeckHolder implements DeckHolder {

    Hand hand;

    public BaseDeckHolder(Hand hand){
        this.hand = hand;
    }

    public void add(){

    }

    public void remove(){

    }

    @Override
    public Hand getHand() {
        return hand;
    }

    @Override
    public void setHand(Hand hand) {
        this.hand = hand;
    }
}
