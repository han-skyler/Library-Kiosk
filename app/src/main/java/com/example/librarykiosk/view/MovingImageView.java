package com.example.librarykiosk.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.example.librarykiosk.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MovingImageView extends RelativeLayout {

    private ImageView gpsIcon;
    private List<Point> pathCoordinates;
    private int[][] grid;
    private int cellSize;
    private int currentIndex = 0;
    private final Handler handler = new Handler();

    private DatabaseReference databaseReference;

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        handler.removeCallbacksAndMessages(null);
    }

    public MovingImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        setupFirebaseListener();
    }

    private void init() {
        initGrid();
        pathCoordinates = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Position");

        gpsIcon = new ImageView(getContext());
        gpsIcon.setImageResource(com.example.librarykiosk.R.drawable.icon_gps);
        LayoutParams params = new LayoutParams(dpToPx(40), dpToPx(40));
        gpsIcon.setLayoutParams(params);
        addView(gpsIcon);

        moveAlongPath();
    }

    private void setupFirebaseListener() {
        // x_current 경로에 대한 ValueEventListener
        DatabaseReference xCurrentRef = databaseReference.child("x_current");
        xCurrentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handleCoordinateChange(snapshot, true);
                Log.d("Firebase", "change x value");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read x_current value.", error.toException());
            }
        });

        // y_current 경로에 대한 ValueEventListener
        DatabaseReference yCurrentRef = databaseReference.child("y_current");
        yCurrentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handleCoordinateChange(snapshot, false);
                Log.d("Firebase", "change y value");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read y_current value.", error.toException());
            }
        });
    }

    // x_current 또는 y_current 경로의 데이터를 처리하는 메서드
    private void handleCoordinateChange(DataSnapshot snapshot, boolean isXCoordinate) {
        Integer coordinateValue = snapshot.getValue(Integer.class);

        if (coordinateValue != null) {
            int coordinate = coordinateValue.intValue();
            Point currentPoint;

            if (isXCoordinate) {
                // x 값만 업데이트
                if (!pathCoordinates.isEmpty()) {
                    currentPoint = pathCoordinates.get(pathCoordinates.size() - 1);
                    pathCoordinates.add(new Point(coordinate, currentPoint.y));
                } else {
                    pathCoordinates.add(new Point(coordinate, 0));
                }
            } else {
                // y 값만 업데이트
                if (!pathCoordinates.isEmpty()) {
                    currentPoint = pathCoordinates.get(pathCoordinates.size() - 1);
                    pathCoordinates.add(new Point(currentPoint.x, coordinate));
                } else {
                    pathCoordinates.add(new Point(0, coordinate));
                }
            }
        }

        moveAlongPath();
    }


    private void initGrid() {
        grid = new int[][]{
                {1, 1, 0, 1, 1, 1, 1, 1},
                {1, 1, 0, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1, 0, 1, 1},
                {1, 1, 0, 1, 1, 0, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 1, 1, 0, 1, 1}
        };

        this.cellSize = calculateCellSize();
    }

    private int calculateCellSize() {
        int widthSize = getWidth();
        int heightSize = getHeight();

        int cellWidth = widthSize / grid[0].length;
        int cellHeight = heightSize / grid.length;

        return Math.min(cellWidth, cellHeight);
    }

    private void moveAlongPath() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (currentIndex < pathCoordinates.size()) {
                    Point currentCoordinate = pathCoordinates.get(currentIndex);
                    moveImageViewToCoordinate(gpsIcon, currentCoordinate.x, currentCoordinate.y);
                    currentIndex++;
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void moveImageViewToCoordinate(final View view, int x, int y) {
        if (cellSize == 0) {
            initGrid();
        }
        int newX = x * cellSize;
        int newY = y * cellSize;

        Log.d("경로표시","(" + x + "," + y + ")");

        // ObjectAnimator를 사용하여 아이콘을 부드럽게 이동
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "translationX", newX);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "translationY", newY);

        // 애니메이션 지속 시간 설정 (1초)
        animatorX.setDuration(1000);
        animatorY.setDuration(1000);

        // 애니메이션 실행
        animatorX.start();
        animatorY.start();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
