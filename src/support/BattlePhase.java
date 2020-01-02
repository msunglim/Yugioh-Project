package support;

import main.Game;

public class BattlePhase extends Phase{

    private MainPhase mainPhase;
    public BattlePhase(Game game, Player player,Player enemy){

        this.game = game;
        this.player = player;
        this.enemy = enemy;

        this.mainPhase2 = true;
        System.out.println("베틀페이즈 입니다.");

        setPhase(new MainPhase(game, player, enemy));
    }
}
