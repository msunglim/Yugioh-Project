package panel;

import main.Game;
import support.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhasePanel extends JPanel {


    private JButton mainPhase2 = new JButton("메인2");
    private JButton battlePhase = new JButton("배틀");
    private JButton endPahse = new JButton("엔드");

    public PhasePanel(Game game, Player player) {
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(100, 700));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        add(new JButton("스텐바이"));
        add(new JButton("드로우"));
        add(new JButton("메인1"));


        battlePhase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (player.getMyTurn() && game.getCurrnetPhase() instanceof MainPhase && !game.getCurrnetPhase().isMainPhase2()) {
                    ((MainPhase) (game.getCurrnetPhase())).goFight();
                }
            }
        });
        mainPhase2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (player.getMyTurn() && game.getCurrnetPhase() instanceof BattlePhase) {
                    ((BattlePhase) (game.getCurrnetPhase())).goMain();
                }
            }
        });


        endPahse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  System.out.println("머"+ game.getCurrnetPhase().toString());
                //    System.out.println("챜"+game.getCurrnetPhase().toString().equals("메인1"));
                if (player.getMyTurn()) {
                    if (game.getCurrnetPhase() instanceof MainPhase) {

                        ((MainPhase) (game.getCurrnetPhase())).goEnd();

                    } else if (game.getCurrnetPhase() instanceof BattlePhase) {
                        ((BattlePhase) (game.getCurrnetPhase())).goEnd();

                        //    System.out.println("엔드를눌렀을때..."+game.getCurrnetPhase().toString());

                        //   System.out.println("game.getCurrnetPhase().toString());

                    }
                    ((EndPhase) (game.getCurrnetPhase())).goNext();
                    ((StandbyPhase) (game.getCurrnetPhase())).goDraw();

                    ((DrawPhase) (game.getCurrnetPhase())).goMain();
                }
            }
        });


        add(battlePhase);
        add(mainPhase2);
        add(endPahse);

    }

    public JButton getMainPhase2() {
        return mainPhase2;
    }

    public JButton getBattlePhase() {
        return battlePhase;
    }

    public JButton getEndPahse() {
        return endPahse;
    }
}
