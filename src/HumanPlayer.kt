import java.util.Scanner

class HumanPlayer : Player() {
    private val reader: Scanner = Scanner(System.`in`)

    override fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean): Int {
        print("=== Human Player Move ===\nEnter a column choice:")
        val columnChoice: Int = reader.nextInt()

        return columnChoice
    }
}
