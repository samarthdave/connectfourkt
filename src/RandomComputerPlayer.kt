import kotlin.random.Random

class RandomComputerPlayer : Player() {
    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        val potentialMoves = board.availableLocations()
        return potentialMoves[Random.nextInt(potentialMoves.size)]
    }
}