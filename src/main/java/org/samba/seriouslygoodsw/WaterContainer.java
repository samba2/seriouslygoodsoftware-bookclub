package org.samba.seriouslygoodsw;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WaterContainer {

    private Set<WaterContainer> neighbors = new HashSet<>();
    private long amount;
    private String name;


    public WaterContainer(String name) {
        this.name = name;
    }

    public void addWater(long newWater) {
        this.amount += newWater;
        // TODO distribute also here + test
    }

    public void connectTo(WaterContainer neighbour) {
        this.newNeighbor(neighbour);
        neighbour.newNeighbor(this);

        distributeWater();
    }

    void newNeighbor(WaterContainer neighbour) {
        this.neighbors.add(neighbour);
    }

    private void distributeWater() {
        var allConnectedContainers = getNeighbors(this, this);
        allConnectedContainers.add(this);

        var totalSum = allConnectedContainers.stream()
                .map(waterContainer -> waterContainer.getAmount())
                .mapToLong(Long::longValue)
                .sum();

        long newAmount = totalSum / allConnectedContainers.size();

        allConnectedContainers.forEach(waterContainer -> waterContainer.setAmount(newAmount));
    }

    Set<WaterContainer> getNeighbors(WaterContainer originalCaller, WaterContainer caller) {
        if (! caller.equals(originalCaller) && this.equals(originalCaller)) return Collections.emptySet();

        var foundNeighbors = new HashSet<WaterContainer>();
        // add neighbors except caller and original caller
        foundNeighbors.addAll(neighbors);
        foundNeighbors.remove(caller);
        foundNeighbors.remove(originalCaller);

        // continue finding neighbors neighbors excluding the caller (infinite loop)
        for (WaterContainer c : neighbors) {
            if (c.equals(caller)) continue;
            foundNeighbors.addAll(c.getNeighbors(originalCaller, this));
        }

        return foundNeighbors;
    }

    protected void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "WaterContainer{" +
                "amount=" + amount +
                ", name='" + name + '\'' +
                '}';
    }
}
