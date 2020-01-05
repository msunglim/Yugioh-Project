package panel;

import main.Game;
import support.Player;

import javax.swing.*;
import java.awt.*;

public class DuelField extends JPanel {

    private PlayZonePanel center;
    private JPanel west;
    private Player player;
    private Player enemy;
    private HandPanel hp;
    private PhasePanel east;
    private JPanel lpPanel;
    private JPanel north;
    private JLabel lp;
    private JLabel lp2;

    public DuelField(Game game, Player player, Player enemy) {

        this.player = player;
        this.enemy = enemy;
        //  gameField = new JPanel();
        // gameField.setPreferredSize(new Dimension(1280,720));
        setLayout(new BorderLayout());


        west = new JPanel();

        west.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        west.add(player.getHand().get(0).getCardImage());
        add(west, BorderLayout.WEST);

        center = new PlayZonePanel(game, player, enemy);
        center.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        add(center, BorderLayout.CENTER);

        hp = new HandPanel(game, player, enemy);

        add(hp, BorderLayout.SOUTH);

        east = new PhasePanel(game, player);
        east.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(east, BorderLayout.EAST);


        north = new JPanel();

        lpPanel = new JPanel();
        lpPanel.setPreferredSize(new Dimension(1000, 30));
        lpPanel.setLayout(new BorderLayout());

        lp = new JLabel("생존점수:" + player.getLp());
        lp.setHorizontalAlignment(JLabel.LEFT);
        lp.setAlignmentX(Component.LEFT_ALIGNMENT);
        lpPanel.add(lp, BorderLayout.WEST);


        lp2 = new JLabel("생존점수:" + enemy.getLp());
        //lp2.setHorizontalAlignment( JLabel.RIGHT);
        lp2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lpPanel.add(lp2, BorderLayout.EAST);
        north.add(lpPanel);
        add(north, BorderLayout.NORTH);

        // add(gameField);
    }

    public PlayZonePanel getCenter() {
        return center;
    }

    public void addAtWest(JPanel cardImage) {
        //  JPanel useMe = (JPanel) ((BorderLayout) (game.getDfp().getLayout())).getLayoutComponent(BorderLayout.WEST);
        //JPanel useMe =  (JPanel)(((BorderLayout)DuelField.getGameField().getLayout()).getLayoutComponent(BorderLayout.WEST));

        //borderLayout을 쓰는 Panel의 west size acess하기...
//                    JPanel useMe = (JPanel)(((BorderLayout)parent.getLayout()).getLayoutComponent(BorderLayout.WEST));
        west.removeAll();
        west.invalidate();
        west.repaint();
        west.add(cardImage);
        west.validate();
    }

    public HandPanel getHp() {
        return hp;
    }

    public PhasePanel getEast() {
        return east;
    }

    public void updatePlayerLp() {
        lp.setText("생존점수" + player.getLp());
        lpPanel.repaint();
        lpPanel.validate();

        north.repaint();
        north.validate();

    }

    public void updateEnemyLp() {
        lp2.setText("생존점수" + enemy.getLp());
        lpPanel.repaint();
        lpPanel.validate();

        north.repaint();
        north.validate();
    }
}