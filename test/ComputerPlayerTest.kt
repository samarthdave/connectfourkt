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

//    Player 2 chose column 3 ; success=true
//    |_|_|_|_|_|_|_|
//    |O|_|_|_|_|_|_|
//    |O|O|_|_|_|_|_|
//    |X|X|_|O|_|_|_|
//    |X|X|X|O|_|_|_|
//    |X|X|X|O|X|_|_|
//    0 1 2 3 4 5 6
      //     ^ dropped in this one?? why didn't it see the win?

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
        board.hydrateBoardState("66554463453")

        val col = player.makeMove(board)

        assertEquals(6, col)
    }
}