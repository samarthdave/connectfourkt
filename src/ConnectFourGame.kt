// It would manage the game state, handle user input, and coordinate between the game engine and the
// user interface.
// ConnectFourEngine: This class would handle the game logic, including checking for a win, making a move,
// and managing the game state. It would interact with the Board class to update the game state.
// ConnectFourState: This class would represent the current state of the game.
// It would be used by the ConnectFourEngine to manage the game state and would be updated whenever a move is made.
// ConnectFourGame.startGame(): Starts the game and initializes the game state.
// ConnectFourGame.endGame(): Ends the game, possibly declaring a winner or indicating a tie.
// ComputerPlayer.makeMove(Board board): Makes a move using an AI algorithm like minimax.
// ConnectFourEngine.handlePlayerMove(int column, int player): Handles a player's move by updating the game state and checking for a win or tie.

enum class ConnectFourGameStatus {
    PLAYING,
    TIE,
    PLAYER_ONE_WIN,
    PLAYER_TWO_WIN,
}

class ConnectFourGame(gameModeChoice: ConnectFourGameMode, playingFirstChoice: Boolean) {
    private lateinit var board: ConnectFourBoard

    private var movesElapsed: Int = 0
    private var players: Array<Player> = emptyArray<Player>()

    companion object {
        val playerColors: Array<ConnectFourBoardPiece> = arrayOf(ConnectFourBoardPiece.RED, ConnectFourBoardPiece.YELLOW)
    }
    // voiding this for now, ask the user how they want to play again
    // private lateinit var currentGameMode: ConnectFourGameMode

    init {
        this.resetGame(gameModeChoice, playingFirstChoice)
    }

    // TODO: this is "play again" functionality but same config... maybe I could just resetGame()?
    private fun resetBoard() {
        this.board.resetBoard()
        this.movesElapsed = 0
    }

    private fun resetGame(gameModeChoice: ConnectFourGameMode, first: Boolean) {
        this.board = ConnectFourBoard()
        this.players = emptyArray<Player>()

        val firstPlayer: Player = HumanPlayer()
        val opponent: Player = when (gameModeChoice) {
            ConnectFourGameMode.SINGLE_PLAYER_VS_RANDOM_AI -> {
                RandomComputerPlayer()
            }
            ConnectFourGameMode.SINGLE_PLAYER_VS_COMPUTER_AI -> {
                ComputerPlayer()
            }
            ConnectFourGameMode.TWO_PLAYER -> {
                HumanPlayer()
            }
        }

        if (first) {
            players += firstPlayer
            players += opponent
        } else {
            players += opponent
            players += firstPlayer
        }
    }

    fun startGame() {
        println(board)
        while (this.status() == ConnectFourGameStatus.PLAYING && board.availableLocations().size > 0) {
            // index into whichever player is "current"
            val currentPlayerIndex: Int = movesElapsed % 2
            val choice = players[currentPlayerIndex].makeMove(board)

            val didDrop = board.dropPiece(choice, ConnectFourGame.playerColors[currentPlayerIndex])
            println("Player ${currentPlayerIndex + 1} chose column $choice ; success=${didDrop.first}")
            if (!didDrop.first)
                continue
            println(board) // toString() used here

            movesElapsed += 1
        }
    }

    private fun status(): ConnectFourGameStatus {
        return this.board.status()
    }
}
