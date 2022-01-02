package me.plasmabase.pastimegamesstandaloneextension.events.pastimegames;

import me.plasmabase.pastimegames.helper.eventsystem.EventListener;
import me.plasmabase.pastimegames.helper.eventsystem.GameResult;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;

public class EndGameEvent implements EventListener<GameResult> {
    @Override
    public void onCall(@NotNull GameResult gameResult) {
        Player winner = gameResult.getWinner();
        if (winner == null) {
            return;
        }
        Objective objective;
        Score score;
        switch (gameResult.getGameType()) {
            case CONNECT4:
                try {
                    //store wins to scoreboard
                    objective = winner.getScoreboard().registerNewObjective("connect4wins", "dummy", "Connect4 Wins");
                }catch (IllegalArgumentException e) {
                    objective = winner.getScoreboard().getObjective("connect4wins");
                }
                break;
            case TICTACTOE:
                try {
                    //store wins to scoreboard
                    objective = winner.getScoreboard().registerNewObjective("tictactoewins", "dummy", "TicTacToe Wins");
                }catch (IllegalArgumentException e) {
                    objective = winner.getScoreboard().getObjective("tictactoewins");
                }
                break;
            default:
                return;
        }
        score = objective.getScore(winner);
        score.setScore(score.getScore() + 1);
    }
}
