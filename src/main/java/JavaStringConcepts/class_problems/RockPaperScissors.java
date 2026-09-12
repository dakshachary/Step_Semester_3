import java.util.Random;

public class RockPaperScissors {

    static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equals("Rock") && computerMove.equals("Scissors"))
                || (playerMove.equals("Paper") && computerMove.equals("Rock"))
                || (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }

    public static void main(String[] args) {
        String[] moves = {"Rock", "Paper", "Scissors"};
        String[] playerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        int rounds = playerMoves.length;
        String[] computerMoves = new String[rounds];
        String[] results = new String[rounds];
        Random random = new Random();

        int wins = 0;
        int losses = 0;
        int draws = 0;

        for (int i = 0; i < rounds; i++) {
            computerMoves[i] = moves[random.nextInt(moves.length)];
            results[i] = playRound(playerMoves[i], computerMoves[i]);
            if (results[i].equals("Player Wins")) {
                wins++;
            } else if (results[i].equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }
        }

        System.out.println("Round | Player Move | Computer Move | Result");
        for (int i = 0; i < rounds; i++) {
            System.out.println((i + 1) + " | " + playerMoves[i] + " | " + computerMoves[i] + " | " + results[i]);
        }

        double winPercentage = (wins * 100.0) / rounds;
        System.out.println("Wins: " + wins + " | Losses: " + losses + " | Draws: " + draws + " | Win % = " + winPercentage + "%");
    }
}
