package com.example.librarykiosk.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.LinkedList;
import java.util.Queue;

public class MapView extends View {
    private Paint paint;
    private int[][] grid;
    private int cellSize;
    private int startX = -1, startY = -1;
    private int endX = -1, endY = -1;

    // 새로운 생성자
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 스타일 속성을 위한 생성자도 추가할 수 있습니다
    public MapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    // 초기화 메소드
    private void init() {
        initGrid();
        paint = new Paint();
    }

    public MapView(Context context) {
        super(context);
        initGrid();
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 사용자가 XML에서 설정한 폭과 높이를 가져옵니다.
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 셀 크기를 계산합니다. 이 예제에서는 폭과 높이 모두를 고려합니다.
        int cellWidth = widthSize / grid[0].length;
        int cellHeight = heightSize / grid.length;

        // 셀이 정사각형이 되도록 더 작은 쪽을 기준으로 맞춥니다.
        int cellSize = Math.min(cellWidth, cellHeight);

        // 최종적으로 계산된 그리드의 폭과 높이를 설정합니다.
        int calculatedWidth = cellSize * grid[0].length;
        int calculatedHeight = cellSize * grid.length;

        // 셀 크기를 클래스 변수로 업데이트합니다.
        this.cellSize = cellSize;

        // 최종적으로 측정된 크기를 설정
        setMeasuredDimension(calculatedWidth, calculatedHeight);
    }

    private void initGrid() {
        // 0은 갈 수 있는 경로, 1은 갈 수 없는 경로
        grid = new int[][]{
                {1,1,0,1,1,1,1,1},
                {1,1,0,1,1,1,1,1},
                {0,0,0,0,0,0,0,0},
                {1,1,0,1,1,0,1,1},
                {1,1,0,1,1,0,1,1},
                {0,0,0,0,0,0,0,0},
                {1,1,0,1,1,0,1,1},
                {1,1,0,1,1,0,1,1},
                {0,0,0,0,0,0,0,0},
                {1,1,0,1,1,0,1,1}
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        if (startX != -1 && startY != -1 && endX != -1 && endY != -1) {
            drawPath(canvas, bfs(startX, startY, endX, endY));
        }
    }

    private void drawGrid(Canvas canvas) {
        paint.setStrokeWidth(1);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    // 블럭의 색상 지정
                    paint.setColor((Color.argb(0,255,0,0)));
                    //paint.setColor(Color.GRAY);
                    canvas.drawRect(j * cellSize, i * cellSize, (j + 1) * cellSize, (i + 1) * cellSize, paint);
                }
            }
        }
    }

    private void drawPath(Canvas canvas, LinkedList<Node> path) {
        if (path == null) return;
        // 블럭의 색상을 지정합니다.
        paint.setColor(Color.rgb(188,119,190));
        paint.setStrokeWidth(10);
        for (Node node : path) {
            if (node.parent != null) {
                canvas.drawLine(
                        (node.y + 0.5f) * cellSize,
                        (node.x + 0.5f) * cellSize,
                        (node.parent.y + 0.5f) * cellSize,
                        (node.parent.x + 0.5f) * cellSize,
                        paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int y = (int) (event.getX() / cellSize);
            int x = (int) (event.getY() / cellSize);

            if (endX == -1 && endY == -1) { // 첫 번째와 두 번째 터치를 처리
                if (startX == -1 && startY == -1) {
                    startX = x;
                    startY = y;
                } else {
                    endX = x;
                    endY = y;
                }
            } else { // 세 번째 터치 처리 (첫 번째 위치를 새로운 시작점으로 업데이트)
                startX = x;
                startY = y;
                endX = -1;
                endY = -1;
            }
            Log.d("Touch Event", "Touch at: (" + x + ", " + y + ")");
            invalidate(); // 화면 다시 그리기
        }
        return true;
    }

    private LinkedList<Node> bfs(int startX, int startY, int endX, int endY) {
        boolean[][] visited = new boolean[grid.length][grid[0].length];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startX, startY, null));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current.x == endX && current.y == endY) {
                return getPath(current);
            }

            // Add neighbors
            for (int[] dir : new int[][]{{0,1}, {1,0}, {-1,0}, {0,-1}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                if (isValid(newX, newY, visited)) {
                    Log.d("MapView", "Exploring: (" + current.x + ", " + current.y + ")");
                    visited[newX][newY] = true;
                    queue.add(new Node(newX, newY, current));
                }
            }
        }

        return null;
    }

    private LinkedList<Node> getPath(Node node) {
        LinkedList<Node> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node);
            node = node.parent;
        }
        Log.d("getPath", "Path : (" + path + ")");
        return path;
    }

    private boolean isValid(int x, int y, boolean[][] visited) {
        //return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && !visited[x][y] && grid[x][y] == 1;
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && !visited[x][y] && grid[x][y] == 0;
    }

    private static class Node {
        int x;
        int y;
        Node parent;

        Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }
}