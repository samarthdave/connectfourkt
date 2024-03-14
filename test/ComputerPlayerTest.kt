import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ComputerPlayerTest {
//    @BeforeEach
//    fun setUp() {
//    }
//
//    @AfterEach
//    fun tearDown() {
//    }

    private val board = ConnectFourBoard()
    private val player = ComputerPlayer()

    @org.junit.jupiter.api.Test
    fun testMinimaxCase1() {
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|X|O|O|O|?|
//                     ^ should drop here
        board.resetBoard()
        board._hydrateBoardState("230405")

        val col = player.makeMove(board)

        assertEquals(6, col)
    }

    @Test
    fun testMinimaxCase2() {
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|X|_|_|
//        |_|_|_|_|X|_|_|
//        |O|O|O|_|X|_|_|
//               ^ should drop here
        board.resetBoard()
        board._hydrateBoardState("404142")

        val col = player.makeMove(board)

        assertEquals(3, col)
    }

    @Test
    fun testMinimaxCase3() {
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|X|_|_|
//        |_|_|_|_|X|O|X|
//        |_|_|_|_|O|O|O|
//        |_|_|_|O|X|X|X|
//         0 1 2 3 4 5 6
//                     ^ should drop here
        board.resetBoard()
        board._hydrateBoardState("66554463453")

        val col = player.makeMove(board)

        assertEquals(6, col)
    }

    @Test
    fun testMinimaxCase4() {
//        |_|_|_|_|_|_|_|
//        |_|_|_|_|_|_|O|
//        |_|O|O|_|_|_|X|
//        |_|X|X|O|_|_|O|
//        |X|X|X|O|_|_|O|
//        |X|X|X|O|_|_|O|
//         0 1 2 3 4 5 6
//               ^
//        both players should choose that position
        board.resetBoard()
        board._hydrateBoardState("061626660313231122")

        var col = player.makeMove(board, false)
        assertEquals(3, col)
        col = player.makeMove(board, false)
        assertEquals(3, col)
    }
}