package com.example.escape;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Chapter1Chess extends AppCompatActivity {

    private GridLayout chessBoard;
    private ImageView queen1;
    private ImageView queen2;
    private ImageButton hiddenButton;
    private float queen1InitialX, queen1InitialY;
    private float queen2InitialX, queen2InitialY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter1_chess);
        setStickyImmersiveMode();
        chessBoard = findViewById(R.id.chessBoard);
        queen1 = findViewById(R.id.queen1);
        queen2 = findViewById(R.id.queen2);
        hiddenButton = findViewById(R.id.hiddenButton);

        // Set tags for the queens
        queen1.setTag("queen1");
        queen2.setTag("queen2");

        // Save initial positions
        queen1.post(() -> {
            queen1InitialX = queen1.getX();
            queen1InitialY = queen1.getY();
        });

        queen2.post(() -> {
            queen2InitialX = queen2.getX();
            queen2InitialY = queen2.getY();
        });

        queen1.setOnTouchListener(new MyTouchListener());
        queen2.setOnTouchListener(new MyTouchListener());

        ConstraintLayout layout = findViewById(R.id.rootLayout);
        layout.setOnDragListener(new MyDragListener());

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        hiddenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemToInventory(R.drawable.chapter1_puzzle3);
            }
        });
    }

    private void setStickyImmersiveMode() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    private void addItemToInventory(int itemResId) {
        Intent intent = new Intent();
        intent.putExtra("ITEM_ID", itemResId);
        setResult(RESULT_OK, intent);
        finish();
    }

    private final class MyTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(view.getTag().toString(), mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    view.startDragAndDrop(dragData, myShadow, view, 0);
                } else {
                    view.startDrag(dragData, myShadow, view, 0);
                }
                return true;
            } else {
                return false;
            }
        }
    }

    private class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                case DragEvent.ACTION_DRAG_ENTERED:
                    return true;
                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:
                    return true;
                case DragEvent.ACTION_DROP:
                    View view = (View) event.getLocalState();
                    if (view.getId() == R.id.queen1 || view.getId() == R.id.queen2) {
                        // Check if drop location is within chess board boundaries
                        int[] location = new int[2];
                        chessBoard.getLocationOnScreen(location);

                        float dropX = event.getX();
                        float dropY = event.getY();

                        if (dropX >= location[0] && dropX <= location[0] + chessBoard.getWidth() &&
                                dropY >= location[1] && dropY <= location[1] + chessBoard.getHeight()) {
                            // Snap to grid
                            int cellWidth = chessBoard.getWidth() / chessBoard.getColumnCount();
                            int cellHeight = chessBoard.getHeight() / chessBoard.getRowCount();

                            int column = (int) ((dropX - location[0]) / cellWidth);
                            int row = (int) ((dropY - location[1]) / cellHeight);

                            view.setX(location[0] + column * cellWidth);
                            view.setY(location[1] + row * cellHeight);
                            view.bringToFront();

                            // Check if queens are placed in the correct positions
                            checkQueensPosition();
                        } else {
                            // If not within boundaries, reset position
                            resetQueenPosition(view);
                        }
                    }
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    return true;
                default:
                    break;
            }
            return true;
        }
    }

    private void checkQueensPosition() {
        int[] location1 = new int[2];
        int[] location2 = new int[2];

        queen1.getLocationOnScreen(location1);
        queen2.getLocationOnScreen(location2);

        int cellWidth = chessBoard.getWidth() / chessBoard.getColumnCount();
        int cellHeight = chessBoard.getHeight() / chessBoard.getRowCount();

        int column1 = (location1[0] - chessBoard.getLeft()) / cellWidth;
        int row1 = (location1[1] - chessBoard.getTop()) / cellHeight;

        int column2 = (location2[0] - chessBoard.getLeft()) / cellWidth;
        int row2 = (location2[1] - chessBoard.getTop()) / cellHeight;

        // Check if queens are in the correct positions (example: A8, H4)
        if ((row1 == 0 && column1 == 0 && row2 == 7 && column2 == 3) ||
                (row2 == 0 && column2  == 0 && row1 == 7 && column1 == 3)) {
            Toast.makeText(this, "정답이다!", Toast.LENGTH_SHORT).show();
            hiddenButton.setVisibility(View.VISIBLE);
        }
    }

    private void resetQueenPosition(View queen) {
        if (queen.getId() == R.id.queen1) {
            queen.setX(queen1InitialX);
            queen.setY(queen1InitialY);
        } else if (queen.getId() == R.id.queen2) {
            queen.setX(queen2InitialX);
            queen.setY(queen2InitialY);
        }
    }
}