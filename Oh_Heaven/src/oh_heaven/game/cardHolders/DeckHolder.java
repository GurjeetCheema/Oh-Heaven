package oh_heaven.game.cardHolders;
import ch.aplu.jcardgame.*;

public interface DeckHolder {


    public void add();
    public void remove();
    public void setHand(Hand hand);
    public Hand getHand();

}
