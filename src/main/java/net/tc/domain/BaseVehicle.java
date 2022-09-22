package net.tc.domain;

abstract public class BaseVehicle implements Vehicle {

    private VehicleCategory vehicleCategory;

    public BaseVehicle() {
        this.vehicleCategory = VehicleCategory.Swedish;
    }

    public BaseVehicle(VehicleCategory vehicleCategory) {
        this.vehicleCategory = vehicleCategory;
    }

    @Override
    public boolean isTollFreeVehicle() {
        return VehicleType.MotorCycle.equals(this.getVehicleType()) ||
                VehicleCategory.Diplomat.equals(vehicleCategory) ||
                VehicleCategory.Emergency.equals(vehicleCategory) ||
                VehicleCategory.Military.equals(vehicleCategory);
    }

}
