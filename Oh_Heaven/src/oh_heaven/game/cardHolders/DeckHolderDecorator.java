package oh_heaven.game.cardHolders;
import ch.aplu.jcardgame.Hand;


public abstract class DeckHolderDecorator implements DeckHolder {

    protected DeckHolder deck;

    public DeckHolderDecorator(DeckHolder deck){

        this.deck = deck;

    }
    public void add(){

    }

    public void remove(){

    }
    public DeckHolder getDeck() {
        return deck;
    }

    public Hand getHand(){
        return deck.getHand();
    }
    public void setHand(Hand hand){
        deck.setHand(hand);
    }
}
