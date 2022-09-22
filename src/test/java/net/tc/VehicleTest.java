package net.tc;

import net.tc.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class VehicleTest {

    @Test
    public void test_tollFreeVehicles(){
        Vehicle swedishVehicle = new Car();
        assertFalse(swedishVehicle.isTollFreeVehicle(),"Swedish vehicle should not be toll free.");

        Vehicle diplomateVehicle = new Car(VehicleCategory.Diplomat);
        assertTrue(diplomateVehicle.isTollFreeVehicle(),"Diplomate vehicle should be toll free.");

        Vehicle emergencyVechile = new Car(VehicleCategory.Emergency);
        assertTrue(emergencyVechile.isTollFreeVehicle(),"Emergency vehicle should be toll free.");

        Vehicle militaryVehicle = new Car(VehicleCategory.Military);
        assertTrue(militaryVehicle.isTollFreeVehicle(),"Military vehicle should be toll free.");

        Vehicle foreignVehicle = new Car(VehicleCategory.Foreign);
        assertFalse(foreignVehicle.isTollFreeVehicle(),"Foreign vehicle car should not be toll free.");

        // Buses Test Scenarios
        Vehicle militaryBus_weight150 = new Bus(VehicleCategory.Military,150);
        assertTrue(militaryBus_weight150.isTollFreeVehicle(),"Military vehicle should be toll free.");

        Vehicle militaryBus_weight100 = new Bus(VehicleCategory.Military,100);
        assertTrue(militaryBus_weight100.isTollFreeVehicle(),"Military vehicle should be toll free.");

        Vehicle swedishBus_weight150 = new Bus(150);
        assertTrue(swedishBus_weight150.isTollFreeVehicle(),"Over weight Bus should be toll free.");

        Vehicle swedishBus_weight100 = new Bus(100);
        assertFalse(swedishBus_weight100.isTollFreeVehicle(),"Bus below 114 ton weight should not be toll free.");

        // MotorCycle Test Scenarios
        Vehicle motorCycle = new MotorCycle();
        assertTrue(motorCycle.isTollFreeVehicle(),"MotorCycle should be toll free.");
    }
}
