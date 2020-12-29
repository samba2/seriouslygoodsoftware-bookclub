package org.samba.seriouslygoodsw;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class WaterContainerTest {

    @Test
    public void emptyContainer() {
        var a = new WaterContainer();
        assertEquals(0, a.getAmount());
    }


    @Test
    public void initialFill() {
        var a = new WaterContainer();
        var b = new WaterContainer();
        var c = new WaterContainer();
        var d = new WaterContainer();

        a.addWater(12);
        d.addWater(8);
        a.connectTo(b);

        assertEquals(6, a.getAmount());
        assertEquals(6, b.getAmount());
        assertEquals(0, c.getAmount());
        assertEquals(8, d.getAmount());
    }


}