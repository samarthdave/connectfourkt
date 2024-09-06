// Board: Represents the game board, with methods to place a piece, check for a win, and check for a tie.
// It would keep track of the game state and provide an interface to manipulate it.
// Board.placePiece(int column, int player): Places a piece on the board for the specified player in the given column.
// Board.checkForWin(): Checks if there is a win on the board.
// Board.checkForTie(): Checks if the game is a tie.

class ConnectFourBoard {

    companion object {
        const val DEFAULT_BOARD_HEIGHT: Int = 6
        const val DEFAULT_BOARD_WIDTH: Int = 7

        val colors: Array<ConnectFourBoardPiece> = arrayOf(ConnectFourBoardPiece.RED, ConnectFourBoardPiece.YELLOW)
    }

    private val height: Int = DEFAULT_BOARD_HEIGHT
    private val width: Int = DEFAULT_BOARD_WIDTH // standard board dimensions
    private var grid: Array<CharArray> = Array(this.height) { CharArray(this.width) };

    init {
        this.resetBoard()
    }

    fun resetBoard() {
        for (row: CharArray in grid) {
            for (i: Int in row.indices) {
                row[i] = ConnectFourBoardPiece.EMPTY.color
            }
        }
    }

    fun availableLocations(): MutableList<Int> {
        val results = mutableListOf<Int>()
        for (col in 0..<this.width) {
            if (this.canDropInColumn(col) != null)
                results += col
        }
        return results
    }

    /**
     * This is a simple heuristic to prioritize the center columns
     * hardcoded to handle 7 columns. can generate this list dynamically if width changes
     * but that'd be a non-standard game
     */
    fun availableLocationsCentrallyWeighted(): MutableList<Int> {
        val optimizedColumns = mutableListOf<Int>(3, 2, 4, 1, 5, 0 ,6)
        val results = mutableListOf<Int>()

        for (col in optimizedColumns) {
            if (this.canDropInColumn(col) != null)
                results += col
        }

        return results
    }


    fun dropPiece(col: Int, pieceColor: ConnectFourBoardPiece): Pair<Boolean, Pair<Int, Int>?> {
        val dropLocation: Pair<Int, Int> = canDropInColumn(col) ?: return Pair(false, null)

        this.grid[dropLocation.first][dropLocation.second] = pieceColor.color
        return Pair(true, dropLocation)
    }

    fun undoDrop(location: Pair<Int, Int>): Boolean {
        if (this.grid[location.first][location.second] == ConnectFourBoardPiece.EMPTY.color) {
            return false
        }
        this.grid[location.first][location.second] = ConnectFourBoardPiece.EMPTY.color
        return true
    }

//    private fun canDropInColumn(col: Int): Boolean {
//        return (mask and topMask(col)) == 0L
//    }
//
//    companion object {
//        fun topMask(col: Int): Long {
//            return (1L shl (HEIGHT - 1)) shl col * (HEIGHT + 1)
//        }
//    }

    fun inBounds(col: Int): Boolean {
        return !(col < 0 || col > this.width - 1)
    }

    private fun canDropInColumn(col: Int): Pair<Int, Int>? {
        // TODO: refactor this to return a boolean & use bitboard
        // check if within bounds
        if (!this.inBounds(col)) return null
        // if there's any space, return true
        for (row in (this.height - 1) downTo 0)
            if (this.grid[row][col] == ConnectFourBoardPiece.EMPTY.color)
                return Pair(row, col)
        // else, no space in the column; return false
        return null
    }

    private fun bitwiseWinCheck(position: Long): Boolean {
        // Horizontal check
        var m = position and (position shr 7)
        if ((m and (m shr 14)) != 0L) return true
        //        Diagonal \
        m = position and (position shr 6)
        if ((m and (m shr 12)) != 0L) return true
        //        Diagonal /
        m = position and (position shr 8)
        if ((m and (m shr 16)) != 0L) return true
        //        Vertical |
        m = position and (position shr 1)
        if ((m and (m shr 2)) != 0L) return true

        return false
    }

    private fun getPositionMaskBitmap(targetPlayer: ConnectFourBoardPiece): Pair<Long, Long> {
        val position: StringBuilder = StringBuilder()
        val mask: StringBuilder = StringBuilder()

        for (j: Int in this.width-1 downTo 0) {
            mask.append('0')
            position.append('0')

            for (i in 0..<this.height) {
                val index: Int = if (this.grid[i][j] != ConnectFourBoardPiece.EMPTY.color) 1 else 0
                mask.append(arrayOf('0', '1')[index])
                position.append(arrayOf('0', '1')[if (this.grid[i][j] == targetPlayer.color) 1 else 0])
            }
        }

        return Pair(position.toString().toLong(2), mask.toString().toLong(2))
    }

    private fun checkForWin(): Pair<Boolean, ConnectFourGameStatus> {
        val playerOneBitmaps: Pair<Long, Long> = this.getPositionMaskBitmap(ConnectFourBoardPiece.RED)
        val opponentPosition: Long = playerOneBitmaps.first xor playerOneBitmaps.second

        if (bitwiseWinCheck(playerOneBitmaps.first))
            return Pair(true, ConnectFourGameStatus.PLAYER_ONE_WIN)

        if (bitwiseWinCheck(opponentPosition))
            return Pair(true, ConnectFourGameStatus.PLAYER_TWO_WIN)

        return Pair(false, ConnectFourGameStatus.PLAYING)
    }

    private fun checkForTie(): Boolean {
        // loop thru all squares and check if they're filled
        for (row: CharArray in grid)
            for (cell: Char in row)
                if (cell == ConnectFourBoardPiece.EMPTY.color) return false
        return true
    }

    /**
     * used in testing to quickly fill the board with moves
     */
    fun _hydrateBoardState(columnMoves: String) {
        var index = 0
        for (char in columnMoves) {
            this.dropPiece(char.digitToInt(), ConnectFourGame.playerColors[index % 2])
            index += 1
        }
    }

    /**
     * same as _hydrateBoardState but 1 based indexing for if you connect this to web
     * basically connectfour.com/moves=142462163 where "142462163" represents board state
     */
    fun _hydrateBoardStateEnglish(columnMoves: String) {
        var index = 0
        for (char in columnMoves) {
            this.dropPiece(char.digitToInt() - 1, ConnectFourGame.playerColors[index % 2])
            index += 1
        }
    }

    fun status(): ConnectFourGameStatus {
        if (this.checkForTie()) return ConnectFourGameStatus.TIE
        return this.checkForWin().second
    }

    fun calculateStaticEvaluation(): Int {
        val playerScore = evaluatePlayerScore()
        val opponentScore = evaluateOpponentScore()
        return playerScore - opponentScore
    }

    private fun evaluatePlayerScore(): Int {
        var score = 0
        for (i in 0..<6) {
            for (j in 0..<7) {
                if (grid[i][j] == ConnectFourBoardPiece.RED.color) {
                    score += evaluatePosition(i, j, ConnectFourBoardPiece.RED.color)
                }
            }
        }
        return score
    }

    private fun evaluateOpponentScore(): Int {
        var score = 0
        for (i in 0..<6) {
            for (j in 0..<7) {
                if (grid[i][j] == ConnectFourBoardPiece.YELLOW.color) {
                    score -= evaluatePosition(i, j, ConnectFourBoardPiece.YELLOW.color)
                }
            }
        }
        return score
    }

    private fun evaluatePosition(row: Int, col: Int, player: Char): Int {
        var score = 0
        val directions = arrayOf(
            Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1),
            Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1)
        )

        for (dir in directions) {
            var count = 0
            var r = row + dir.first
            var c = col + dir.second
            while (r in 0..<6 && c in 0..<7 && grid[r][c] == player) {
                count++
                r += dir.first
                c += dir.second
            }
            score += count
        }
        return score
    }

    override fun toString(): String {
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|0|_|_|_|
//        |_|_|X|0|_|_|_|
//         0 1 2 3 4 5 6

        val sb: StringBuilder = StringBuilder()
        for (row: CharArray in grid) {
            sb.append(' ')
            for (char in row) {
                if (char == ConnectFourBoardPiece.RED.color) sb.append("🔴")
                if (char == ConnectFourBoardPiece.YELLOW.color) sb.append("🟡")
                if (char == ConnectFourBoardPiece.EMPTY.color) sb.append("⚪")
            }
            sb.append("\n")
        }
//        sb.append(" ${(0..<this.width).joinToString(" ")}\n")
        sb.append(" 1\uFE0F⃣2\uFE0F⃣3\uFE0F⃣4\uFE0F⃣5\uFE0F⃣6\uFE0F⃣7\uFE0F⃣")
        return sb.toString()
    }

}
