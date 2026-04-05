package com.narxoz.rpg.chain;

import com.narxoz.rpg.arena.ArenaFighter;

public class HpHandler extends DefenseHandler {

    @Override
    public void handle(int incomingDamage, ArenaFighter target) {
        // TODO: Apply incomingDamage to the target using target.takeDamage(int).
        // TODO: Print a message showing how much damage landed.
        // Note: This is the terminal handler — never call passToNext here.
        //       If damage reaches this point, it hits the fighter's HP, no matter what.
        target.takeDamage(incomingDamage);
        System.out.println(target.getName() + " landed " + incomingDamage + " damage");
    }
}
