import kotlin.math.max
import kotlin.math.min

class ComputerPlayer : Player() {
    private val PLAYER_ONE_WIN_SCORE = 1000
    private val PLAYER_TWO_WIN_SCORE = -1000
    private val AGING_PENALTY = 10

    private val TRUE_COLOR = ConnectFourBoardPiece.RED
    private val FALSE_COLOR = ConnectFourBoardPiece.YELLOW

    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        val colAndScore = minimax(board, 5, false, Int.MIN_VALUE, Int.MAX_VALUE)
        return colAndScore.first
    }

    private fun runningGameEval(board: ConnectFourBoard): Int {
        return board.evaluateBoard()
    }

    private fun staticEvaluationOfPosition(board: ConnectFourBoard, depth: Int): Int {
        return when (board.status()) {
            ConnectFourGameStatus.PLAYING -> {
                runningGameEval(board)
            }

            ConnectFourGameStatus.TIE -> {
                0
            }

            ConnectFourGameStatus.PLAYER_ONE_WIN -> {
                PLAYER_ONE_WIN_SCORE + depth * AGING_PENALTY
            }

            ConnectFourGameStatus.PLAYER_TWO_WIN -> {
                PLAYER_TWO_WIN_SCORE - (depth * AGING_PENALTY)
            }
        }
    }

    private fun minimax(node: ConnectFourBoard, depth: Int, maximizingPlayer: Boolean, paramAlpha: Int, paramBeta: Int): Pair<Int, Int> {
        var alpha = paramAlpha
        var beta = paramBeta
        if (depth == 0 || node.status() != ConnectFourGameStatus.PLAYING) {
            return Pair(-1, staticEvaluationOfPosition(node, depth))
        }
        if (maximizingPlayer) {
            var maxEval = Int.MIN_VALUE
            var chosenCol = -1
            for (col in node.availableLocations()) {
                val res = node.dropPiece(col, TRUE_COLOR)
                val eval = minimax(node, depth - 1, false, alpha, beta).second
                if (eval > maxEval) {
                    maxEval = eval
                    chosenCol = col
                }
                if (beta < alpha) {
                    node.undoDrop(res.second!!)
                    break
                }
                alpha = max(alpha, eval)
                node.undoDrop(res.second!!)
            }
            return Pair(chosenCol, maxEval)
        } else {
            var minEval = Int.MAX_VALUE
            var chosenCol = -1
            for (col in node.availableLocations()) {
                val res = node.dropPiece(col, FALSE_COLOR)
                val eval = minimax(node, depth - 1, true, alpha, beta).second
                if (eval < minEval) {
                    minEval = eval
                    chosenCol = col
                }
                if (beta < alpha) {
                    node.undoDrop(res.second!!)
                    break
                }
                beta = min(beta, eval)
                node.undoDrop(res.second!!)
            }
            return Pair(chosenCol, minEval)
        }
    }
}
