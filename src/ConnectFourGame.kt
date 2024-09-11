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

        val firstPlayer: Player

        if (gameModeChoice === ConnectFourGameMode.COMPUTER_AI_2_VS_COMPUTER_AI_2) {
            firstPlayer = ComputerPlayerLevel2()
        } else {
            firstPlayer = HumanPlayer()
        }

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
            ConnectFourGameMode.SINGLE_PLAYER_VS_COMPUTER_AI_2 -> {
                ComputerPlayerLevel2()
            }
            ConnectFourGameMode.COMPUTER_AI_2_VS_COMPUTER_AI_2 -> {
                ComputerPlayerLevel2()
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
            println("Player ${currentPlayerIndex + 1} chose column ${choice+1} ; success=${didDrop.first}")
            if (!didDrop.first)
                continue
            println(board) // toString() used here

            movesElapsed += 1
        }
    }

    private fun status(): ConnectFourGameStatus {
        return this.board.status()
    }

    fun _hydrateGameStateEnglish(columnMoves: String) {
        this.board._hydrateBoardStateEnglish(columnMoves)
    }
}
