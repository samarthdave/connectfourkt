// Player: An abstract class or interface that represents a player, which could be a human or a computer.
// It would define methods that a player must implement, such as makeMove.
// Player.makeMove(Board board): Interface method for making a move, which the HumanPlayer and ComputerPlayer would implement differently.

abstract class Player {
    abstract fun makeMove(board: ConnectFourBoard, isMaximizingPlayer: Boolean = false): Int
}
