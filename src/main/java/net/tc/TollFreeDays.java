package net.tc;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class TollFreeDays {
    private Set<LocalDate> freeDays =  new HashSet<>();
    Logger logger = Logger.getLogger(TollFreeDays.class);

    private static TollFreeDays tollFreeDays;
    public static TollFreeDays create(int year, String holidayListFile){
        if (tollFreeDays == null) {
            tollFreeDays =  new TollFreeDays();
            tollFreeDays.loadFreeDates(year,holidayListFile);
        }
        return tollFreeDays;
    }

    private TollFreeDays(){
        //To avoid object creation without factory method.
    }

    private void loadFreeDates(int year,String holidayListFile)  {
        String line;
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(holidayListFile)))
        {
            while ((line = reader.readLine()) != null) {
                try{
                    String[] dateMonthArr = line.split(":");
                    Integer month = Integer.valueOf(dateMonthArr[0]);
                    if(dateMonthArr.length > 1){
                        Arrays.asList(dateMonthArr[1].split(","))
                                .forEach(d -> {
                                    LocalDate date = LocalDate.of(year, month, Integer.valueOf(d));
                                    LocalDate previousDate = date.minusDays(1);
                                    freeDays.add(date);
                                    freeDays.add(previousDate);
                                });
                    }else if(dateMonthArr.length == 1){
                        LocalDate startOfMonth = LocalDate.of(year, month,1);
                        LocalDate endOfMonth = startOfMonth.plusMonths(1);
                        while(startOfMonth.isBefore(endOfMonth)) {
                            freeDays.add(startOfMonth);
                            startOfMonth = startOfMonth.plusDays(1);
                        }
                    }
                }catch (Exception e){
                    logger.error("Error while reading and parsing file.",e);
                }
            }
        }
        catch (Exception e) {
            logger.error("Error while reading data from file.",e);
        }
    }

    public boolean isFreeDay(LocalDateTime date){
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY)
            return true;

        return freeDays.stream().filter(d -> d.getMonthValue()== date.getMonthValue() && d.getDayOfMonth() == date.getDayOfMonth())
                .findFirst().isPresent();
    }
}
