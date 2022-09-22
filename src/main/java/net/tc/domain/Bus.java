package net.tc.domain;

public class Bus extends BaseVehicle {

    private static final int TOLL_FREE_WEIGHT = 114;

    private int weight;

    public Bus(int weight) {
        super();
        this.weight = weight;
    }

    public Bus(VehicleCategory vehicleCategory, int weight) {
        super(vehicleCategory);
        this.weight = weight;
    }
    @Override
    public VehicleType getVehicleType() {
        return VehicleType.Bus;
    }

    @Override
    public boolean isTollFreeVehicle() {
        return super.isTollFreeVehicle() || weight > TOLL_FREE_WEIGHT;
    }
}
