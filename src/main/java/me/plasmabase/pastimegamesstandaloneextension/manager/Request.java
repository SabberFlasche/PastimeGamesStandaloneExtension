package me.plasmabase.pastimegamesstandaloneextension.manager;

import me.plasmabase.pastimegames.manager.Games.GameManager;
import me.plasmabase.pastimegames.manager.Games.GameType;
import org.bukkit.entity.Player;

public class Request {

    private Player requester;
    private Player requested;
    private GameType gameType;

    public Request(GameType gameType, Player requester, Player requested) {
        this.requester = requester;
        this.requested = requested;
        this.gameType = gameType;
    }

    public void accept() {
        GameManager.createGame(gameType, requested, requester);
        RequestManager.removeRequest(this);
    }

    public Player getRequested() {
        return requested;
    }

    public Player getRequester() {
        return requester;
    }

    public GameType getGameType() {
        return gameType;
    }
}
