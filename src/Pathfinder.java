
import java.util.*;

class Pathfinder 
{
static int ROW_MAX = 750;
static int COL_MAX = 700;

static class Point
{
    int x;
    int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
    }
};

static class QueueNode
{
    Point pt;
    int dist;
    QueueNode parent;
 
    public QueueNode(Point pt, int dist, QueueNode parent)
    {
        this.pt = pt;
        this.dist = dist;
        this.parent = parent;
    }
};
static boolean isValid(int row, int col)
{
    return (row >= 0) && (row < ROW_MAX) &&
        (col >= 0) && (col < COL_MAX);
}


static int rowNum[] = {-1, 0, 0, 1};
static int colNum[] = {0, -1, 1, 0};

// BFS implementation
static LinkedList<Point> BFS(int mat[][], int sx, int sy, int dx, int dy)
{
    
    Point src = new Point(sx, sy);
    Point dest = new Point(dx, dy);
    

    boolean [][]visited = new boolean[ROW_MAX][COL_MAX];
    
    visited[src.x][src.y] = true;

    LinkedList<QueueNode> q = new LinkedList<QueueNode>();
    
    q.add(new QueueNode(src, 0, null));
    
    while (!q.isEmpty())
    {
        QueueNode curr = q.peek();
        Point pt = curr.pt;
        
        q.remove();
        // System.out.println(pt.toString());

        if (pt.x == dest.x && pt.y == dest.y) {
            return getPath(curr);
        }

        for (int i = 0; i < 4; i++)
        {
            int row = pt.x + rowNum[i];
            int col = pt.y + colNum[i];
            
            if (isValid(row, col) && 
                    mat[row][col] == 0 && 
                    !visited[row][col])
            {
                visited[row][col] = true;
                q.add(new QueueNode(new Point(row, col), curr.dist + 1, curr));            
            }
        }
    }
    return null;
}


private static LinkedList<Point> getPath(QueueNode node) {
    LinkedList<Point> l = new LinkedList<Point>();
    QueueNode curr = node;
    while (curr != null) {
        l.addLast(curr.pt);
        curr = curr.parent;
}
    Collections.reverse(l);
    return l;
}

public static void main(String[] args) 
{
    /*
    int mat[][] = {
                { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
                { 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
                { 1, 1, 1, 0, 1, 1, 0, 1, 0, 1 },
                { 0, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
                { 1, 1, 1, 0, 1, 1, 1, 0, 1, 0 },
                { 1, 0, 1, 1, 1, 1, 0, 1, 0, 0 },
                { 1, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
                { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1 },
                { 1, 1, 0, 0, 0, 0, 1, 0, 0, 1 }};

    int mat2[][] = new int[900][900];
    
    Queue<Point> path = BFS(mat2, 450,450,500,600);
    System.out.println(path.size());
    */

    
}
}


