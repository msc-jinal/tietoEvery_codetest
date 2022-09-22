package net.tc;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TollFreeDaysTest {

    static TollFreeDays tollFreeDays;
    @BeforeAll
    public static void setup(){
        String holidayListFileName = "holidays_2022.txt";
        ClassLoader classLoader = TollFreeDaysTest.class.getClassLoader();
        File file = new File(classLoader.getResource(holidayListFileName).getFile());
        String holidayListFile = file.getAbsolutePath();
        tollFreeDays = TollFreeDays.create(2022,holidayListFile);
    }

    @Test
    public void test_FreeDate(){
        LocalDateTime holiday_15April2022 = LocalDateTime.of(2022,4,15,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(holiday_15April2022),"Holiday should be freeDate");

        LocalDateTime dayBeforeHoliday_13April2022 = LocalDateTime.of(2022,4,13,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(dayBeforeHoliday_13April2022),"Day before Holiday should be freeDate");

        LocalDateTime dayAfterHoliday_27december2022 = LocalDateTime.of(2022,12,27,12,12,12);
        assertFalse(tollFreeDays.isFreeDay(dayAfterHoliday_27december2022),"Day after Holiday is not freeDate");

        LocalDateTime weekendSaturday_17September2022 = LocalDateTime.of(2022,9,17,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(weekendSaturday_17September2022),"Weekend Saturday should be freeDate");

        LocalDateTime weekendSunday_18September2022 = LocalDateTime.of(2022,9,18,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(weekendSunday_18September2022),"Weekend Sunday should be freeDate");

    }
    @Test
    public void test_SummerMonthDays(){
        LocalDateTime summerMonth_firstDay_01July2022 = LocalDateTime.of(2022,7,1,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(summerMonth_firstDay_01July2022),"Summer Month should be freeDate");

        LocalDateTime summerMonth_lastDay_31July2022 = LocalDateTime.of(2022,7,31,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(summerMonth_lastDay_31July2022),"Summer Month should be freeDate");

        LocalDateTime summerMonth_otherDay_11July2022 = LocalDateTime.of(2022,7,11,12,12,12);
        assertTrue(tollFreeDays.isFreeDay(summerMonth_otherDay_11July2022),"Summer Month should be freeDate");

        LocalDateTime nextday_summerMonth_01Aug2022 = LocalDateTime.of(2022,8,1,12,12,12);
        assertFalse(tollFreeDays.isFreeDay(nextday_summerMonth_01Aug2022),"Next day of Summer Month is not freeDate");

        LocalDateTime previousday_summerMonth_30June2022 = LocalDateTime.of(2022,6,30,12,12,12);
        assertFalse(tollFreeDays.isFreeDay(previousday_summerMonth_30June2022),"Previous day of Summer Month is  not  freeDate");

    }
}
