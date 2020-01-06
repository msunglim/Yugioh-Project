package support;

import Cards.Card;
import Cards.Monster;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import main.Game;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Map;

public class BattlePhase extends Phase {

    private Card attacker;
    private JPanel attackerImage;

    private Card victim;
    private JPanel victimImage;

    private boolean letsFight;



    //싸운놈리시트
    ArrayList<Card> hasFought;

    public BattlePhase(Game game, Player player, Player enemy) {

//        this.game = game;
//        this.player = player;
//        this.enemy = enemy;
        phaseName = "배틀";
        this.mainPhase2 = true;
        System.out.println("베틀페이즈 입니다.");
        setFightable();
        setVictim();
        hasFought = new ArrayList<>(5);
    }

    public void setVictim() {
        //적의 몬스터에 마우스어뎁터 추가요.
        for (Map.Entry<Card, JPanel> entry : player.getDfp().getCenter().getEnemyMonsterList().entrySet()) {
            System.out.println("적들" + entry.getKey().getName());
            entry.getValue().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //  entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length- 1]);
                    if (letsFight) {//공격선언을 해야 적의 몬스터들을 클릭했을때 전투를 실행하다.
                        victim = entry.getKey();
                        victimImage = entry.getValue();

                        System.out.println(entry.getKey().getName() + "을/를 공격했습니다!");

                        //공격표시로 존재하는 몬스터를 공격했을때,
                        if (player.getDfp().getCenter().getFightableEnemyMonsterList().contains(entry.getKey())) {
                            int damage = ((Monster) (attacker)).getATK() - ((Monster) (victim)).getATK();
                            if (damage < 0) {
                                //쨉도안되는 공격력으로 깝쳤을경우
                                player.setLp(player.getLp() + damage);
                                player.getDfp().updatePlayerLp();
                                enemy.getDfp().updateEnemyLp();

                                System.out.println("플레이어 생존점수:" + player.getLp());
                                System.out.println("공격한놈의 공격력이 맞은놈의 공격력보다 낮아 공격한놈이 파괴되었습니다.");

                              //  player.getDfp().getCenter().goToCemetery(attacker, attackerImage);
                                System.out.println("공격자"+ attacker.getName());
                        //        player.getDfp().getCenter().getMyFieldMonsterZone().get(attacker).remove(1);
                       //         player.getDfp().getCenter().getMyFieldMonsterZone().get(attacker).repaint();
                         //       player.getDfp().getCenter().getMyFieldMonsterZone().get(attacker).validate();


                                //이거만손보면도ㅐ
                                //지금안되는이유는 공격한놈 frame에서는 공격자 제거가 가능한데, 정작 맞은놈쪽에서 공격자의 이미지를 제거할 방법이없다.
                                enemy.getDfp().getCenter().goToCemetery(attacker, attackerImage);
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).remove(1);
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).repaint();
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).validate();



                            } else if (damage > 0) {
                                //상대몬스터를 개팻을경우
                                enemy.setLp(enemy.getLp() - damage);
                                player.getDfp().updateEnemyLp();
                                enemy.getDfp().updatePlayerLp();
                                System.out.println("적의 생존점수:" + enemy.getLp());
                                System.out.println("공격한놈의 공격력이 맞은놈의 공격력보다 높아 맞은놈이 파괴되었습니다.");

                                //자기 필드에서 맞은놈을 삭제
                                player.getDfp().getCenter().goToCemetery(victim, victimImage);

                                //적의 시야에 보이는 피해자의 몬스터를 삭제
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).repaint();
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).validate();


                            } else {
                                //같을경우
                                System.out.println("공격한놈의 공격력이 맞은놈의 공격력과 같아 동반자살하였습니다.");


                                player.getDfp().getCenter().goToCemetery(victim, victimImage);

                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);


                                enemy.getDfp().getCenter().goToCemetery(attacker, attackerImage);
                                player.getDfp().getCenter().getMyFieldMonsterZone().get(attacker).remove(1);
                            }
                        } else { //수비표시로 존재하는 몬스터를 공격했을때.
                            int damage = ((Monster) (attacker)).getATK() - ((Monster) (victim)).getDEF();
                            if (player.getDfp().getCenter().getSetEnemyMonsterList().containsKey(entry.getKey())) {
                                System.out.println("세트된 몬스터를 공격하셨군요");

                                entry.getValue().removeAll();
                                entry.getValue().repaint();
                                JPanel reversedImage = entry.getKey().getCardPreviewImage();

                                player.getDfp().getCenter().reverseSetMonster(entry.getKey(), reversedImage);
                                JPanel container = ((JPanel) (entry.getValue().getParent()));

                                container.add(reversedImage);
                                container.remove(entry.getValue());
                                container.repaint();
                                container.validate();

                                enemy.getDfp().getCenter().updateMyMonsterZone(entry.getKey(), reversedImage);
                                victimImage = reversedImage;

                                if (damage < 0) {
                                    player.setLp(player.getLp() + damage);
                                    player.getDfp().updatePlayerLp();
                                    enemy.getDfp().updateEnemyLp();
                                    System.out.println("플레이어 생존점수:" + player.getLp());
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 낮아 공격한 놈의 컨트롤러가 그 수치만큼 데미지를 입습니다.");
                                } else if (damage > 0) {
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 높아 맞은놈이 파괴되었습니다.");

                                    //공격자의 frame중 맞은놈을 삭제
                                    player.getDfp().getCenter().goToCemetery(victim, victimImage);
                                    //피해자의 frame중 맞은놈을 삭제
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                } else {
                                    //같을경우
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력과 같아 서로 아무 데미지도 입지않았습니다.");
                                }
                            } else {
                                if (damage < 0) {
                                    player.setLp(player.getLp() + damage);
                                    player.getDfp().updatePlayerLp();
                                    enemy.getDfp().updateEnemyLp();
                                    System.out.println("플레이어 생존점수:" + player.getLp());
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 낮아 공격한 놈의 컨트롤러가 그 수치만큼 데미지를 입습니다.");
                                } else if (damage > 0) {
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 높아 맞은놈이 파괴되었습니다.");

                                    player.getDfp().getCenter().goToCemetery(victim, victimImage);
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                } else {
                                    //같을경우
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력과 같아 서로 아무 데미지도 입지않았습니다.");
                                }
                            }
                        }
                        letsFight = false;
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

                    System.out.println("하이");
                    attacker = entry.getKey();
                    attackerImage = entry.getValue();

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

                            m1.removeActionListener(m1.getActionListeners()[0]);

                            letsFight = true;


                            //한번 공격버튼을 누르면 이번턴에 공격불가능
                            entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length - 1]);
                            //싸운놈 리스트에 명단올라가
                            hasFought.add(entry.getKey());

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

            if (!hasFought.contains(entry.getKey())) {
                //자이제 새로운 세팅 출력합시다. 음 근데 이거 어짜피 배틀페이지에 표시형식변경 못하게해놨잫ㄴ아..? 지금이 전략 안먹힌다면, 그냥 에드리스너만 해도될듯..
                entry.getValue().removeMouseListener(entry.getValue().getMouseListeners()[entry.getValue().getMouseListeners().length - 1]);

            }
        }
    }
}
