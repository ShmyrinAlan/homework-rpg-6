package com.narxoz.rpg.tournament;

import com.narxoz.rpg.arena.ArenaFighter;
import com.narxoz.rpg.arena.ArenaOpponent;
import com.narxoz.rpg.arena.TournamentResult;
import com.narxoz.rpg.chain.ArmorHandler;
import com.narxoz.rpg.chain.BlockHandler;
import com.narxoz.rpg.chain.DefenseHandler;
import com.narxoz.rpg.chain.DodgeHandler;
import com.narxoz.rpg.chain.HpHandler;
import com.narxoz.rpg.command.ActionQueue;
import com.narxoz.rpg.command.AttackCommand;
import com.narxoz.rpg.command.DefendCommand;
import com.narxoz.rpg.command.HealCommand;
import java.util.Random;

public class TournamentEngine {
    private final ArenaFighter hero;
    private final ArenaOpponent opponent;
    private Random random = new Random(1L);

    public TournamentEngine(ArenaFighter hero, ArenaOpponent opponent) {
        this.hero = hero;
        this.opponent = opponent;
    }

    public TournamentEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public TournamentResult runTournament() {
        TournamentResult result = new TournamentResult();
        int round = 0;
        final int maxRounds = 20;

        // TODO: Build the defense chain using fluent setNext():
        //   DodgeHandler -> BlockHandler -> ArmorHandler -> HpHandler
        // Hint: use hero stats for each handler's parameters.
        //   new DodgeHandler(hero.getDodgeChance(), <seed>)
        //   new BlockHandler(hero.getBlockRating() / 100.0)   <-- note the int-to-double conversion
        //   new ArmorHandler(hero.getArmorValue())
        //   new HpHandler()
        // Chain them: dodge.setNext(block).setNext(armor).setNext(hp)

        DefenseHandler chain = new DodgeHandler(hero.getDodgeChance(), 55L)
                        .setNext(new BlockHandler(hero.getBlockRating()/100d))
                        .setNext(new ArmorHandler(hero.getArmorValue()))
                        .setNext(new HpHandler());
        // TODO: Create an ActionQueue (the invoker).
        ActionQueue invoker = new ActionQueue();

        // TODO: Simulate rounds until hero or opponent is defeated (or maxRounds is reached).
        // Each round should:
        //   1) Increment round counter.
        //   2) Enqueue hero actions: AttackCommand, HealCommand, DefendCommand.
        //      Use hero.getAttackPower() for AttackCommand, a fixed heal amount for HealCommand,
        //      and a small dodge boost for DefendCommand.
        //   3) Print the queued commands using actionQueue.getCommandDescriptions().
        //   4) Call actionQueue.executeAll() to run all hero actions.
        //   5) If the opponent is still alive: have the opponent attack the hero.
        //      Route the attack through the defense chain: defenseChain.handle(opponent.getAttackPower(), hero)
        //      Do NOT call hero.takeDamage() directly here.
        //   6) Log round results (e.g. "[Round N] Opponent HP: X | Hero HP: Y").
        //   7) Add the log line to result.addLine(...).
        for (int i = 0; i < maxRounds && hero.isAlive(); i++) {
            round = i;

            invoker.enqueue(new AttackCommand(opponent, hero.getAttackPower()));
            invoker.enqueue(new HealCommand(hero, 9));
            invoker.enqueue(new DefendCommand(hero, 0.12d));

            System.out.println("Queued actins: ");
            for(String cmd : invoker.getCommandDescriptions())
                System.out.println("\t" + cmd);
            invoker.executeAll();

            if(!opponent.isAlive()) {
                result.addLine(String.format("[Round %d] %s's attack was very effective, so %s did not have able to handle it",
                        round,
                        hero.getName(),
                        opponent.getName()));
                break;
            }

            chain.handle(opponent.getAttackPower(), hero);

            result.addLine(String.format("[Round %d] %s HP: %d | %s HP: %d",
                    round,
                    opponent.getName(), opponent.getHealth(),
                    hero.getName(), hero.getHealth()
            ));
        }
        // TODO: After the loop, determine the winner.
        //   result.setWinner(hero.isAlive() ? hero.getName() : opponent.getName());
        result.setWinner(hero.isAlive() ? hero.getName() : opponent.getName());
        result.setRounds(round);
        return result;
    }
}
