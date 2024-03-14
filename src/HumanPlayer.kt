import java.util.Scanner

class HumanPlayer : Player() {
    private val reader: Scanner = Scanner(System.`in`)

    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        println("=== Human Player Move ===")
        var columnChoice: Int
        do {
            print("Enter a valid column (1-${ConnectFourBoard.DEFAULT_BOARD_WIDTH}): ")
            columnChoice = reader.nextInt() - 1
        } while(board.inBounds(columnChoice) && !board.availableLocations().contains(columnChoice));
        return columnChoice
    }
}
