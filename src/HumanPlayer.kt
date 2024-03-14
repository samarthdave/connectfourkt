import java.util.Scanner

class HumanPlayer : Player() {
    private val reader: Scanner = Scanner(System.`in`)

    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        println("=== Human Player Move ===")
        var columnChoice: Int
        do {
            println("Enter a valid column: ")
            columnChoice = reader.nextInt()
        } while(!board.availableLocations().contains(columnChoice));
        return columnChoice
    }
}
