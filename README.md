MENACE-Style Tic Tac Toe in Java
===============================
I saw this concept in passing on Reddit, and found it fascinating as I read more. In the 1960s, Donald Mitchie described a system that would learn to play Tic Tac Toe called the Matchbox Educable Noughts And Crosses Engine.

Each matchbox represents a current board state. Before each turn, the correct matchbox is selected. Inside each matchbox are a number of 9 different kinds of beans or beads. Each kind represents a specific location on the board to play. The system makes the play with the current highest number of beans.

At the end of the round, if the system won, two beans for each play made is added to each corresponding matchbox. If it lost, a bean removed from each matchbox. In the case of a draw, one bean is added.

After a sufficient number of rounds, the system "learns" to play by keeping track of which plays resulted in favorable outcomes and which resulted in unfavorable outcomes.

For more information, I recommend:

- [A New Theory of Awesomeness and Miracles](http://shorttermmemoryloss.com/menace/)
- [Tic-Tac-Toe computer learns with beans](http://blog.makezine.com/2009/11/02/mechanical-tic-tac-toe-computer/)

In this implementation, BoardState corresponds to a matchbox and the logicCounter array keeps the "bean" count.

Analysis
======
For an analysis of this implementation check out my [blog writeup](http://letstalkdata.com/?p=164).