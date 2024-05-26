package at.mtxframe.mtxframe.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ChatFilterConfig {
    private static File file;
    private static FileConfiguration chatfilterFile;
    private static ArrayList<String> wordList;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MtxFrame").getDataFolder(),"chatfilter.yml");
        wordList = ChatFilterManager.getWordList();
        try {
            if (!file.exists()){
                file.createNewFile();
            }
        } catch (IOException e){
        }
        chatfilterFile = YamlConfiguration.loadConfiguration(file);

        chatfilterFile.addDefault("wordfilter", wordList);
    }

    public static FileConfiguration get() {
        return chatfilterFile;
    }

    public static void save(){
        try {
            chatfilterFile.save(file);
        } catch (IOException e){
            System.out.println("ChatFilterConfig konnt enicht gespeichert werden.");
        }
    }

    public static void reload(){
        chatfilterFile = YamlConfiguration.loadConfiguration(file);
    }




}
