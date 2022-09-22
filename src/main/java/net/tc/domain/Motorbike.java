package net.tc.domain;

public class Motorbike extends BaseVehicle {

    public Motorbike() {
        super();
    }

    public Motorbike(VehicleCategory vehicleCategory) {
        super(vehicleCategory);
    }

    @Override
    public VehicleType getVehicleType() {
        return VehicleType.MotorBike;
    }
}
