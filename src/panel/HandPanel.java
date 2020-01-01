package panel;

import Cards.Card;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.Game;
import support.Player;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HandPanel extends JPanel {

    private Game game;
    private Player player;
    private Player enemy;

    public HandPanel(Player enemy) {
        this.enemy = enemy;
        for (int i = 0; i < enemy.getHandSize(); i++) {
            JPanel cardBack = enemy.getHand().get(i).getCardBack();
            add(cardBack);
        }

    }

    public HandPanel(Game game, Player player, Player enemy) {
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 100));
        setBorder(BorderFactory.createLineBorder(Color.black));


//        JPanel west =  (JPanel)game.getJf().getContentPane();
//        west.invalidate();
//        west.repaint();
//        west.add(game.getPlayer().getDeck()[0].getCardImage(),BorderLayout.WEST);
//        west.validate();
        for (int i = 0; i < player.getHandSize(); i++) {
            //       System.out.println("패의 카드:"+game.getPlayer().getHand());
            JPanel preview = player.getHand().get(i).getCardPreviewImage();


            //      System.out.println("너냐?"+ game.getDfp().getPlayer());
            //      if (player.equals(
            //             game.getDfp().getPlayer())) {
            final int ii = i;
            Card card = player.getHand().get(ii);
            Card card2 = player.getHand().get(ii);
            preview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    // JPanel parent =  (JPanel)game.getJf().getContentPane();


                    player.getDfp().addAtWest(card.getCardImage());
                    //이제 addAtWest가 있으니 밑에 코드를 쓸필요가없지만, 좋은 코드라 남겨둡니다.
//                    JPanel useMe = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.WEST);
//                    //JPanel useMe =  (JPanel)(((BorderLayout)DuelField.getGameField().getLayout()).getLayoutComponent(BorderLayout.WEST));
//
//                    //borderLayout을 쓰는 Panel의 west size acess하기...
////                    JPanel useMe = (JPanel)(((BorderLayout)parent.getLayout()).getLayoutComponent(BorderLayout.WEST));
//                    useMe.removeAll();
//                    useMe.invalidate();
//                    useMe.repaint();
//                    useMe.add(game.getPlayer().getHand().get(ii).getCardImage());
//                    useMe.validate();

                }
            });
            preview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // System.out.println("두둥탁!");
                    //   System.out.println("카드타입을알수있나요?" + game.getPlayer().getDeck()[ii].getCardType());
                    JMenuItem m1, m2;
                    switch (player.getHand().get(ii).getCardType()) {
                        case 0:
                            JPopupMenu pm = new JPopupMenu("선택창");
                            m1 = new JMenuItem("소환");
                            m2 = new JMenuItem("세트");
                            pm.add(m1);
                            pm.add(m2);
                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());

                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(player.getHand().get(ii).getName() + "을/를 소환합니다");
                                    //    JPanel summon = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.CENTER);
                                    //summon.removeAll();
                                    //   summon.invalidate();
                                    //   summon.repaint();
                                    //   summon.add(game.getPlayer().getHand().get(ii).getCardPreviewImage(), BorderLayout.CENTER);
                                    //   summon.validate();

                                    player.getDfp().getCenter().setCard(card, 1, false);
                                    enemy.getDfp().getCenter().updateEnemyField(card, 0, false);

                                    remove(preview);
                                    enemy.getDfp().getCenter().getEnemyHand().updateEnemyGraphic(-1);

                                    repaint();
                                    validate();
                                }
                            });
                            m2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(player.getHand().get(ii).getName() + "을/를 세트합니다");
                                    player.getDfp().getCenter().setCard(card, 1, true);
                                    //    enemy.getDfp().getCenter().setCard(card, 0, true);
                                    enemy.getDfp().getCenter().updateEnemyField(card, 0, true);
                                    remove(preview);
                                    enemy.getDfp().getCenter().getEnemyHand().updateEnemyGraphic(-1);

                                    repaint();
                                    validate();
                                }
                            });
                            break;
                        case 1:
                            pm = new JPopupMenu("선택창");
                            m1 = new JMenuItem("발동");
                            m2 = new JMenuItem("세트");
                            pm.add(m1);
                            pm.add(m2);
                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());
                            m1.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(player.getHand().get(ii).getName() + "을/를 발동합니다");
                                    //    JPanel summon = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.CENTER);
                                    //summon.removeAll();
                                    //   summon.invalidate();
                                    //   summon.repaint();
                                    //   summon.add(game.getPlayer().getHand().get(ii).getCardPreviewImage(), BorderLayout.CENTER);
                                    //   summon.validate();

                                    player.getDfp().getCenter().setCard(card, 0, false);
                                    enemy.getDfp().getCenter().updateEnemyField(card, 1, false);
                                    remove(preview);
                                    enemy.getDfp().getCenter().getEnemyHand().updateEnemyGraphic(-1);

                                    repaint();
                                    validate();
                                }
                            });
                            m2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(player.getHand().get(ii).getName() + "을/를 세트합니다");

                                    player.getDfp().getCenter().setCard(card, 0, true);
                                    enemy.getDfp().getCenter().updateEnemyField(card, 1, true);
                                    remove(preview);
                                    enemy.getDfp().getCenter().getEnemyHand().updateEnemyGraphic(-1);

                                    repaint();
                                    validate();
                                }
                            });
                            break;
                        case 2:
                            pm = new JPopupMenu("선택창");
                            //    m1 = new JMenuItem("발동");
                            m2 = new JMenuItem("세트");
                            //     pm.add(m1);
                            pm.add(m2);
                            pm.show(e.getComponent(),
                                    e.getX(), e.getY());

                            m2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(player.getHand().get(ii).getName() + "을/를 세트합니다");


                                    player.getDfp().getCenter().setCard(card, 0, true);
                                    enemy.getDfp().getCenter().updateEnemyField(card, 1, true);
                                    remove(preview);
                                    enemy.getDfp().getCenter().getEnemyHand().updateEnemyGraphic(-1);

                                    repaint();
                                    validate();
                                }
                            });
                            break;
                        default:
                            break;
                    }

//
//                    System.out.println("마우스 좌표:" + MouseInfo.getPointerInfo().getLocation().x);
//
//                    System.out.println("마우스 좌표:" + MouseInfo.getPointerInfo().getLocation().y);


                }
            });

            //상대가 보고있는 player1의 패의 수를 차감.
            add(preview);
            //     } else {
            //        add(cardBack);
            //     }
        }
    }


    /*
    @param i: indicates whether enemy gains a card or discards a card.
     */
    public void updateEnemyGraphic(int i) {
        if (i == -1) {
            remove(0);
        }
        repaint();
        validate();
    }


}
