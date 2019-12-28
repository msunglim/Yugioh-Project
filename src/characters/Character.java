package characters;

import Cards.Card;
import main.Game;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Character {

    private String name;
    private Card[] deck = new Card[15];
    private ImageIcon img;
    private JLabel image;
    private Game game;
    private int deckSize;
    private int selectedCharacter;

    public Character(Game game, int i) {

        this.game = game;
        selectedCharacter = i + 1;

        setCharacter(i + 1);
        setDeck(i + 1);
    }

    private void setCharacter(int i) {
        String fileName = "data/images/characters/c" + i + ".PNG";
        img = new ImageIcon(fileName);
        img.setImage(img.getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT));
        image = new JLabel(img);

        deckSize = 0;



    }
    private void setDeck(int i){
        switch (i) {

            case 1:
                System.out.println("유희입니다.");
                name = "유희";

                game.getCardUniverse().assignMonsterDeck(this, 2, 3, 11, 13, 16, 17);

                game.getCardUniverse().assignMagicDeck(this, 1, 5, 7);

                game.getCardUniverse().assignTrapDeck(this, 4, 5);
                System.out.println("룰과 메너를 지켜 즐거운 듀얼을 하자!");
                break;

            case 2:
                System.out.println("조이입니다.");
                name = "조이";
                game.getCardUniverse().assignMonsterDeck(this, 4,5,7,15,18);

                game.getCardUniverse().assignMagicDeck(this, 5,6,8);

                game.getCardUniverse().assignTrapDeck(this, 2);

                System.out.println("완전부활 초 퍼펙트 죠노우치님이시다!");

                break;

            case 3:
                name = "카이바";
                System.out.println("훙.");
                game.getCardUniverse().assignMonsterDeck(this, 1,1,1,6,8,9,12,14);

                game.getCardUniverse().assignMagicDeck(this, 3,4,5);

                game.getCardUniverse().assignTrapDeck(this, 1,3);

                break;

            case 4:
                name = "마리크";
                System.out.println("오픈뿡뿡 뿡뿌뿡뿡뿡 존존존존존존존존존");
          //      game.getCardUniverse().assignMonsterDeck(this, 1,1,1,6,8,9,12,14);

                game.getCardUniverse().assignMagicDeck(this, 5);

            //    game.getCardUniverse().assignTrapDeck(this, 1,3);

                break;

            case 5:
                name = "바쿠라";
                System.out.println("최후에 어둠으로 사라지는 건 네놈이다...");

                game.getCardUniverse().assignMagicDeck(this, 5);

                break;


            case 6:

                name = "페가사스";

                game.getCardUniverse().assignMagicDeck(this, 5);

                System.out.println("ME는 YOU의 패를 전부 파악하고 있습니다!");
                break;

            default:
                name = "기본";
                System.out.println("기본입니다.");

                break;


        }
    }

    public String getName() {
        return name;
    }

    public ImageIcon getIcon() {
        return img;
    }

    public JLabel getImage() {
        return image;
    }

    public Card[] getDeck() {
        return deck;
    }

    public void addCard(Card card) {
        if (deckSize + 1 < deck.length) {
            deck[deckSize] = card;
            deckSize++;
        }
        else{
            System.out.println("덱 capacity에서 벗어나서 "+card.getName()+"을 추가하지 못했습니다.");
        }
    }


    public int getSelectedCharacter() {
        return selectedCharacter;
    }



    public int getDeckSize() {
        return deckSize;
    }
}
