enum class ConnectFourBoardPiece(val color: Char) {
    RED('X'), // was R
    YELLOW('O'), // and was Y
    EMPTY('_'), // but these are far more readable (see below)
}

//|_|_|_|_|_|_|_|
//|_|_|_|_|_|_|_|
//|_|_|_|_|_|_|_|
//|_|_|0|X|_|_|_|
//|_|_|0|0|X|_|_|
//|_|_|X|0|0|X|_|
