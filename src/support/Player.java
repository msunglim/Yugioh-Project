package support;

import Cards.Card;
import characters.Character;
import panel.DuelField;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Player {
    private Card[] deck;
    private int deckSize;
    private Character character;
    private ArrayList<Card> hand = new ArrayList<>();
    private int handSize;
    private Stack<Card> deckStack = new Stack<>();
    private int lp;
    private DuelField dfp;
    private boolean myTurn;
    private boolean defeat = false;

    public Player(Character c) {

        this.character = c;
        deck = character.getDeck();
        deckSize = character.getDeckSize();
        //  deckSize = 0;
        lp = 2000;
//        handSize = 5;
//        deckSize -= 5;
    }

    public void setMyTurn(boolean tf){
        myTurn= tf;
    }
    public boolean getMyTurn(){
        return  myTurn;
    }
    public void gameStart() {
        lp = 2000;
        for (int i = 0; i < 5; i++) {
            handSize++;
            drawCard();
        }
    }

    public void setHandSize(int change) {
        handSize += change;
    }

    public int getHandSize() {
        return hand.size();
    }

    public int getDeckSize() {


        return deckSize;
    }

    public void drawCard() {
//        System.out.println("드로");
//
        if (deckStack.size() != 0){
            System.out.println("드로된 카드:" + (Card) deckStack.peek());
            hand.add((Card) deckStack.pop());
        }
        else{
            System.out.println("드로우 할 카드가 없습니다.");
            defeat = true;
        }
        //
//
//
    }

    public boolean getDefeat(){
        return defeat;
    }
    public Stack getDeckStack() {
        return deckStack;
    }

    public void shuffleDeck() {

//        System.out.println("하기전덱mmmmmmmmmmmmmmmmmmmmmmmmmmm");
//        for(int i = 0; i < deckSize; i++){
//
//            System.out.println(deck[i]+" i: "+ i);
//        }
        for (int i = 0; i < deckSize; i++) {
            int randomInteger = new Random().nextInt(deckSize);
            Card curr = deck[i];
            deck[i] = deck[randomInteger];
            deck[randomInteger] = curr;

            //    deckStack.push(curr);
            //     System.out.println("random"+randomInteger);
            //    System.out.println("넣은거"+ curr);
            //    System.out.println("덱스텍:" + deckStack.peek());
        }
//        System.out.println("덱mmmmmmmmmmmmmmmmmmmmmmmmmmm");
        //    System.out.print("덱열람array");

        for (int i = 0; i < deckSize; i++) {
            deckStack.push(deck[i]);
            // System.out.print("넣어진카드"+deck[i]);
            //      System.out.print(deck[i]+", ");
        }
        System.out.println();
//        System.out.println("덱열람array"+ deck.toString());
        System.out.println("덱열람stack" + deckStack);
    }

    public void increaseDeckSzie() {
        deckSize++;
    }

    public Card[] getDeck() {
        return deck;
    }

    public String getName() {
        return character.getName();
    }

    public JLabel getImage() {
        return character.getImage();
    }

    public Character getCharacter() {
        return character;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int newLp){
        lp = newLp;
    }
    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setDfp(DuelField dfp) {
        this.dfp = dfp;
    }

    public DuelField getDfp() {
        return dfp;
    }
}
