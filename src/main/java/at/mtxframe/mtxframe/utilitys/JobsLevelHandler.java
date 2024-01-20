package at.mtxframe.mtxframe.utilitys;

import java.util.HashMap;
import java.util.Map;

public class JobsLevelHandler {
    public HashMap<Integer, Double> jobLevels = new HashMap<>();
    public  HashMap<Integer,Long> minerAbilityCooldown = new HashMap<>();
    HashMap<Integer,Double> dropChances = new HashMap<>();
    public HashMap<Integer, Double> getDropChances() {
        dropChances.put(1, 00.05*1 );
        dropChances.put(2, 00.05*2 );
        dropChances.put(3, 00.05*3 );
        dropChances.put(4, 00.05*4 );
        dropChances.put(5, 00.05*5 );
        dropChances.put(6, 00.05*6 );
        dropChances.put(7, 00.05*7 );
        dropChances.put(8, 00.05*8 );
        dropChances.put(9, 00.05*9 );
        dropChances.put(10,00.05*10+1.50);
        dropChances.put(11,00.05*11+1.50);
        dropChances.put(12,00.05*12+1.50);
        dropChances.put(13,00.05*13+1.50);
        dropChances.put(14,00.05*14+1.50);
        dropChances.put(15,00.05*15+2.50);
        dropChances.put(16,00.05*16+2.50);
        dropChances.put(17,00.05*17+2.50);
        dropChances.put(18,00.05*18+2.50);
        dropChances.put(19,00.05*19+2.50);
        dropChances.put(20,00.05*20+4.00);
        dropChances.put(21,00.05*21+4.00);
        dropChances.put(22,00.05*22+4.00);
        dropChances.put(23,00.05*23+4.00);
        dropChances.put(24,00.05*24+4.00);
        dropChances.put(25,00.05*25+4.50);
        dropChances.put(26,00.05*26+4.50);
        dropChances.put(27,00.05*27+4.50);
        dropChances.put(28,00.05*28+4.50);
        dropChances.put(29,00.05*29+4.50);
        dropChances.put(30,00.05*30+6.00);
        dropChances.put(31,00.05*31+6.00);
        dropChances.put(32,00.05*32+6.00);
        dropChances.put(33,00.05*33+6.00);
        dropChances.put(34,00.05*34+6.00);
        dropChances.put(35,00.05*35+6.00);
        dropChances.put(36,00.05*36+6.00);
        dropChances.put(37,00.05*37+6.00);
        dropChances.put(38,00.05*38+6.00);
        dropChances.put(39,00.05*39+6.00);
        dropChances.put(40,00.05*40+6.00);
        dropChances.put(41,00.05*41+6.00);
        dropChances.put(42,00.05*42+6.00);
        dropChances.put(43,00.05*43+6.00);
        dropChances.put(44,00.05*44+6.00);
        dropChances.put(45,00.05*45+7.50);
        dropChances.put(46,00.05*46+7.50);
        dropChances.put(47,00.05*47+7.50);
        dropChances.put(48,00.05*48+7.50);
        dropChances.put(49,00.05*49+7.50);
        dropChances.put(50,00.05*50+10.00);
        return dropChances;
    }


    public HashMap<Integer, Long> getMinerAbilityCooldown() {
        minerAbilityCooldown.put(5, 60000L);
        minerAbilityCooldown.put(6, 60000L);
        minerAbilityCooldown.put(7, 60000L);
        minerAbilityCooldown.put(8, 60000L);
        minerAbilityCooldown.put(9, 60000L);
        minerAbilityCooldown.put(10,50000L);
        minerAbilityCooldown.put(11,50000L);
        minerAbilityCooldown.put(12,50000L);
        minerAbilityCooldown.put(13,50000L);
        minerAbilityCooldown.put(14,50000L);
        minerAbilityCooldown.put(15,40000L);
        minerAbilityCooldown.put(16,40000L);
        minerAbilityCooldown.put(17,40000L);
        minerAbilityCooldown.put(18,40000L);
        minerAbilityCooldown.put(19,40000L);
        minerAbilityCooldown.put(20,35000L);
        minerAbilityCooldown.put(21,35000L);
        minerAbilityCooldown.put(22,35000L);
        minerAbilityCooldown.put(23,35000L);
        minerAbilityCooldown.put(24,35000L);
        minerAbilityCooldown.put(25,25000L);
        minerAbilityCooldown.put(26,25000L);
        minerAbilityCooldown.put(27,25000L);
        minerAbilityCooldown.put(28,25000L);
        minerAbilityCooldown.put(29,25000L);
        minerAbilityCooldown.put(30,20000L);
        minerAbilityCooldown.put(31,20000L);
        minerAbilityCooldown.put(32,20000L);
        minerAbilityCooldown.put(33,20000L);
        minerAbilityCooldown.put(34,20000L);
        minerAbilityCooldown.put(35,20000L);
        minerAbilityCooldown.put(36,20000L);
        minerAbilityCooldown.put(37,20000L);
        minerAbilityCooldown.put(38,20000L);
        minerAbilityCooldown.put(39,20000L);
        minerAbilityCooldown.put(40,15000L);
        minerAbilityCooldown.put(41,15000L);
        minerAbilityCooldown.put(42,15000L);
        minerAbilityCooldown.put(43,15000L);
        minerAbilityCooldown.put(44,15000L);
        minerAbilityCooldown.put(45,15000L);
        minerAbilityCooldown.put(46,15000L);
        minerAbilityCooldown.put(47,15000L);
        minerAbilityCooldown.put(48,15000L);
        minerAbilityCooldown.put(49,15000L);
        minerAbilityCooldown.put(50,15000L);



        return minerAbilityCooldown;
    }











    public HashMap<Integer, Double> getJobLevels() {
        jobLevels.put(1,50.00);
        jobLevels.put(2,100.00);
        jobLevels.put(3,200.00);
        jobLevels.put(4,400.00);
        jobLevels.put(5,800.00);
        jobLevels.put(6,1600.00);
        jobLevels.put(7,3200.00);
        jobLevels.put(8,6400.00);
        jobLevels.put(9,12800.00);
        jobLevels.put(10,25600.00);
        jobLevels.put(11,25600.00);
        jobLevels.put(12,25600.00);
        jobLevels.put(13,25600.00);
        jobLevels.put(14,25600.00);
        jobLevels.put(15,25600.00);
        jobLevels.put(16,25600.00);
        jobLevels.put(17,25600.00);
        jobLevels.put(18,25600.00);
        jobLevels.put(19,25600.00);
        jobLevels.put(20,25600.00);
        jobLevels.put(21,25600.00);
        jobLevels.put(22,25600.00);
        jobLevels.put(23,25600.00);
        jobLevels.put(24,25600.00);
        jobLevels.put(25,25600.00);
        jobLevels.put(26,25600.00);
        jobLevels.put(27,25600.00);
        jobLevels.put(28,25600.00);
        jobLevels.put(29,25600.00);
        jobLevels.put(30,25600.00);
        jobLevels.put(31,25600.00);
        jobLevels.put(32,25600.00);
        jobLevels.put(33,25600.00);
        jobLevels.put(34,25600.00);
        jobLevels.put(35,25600.00);
        jobLevels.put(36,25600.00);
        jobLevels.put(37,25600.00);
        jobLevels.put(38,25600.00);
        jobLevels.put(39,25600.00);
        jobLevels.put(40,25600.00);
        jobLevels.put(41,25600.00);
        jobLevels.put(42,25600.00);
        jobLevels.put(43,25600.00);
        jobLevels.put(44,25600.00);
        jobLevels.put(45,25600.00);
        jobLevels.put(46,25600.00);
        jobLevels.put(47,25600.00);
        jobLevels.put(48,25600.00);
        jobLevels.put(49,25600.00);
        jobLevels.put(50,25600.00);

        return jobLevels;
    }
    public boolean isLevelUp(Integer jobLevel, double jobXP){
        HashMap<Integer, Double> levels = getJobLevels();
        if (jobXP == levels.get(jobLevel) || jobXP >= levels.get(jobLevel)){
            return true;
        }
        return  false;
    }







}
