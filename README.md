**Overview:**

This project was created as a submission for a competition held by the Technical University of Munich as part of the 'Praktikum Grundlagen der Programmierung' (short PGdP - engl. Introduction to programming / compareable to e.g. CS50) modul. The course is attended by roughly 1500 to 2000 students of computer science and related fileds per year.

This year's goal was to build an AI for the mathematical game domineering on a 13 by 13 board (https://en.wikipedia.org/wiki/Domineering) that could beat out its competition from other students. Importantly we had serve time and ressource constraints (e.g. limiting the length of a game to 10 seconds), so the AI had to be well optimized. To encourge improvements up to the final deadline the competition was held with online rankings updated daily, so that you could see how changes to your AI fare against other players. After two weeks of strong competition, I managed to win the contest with a good margin (see picture below).

![text](https://image.shutterstock.com/image-vector/sample-stamp-grunge-texture-vector-600w-1389188327.jpg)

In the following I will detail the core ideas that shaped my submission.

**A short explanation of final submission:**

Up to the first 4 moves are entirely rule based and played based on 
the Corner Stone Opening (_see class_).

After that a MinMax AI with Alpha Beta Pruning searches 
for the best move up to a depth of 5. How do I achieve such a deep search depth:

- **The Tile class:** Representing the board in a 2D array of tiles allowed me
to only update a fraction of the board per move made and still get accurate results.

- **Safe Move Pruning:** I assumed that Safe Moves
(= Moves that the opponent can't ever block) are always a losing moves and 
thus prune them in the search. This was later confirmed by a really old 
looking website about a Domineering paper from the late 90s.

- **Move Proposal Mechanism (SMC Pruning):** This is probably most important for 
runtime and allowed me to increase my depth from 3 to 5. After debugging a few games I soon realized
that most moves just consistently suck. I was able to categorize the most useful moves
into a few categories:
    - **Safe Move Creation Moves:** These are moves that create a Safe Move
    (with a few exceptions).
    - **Blocking Moves:** Moves that block certain strong moves.
    - **Extension Moves:** Moves that are played adjusted to a filled tile. Here
    you basically only need moves on odd rows/columns.
    - **Banger Moves:** A rare kind of move by which you play a tile in the middle
    of a 7 tile space to get 3 tile spaces that each yield 2 safe moves (if not blocked).
    
- **Hashtables:** Each turn all already evaluted position are saved to a Hashtable.
I am really not sure how effective this solution is, but it seems to make it a 
little bit faster.


Last but not least I want to talk about my evaluation function. What I build looks 
pretty weird and looking back it is useless, so I will just explain broad logic behind it.
The evaluation works basically on three tiers, first minimizing/maximizing the
(numerically) lower ones and then moving on to the higher tiers
(In each tier we look at the difference between the vertical and horizontal player).

**Tier 1:** _RealMoves_ (= The maximum amount of moves 
that can be played if the oppontent wouldn't play a move) + _SafeMoves_ (= Moves that can't be blocked by the opponent). As SafeMoves
are also real moves they get a double weighing.
**Tier 2:** _SafeMovePossibilities_ (= Moves that can be converted into a Safe Move within one turn)
**Tier 3:** _OpponentsDeadMoves_  (= RealMoves that can never be turned into a Safe Move)
**Tier 4:** _OpponentsVulnerableTiles_ (= Basically left-over tiles from a safe move e.g.
a three-length Safe-Tile stretch only produces one safe move. If it is closed off, you
also for sure can't get another RealMove from it, if not you have to fight for
that move in the end game.)

**NOTE FOR SOMEONE READING MY CODE:**

Start with MinMaxV6 and work your way from there. All MinMaxAI's are pretty similar.
The BiasFunction never worked and is outdated. The TileManager has a lot of very similar
and long methods that couldn't be split up for optimization reasons. Older versions are
still in the code to give a reliable benchmark for newer versions.
This can be tested in a range of TestClasses that I can't upload as Junit causes Build errors.
You can convert a board to a String and back with the BoardConverter Class.

That should be most of it!
Thank you for reading! I hope that this helped you understand my code and thought process!
Regardless of my final placement, this has been an amazing competition and learning experience!
Many thanks to team that made this all possible!
