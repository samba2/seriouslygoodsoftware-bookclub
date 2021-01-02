package org.samba.seriouslygoodsw.chapter1.exercise3;

import java.util.*;


public class User {
    private final String name;

    final Set<User> friends = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public void befriend(User other) {
        friends.add(other);
        other.friends.add(this);
    }

    public boolean isDirectFriendOf(User other) {
        return friends.contains(other);
    }

    // breadth-first search (Breitensuche) iterative
    public boolean isIndirectFriendOf(User other) {
        Queue<User> queue = new LinkedList<>();
        queue.add(this);

        Set<User> alreadyVisited = new HashSet<>();
        alreadyVisited.add(this);

        while (! queue.isEmpty()) {
            User user = queue.remove();
            if (user.equals(other)) return true;
            alreadyVisited.add(user);
            queue.addAll(user.friends);
            queue.removeAll(alreadyVisited);
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
