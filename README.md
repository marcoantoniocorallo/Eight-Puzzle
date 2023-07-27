# Eight-Puzzle
*Advanced Programming* Assignment about components and event-based communication mechanisms with Java Beans.

The *8-puzzle* game is a reduced version of the more famous 15-puzzle game: starting from a random configuration of the tiles, the puzzle consists of reaching the final configuration (the sorted one) with a sequence of moves. 
Each move consists of sliding one tile on the hole, thus exchanging the positions of that tile and the hole.

The system is made of a graphical dashboard, EightBoard, containing the board, an EightController label, and two buttons: RESTART and FLIP. The board contains 9 tiles EightTile.

The assignment requires to implement the 8-puzzle game using _Java Beans_ and event-based communication mechanisms.

For other requirements and design choices, you can read the [report](https://github.com/marcoantoniocorallo/Eight-Puzzle/blob/main/report.pdf).

Each component provides a reusable bean in its `target` directory.
#### You can play the 8-puzzle game running `java -jar EightBoard/target/EightBoard-1.0.jar`
