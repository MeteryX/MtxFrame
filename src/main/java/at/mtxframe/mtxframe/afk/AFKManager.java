package at.mtxframe.mtxframe.afk;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class AFKManager {

    private static final HashMap<Player, Long> lastMovement = new HashMap<>();
    private static final long MOVEMENT_THRESHOLD = 60000L;

    //Spieler beim beitritt in die HashMap hinzufügen
    public static void playerJoined(Player player){
        lastMovement.put(player, System.currentTimeMillis());
    }
    //Spieler beim Verlassen des Servers aus der HashMap entfernen
    public static void playerLeft(Player player){
        lastMovement.remove(player);
    }
    //Update des Spielers in der hashmap bei Bewegung
    public static void playerMoved(Player player){

        boolean wasAFK = isAFK(player);
        lastMovement.put(player, System.currentTimeMillis());
        boolean nowAFK = isAFK(player);

        if (wasAFK && !nowAFK){
            player.sendMessage("Du bist nun nicht mehr AFK.");
        } else {
            if (!wasAFK && nowAFK){
                player.sendMessage("Du bist jetzt AFK.");
            }


        }

        //TODO - Check des AFK Status


    }

    public  static boolean isAFK(Player player){
        long timeElapsed = System.currentTimeMillis() - lastMovement.get(player);
        //Check ob der Spieler sich seit einer Minute bewegt hat
        //60000
        if (timeElapsed >= MOVEMENT_THRESHOLD){
            return true;
        }
        return false;
    }

    public static boolean toggleAFKStatus(Player player){
        if (isAFK(player)){
            lastMovement.put(player, System.currentTimeMillis());
            return false;
        } else {
            lastMovement.put(player, -1L);
            return true;
        }

    }



}
