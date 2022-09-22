package net.tc;


import net.tc.domain.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TollCalculatorTest {

    static TollCalculator tollCalculator;

    @BeforeAll
    public static void setup(){
        String holidayListFileName = "holidays_2022.txt";
        ClassLoader classLoader = TollFreeDaysTest.class.getClassLoader();
        File file = new File(classLoader.getResource(holidayListFileName).getFile());
        String holidayListFile = file.getAbsolutePath();
        TollFreeDays tollFreeDays = TollFreeDays.create(2022,holidayListFile);

        tollCalculator = new TollCalculator(tollFreeDays);
    }

    @Test
    public void test_getTollFee_HighestToll_Within_Hour(){
        Vehicle swedishCar = new Car();
        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,6,25,12));
        tollEntries.add(LocalDateTime.of(2022,4,11,7,10,12));
        tollEntries.add(LocalDateTime.of(2022,4,11,7,20,15));
        int actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(22,actualToll,"Calculated toll fee is wrong");
    }


    @Test
    public void test_getTollFee_Moth_Than_Hour(){
        Vehicle swedishCar = new Car();
        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,6,25,12));
        tollEntries.add(LocalDateTime.of(2022,4,11,7,10,12));
        tollEntries.add(LocalDateTime.of(2022,4,11,9,20,15));
        int actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(31,actualToll,"Calculated toll fee is wrong");
    }

    @Test
    public void test_getTollFee_MaxToll_forDay(){
        Vehicle swedishCar = new Car();

        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,6,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,7,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,8,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,9,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,10,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,11,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,12,00,00));
        tollEntries.add(LocalDateTime.of(2022,4,11,13,00,00));
        int actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);

        assertEquals(60,actualToll,"Maximum toll for the day should be 60");
    }

    @Test
    public void test_getTollFee_TollFreeTime(){
        Vehicle swedishCar = new Car();

        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,18,29,59));

        int actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(9,actualToll,"Toll fee should be 9 at time 18:29:50");

        tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,18,30,00));
        actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(0,actualToll,"Toll fee should be 0 from time 18:30:00");

        tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,5,59,0));
        actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(0,actualToll,"Toll fee should be 0 till time 5:59:00");

        tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,6,0,0));
        actualToll = tollCalculator.getTollFee(swedishCar,tollEntries);
        assertEquals(9,actualToll,"Toll fee should be 9 at time 6:00:00");

    }

    @Test
    public void test_getTollFee_TollFreeVehicle(){
        Vehicle diplomatVehicle = new Car(VehicleCategory.Diplomat);
        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,4,11,9,30,12));

        int toll = tollCalculator.getTollFee(diplomatVehicle,tollEntries);
        assertEquals(0,toll,"Toll fee for Diplomat vehicle should be 0");
    }

    @Test
    public void test_getTollFee_OnFreeDays(){
        Vehicle car = new Car();
        List<LocalDateTime> tollEntries = new ArrayList<>();
        tollEntries.add(LocalDateTime.of(2022,12,31,9,30,12));

        int toll = tollCalculator.getTollFee(car,tollEntries);
        assertEquals(0,toll,"Toll fee should be 0 on Holidays");
    }
}
