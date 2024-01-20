package at.mtxframe.mtxframe.economy;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.DatabaseConnection;
import at.mtxframe.mtxframe.database.DatabaseJobs;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;
import at.mtxframe.mtxframe.handlers.DbPlayerStatsHandler;
import at.mtxframe.mtxframe.messaging.MessageHandler;
import at.mtxframe.mtxframe.models.PlayerJobStatModel;
import at.mtxframe.mtxframe.models.PlayerStatsModel;
import at.mtxframe.mtxframe.utilitys.JobsLevelHandler;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class BalanceHandler{

    MtxFrame plugin = MtxFrame.getPlugin();
    DatabaseConnection connection = plugin.getDatabaseConnection();
    DbPlayerStatsHandler handler = new DbPlayerStatsHandler();
    DatabasePlayerStats dataBase = new DatabasePlayerStats(MtxFrame.getPlugin());
    DatabaseJobs jobsDatabase = new DatabaseJobs();
    MessageHandler msgHandler = new MessageHandler();
    JobsLevelHandler levelHandler = new JobsLevelHandler();
    HashMap<Player,PlayerStatsModel> localPlayerStats = new HashMap<>();

    String economyUnit = " ⛁ ";
    //BUFFERS UND TIMER
    private HashMap<UUID, Double> balanceBuffer = new HashMap<>();
    private HashMap<UUID, Double> xPBuffer = new HashMap<>();
    public static HashMap<UUID, Long> lastBlockBreakTime = new HashMap<>();
    public HashMap<UUID, Long> getLastBlockBreakTime() {
        return lastBlockBreakTime;
    }

    //VAULT

    public void setLastBlockBreakTime(HashMap<UUID, Long> lastBlockBreakTime) {
        BalanceHandler.lastBlockBreakTime = lastBlockBreakTime;
    }

    //Adding Money to a Player, list of Players, or all Players
    public void addMoneyPlayer(Player player,double amount) throws SQLException {
        Economy eco = plugin.getEco();
        OfflinePlayer offlinePlayer = Bukkit.getServer().getOfflinePlayer(player.getUniqueId());

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long currentTime = System.currentTimeMillis();
            for (UUID playerId : lastBlockBreakTime.keySet()) {
                if (currentTime - lastBlockBreakTime.get(playerId) >= 5000) {
                    // Spieler hat seit 10 Sekunden keinen Block mehr abgebaut
                    balanceBuffer.remove(playerId);
                    balanceBuffer.put(playerId, 0.0);
                }
            }
        }, 0, 20); // Überprüfe alle 20 Ticks (1 Sekunde)
        UUID playerId = player.getUniqueId();

        PlayerStatsModel pStats = plugin.getLocalPlayerStats().get(player);

        BigDecimal balance = BigDecimal.valueOf(pStats.getBalance());
        BigDecimal additionalAmount = BigDecimal.valueOf(amount);
        BigDecimal newBalance = balance.add(additionalAmount);
        pStats.setBalance(newBalance.doubleValue());

        // Add the amount to the player's buffer value

        double bufferAmount = balanceBuffer.getOrDefault(playerId, 0.0);
        balanceBuffer.put(playerId, bufferAmount + amount);

        String formattedAmount = String.format("%.2f", bufferAmount + amount);
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String formattedBalance = df.format(newBalance);


        msgHandler.actionBarMessage(player, ChatColor.GREEN + "+" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);
        localPlayerStats.put(player,pStats);
        plugin.setLocalPlayerStats(localPlayerStats);
        eco.depositPlayer(offlinePlayer,amount);


    }

    public void addMoneyAndEXP(Player player,double amount, double xpAmount, String switchStatement,PlayerJobStatModel localJobStats) throws SQLException {

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            long currentTime = System.currentTimeMillis();
            for (UUID playerId : lastBlockBreakTime.keySet()) {
                if (currentTime - lastBlockBreakTime.get(playerId) >= 5000) {
                    // Spieler hat seit 10 Sekunden keinen Block mehr abgebaut
                    balanceBuffer.remove(playerId);
                    xPBuffer.remove(playerId);
                }
            }
        }, 0, 20); // Überprüfe alle 20 Ticks (1 Sekunde)
        PlayerStatsModel stats = plugin.getLocalPlayerStats().get(player);

        BigDecimal balance = BigDecimal.valueOf(stats.getBalance());
        BigDecimal additionalAmount = BigDecimal.valueOf(amount);
        BigDecimal newBalance = balance.add(additionalAmount);

        stats.setBalance(newBalance.doubleValue());
        // Add the balance amount to the player's buffer value
        UUID playerId = player.getUniqueId();
        double bufferAmount = balanceBuffer.getOrDefault(playerId, 0.0);
        balanceBuffer.put(playerId, bufferAmount + amount);

        String formattedAmount = String.format("%.2f", bufferAmount + amount);
        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        String formattedBalance = df.format(newBalance);


        BigDecimal currentXP;
        double percentage = 0;
        double xPBufferAmount = xPBuffer.getOrDefault(playerId,xpAmount);
        String formattedXp = String.format("%.2f", xPBufferAmount);
        HashMap<Integer, Double> levels = levelHandler.getJobLevels();

        switch (switchStatement) {
            //Add xp to players buffer amount
            case "mining": {
                currentXP = BigDecimal.valueOf(localJobStats.getMiningXP());
                percentage = (currentXP.doubleValue() / levels.get(localJobStats.getMiningLevel()) * 100);
                localJobStats.setMiningXP(currentXP.add(BigDecimal.valueOf(xpAmount)).doubleValue());
                xPBuffer.put(playerId, xPBufferAmount + xpAmount);
                if (levelHandler.isLevelUp(localJobStats.getMiningLevel(), currentXP.doubleValue())){
                    msgHandler.titleMessageJobs(player,ChatColor.BLUE + "Miner", ChatColor.GOLD + "Dein Miner Level ist gestiegen.");
                    localJobStats.setMiningLevel(localJobStats.getWoodcutterLevel() + 1);
                    localJobStats.setMiningXP(00.00);
                    //TODO: Title Messages für levelUps
                    player.sendMessage("Du bist im Miner Level gestiegen! Aktuelles Level: " + localJobStats.getMiningLevel());
                }

                break;
            }
            case "farming": {
                currentXP = BigDecimal.valueOf(localJobStats.getFarmerXP());
                percentage = (currentXP.doubleValue() / levels.get(localJobStats.getFarmerLevel()) * 100);
                localJobStats.setFarmerXP(currentXP.add(BigDecimal.valueOf(xpAmount)).doubleValue());
                xPBuffer.put(playerId, xPBufferAmount + xpAmount);
                if (levelHandler.isLevelUp(localJobStats.getFarmerLevel(), currentXP.doubleValue())){
                    msgHandler.titleMessageJobs(player,ChatColor.GREEN + "Farmer", ChatColor.GOLD + "Dein Farmer Level ist gestiegen.");
                    localJobStats.setWoodcutterLevel(localJobStats.getFarmerLevel() + 1);
                    localJobStats.setFarmerXP(00.00);
                    //TODO: Title Messages für levelUps
                    player.sendMessage("Du bist im Farmer Level gestiegen! Aktuelles Level: " + localJobStats.getFarmerLevel());
                }

                break;
            }
            case "hunting": {
                currentXP = BigDecimal.valueOf(localJobStats.getHunterXP());
                percentage = (currentXP.doubleValue() / levels.get(localJobStats.getHunterLevel()) * 100);
                localJobStats.setHunterXP(currentXP.add(BigDecimal.valueOf(xpAmount)).doubleValue());
                xPBuffer.put(playerId, xPBufferAmount + xpAmount);
                if (levelHandler.isLevelUp(localJobStats.getHunterLevel(), currentXP.doubleValue())){
                    msgHandler.titleMessageJobs(player,ChatColor.RED + "Jäger", ChatColor.GOLD + "Dein Jäger Level ist gestiegen.");
                    localJobStats.setHunterLevel(localJobStats.getHunterLevel() + 1);
                    localJobStats.setHunterXP(00.00);
                    //TODO: Title Messages für levelUps
                    player.sendMessage("Du bist im Jäger Level gestiegen! Aktuelles Level: " + localJobStats.getHunterLevel());
                }

                break;
            }
            case "woodcutting": {
                currentXP = BigDecimal.valueOf(localJobStats.getWoodcutterXP());
                percentage = (currentXP.doubleValue() / levels.get(localJobStats.getWoodcutterLevel()) * 100);
                localJobStats.setWoodcutterXP(currentXP.add(BigDecimal.valueOf(xpAmount)).doubleValue());
                xPBuffer.put(playerId, xPBufferAmount + xpAmount);
                if (levelHandler.isLevelUp(localJobStats.getWoodcutterLevel(), currentXP.doubleValue())){
                    msgHandler.titleMessageJobs(player,ChatColor.DARK_GREEN + "Holzfäller", ChatColor.GOLD + "Dein Holzfäller Level ist gestiegen.");
                    localJobStats.setWoodcutterLevel(localJobStats.getWoodcutterLevel() + 1);
                    localJobStats.setWoodcutterXP(00.00);
                    //TODO: Title Messages für levelUps
                    player.sendMessage("Du bist im Holzfäller Level gestiegen! Aktuelles Level: " + localJobStats.getWoodcutterLevel());
                }

                break;
            }
            case "fishing": {
                currentXP = BigDecimal.valueOf(localJobStats.getFisherXP());
                percentage = (currentXP.doubleValue() / levels.get(localJobStats.getFisherLevel()) * 100);
                localJobStats.setFisherXP(currentXP.add(BigDecimal.valueOf(xpAmount)).doubleValue());
                xPBuffer.put(playerId, xPBufferAmount + xpAmount);
                if (levelHandler.isLevelUp(localJobStats.getWoodcutterLevel(), currentXP.doubleValue())){
                    msgHandler.titleMessageJobs(player,ChatColor.AQUA + "Fischer", ChatColor.GOLD + "Dein Fischer Level ist gestiegen.");
                    localJobStats.setFisherLevel(localJobStats.getFisherLevel() + 1);
                    localJobStats.setFisherXP(00.00);
                    //TODO: Title Messages für levelUps
                    player.sendMessage("Du bist im Fischer Level gestiegen! Aktuelles Level: " + localJobStats.getFisherLevel());
                }
                break;

            }
        }
        String formattedNeededXP = String.format("%.2f", percentage);
        //dataBase.updatePlayerStats(stats);
        localPlayerStats.put(player,stats);
        plugin.setLocalPlayerStats(localPlayerStats);
        msgHandler.actionBarMessage(player, ChatColor.GREEN + "+" + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + " | " + ChatColor.GOLD + formattedBalance + ChatColor.GOLD + economyUnit + ChatColor.GRAY + " | " + ChatColor.BLUE + formattedXp + " XP" + ChatColor.GRAY + " | " + ChatColor.DARK_PURPLE + formattedNeededXP + "%");



    }


    public void addMoneyPlayerList(List<Player> playerList, double amount) throws SQLException {
        for (Player player : playerList){
            if (player.isOnline()) {
                PlayerStatsModel stats = dataBase.findPlayerStatDataByUUID(String.valueOf(player.getUniqueId()));
                BigDecimal balance = BigDecimal.valueOf(stats.getBalance());
                BigDecimal additionalAmount = BigDecimal.valueOf(amount);
                BigDecimal newBalance = balance.add(additionalAmount);

                stats.setBalance(newBalance.doubleValue());
                String formattedAmount = String.format("%.2f", additionalAmount);
                String formattedBalance = String.format("%.2f", newBalance.doubleValue());
                dataBase.updatePlayerStats(stats);
                msgHandler.actionBarMessage(player, ChatColor.GREEN + "+" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

            }
        }
    }
    public void addMoneyAll(double amount) throws SQLException {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStatsModel stats = dataBase.findPlayerStatDataByUUID(String.valueOf(player.getUniqueId()));
            stats.setBalance((stats.getBalance())+amount);
            String formattedAmount = String.format("%.2f", amount);
            String formattedBalance = String.format("%.2f", stats.getBalance());
            dataBase.updatePlayerStats(stats);
            msgHandler.actionBarMessage(player, ChatColor.GREEN + "+" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

        }
    }

    //Removing Money drom a Player, list of Players, or all Players
    public void removeMoneyPlayer(Player player,double amount) throws SQLException {
        PlayerStatsModel stats = plugin.getLocalPlayerStats().get(player);
        if (stats.getBalance() >= amount) {
            stats.setBalance((stats.getBalance()) - amount);
            String formattedAmount = String.format("%.2f", amount);
            String formattedBalance = String.format("%.2f", stats.getBalance());
            dataBase.updatePlayerStats(stats);
            localPlayerStats = plugin.getLocalPlayerStats();
            localPlayerStats.put(player,stats);
            plugin.setLocalPlayerStats(localPlayerStats);
            msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
            msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

        } else {
            stats.setBalance(0);
            String formattedAmount = String.format("%.2f", amount);
            String formattedBalance = String.format("%.2f", stats.getBalance());
            dataBase.updatePlayerStats(stats);
            localPlayerStats = plugin.getLocalPlayerStats();
            localPlayerStats.put(player,stats);
            plugin.setLocalPlayerStats(localPlayerStats);
            msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
            msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

        }
    }
    public void removeMoneyPlayerList(List<Player> playerList, double amount) throws SQLException {
        for (Player player : playerList) {
            PlayerStatsModel stats = dataBase.findPlayerStatDataByUUID(String.valueOf(player.getUniqueId()));
            if (stats.getBalance() >= amount) {
                stats.setBalance((stats.getBalance()) - amount);
                String formattedAmount = String.format("%.2f", amount);
                String formattedBalance = String.format("%.2f", stats.getBalance());
                dataBase.updatePlayerStats(stats);
                msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
                msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

            } else {
                stats.setBalance(0);
                String formattedAmount = String.format("%.2f", amount);
                String formattedBalance = String.format("%.2f", stats.getBalance());
                dataBase.updatePlayerStats(stats);
                msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
                msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

            }
        }
    }
    public void removeMoneyAll(double amount) throws SQLException {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerStatsModel stats = dataBase.findPlayerStatDataByUUID(String.valueOf(player.getUniqueId()));
            if (stats.getBalance() >= amount) {
                stats.setBalance((stats.getBalance()) - amount);
                String formattedAmount = String.format("%.2f", amount);
                String formattedBalance = String.format("%.2f", stats.getBalance());
                dataBase.updatePlayerStats(stats);
                msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
                msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

            } else {
                stats.setBalance(0);
                String formattedAmount = String.format("%.2f", amount);
                String formattedBalance = String.format("%.2f", stats.getBalance());
                dataBase.updatePlayerStats(stats);
                msgHandler.economyMessage(player, "Es wurden " + ChatColor.RED + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "von deinem konto entfernt.");
                msgHandler.actionBarMessage(player, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalance);

            }
        }
    }

    //Sending and receiving Money between 2 Players
    public void sendMoney(Player sender, Player receiver, double amount) throws SQLException {
        PlayerStatsModel statsSender = plugin.getLocalPlayerStats().get(sender);
        PlayerStatsModel statsReceiver = plugin.getLocalPlayerStats().get(receiver);
        if (statsSender.getBalance() >= amount){
            String formattedAmount = String.format("%.2f", amount);
            String formattedBalanceS = String.format("%.2f", statsSender.getBalance());
            statsSender.setBalance((statsSender.getBalance()-amount));
            msgHandler.economyMessage(sender, "Du hast " + ChatColor.GREEN + formattedAmount + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "an " + ChatColor.AQUA + receiver.getName() + ChatColor.GRAY + " gezahlt.");
            dataBase.updatePlayerStats(statsSender);
            localPlayerStats = plugin.getLocalPlayerStats();
            localPlayerStats.put(sender,statsSender);


            msgHandler.actionBarMessage(sender, ChatColor.RED + "-" + formattedAmount + ChatColor.GOLD + economyUnit + " | " + formattedBalanceS);
            statsReceiver.setBalance((statsReceiver.getBalance())+amount);

            dataBase.updatePlayerStats(statsReceiver);
            localPlayerStats = plugin.getLocalPlayerStats();
            localPlayerStats.put(receiver,statsReceiver);
            plugin.setLocalPlayerStats(localPlayerStats);
            String formattedAmountR = String.format("%.2f", amount);
            String formattedBalanceR = String.format("%.2f", statsReceiver.getBalance());
            msgHandler.actionBarMessage(receiver, ChatColor.GREEN + "+" + formattedAmountR + ChatColor.GOLD + economyUnit + " | " + formattedBalanceR);
            msgHandler.economyMessage(receiver, ChatColor.AQUA + sender.getName() + ChatColor.GRAY + " hat dir " + ChatColor.GREEN + formattedAmountR + ChatColor.GOLD + economyUnit + ChatColor.GRAY + "gezahlt.");
        } else if (statsSender.getBalance() < amount) {
            msgHandler.economyMessage(sender,"Du kannst dir das nicht leisten, wähle einen geringeren Betrag.");
        }

    }




}
