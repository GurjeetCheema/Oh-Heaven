package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.cardHolders.BaseDeckHolder;
import oh_heaven.game.cardHolders.Players.Human;
import oh_heaven.game.cardHolders.Players.Player;
import oh_heaven.game.cardHolders.Trick;
import oh_heaven.game.cardHolders.Trump;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {

  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }

  final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

  static public boolean enforceRules=false;
  static public int seed = 30006;
  static final Random random = new Random(seed);

  List<Player> Players;

  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

  // return random Card from Hand
  public static Card randomCard(Hand hand){
	  int x = random.nextInt(hand.getNumberOfCards());
	  return hand.get(x);
  }


  private void dealingOut(int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);
		      Players.get(j).getDeck().getHand().insert(dealt, false);
			  // dealt.transfer(hands[j], true);
		  }
	  }
  }



  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }

  private final String version = "1.0";
  public final int nbPlayers = 4;
  public static int nbStartCards = 13;
  public static int nbRounds = 3;
  //public final int madeBidBonus = 10;
  private final int handWidth = 400;
  private final int trickWidth = 40;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final Location[] handLocations = {
			  new Location(350, 625),
			  new Location(75, 350),
			  new Location(350, 75),
			  new Location(625, 350)
	  };
  private final Location[] scoreLocations = {
			  new Location(575, 675),
			  new Location(25, 575),
			  new Location(575, 25),
			  // new Location(650, 575)ja
			  new Location(575, 575)
	  };
  private Actor[] scoreActors = {null, null, null, null };
  private final Location trickLocation = new Location(350, 350);
  private final Location textLocation = new Location(350, 450);
  private final int thinkingTime = 2000;
  //private Hand[] hands;
  private Location hideLocation = new Location(-500, - 500);
  private Location trumpsActorLocation = new Location(50, 50);


  public void setStatus(String string) { setStatusText(string); }


Font bigFont = new Font("Serif", Font.BOLD, 36);

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 // scores[i] = 0;
		 String text = "[" + String.valueOf(Players.get(i).getScore()) + "]" + String.valueOf(Players.get(i).getTrick())
				 + "/" + String.valueOf(Players.get(i).getBid());
		 scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateDisplayScore(int player) {
	removeActor(scoreActors[player]);
	String text = "[" + String.valueOf(Players.get(player).getScore()) + "]" + String.valueOf(Players.get(player).getTrick())
			+ "/" + String.valueOf(Players.get(player).getBid());
	scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}


private void updateScores() {
	for (Player player:Players) {
		player.updateScore();
	}
}


private void initTricks(){
	for (Player player:Players) {
		player.setTrick(0);
	}
}


private void initBids(int nextPlayer) {
	int total = 0;
	int bid;
	for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
		int iP = i % nbPlayers;
		Players.get(iP).decideBid(nbStartCards, random);
		total += Players.get(iP).getBid();
	}
	if (total == nbStartCards) {  // Force last bid so not every bid possible
		int iP = (nextPlayer + nbPlayers) % nbPlayers;
		if (Players.get(iP).getBid() == 0) {
			Players.get(iP).setBid(1);
		} else {
			bid = random.nextBoolean() ? -1 : 1;
			Players.get(iP).addBid(bid);
		}
	}
	// for (int i = 0; i < nbPlayers; i++) {
	// 	 bids[i] = nbStartCards / 4 + 1;
	//  }
}

private Card selected;

private void initRound() {

		for (Player player: Players) {
			player.setHand(new Hand(deck));
		}

		dealingOut(nbPlayers, nbStartCards);
		 for (int i = 0; i < nbPlayers; i++) {
			   Players.get(i).getDeck().getHand().sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card
			    {
			      public void leftDoubleClicked(Card card) { selected = card; Players.get(0).getDeck().getHand().setTouchEnabled(false); }
			    };
		Players.get(0).getDeck().getHand().addCardListener(cardListener);
		 // graphics
	    RowLayout[] layouts = new RowLayout[nbPlayers];

	    for (int i = 0; i < nbPlayers; i++) {

	    	layouts[i] = new RowLayout(handLocations[i], handWidth);
	      	layouts[i].setRotationAngle(90 * i);
	      	// layouts[i].setStepDelay(10);
			Players.get(i).getDeck().getHand().setView(this, layouts[i]);
			Players.get(i).getDeck().getHand().setTargetArea(new TargetArea(trickLocation));
			Players.get(i).getDeck().getHand().draw();
	    }
//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);			// You do not need to use or change this code.
	    // End graphics
 }

 private void playRound( DeckFactory deckFactory) {
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit
	Trick trick = deckFactory.getTrickDeck();
	Trump trump = deckFactory.getTrumpDeck(trumps);
	//Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	initBids(nextPlayer);
    // initScore();
    for (int i = 0; i < nbPlayers; i++) updateDisplayScore(i);
	for (int i = 0; i < nbStartCards; i++) {
		//trick = new Hand(deck);
		trick.setHand(new Hand(deck));
    	selected = null;
    	// if (false) {
        if (0 == nextPlayer && Players.get(0).getType() == 'H') {  // Select lead depending on player type
			Players.get(0).getDeck().getHand().setTouchEnabled(true);
    		setStatus("Player 0 double-click on card to lead.");
    		while (null == selected) delay(100);
        } else {
    		setStatusText("Player " + nextPlayer + " thinking...");
            delay(thinkingTime);
            selected = Players.get(nextPlayer).getSelected(trick, trump);
            //selected = randomCard(Players.get(nextPlayer).getDeck().getHand());
        }
        // Lead with selected card
	        trick.getHand().setView(this, new RowLayout(trickLocation,
					(trick.getHand().getNumberOfCards()+2)*trickWidth));
			trick.getHand().draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick.getHand(), true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selected = null;
			// if (false) {
	        if (0 == nextPlayer && Players.get(0).getType() == 'H') {
				Players.get(0).getDeck().getHand().setTouchEnabled(true);
	    		setStatus("Player 0 double-click on card to follow.");
	    		while (null == selected) delay(100);
	        } else {
		        setStatusText("Player " + nextPlayer + " thinking...");
		        delay(thinkingTime);
				selected = Players.get(nextPlayer).getSelected(trick, trump);
		        //selected = randomCard(Players.get(nextPlayer).getDeck().getHand());
	        }
	        // Follow with selected card
		        trick.getHand().setView(this,
						new RowLayout(trickLocation, (trick.getHand().getNumberOfCards()+2)*trickWidth));
				trick.getHand().draw();
				selected.setVerso(false);  // In case it is upside down
				// Check: Following card must follow suit if possible
					if (selected.getSuit() != lead && Players.get(nextPlayer).getDeck().getHand().getNumberOfCardsWithSuit(lead) > 0) {
						 // Rule violation
						 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
						 System.out.println(violation);
						 if (enforceRules)
							 try {
								 throw(new BrokeRuleException(violation));
								} catch (BrokeRuleException e) {
									e.printStackTrace();
									System.out.println("A cheating player spoiled the game!");
									System.exit(0);
								}
					 }
				// End Check
				 selected.transfer(trick.getHand(), true); // transfer to trick (includes graphic effect)
				 System.out.println("winning: " + winningCard);
				 System.out.println(" played: " + selected);
				 // System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				 // System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));
				 if ( // beat current winner with higher card
					 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
					  // trumped when non-trump was winning
					 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					 System.out.println("NEW WINNER");
					 winner = nextPlayer;
					 winningCard = selected;
				 }
			// End Follow
		}
		delay(600);
		trick.getHand().setView(this, new RowLayout(hideLocation, 0));
		trick.getHand().draw();
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		Players.get(nextPlayer).addTrick(1);
		updateDisplayScore(nextPlayer);
	}
	removeActor(trumpsActor);
}

  public Oh_Heaven(List<String> TypesPlayers)
  {
	super(700, 700, 30);
    setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");

    DeckFactory deckFactory = new DeckFactory();

    Players = deckFactory.getPlayerList(TypesPlayers, seed);

    initScore();

    for (int i=0; i <nbRounds; i++) {

      initTricks();
      initRound();
      playRound(deckFactory);
      updateScores();

    };
    for (int i=0; i <nbPlayers; i++) updateDisplayScore(i);
    int maxScore = 0;
    for (int i = 0; i <nbPlayers; i++) if (Players.get(i).getScore() > maxScore) maxScore = Players.get(i).getScore();
    Set <Integer> winners = new HashSet<Integer>();
    for (int i = 0; i <nbPlayers; i++) if (Players.get(i).getScore() == maxScore) winners.add(i);
    String winText;
    if (winners.size() == 1) {
    	winText = "Game over. Winner is player: " +
    			winners.iterator().next();
    }
    else {
    	winText = "Game Over. Drawn winners are players: " +
    			String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
    }
    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText(winText);
    refresh();
  }



	public static void main(String[] args)
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
	//loads property files
	final Properties properties;
	if (args == null || args.length == 0) {
		properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
		properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
	//stores property files values
	seed = PropertiesLoader.loadSeed(properties);
	enforceRules = PropertiesLoader.loadEnforceRules(properties);
	nbStartCards = PropertiesLoader.loadNumberofCards(properties);
	nbRounds = PropertiesLoader.loadNumberofRound(properties);
	List<String> PlayerTypes =  PropertiesLoader.loadPlayerTypes(properties);

	//starts game
    new Oh_Heaven(PlayerTypes);
  }

}
