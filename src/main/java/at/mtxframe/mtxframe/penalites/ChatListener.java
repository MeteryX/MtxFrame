package at.mtxframe.mtxframe.penalites;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.ColorAPI;
import at.mtxframe.mtxframe.files.ChatFilterConfig;
import at.mtxframe.mtxframe.files.ChatFilterManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener implements Listener {
    MtxFrame plugin = MtxFrame.getPlugin();
    ColorAPI colorAPI = new ColorAPI(plugin);
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        ArrayList<String> wordFilter = (ArrayList<String>) ChatFilterConfig.get().getStringList("wordfilter");

        String[] words = message.split("\\s+");
        for (String word : words){
            if (wordFilter.contains(word.toLowerCase())){
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "Du hast ein böses Wort gesagt! " + ChatColor.GRAY + "Wenn du versuchst unseren Wortfilter zu umgehen kann das zu einer Strafe führen.");
            }
        }

        String newMessage = colorAPI.translateColorCodes(message);
        event.setMessage(newMessage);

    }

}
