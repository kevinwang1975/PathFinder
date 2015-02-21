This project contains a Java implementation of A* (A star) search algorithm
that is widely used in path finding and graph traversal, and Dijkstra algorithm
that is used in Cisco router shortest path finding.

There are great similarities in the two algorithms, with the difference being that
A* has a heuristic mechanism while Dijkstra does not. With the heuristic, A* 
usually outperforms Dijkstra in speed in finding a path, however, unlike Dijkstra,
the path A* finds may not always be the optimal one.

A Java/Swing graphical demo program is provided to animate the progress of
path searching, and the two resulted paths side by side for comparison.