package panel;

import main.Game;

import javax.swing.*;
import java.awt.*;

public class DuelField extends JPanel {

    private PlayZonePanel center;
    private JPanel west;
    public DuelField(Game game) {

        //  gameField = new JPanel();
        // gameField.setPreferredSize(new Dimension(1280,720));
        setLayout(new BorderLayout());

        HandPanel hp = new HandPanel(game, game.getPlayer());

        add(hp, BorderLayout.SOUTH);

         west = new JPanel();

        west.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        west.add(game.getPlayer().getDeck()[0].getCardImage());
        add(west, BorderLayout.WEST);


        center = new PlayZonePanel(game);
        center.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        add(center, BorderLayout.CENTER);

        PhasePanel east = new PhasePanel(game);
        east.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(east, BorderLayout.EAST);



        JPanel north = new JPanel();

        JPanel lpPanel = new JPanel();
        lpPanel.setPreferredSize(new Dimension(1000, 30));
        lpPanel.setLayout(new BorderLayout());

        JLabel lp = new JLabel("생존점수:" + game.getPlayer().getLp());
        lp.setHorizontalAlignment( JLabel.LEFT);
        lp.setAlignmentX(Component.LEFT_ALIGNMENT);
        lpPanel.add(lp, BorderLayout.WEST);


        JLabel lp2 = new JLabel("생존점수:" + game.getPlayer2().getLp());
       //lp2.setHorizontalAlignment( JLabel.RIGHT);
        lp2.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lpPanel.add(lp2, BorderLayout.EAST);
        north.add(lpPanel);
        add(north, BorderLayout.NORTH);

        // add(gameField);
    }

    public PlayZonePanel getCenter(){
        return center;
    }

    public void addAtWest(JPanel cardImage){
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


}