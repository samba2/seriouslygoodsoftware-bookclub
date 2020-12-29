package org.samba.seriouslygoodsw;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WaterContainer {

    private final Set<WaterContainer> neighbors = new HashSet<>();
    private long amount;
    private final String name;


    public WaterContainer(String name) {
        this.name = name;
    }

    public void addWater(long newWater) {
        this.amount += newWater;
        distributeWater();
    }

    public void connectTo(WaterContainer neighbour) {
        if (neighbour.equals(this))
            throw new UnsupportedOperationException("Can't connect to myself");

        this.addNewNeighbor(neighbour);
        neighbour.addNewNeighbor(this);

        distributeWater();
    }

    void addNewNeighbor(WaterContainer neighbour) {
        this.neighbors.add(neighbour);
    }

    private void distributeWater() {
        var allConnectedContainers = discoverNeighbors(this, this);
        allConnectedContainers.add(this);

        var totalSum = allConnectedContainers.stream()
                .map(WaterContainer::getAmount)
                .mapToLong(Long::longValue)
                .sum();

        long newAmount = totalSum / allConnectedContainers.size();

        allConnectedContainers.forEach(waterContainer -> waterContainer.setAmount(newAmount));
    }

    // recursively find all neighbors of a container.
    // param "originalCaller": to detect a loop
    // param "caller": to prevent discovering back to the calling node
    Set<WaterContainer> discoverNeighbors(WaterContainer originalCaller, WaterContainer caller) {
        // it is not the first invocation and the current container is the originally calling one, we're at the end of this leaf
        if (! caller.equals(originalCaller) && this.equals(originalCaller)) return Collections.emptySet();

        // add neighbors except caller and original caller
        var foundNeighbors = new HashSet<>(neighbors);
        foundNeighbors.remove(caller);
        foundNeighbors.remove(originalCaller);

        // continue finding neighbors neighbors excluding the caller (prevent infinite loop)
        for (WaterContainer c : neighbors) {
            if (c.equals(caller)) continue;
            foundNeighbors.addAll(c.discoverNeighbors(originalCaller, this));
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
