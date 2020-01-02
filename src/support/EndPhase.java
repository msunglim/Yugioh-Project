package support;

import main.Game;

public class EndPhase extends Phase{

    public EndPhase(Game game, Player player, Player enemy){
        this.game = game;
        this.player = player;
        this.enemy = enemy;
        System.out.println("엔드페이즈입니다.");
//
        setPhase(new StandbyPhase(game, enemy, player));
    }


}
