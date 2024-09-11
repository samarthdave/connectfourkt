//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("===============================")
    println("========= ConnectFour =========")
    println("==== implemented in Kotlin ====")
    println("===============================")
    println()
    val myGame = ConnectFourGame(ConnectFourGameMode.COMPUTER_AI_VS_COMPUTER_AI, true);

    myGame.startGame()

}