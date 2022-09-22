package net.tc.domain;

public class Car extends BaseVehicle {

    public Car() {
        super();
    }

    public Car(VehicleCategory vehicleCategory) {
        super(vehicleCategory);
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.Car;
    }

}
