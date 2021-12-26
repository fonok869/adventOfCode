package com.fmolnar.code.year2021.day21;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Day21Challenge02Beauty {

    private Map<Game, BigDecimal> games;
    private final Map<Integer, Integer> diceValues = Map.of(
            3, 1,
            4, 3,
            5, 6,
            6, 7,
            7, 6,
            8, 3,
            9, 1
    );

    public Day21Challenge02Beauty(int startingPositionPlayer1, int startingPositionPlayer2) {
        var game = Game.builder()
                .position1(startingPositionPlayer1)
                .points1(0)
                .position2(startingPositionPlayer2)
                .points2(0)
                .round(1)
                .build();
        this.games = new HashMap<>(Map.of(game, BigDecimal.valueOf(1L)));
    }

    public void play() {
        while (games.keySet().stream().anyMatch(game -> !game.isFinished())) {
            var newGames = new HashMap<Game, BigDecimal>();
            for (Game game : games.keySet()) {
                playGame(game, newGames);
            }
            games = newGames;
        }
    }

    private void playGame(Game game, Map<Game, BigDecimal> newGames) {
        var currentCount = games.get(game);

        if (game.isFinished()) {
            newGames.merge(game, currentCount, BigDecimal::add);
            return;
        }

        diceValues.forEach((dice, count) -> newGames.merge(game.move(dice), currentCount.multiply(BigDecimal.valueOf(count)), BigDecimal::add));
    }

    public long wonGamesForPlayer1() {
        System.out.println("games size: " + games.size());
        return games.entrySet().stream().filter(entry -> entry.getKey().points1() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }

    public long wonGamesForPlayer2() {
        return games.entrySet().stream().filter(entry -> entry.getKey().points2() >= 21).mapToLong(entry -> entry.getValue().longValue()).sum();
    }

    public static record Game(int points1, int position1, int points2, int position2, int round) {

        @Builder
        public Game {
        }

        public boolean isFinished() {
            return points1 >= 21 || points2 >= 21;
        }

        public Game move(int dice) {
            if (round % 2 == 1) {
                var newPosition = (position1() + dice - 1) % 10 + 1;
                var newPoints = points1() + newPosition;
                return new Game(newPoints, newPosition, points2(), position2(), round + 1);
            } else {
                var newPosition = (position2() + dice - 1) % 10 + 1;
                var newPoints = points2() + newPosition;
                return new Game(points1(), position1(), newPoints, newPosition, round + 1);
            }
        }
    }

}
