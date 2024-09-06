class ComputerPlayerLevel2 : ComputerPlayer() {
    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        val colAndScore = super.minimax(board, 9, false, Int.MIN_VALUE, Int.MAX_VALUE)
        return colAndScore.first
    }
}