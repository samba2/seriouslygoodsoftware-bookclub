package org.samba.seriouslygoodsw;

import java.util.ArrayList;
import java.util.List;

public class WaterContainer {

    private List<WaterContainer> connectedContainers = new ArrayList<>();
    private long amount;

    public WaterContainer() {
        connectedContainers.add(this);
    }

    public void addWater(long newWater) {
        this.amount += newWater;
    }

    public void connectTo(WaterContainer neighbour) {
        this.connectedContainers.add(neighbour);
        distributeWater();
    }

    private void distributeWater() {
        var totalSum = connectedContainers.stream()
                .map(waterContainer -> waterContainer.getAmount())
                .mapToLong(Long::longValue)
                .sum();

        long newAmount = totalSum / connectedContainers.size();

        connectedContainers.forEach(waterContainer -> waterContainer.setAmount(newAmount));
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }
}
