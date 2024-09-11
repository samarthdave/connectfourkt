class TranspositionTable {
    private val table = mutableMapOf<String, Int>()

    fun getScore(board: ConnectFourBoard): Int? {
        val key = board.generateKey(board)
        return table[key]
    }

    fun setScore(board: ConnectFourBoard, score: Int): Int {
        val key = board.generateKey(board)
        table[key] = score
        return score
    }
}
