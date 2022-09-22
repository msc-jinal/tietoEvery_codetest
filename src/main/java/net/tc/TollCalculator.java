package net.tc;

import net.tc.domain.Vehicle;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TollCalculator {
    private TollFreeDays tollFreeDays;

    public TollCalculator(TollFreeDays tollFreeDays){
        this.tollFreeDays = tollFreeDays;
    }
    public int getTollFee(Vehicle vehicle, List<LocalDateTime> tollEntries) {
        LocalDateTime intervalStart = tollEntries.get(0);
        int totalFee = 0;
        int firstTollFee = getSinglePassTollFee(intervalStart, vehicle);
        for (LocalDateTime entryTime : tollEntries) {
            int nextFee = getSinglePassTollFee(entryTime, vehicle);
            long minutes = ChronoUnit.MINUTES.between(intervalStart, entryTime);
            if (minutes <= 60) {
                if (totalFee > 0) totalFee -= firstTollFee;
                if (nextFee >= firstTollFee) firstTollFee = nextFee;
                totalFee += firstTollFee;
            } else {
                totalFee += nextFee;
            }

            if (totalFee > 60){
                totalFee = 60;
                return totalFee;
            }
        }
        return totalFee;
    }

    private int getSinglePassTollFee(LocalDateTime date, Vehicle vehicle) {
        if (tollFreeDays.isFreeDay(date) || vehicle.isTollFreeVehicle()) return 0;

        int timeInMinutes = (date.getHour() * 60)+date.getMinute();
        if (timeInMinutes >= (6*60) && timeInMinutes <= (6*60)+29) return 9;
        else if (timeInMinutes >= (6*60)+30 && timeInMinutes <= (6*60)+59) return 16;
        else if (timeInMinutes >= (7*60) && timeInMinutes <= (7*60)+59) return 22;
        else if (timeInMinutes >= (8*60) && timeInMinutes <= (8*60)+29) return 16;
        else if (timeInMinutes >= (8*60)+30 && timeInMinutes <= (14*60)+59) return 9;
        else if (timeInMinutes >= (15*60) && timeInMinutes <= (15*60)+29) return 16;
        else if (timeInMinutes >= (15*60)+30 && timeInMinutes <= (16*60)+59) return 22;
        else if (timeInMinutes >= (17*60) && timeInMinutes <= (17*60)+59) return 16;
        else if (timeInMinutes >= (18*60) && timeInMinutes <= (18*60)+29) return 9;
        else return 0;
    }

}



