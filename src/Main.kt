//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("===============================")
    println("========= ConnectFour =========")
    println("==== implemented in Kotlin ====")
    println("===============================")
    println()

    val myGame = ConnectFourGame(ConnectFourGameMode.SINGLE_PLAYER_VS_COMPUTER_AI, true);

    myGame.startGame()

    // [x] TODO get all Java files converted to Kotlin
    // [x] TODO then, implement simple functionality against a human player (ConnectFourGameMode.TWO_PLAYER)
    // [x] TODO and after that, against a random robot
    // [x] TODO we're not done yet - implement Minimax!
    // [ ] TODO and finally, alpha beta pruning
    // [x] TODO clean up printing of board
    // [x] TODO add unit testing for minimax
    // [ ] TODO improve scoring function for minimax
    // [ ] TODO oh and after all that, profile the program - I probably need to store the bitboard
    // [ ] TODO include & enforce a style guide

    // [ ] TODO and after all that, make a test helper: board.readFromAscii(...) that converts the toString back to the grid
    //     instead of making a new one every time

}