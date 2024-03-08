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

        board.hydrateBoardState("230405")

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

        board.hydrateBoardState("404142")

        val col = player.makeMove(board)

        assertEquals(3, col)
    }
}