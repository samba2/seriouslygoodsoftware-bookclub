package org.samba.seriouslygoodsw.chapter1.exercise3;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;


public class UserTest {

    @Test
    public void noFriends() {
        var ronny = new User("Ronny");
        var petra = new User("Petra");

        assertThat(ronny.isDirectFriendOf(petra)).isFalse();
        assertThat(petra.isDirectFriendOf(ronny)).isFalse();

        assertThat(ronny.isIndirectFriendOf(petra)).isFalse();
        assertThat(petra.isIndirectFriendOf(ronny)).isFalse();
    }

    @Test
    public void directFriends() {
        var ronny = new User("Ronny");
        var petra = new User("Petra");

        ronny.befriend(petra);

        assertThat(ronny.isDirectFriendOf(petra)).isTrue();
        assertThat(petra.isDirectFriendOf(ronny)).isTrue();
    }

    @Test
    public void indirectFriends() {
        var ronny = new User("Ronny");
        var petra = new User("Petra");
        var horst = new User("Horst");

        ronny.befriend(petra);
        petra.befriend(horst);

        assertThat(ronny.isIndirectFriendOf(horst)).isTrue();
        assertThat(horst.isIndirectFriendOf(ronny)).isTrue();
    }

    @Test
    public void indirectFriends2() {
        var ronny = new User("Ronny");
        var petra = new User("Petra");
        var horst = new User("Horst");
        var ursel = new User("Ursel");

        ronny.befriend(petra);
        petra.befriend(horst);
        horst.befriend(ursel);

        assertThat(ronny.isIndirectFriendOf(ursel)).isTrue();
        assertThat(ursel.isIndirectFriendOf(ronny)).isTrue();
    }

    @Test
    public void indirectFriends3() {
        var ronny = new User("Ronny");
        var petra = new User("Petra");
        var horst = new User("Horst");
        var ursel = new User("Ursel");

        ronny.befriend(petra);
        petra.befriend(new User("Klaus"));
        petra.befriend(new User("Wolfram"));
        petra.befriend(new User("Margit"));
        petra.befriend(new User("Semanta"));
        petra.befriend(horst);
        horst.befriend(new User("Alex"));
        horst.befriend(new User("John"));
        horst.befriend(new User("Mark"));
        horst.befriend(ursel);

        assertThat(ronny.isIndirectFriendOf(ursel)).isTrue();
        assertThat(ursel.isIndirectFriendOf(ronny)).isTrue();
    }
}
