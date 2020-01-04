package support;

import Cards.Card;
import Cards.Monster;
import main.Game;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

public class BattlePhase extends Phase {

    private Card attacker;
    private Card victim;
    private boolean letsFight;
    public BattlePhase(Game game, Player player, Player enemy) {

//        this.game = game;
//        this.player = player;
//        this.enemy = enemy;
        phaseName = "배틀";
        this.mainPhase2 = true;
        System.out.println("베틀페이즈 입니다.");


        setFightable();


        //    System.out.println("왜안해");
            setVictim();
        //}
    }

    public void setVictim() {
        for (Map.Entry<Card, JPanel> entry : player.getDfp().getCenter().getEnemyMonsterList().entrySet()) {


            entry.getValue().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    //  entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length- 1]);
                    if (letsFight) {
                        victim = entry.getKey();

                        System.out.println(entry.getKey().getName() + "을/를 공격했습니다!");


                        if (((Monster) (attacker)).getATK() < ((Monster) (victim)).getATK()) {
                            System.out.println("공격한놈의 공격력이 맞은놈의 공격력보다 낮아 공격한놈이 파괴되었습니다.");
                        } else if (((Monster) (attacker)).getATK() > ((Monster) (victim)).getATK()) {
                            System.out.println("공격한놈의 공격력이 맞은놈의 공격력보다 높아 맞은놈이 파괴되었습니다.");
                        } else {
                            //같을경우
                            System.out.println("공격한놈의 공격력이 맞은놈의 공격력과 같아 동반자살하였습니다.");
                        }

                        letsFight =false;
                    }
                }

            });

        }
    }

    public void setFightable() {

        //1. 모든 필드의 공격표시몬스터에게 popup추가하라.
        for (Map.Entry<Card, JPanel> entry : player.getDfp().getCenter().getFightableList().entrySet()) {

            entry.getValue().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);

                    attacker = entry.getKey();

                    JPopupMenu pm = new JPopupMenu("선택창");
                    JMenuItem m1 = new JMenuItem("공격");


                    pm.add(m1);
                    pm.show(e.getComponent(),
                            e.getX(), e.getY());

               //     System.out.println("검찰1");
                    m1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            System.out.println(entry.getKey().getName() + "이/가 공격합니다!!");

//                            System.out.println("검찰2");
//                            System.out.println("이건뭐냐"+m1.getMouseListeners().length);
                        //    entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length- 1]);

                            m1.removeActionListener(m1.getActionListeners()[0]);

                            letsFight = true;
                        //    System.out.println("렛츠파잇"+ letsFight);
                        }

                    });


                }
            });

        }
    }

    public void goEnd() {

        removeFightable();
        setPhase(new EndPhase(game, player, enemy));
    }

    public void goMain() {

        removeFightable();

        setPhase(new MainPhase(game, player, enemy));
    }

    public void removeFightable() {
        for (Map.Entry<Card, JPanel> entry : player.getDfp().getCenter().getFightableList().entrySet()) {
            //이거 뺏었다가 나중에 끝날때 돌려줘야해.....
//            MouseListener []ml = player.getDfp().getCenter().getFightableList().get(i).getMouseListeners();
//
//            for(int j= 0; j < ml.length; j++) {
//                player.getDfp().getCenter().getFightableList().get(i).removeMouseListener(ml[j]);
//            }

            //   final int ii = i;

            //자이제 새로운 세팅 출력합시다. 음 근데 이거 어짜피 배틀페이지에 표시형식변경 못하게해놨잫ㄴ아..? 지금이 전략 안먹힌다면, 그냥 에드리스너만 해도될듯..
            entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length - 1]);
        }
    }
}
