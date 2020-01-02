package support;

import main.Game;

public class DrawPhase extends Phase{

    public DrawPhase(Game game, Player player, Player enemy){
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        System.out.println("드로우페이즈입니다");
        this.player.drawCard();

        if(!player.getDefeat()) {
            this.player.getDfp().getHp().updateHandPanel();

            this.player.getDfp().validate();


            setPhase(new MainPhase(game, player, enemy));
        }
        else{
            System.out.println("플레이어가 졌습니다");
        }
        }


}
