import kotlin.math.max
import kotlin.random.Random
import kotlin.system.exitProcess

class ComputerPlayer : Player() {
    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {

        val colAndScore = minimax(board, 1, false)
        return colAndScore.first

    }

    private fun naiveHeuristicEval(board: ConnectFourBoard, depth: Int): Int {
        when (board.status()) {
            ConnectFourGameStatus.PLAYING -> {
                return 0
            }
            ConnectFourGameStatus.TIE -> {
                return 0
            }
            ConnectFourGameStatus.PLAYER_ONE_WIN -> {
                return LOSE_SCORE + depth * AGING_PENALTY
            }
            ConnectFourGameStatus.PLAYER_TWO_WIN -> {
                return WIN_SCORE + depth * AGING_PENALTY
            }
        }
    }

    private val WIN_SCORE = 1000
    private val LOSE_SCORE = -1000
    private val AGING_PENALTY = 3

    private val TRUE_COLOR = ConnectFourBoardPiece.RED
    private val FALSE_COLOR = ConnectFourBoardPiece.YELLOW

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
