package support;

import Cards.Card;
import Cards.Monster;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
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


        System.out.println("적들의 수:" + player.getDfp().getCenter().getEnemyMonsterList().size());
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


                                //자기가 공격하고 자기가 파괴되는
                                player.getDfp().getCenter().goToCemetery(attacker, attackerImage);
                                //상대편에서 보는 공격자의 필드에서 공격몬스터 삭제
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).remove(1);
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).repaint();
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).validate();

                                //공 < 공
                                battleBetweenAttackPositionMonsters(enemy, player, attacker);

                                player.getDfp().getCenter().setNumberOfMonsters(player.getDfp().getCenter().getNumberOfMonsters() - 1);
                                enemy.getDfp().getCenter().setNumberOfMonstersOfEnemy(player.getDfp().getCenter().getNumberOfMonsters());

                            } else if (damage > 0) {
                                //상대몬스터를 개팻을경우
                                enemy.setLp(enemy.getLp() - damage);
                                player.getDfp().updateEnemyLp();
                                enemy.getDfp().updatePlayerLp();
                                System.out.println("적의 생존점수:" + enemy.getLp());
                                System.out.println("공격한놈의 공격력이 맞은놈의 공격력보다 높아 맞은놈이 파괴되었습니다.");

                                //공격자의 필드에서 보이는 상대방 필드에있는 피해자삭제
                                enemy.getDfp().getCenter().goToCemetery(victim, victimImage);

                                //맞은놈의 필드에서 맞은놈 삭제
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).repaint();
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).validate();

                                //공 > 공
                                battleBetweenAttackPositionMonsters(player, enemy, victim);


                                enemy.getDfp().getCenter().setNumberOfMonsters(enemy.getDfp().getCenter().getNumberOfMonsters() - 1);
                                player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());

                            } else {
                                //같을경우
                                System.out.println("공격한놈의 공격력이 맞은놈의 공격력과 같아 동반자살하였습니다.");

                                //그냥 위에두개 복붙함.
                                player.setLp(player.getLp() + damage);
                                player.getDfp().updatePlayerLp();
                                enemy.getDfp().updateEnemyLp();

                                player.getDfp().getCenter().goToCemetery(attacker, attackerImage);

                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).remove(1);
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).repaint();
                                enemy.getDfp().getCenter().getEnemyMonsterZone().get(attacker).validate();


                                battleBetweenAttackPositionMonsters(enemy, player, attacker);

                                player.getDfp().getCenter().setNumberOfMonsters(player.getDfp().getCenter().getNumberOfMonsters() - 1);
                                enemy.getDfp().getCenter().setNumberOfMonstersOfEnemy(player.getDfp().getCenter().getNumberOfMonsters());
                                enemy.getDfp().getCenter().goToCemetery(victim, victimImage);

                                //맞은놈의 필드에서 맞은놈 삭제
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).repaint();
                                enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).validate();


                                battleBetweenAttackPositionMonsters(player, enemy, victim);


                                enemy.getDfp().getCenter().setNumberOfMonsters(enemy.getDfp().getCenter().getNumberOfMonsters() - 1);
                                player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());

                                //                              enemy.getDfp().getCenter().setNumberOfMonsters(player.getDfp().getCenter().getNumberOfMonsters());
//                                player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());

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

                                player.getDfp().getCenter().getSetEnemyMonsterList().remove(victim);

                                if (damage < 0) {
                                    player.setLp(player.getLp() + damage);
                                    player.getDfp().updatePlayerLp();
                                    enemy.getDfp().updateEnemyLp();
                                    System.out.println("플레이어 생존점수:" + player.getLp());
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 낮아 공격한 놈의 컨트롤러가 그 수치만큼 데미지를 입습니다.");
                                } else if (damage > 0) {
                                    System.out.println("공격한놈의 공격력이 맞은놈의 수비력보다 높아 맞은놈이 파괴되었습니다.");

                                    //공격자의 frame중 맞은놈을 삭제 (맞은놈이라함은 공격자의 frame에서 보이는 상대방필드입니다.
                                    enemy.getDfp().getCenter().goToCemetery(victim, victimImage);
                                    //피해자의 frame중 맞은놈을 삭제.
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).repaint();
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).validate();

                                    battleBetweenEachPositionMonster(player, enemy, victim);

                                    enemy.getDfp().getCenter().setNumberOfMonsters(enemy.getDfp().getCenter().getNumberOfMonsters() - 1);
                                    player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());

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

                                    enemy.getDfp().getCenter().goToCemetery(victim, victimImage);
                                    enemy.getDfp().getCenter().getMyFieldMonsterZone().get(victim).remove(1);

                                    battleBetweenEachPositionMonster(player, enemy, victim);


                                    enemy.getDfp().getCenter().setNumberOfMonsters(player.getDfp().getCenter().getNumberOfMonsters() - 1);
                                    player.getDfp().getCenter().setNumberOfMonstersOfEnemy(enemy.getDfp().getCenter().getNumberOfMonsters());

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

                            if (player.getDfp().getCenter().getEnemyMonsterList().size() == 0) {
                                System.out.println(entry.getKey().getName() + "의 직접공격!");
                                directAttack(((Monster) (entry.getKey())).getATK());
                            }
                        }

                    });


                }
            });

        }
    }

    public void directAttack(int atk) {
        enemy.setLp(enemy.getLp() - atk);
        enemy.getDfp().updatePlayerLp();
        player.getDfp().updateEnemyLp();

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

    public void battleBetweenAttackPositionMonsters(Player p1, Player p2, Card card) {


        p1.getDfp().getCenter().getEnemyMonsterList().remove(card);
        p1.getDfp().getCenter().getFightableEnemyMonsterList().remove(card);
        p1.getDfp().getCenter().getEnemyMonsterZone().remove(card);

        p2.getDfp().getCenter().getFightableList().remove(card);
        p2.getDfp().getCenter().getMyFieldMonsterZone().remove(card);

    }

    public void battleBetweenEachPositionMonster(Player p1, Player p2, Card card) {
        p2.getDfp().getCenter().getMyFieldMonsterZone().remove(card);

        p1.getDfp().getCenter().getEnemyMonsterList().remove(card);

        p1.getDfp().getCenter().getEnemyMonsterZone().remove(card);
    }
}
