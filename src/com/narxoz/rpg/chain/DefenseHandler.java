package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public abstract class DefenseHandler {
    private DefenseHandler next;

    public DefenseHandler setNext(DefenseHandler next) {
        // Provided: fluent setter so chains can be built like:
        //   dodge.setNext(block).setNext(armor).setNext(hp)
        // setNext returns the argument so the chain reads left-to-right.
        this.next = next;
        return next;
    }

    protected DefenseHandler getNext() {
        return next;
    }

    protected void passToNext(int damage, ArenaFighter target) {
        // TODO: If damage is still greater than 0 and a next handler exists, forward it.
        // TODO: What should happen when there is no next handler but damage is nonzero?
        if (next != null) {
            if(damage == 0) return;
            next.handle(damage, target);
        } else if (damage > 0) throw new IllegalStateException("At the end of handler chain must be the HpHandler");

    }

    public abstract void handle(int incomingDamage, ArenaFighter target);
}
