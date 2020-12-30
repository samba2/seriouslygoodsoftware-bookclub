package org.samba.seriouslygoodsw;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WaterContainerTest {

    @Test
    public void emptyContainer() {
        var a = new WaterContainer("A");
        assertEquals(0, a.getAmount());
    }


    @Test
    public void singleContainerHasNoNeighbors() {
        var a = new WaterContainer("A");
        assertEquals(Collections.emptySet(), a.discoverNeighbors(a, a));
    }

    @Test
    public void directNeighbor() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        a.connectTo(b);
        assertEquals(Set.of(a, b), a.discoverNeighbors(a, a));
        assertEquals(Set.of(a, b), b.discoverNeighbors(b, b));
    }

    @Test
    public void twoNeighbors() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        b.connectTo(a);
        b.connectTo(c);

        assertEquals(Set.of(a, b, c), b.discoverNeighbors(b, b));
        assertEquals(Set.of(a, b, c), a.discoverNeighbors(a, a));
        assertEquals(Set.of(a, b, c), c.discoverNeighbors(c, c));
    }

    @Test
    public void indirectNeighbor() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        a.connectTo(b);
        b.connectTo(c);

        assertEquals(Set.of(a, b, c), b.discoverNeighbors(b, b));
        assertEquals(Set.of(a, b, c), a.discoverNeighbors(a, a));
        assertEquals(Set.of(a, b, c), c.discoverNeighbors(c, c));
    }

    @Test
    public void cutLoop() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        a.connectTo(b);
        b.connectTo(c);
        c.connectTo(a);

        assertEquals(Set.of(a, b, c), b.discoverNeighbors(b, b));
        assertEquals(Set.of(a, b, c), a.discoverNeighbors(a, a));
        assertEquals(Set.of(a, b, c), c.discoverNeighbors(c, c));
    }

    @Test
    public void initialFill() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        var d = new WaterContainer("D");

        a.addWater(12);
        d.addWater(8);
        a.connectTo(b);

        assertEquals(6, a.getAmount());
        assertEquals(6, b.getAmount());
        assertEquals(0, c.getAmount());
        assertEquals(8, d.getAmount());
    }


    @Test
    public void secondStep() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        var d = new WaterContainer("D");

        a.addWater(12);
        d.addWater(8);
        a.connectTo(b);
        b.connectTo(c);

        assertEquals(4, a.getAmount());
        assertEquals(4, b.getAmount());
        assertEquals(4, c.getAmount());
        assertEquals(8, d.getAmount());
    }

    @Test
    public void thirdStep() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        var c = new WaterContainer("C");
        var d = new WaterContainer("D");

        a.addWater(12);
        d.addWater(8);
        a.connectTo(b);
        b.connectTo(c);
        b.connectTo(d);

        assertEquals(5, a.getAmount());
        assertEquals(5, b.getAmount());
        assertEquals(5, c.getAmount());
        assertEquals(5, d.getAmount());
    }

    @Test
    public void addWaterLater() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");
        a.connectTo(b);
        a.addWater(12);

        assertEquals(6, a.getAmount());
        assertEquals(6, b.getAmount());
    }

    @Test
    public void robustness() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");

        a.addWater(12);
        a.connectTo(b);
        a.connectTo(b);
        b.connectTo(a);

        assertEquals(6, a.getAmount());
        assertEquals(6, b.getAmount());
    }

    @Test
    public void cantConnectToMyself() {
        var a = new WaterContainer("A");
        var b = new WaterContainer("B");

        assertThrows(UnsupportedOperationException.class, () -> a.connectTo(a));
    }



}