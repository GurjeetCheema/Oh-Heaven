package oh_heaven.game;
import ch.aplu.jcardgame.*;
import oh_heaven.game.cardHolders.*;
import oh_heaven.game.cardHolders.Players.NPC.LegalNPC;
import oh_heaven.game.cardHolders.Players.NPC.RandomNPC;
import oh_heaven.game.cardHolders.Players.NPC.SmartNPC;
import oh_heaven.game.cardHolders.Players.Human;
import oh_heaven.game.cardHolders.Players.Player;

import java.util.ArrayList;
import java.util.List;

public class DeckFactory {

    //finds if string has deck type present, if it does it creates instance
    // else it sends out message in terminal that it wasn't found and uses legal
    public Player getDecktype(String name, int seed){


        if(name.equals("human")){
            return new Human(0,0,0,new BaseDeckHolder(new Hand(
                    new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover")
            )));
        }
        else if(name.equals("random")){
            return new RandomNPC(seed, 0,0,0,new BaseDeckHolder(new Hand(
                    new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover")
            )));

        }
        else if(name.equals("smart")){
            return new SmartNPC(seed,0,0,0,new BaseDeckHolder(new Hand(
                    new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover")
            )));

        }
        else if(name.equals("legal")){
            return new LegalNPC(seed,0,0,0,new BaseDeckHolder(new Hand(
                    new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover")
            )));

        }

        System.out.println("No Matching Deck Type found");
        return new LegalNPC(seed,0,0,0,new BaseDeckHolder(new Hand(
                new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover")
        )));
    }

    //loops through list of players types and create player instances with relevant subclasses
    public List<Player> getPlayerList(List<String> TypesPlayers, int seed){
        List<Player> Players = new ArrayList<Player>();

        for (String type : TypesPlayers){
            Players.add(this.getDecktype(type, seed));
        }

        return Players;
    }
    //gets trick deck
    public Trick getTrickDeck(){
        return new Trick(new BaseDeckHolder(new Hand(
                new Deck(Oh_Heaven.Suit.values(), Oh_Heaven.Rank.values(), "cover"))));
    }
    //gets trump deck
    public Trump getTrumpDeck(Oh_Heaven.Suit trumpSuit){
        return new Trump(new BaseDeckHolder(new Hand(
                new Deck(trumpSuit.values(), Oh_Heaven.Rank.values(), "cover"))));
    }
}
