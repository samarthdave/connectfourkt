import kotlin.math.max
import kotlin.random.Random
import kotlin.system.exitProcess

class ComputerPlayer : Player() {
    private val PLAYER_ONE_WIN_SCORE = 1000
    private val PLAYER_TWO_LOSE_SCORE = -1000
    private val AGING_PENALTY = 3

    private val TRUE_COLOR = ConnectFourBoardPiece.RED
    private val FALSE_COLOR = ConnectFourBoardPiece.YELLOW

    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {

        val colAndScore = minimax(board, 1, false)
        return colAndScore.first

    }

    // TODO implement this
    // game is ongoing, block opponent wins, count 3 in a rows
    private fun runningGameEval(board: ConnectFourBoard): Int {
        // block a win from opponent
        return 1
    }

    private fun naiveHeuristicEval(board: ConnectFourBoard, depth: Int): Int {
        when (board.status()) {
            ConnectFourGameStatus.PLAYING -> {
                return runningGameEval(board)
            }
            ConnectFourGameStatus.TIE -> {
                return 0
            }
            ConnectFourGameStatus.PLAYER_ONE_WIN -> {
                return PLAYER_TWO_LOSE_SCORE + depth * AGING_PENALTY
            }
            ConnectFourGameStatus.PLAYER_TWO_WIN -> {
                return PLAYER_ONE_WIN_SCORE + depth * AGING_PENALTY
            }
        }
    }

    private fun minimax(node: ConnectFourBoard, depth: Int, maximizingPlayer: Boolean): Pair<Int, Int> {
        if (depth == 0 || node.status() != ConnectFourGameStatus.PLAYING) {
            return Pair(-1, naiveHeuristicEval(node, depth))
        }
        if (maximizingPlayer) {
            var valueNBSP = Int.MIN_VALUE
            var chosenCol = -1
            for (col in node.availableLocations()) {
                val res = node.dropPiece(col, TRUE_COLOR)
                val score = minimax(node, depth - 1, false).second
                if (score > valueNBSP) {
                    chosenCol = col
                    valueNBSP = score
                }
                node.undoDrop(res.second!!)
            }
            return Pair(chosenCol, valueNBSP)
        } else {
            var valueNBSP = Int.MAX_VALUE
            var chosenCol = -1
            for (col in node.availableLocations()) {
                val res = node.dropPiece(col, FALSE_COLOR)
                val score = minimax(node, depth - 1, true).second
                if (score < valueNBSP) {
                    valueNBSP = score
                    chosenCol = col
                }
                node.undoDrop(res.second!!)
            }
            return Pair(chosenCol, valueNBSP)
        }
    }

//function minimax(node, depth, maximizingPlayer) is
//    if depth = 0 or node is a terminal node then
//        return the heuristic value of node
//    if maximizingPlayer then
//        value := −∞
//        for each child of node do
//            value := max(value, minimax(child, depth − 1, FALSE))
//        return value
//    else (* minimizing player *)
//        value := +∞
//        for each child of node do
//            value := min(value, minimax(child, depth − 1, TRUE))
//        return value


}
