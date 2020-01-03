package support;

import main.Game;

public class BattlePhase extends Phase{

    public BattlePhase(Game game, Player player,Player enemy){

//        this.game = game;
//        this.player = player;
//        this.enemy = enemy;
        phaseName = "배틀";
        this.mainPhase2 = true;
        System.out.println("베틀페이즈 입니다.");
    }
    public void goEnd(){

        setPhase(new EndPhase(game, player, enemy));
    }
    public void goMain(){
        setPhase(new MainPhase(game, player, enemy));
    }
}
