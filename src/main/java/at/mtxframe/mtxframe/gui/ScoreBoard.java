package at.mtxframe.mtxframe.gui;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.database.DatabasePlayerStats;
import at.mtxframe.mtxframe.models.PlayerStatsModel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.sql.SQLException;
import java.text.DecimalFormat;

public class ScoreBoard implements  Runnable{
    private  final static ScoreBoard instance = new ScoreBoard();
    DatabasePlayerStats databaseStats = new DatabasePlayerStats();
    String economyUnit = "⛁";
    public ScoreBoard(){
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()){
            try {
                if (player.getScoreboard() != null && player.getScoreboard().getObjective(MtxFrame.getPlugin().getName()) != null) {
                    updateScoreboard(player);
                } else {
                    createNewScoreboard(player);
                }
            } catch (SQLException exception){
                System.out.println(exception.getMessage());
            }
        }
    }

    public  void setBelowName(Player player) throws SQLException {
        PlayerStatsModel stats = databaseStats.findPlayerStatDataByUUID(player.getUniqueId().toString());
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(MtxFrame.getPlugin().getName(), "dummy");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        if (stats.getPlayerGuild().equals("No Guild")){
            objective.setDisplayName(ChatColor.GREEN + "Einzelspieler");
        } else {
            objective.setDisplayName(ChatColor.GOLD + stats.getPlayerGuild());
        }

    }

    private void createNewScoreboard(Player player) throws SQLException {
        PlayerStatsModel stats = databaseStats.findPlayerStatDataByUUID(player.getUniqueId().toString());


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(MtxFrame.getPlugin().getName(),"dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "EridiumRPG");

        Score score = objective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD + "Name: ");
        Score name = objective.getScore(ChatColor.WHITE + player.getDisplayName());
        Score score1 = objective.getScore(ChatColor.BLUE + "" + ChatColor.BOLD +  "Gilde: ");
        Score guild = objective.getScore(ChatColor.WHITE + stats.getPlayerGuild());
        Score empty2 = objective.getScore(ChatColor.GRAY + " ");
        //Score score3 = objective.getScore(ChatColor.GREEN + "Geld: ");
        //Score score4 = objective.getScore( ChatColor.WHITE + String.valueOf(stats.getBalance()));
        Score empty3 = objective.getScore(ChatColor.WHITE + " ");

        Team teamBalance = scoreboard.registerNewTeam("Geld");
        String teamBalanceKey = ChatColor.GOLD.toString();
        teamBalance.addEntry(teamBalanceKey);
        teamBalance.setPrefix(ChatColor.GREEN + "" + ChatColor.BOLD + "Geld: ");
        teamBalance.setColor(ChatColor.GOLD);
        Double currentBalance = stats.getBalance();
        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        String formattedBalance = df.format(currentBalance);
        String balanceSuffix = formattedBalance + " " + economyUnit;

        teamBalance.setSuffix(balanceSuffix);
        objective.getScore(teamBalanceKey).setScore(0);



        score.setScore(6);
        name.setScore(5);
        empty2.setScore(4);
        score1.setScore(3);
        guild.setScore(2);

        //score3.setScore(8);
        //score4.setScore(7);
        empty3.setScore(1);

        player.setScoreboard(scoreboard);

    }

    private void updateScoreboard(Player player) throws SQLException {
        PlayerStatsModel stats = databaseStats.findPlayerStatDataByUUID(player.getUniqueId().toString());

        Scoreboard scoreboard = player.getScoreboard();
        Team teamBalance = scoreboard.getTeam("Geld");

        teamBalance.setSuffix(String.valueOf(stats.getBalance()));

    }




    public static ScoreBoard getInstance(){
        return instance;
    }

}
