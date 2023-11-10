package at.mtxframe.mtxframe.messaging;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessageHandler {

        public void guildMessage(Player receiver, String message) {


            receiver.sendMessage(ChatColor.GOLD + "[GILDEN]: " + ChatColor.GRAY + message);

        }

        public void statsMessage(Player receiver, String message) {


            receiver.sendMessage(ChatColor.DARK_GREEN + "[STATISTIK]: " + ChatColor.GRAY + message);

        }
        public void claimMessage(Player receiver, String message){

            receiver.sendMessage(ChatColor.AQUA + "[CLAIMING]: " + ChatColor.GRAY + message);

        }
        public void guildClaimMessage(Player receiver, String message){

            receiver.sendMessage( ChatColor.AQUA +"["+ ChatColor.GOLD +"GILDEN-CLAIM" + ChatColor.AQUA + "]" + ChatColor.GOLD + ": " + ChatColor.GRAY + message);

        }

        public void economyMessage(Player receiver, String message) {


            receiver.sendMessage(ChatColor.GREEN + "[FINANZEN]: " + ChatColor.GRAY + message);

        }

        public void actionBarMessage(Player receiver, String message){

            receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));

        }

        public  void titleMessageJobs(Player receiver, String message, String jobName){
            //sendTitle Aufbau (title, subTitle, (int) fadeInTime, (int) stayTime, (int) fadeOutTime
            receiver.playSound(receiver.getLocation(),Sound.ENTITY_PLAYER_LEVELUP,0.3F,0.1F);
            receiver.sendTitle(message, jobName, 20,60,40);
        }

    public  void titleMessageAbility(Player receiver, String message, String jobName){
        //sendTitle Aufbau (title, subTitle, (int) fadeInTime, (int) stayTime, (int) fadeOutTime
        receiver.playSound(receiver.getLocation(),Sound.BLOCK_NOTE_BLOCK_GUITAR,0.3F,0.1F);
        receiver.sendTitle(message, jobName, 10,30,20);
    }





}
