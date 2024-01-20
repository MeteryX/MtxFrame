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
    private  final static ScoreBoard instance = new ScoreBoard(MtxFrame.getPlugin());
    DatabasePlayerStats databaseStats = new DatabasePlayerStats(MtxFrame.getPlugin());
    String economyUnit = "⛁";
    MtxFrame plugin;
    public ScoreBoard(MtxFrame plugin){
        this.plugin = plugin;
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
        PlayerStatsModel stats = plugin.getLocalPlayerStats().get(player);
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
        PlayerStatsModel stats = plugin.getLocalPlayerStats().get(player);


        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(MtxFrame.getPlugin().getName(),"dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "EridiumRPG");

        Score score = objective.getScore(ChatColor.GRAY + "▪ " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Name: ");
        Score name = objective.getScore(ChatColor.GRAY + "  ▸ " + ChatColor.GRAY + player.getDisplayName());
        Score score1 = objective.getScore(ChatColor.GRAY + "▪ " + ChatColor.DARK_AQUA + "" + ChatColor.BOLD +  "Gilde: ");
        Score guild = objective.getScore(ChatColor.GRAY + "  ▸ " + ChatColor.GRAY + stats.getPlayerGuild());
        Score empty2 = objective.getScore(ChatColor.GRAY + " ");
        //Score score3 = objective.getScore(ChatColor.GREEN + "Geld: ");
        //Score score4 = objective.getScore( ChatColor.WHITE + String.valueOf(stats.getBalance()));
        Score empty3 = objective.getScore(ChatColor.WHITE + " ");

        Team teamBalance = scoreboard.registerNewTeam("Geld");
        String teamBalanceKey = ChatColor.GOLD.toString();
        teamBalance.addEntry(teamBalanceKey);
        teamBalance.setPrefix(ChatColor.GRAY + "▪ " + ChatColor.GREEN + "" + ChatColor.BOLD + "Geld: \n");
        teamBalance.setColor(ChatColor.GOLD);
        Double currentBalance = stats.getBalance();


        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        String formattedBalance = df.format(currentBalance);


        teamBalance.setSuffix(formattedBalance + " " + economyUnit);
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
        PlayerStatsModel stats = plugin.getLocalPlayerStats().get(player);

        Scoreboard scoreboard = player.getScoreboard();
        Team teamBalance = scoreboard.getTeam("Geld");

        Double currentBalance = stats.getBalance();


        DecimalFormat df = new DecimalFormat("##,###,##0.00");
        String formattedBalance = df.format(currentBalance);

        assert teamBalance != null;
        teamBalance.setSuffix(formattedBalance + " " + economyUnit);

    }




    public static ScoreBoard getInstance(){
        return instance;
    }

}
