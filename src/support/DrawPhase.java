package support;

import main.Game;

public class DrawPhase extends Phase {

    public DrawPhase(Game game, Player player, Player enemy) {
//        this.game = game;
//        this.player = player;
//        this.enemy = enemy;
        phaseName = "드로우";

        System.out.println("드로우페이즈입니다");
        this.player.drawCard();

        if (!player.getDefeat()) {
            this.player.getDfp().getHp().updateHandPanel();

            this.player.getDfp().validate();


        } else {
            System.out.println(player.getName()+ "이/가 졌습니다");
        }
    }
    public void goMain(){
        setPhase(new MainPhase(game, player ,enemy));
    }


}
