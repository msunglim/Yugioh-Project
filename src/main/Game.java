package main;

import Cards.Card;
import Cards.CardUniverse;
import panel.DeckCreationPanel;
import panel.DuelField;
import panel.PlayerCreationPanel;
import panel.WelcomePanel;
import support.Player;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import characters.Character;

public class Game {
    private JFrame jf;
    private WelcomePanel wp;
    private PlayerCreationPanel pcp;
    private Player player;
    private Character character;
    private CardUniverse cardUniverse;
    private DeckCreationPanel dcp;
    private DuelField dfp;
    public static void main(String[] args) {
        new Game();


    }

    public Game() {
        System.out.println("듀얼아카데미에 오신것을 환영합니다.");
        jf = new JFrame();
        showWelcomePanel();

        jf.setSize(new Dimension(1280, 720));
        jf.setVisible(true);
        jf.pack();//이것은 알아서 resize를 해준다.
        jf.setLocationRelativeTo(null); //이것은 창을 모니터 가운데에 띄우게해준다.
    }

    public void showWelcomePanel() {
        wp = new WelcomePanel(this);
        jf.setContentPane(wp);
    }

    public void showPlayerCreationPanel() {
        jf.remove(wp);

        cardUniverse = new CardUniverse(this);
        pcp = new PlayerCreationPanel(this);
        jf.setContentPane(pcp);
        jf.validate();

    }

    public void showDeckCreationPaenl(int i) {
        jf.remove(pcp);
        dcp = new DeckCreationPanel(this, i);
        jf.setContentPane(dcp);


        jf.validate();

    }

    public void showDuelField(){
        jf.remove(dcp);

        player.shuffleDeck();
        player.gameStart();
        dfp = new DuelField(this);
        jf.setContentPane(dfp);


        System.out.println("플레이어 확인:"+ player.getName());
        System.out.println("플레이어 덱확인:");
        int i = 0;
        while(i < player.getDeckSize()){
        //    System.out.println(((Card)player.getDeckStack().peek()).getName());
            i++;
        }
        jf.validate();

//        DuelField dfp2 = new DuelField(this);
//        JFrame jf2 = new JFrame();
//        jf2.setTitle("복제품");
//        jf2.setSize(new Dimension(1280, 720));
//        jf2.setVisible(true);
//        jf2.pack();//이것은 알아서 resize를 해준다.
//        jf2.setLocationRelativeTo(null); //이것은 창을 모니터 가운데에 띄우게해준다.
//        jf2.setContentPane(dfp2);
//        jf2.validate();


    }

    public DuelField getDfp(){
        return dfp;
    }

    public void setPlayer(Character character) {
        player = new Player(character);
    }

    public Player getPlayer() {
        return player;
    }

    public void setCharacter(int i) {
        character = new Character(this, i);
        setPlayer(character);
    }

    public Character getCharacter() {
        return character;
    }

    public CardUniverse getCardUniverse() {
        return cardUniverse;
    }

    public JFrame getJf(){
        return jf;
    }
}
