class ComputerPlayerLevel2 : ComputerPlayer() {
    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        val colAndScore = super.minimax(board, 9, false, Int.MIN_VALUE, Int.MAX_VALUE)
        return colAndScore.first
    }

    fun configure(board: ConnectFourBoard) : Boolean {
//        board.status() > 25.
//        get the status and add 25 to configure the state
        var isWinning: Boolean = false

        if (Math.random() > 0.5) {
            isWinning = true
        }

        return isWinning
    }
}