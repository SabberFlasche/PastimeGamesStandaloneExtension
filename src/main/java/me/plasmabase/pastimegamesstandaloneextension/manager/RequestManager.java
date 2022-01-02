package me.plasmabase.pastimegamesstandaloneextension.manager;

import me.plasmabase.pastimegames.manager.Games.GameType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class RequestManager {
    private static ArrayList<Request> connect4Requests = new ArrayList<>();
    private static ArrayList<Request> tictactoeRequests = new ArrayList<>();

    public static boolean addConnect4Request(Player requester, Player requested) {
        boolean x = true;
        for (Request request : connect4Requests) {
            if (request.getRequester() == requester) {
                x = false;
            }
        }
        if (x) {
            connect4Requests.add(new Request(GameType.CONNECT4, requester, requested));
        }
        return x;
    }

    public static boolean addTicTacToeRequest(Player requester, Player requested) {
        boolean x = true;
        for (Request request : tictactoeRequests) {
            if (request.getRequester() == requester) {
                x = false;
            }
        }
        if (x) {
            tictactoeRequests.add(new Request(GameType.TICTACTOE, requester, requested));
        }
        return x;
    }

    public static void removeRequest(Request request) {
        connect4Requests.remove(request);
        tictactoeRequests.remove(request);
    }

    /**
     * @param player accepting player
     * @return 0 = fail, 1 = success, 2 = multiple Requests for this player
     */
    public static int accept(Player player) {
        int xy = 0;
        Request finalRequest = null;

        //connect4Requests
        for (Request request : connect4Requests) {
            if (request.getRequested().equals(player)) {
                finalRequest = request;
                xy++;
            }
        }

        //tictactoeRequests
        for (Request request : tictactoeRequests) {
            if (request.getRequested().equals(player)) {
                finalRequest = request;
                xy++;
            }
        }

        //other request-lists
        //...


        if (xy == 1) {
            finalRequest.accept();
        }
        return xy;
    }

    /**
     * @param player accepting player
     * @return 0 = fail, 1 = success, 2 = multiple Requests for this player
     */
    public static int accept(Player player, Player requester) {
        int xy = 0;
        Request finalRequest = null;

        //connect4Requests
        for (Request request : connect4Requests) {
            if (request.getRequested().equals(player) && request.getRequester().equals(requester)) {
                finalRequest = request;
                xy++;
            }
        }

        //connect4Requests
        for (Request request : tictactoeRequests) {
            if (request.getRequested().equals(player) && request.getRequester().equals(requester)) {
                finalRequest = request;
                xy++;
            }
        }

        //other request-lists
        //...


        if (xy == 1) {
            finalRequest.accept();
        }else if (xy >= 2) {
            return 2;
        }
        return xy;
    }

    /**
     * @param player accepting player
     * @return 0 = fail, 1 = success, 2 = multiple Requests for this player
     */
    public static int accept(Player player, Player requester, GameType gameType) {
        int xy = 0;
        Request finalRequest = null;

        switch (gameType) {
            case CONNECT4:
                //connect4Requests
                for (Request request : connect4Requests) {
                    if (request.getRequested().equals(player) && request.getRequester().equals(requester)) {
                        finalRequest = request;
                        xy++;
                    }
                }
                break;
            case TICTACTOE:
                //connect4Requests
                for (Request request : tictactoeRequests) {
                    if (request.getRequested().equals(player) && request.getRequester().equals(requester)) {
                        finalRequest = request;
                        xy++;
                    }
                }
                break;
        }

        if (xy == 1) {
            finalRequest.accept();
        }else if (xy >= 2) {
            return 2;
        }
        return xy;
    }
}
