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

}