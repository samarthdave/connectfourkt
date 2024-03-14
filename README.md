# connect four kotlin (connectfourkt)

- just found this brilliant blog post
  - http://blog.gamesolver.org/
  - looks quite thorough so I'll take a look at what I can implement here
- i'll also need to move my todos from main.kt to here

## todos

[x] get all Java files converted to Kotlin
[x] then, implement simple functionality against a human player (ConnectFourGameMode.TWO_PLAYER)
[x] and after that, against a random robot
[x] we're not done yet - implement Minimax!
[x] clean up printing of board
[x] add unit testing for minimax
[x] and after all that, make a test helper: board.readFromAscii(...)

[x] fix player 1 & 2 win condition bug
[x] and finally, alpha beta pruning
[x] improve scoring function for minimax
[ ] fix numbering for the integer drop column lol
[ ] profile the program - I probably need to cache the bitboard
[ ] include & enforce a style guide
[ ] read the blog above & start implementing
  - one anti-pattern i introduced is having multiple score mechanisms for grading...

misc

[ ] TODO change drop piece to not accept "colors"
    remove the concept of colors and just drop Player Pieces
    "colors" can be determined at render time with a mapping PlayerToColor
