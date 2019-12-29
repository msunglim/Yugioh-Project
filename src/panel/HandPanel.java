package panel;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import main.Game;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HandPanel extends JPanel {

    private Game game;

    public HandPanel(Game game) {
        this.game = game;
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(500, 100));
        setBorder(BorderFactory.createLineBorder(Color.black));


//        JPanel west =  (JPanel)game.getJf().getContentPane();
//        west.invalidate();
//        west.repaint();
//        west.add(game.getPlayer().getDeck()[0].getCardImage(),BorderLayout.WEST);
//        west.validate();
        for (int i = 0; i < game.getPlayer().getHandSize(); i++) {
            //       System.out.println("패의 카드:"+game.getPlayer().getHand());
            JPanel preview = game.getPlayer().getHand().get(i).getCardPreviewImage();
            final int ii = i;
            preview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);

                    // JPanel parent =  (JPanel)game.getJf().getContentPane();


                    JPanel useMe = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.WEST);
                    //JPanel useMe =  (JPanel)(((BorderLayout)DuelField.getGameField().getLayout()).getLayoutComponent(BorderLayout.WEST));

                    //borderLayout을 쓰는 Panel의 west size acess하기...
//                    JPanel useMe = (JPanel)(((BorderLayout)parent.getLayout()).getLayoutComponent(BorderLayout.WEST));
                    useMe.removeAll();
                    useMe.invalidate();
                    useMe.repaint();
                    useMe.add(game.getPlayer().getHand().get(ii).getCardImage());
                    useMe.validate();

                }
            });
            preview.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    // System.out.println("두둥탁!");
                    //   System.out.println("카드타입을알수있나요?" + game.getPlayer().getDeck()[ii].getCardType());
                    JMenuItem m1, m2;
                    switch (game.getPlayer().getHand().get(ii).getCardType()) {
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
                                    System.out.println(game.getPlayer().getHand().get(ii).getName() + "을/를 소환합니다");
                                //    JPanel summon = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.CENTER);
                                    //summon.removeAll();
                                 //   summon.invalidate();
                                 //   summon.repaint();
                                 //   summon.add(game.getPlayer().getHand().get(ii).getCardPreviewImage(), BorderLayout.CENTER);
                                 //   summon.validate();

                              game.getDfp().getCenter().setCard(preview,1);
                              remove(preview);
                        //      repaint();
                              validate();
                                }
                            });
                            m2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(game.getPlayer().getHand().get(ii).getName() + "을/를 세트합니다");
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
                                    System.out.println(game.getPlayer().getHand().get(ii).getName() + "을/를 발동합니다");
                                }
                            });
                            m2.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    System.out.println(game.getPlayer().getHand().get(ii).getName() + "을/를 세트합니다");
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
                                    System.out.println(game.getPlayer().getHand().get(ii).getName() + "을/를 세트합니다");
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
            add(preview);
        }
    }
}
