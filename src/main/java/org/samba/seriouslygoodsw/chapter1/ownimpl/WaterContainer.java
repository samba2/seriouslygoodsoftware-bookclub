package org.samba.seriouslygoodsw.chapter1.ownimpl;

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

    /**
     * Recursively find all neighbors of a container.
     *
     * iteration ends when:
     * - a container has no other neighbor except the calling one
     * - a loop of connected containers is detected
     * In both cases an empty Set is returned.
     *
     * param "originalCaller": to detect a container loop Figure 1.4 left
     * param "caller": to prevent discovering back to the calling node
     */
    Set<WaterContainer> discoverNeighbors(WaterContainer originalCaller, WaterContainer caller) {
        if (isContainerLoop(originalCaller, caller)) return Collections.emptySet();

        var foundNeighbors = new HashSet<>(neighbors);

        // continue finding neighbors neighbors excluding the caller (prevent infinite loop)
        for (WaterContainer c : neighbors) {
            if (c.equals(caller)) continue;
            foundNeighbors.addAll(c.discoverNeighbors(originalCaller, this));
        }

        return foundNeighbors;
    }

    // it is not the first invocation and
    // the current container is the originally calling one, we're at the end of this leaf
    private boolean isContainerLoop(WaterContainer originalCaller, WaterContainer caller) {
        return ! caller.equals(originalCaller) && this.equals(originalCaller);
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
