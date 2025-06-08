package com.example.escape;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Chapter1Activity extends AppCompatActivity {

    private HashSet<Integer> acquiredItems = new HashSet<>(); // 획득한 아이템을 추적하기 위한 HashSet, 아이템 요소 중복 제거
    private ImageView firstImageView;
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    private MediaPlayer bgmPlayer;
    private ArrayList<Integer> inventoryImages = new ArrayList<>();
    private ArrayList<Integer> usedPuzzlePieces = new ArrayList<>();
    private HashMap<Integer, Integer> correctPuzzlePositions = new HashMap<>();
    private int nextPuzzlePieceIndex = 0;
    private Dialog bothPopupsDialog;

    private ImageView puzzlePiece1;
    private ImageView puzzlePiece2;
    private ImageView puzzlePiece3;
    private ImageView puzzlePiece4;

    private Button area1;
    private Button area2;
    private Button area3;
    private Button area4;
    private Button area5;
    private Button area6;
    private Button area7;
    private Button area8;
    private Button area9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter1);
        setStickyImmersiveMode();

        // MediaPlayer 초기화 및 BGM 설정
        bgmPlayer = MediaPlayer.create(this, R.raw.chapter1_bgm);
        bgmPlayer.setLooping(true); // 반복 재생 설정
        bgmPlayer.start(); // BGM 시작

        firstImageView = findViewById(R.id.firstImageView);

        ImageButton bagButton = findViewById(R.id.bagButton);

        // 올바른 퍼즐 조각 위치 설정
        correctPuzzlePositions.put(R.drawable.chapter1_puzzle1, 0);
        correctPuzzlePositions.put(R.drawable.chapter1_puzzle2, 1);
        correctPuzzlePositions.put(R.drawable.chapter1_puzzle3, 2);
        correctPuzzlePositions.put(R.drawable.chapter1_puzzle4, 3);

        area1 = findViewById(R.id.button);
        area2 = findViewById(R.id.button2);
        area3 = findViewById(R.id.button3);
        area4 = findViewById(R.id.button4);
        area5 = findViewById(R.id.button5);
        area6 = findViewById(R.id.button6);
        area7 = findViewById(R.id.button7);
        area8 = findViewById(R.id.button8);
        area9 = findViewById(R.id.button9);

        area1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 첫 번째 영역을 터치했을 때 첫 번째 팝업 이미지 표시
                showPopupImage();
                playSFX_area1();
                if (!acquiredItems.contains(R.drawable.chapter1_puzzle1)) {
                    addItemToInventory(R.drawable.chapter1_puzzle1); // 아이템 요소 중복 제거
                }
            }
        });

        area2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 두 번째 영역을 터치했을 때 두 번째 팝업 이미지 표시
                showPopupImage2();
                playSFX_drawer();
                if (!acquiredItems.contains(R.drawable.chapter1_puzzle2)) {
                    addItemToInventory(R.drawable.chapter1_puzzle2); // 아이템 요소 중복 제거
                }
            }
        });

        area3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chapter1_key가 인벤토리에 없는 경우에만 area3 작동
                if (!acquiredItems.contains(R.drawable.chapter1_key)) {
                    showBothPopups();
                }
            }
        });


        area4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (acquiredItems.contains(R.drawable.chapter1_key)) {
                    // item5가 인벤토리에 있으면 StartChapter2Activity로 이동
                    Intent intent = new Intent(Chapter1Activity.this, StartChapter2Activity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } else {
                    showPopupImage4();
                }
            }
        });

        area5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupImage5();
            }
        });

        area6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupImage6();
                playSFX_area1();
            }
        });

        area7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSFX_steel();
            }
        });

        area8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSFX_steel();
            }
        });

        area9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!acquiredItems.contains(R.drawable.chapter1_puzzle4)) {
                    addItemToInventory(R.drawable.chapter1_puzzle4);
                }
                return false;
            }
        });

        bagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBagPopup();
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
        inventoryImages.add(itemResId);
        Toast.makeText(this, "퍼즐 조각을 발견했다", Toast.LENGTH_SHORT).show();
        acquiredItems.add(itemResId); // 획득한 아이템 추가 //아이템 요소 중복 제거
    }

    private void addItemToInventory2(int itemResId) {
        inventoryImages.add(itemResId);
        Toast.makeText(this, "열쇠를 발견했다", Toast.LENGTH_SHORT).show();
        acquiredItems.add(itemResId); // 획득한 아이템 추가 //아이템 요소 중복 제거
    }

    private void showBagPopup() {
        Dialog bagDialog = new Dialog(this);
        bagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bagDialog.setContentView(R.layout.bag_inside);
        GridView gridView = bagDialog.findViewById(R.id.gridView);
        BagImageAdapter adapter = new BagImageAdapter(this, inventoryImages);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int itemResId = inventoryImages.get(position);
                    // item5가 아닌 경우에만 퍼즐 조각 배치
                    if (itemResId != R.drawable.chapter1_key) {
                        placePuzzlePiece(itemResId);
                        inventoryImages.remove(position); // GridView에서 사용된 퍼즐 조각 제거
                        adapter.notifyDataSetChanged(); // 어댑터 갱신
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                } finally {
                    bagDialog.dismiss();
                }
            }
        });

        Window window = bagDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.RIGHT;
            wlp.x = 0; // 원하는 오른쪽 여백 설정 (필요에 따라 조정)
            wlp.y = 0; // 필요한 경우 수직 위치 조정
            window.setAttributes(wlp);
        }

        bagDialog.show();
    }

    private void showBothPopups() {
        bothPopupsDialog = new Dialog(this);
        bothPopupsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        bothPopupsDialog.setContentView(R.layout.chapter1_area3);

        // 퍼즐 조각을 배치할 ImageView들 초기화
        puzzlePiece1 = bothPopupsDialog.findViewById(R.id.puzzlePiece1);
        puzzlePiece2 = bothPopupsDialog.findViewById(R.id.puzzlePiece2);
        puzzlePiece3 = bothPopupsDialog.findViewById(R.id.puzzlePiece3);
        puzzlePiece4 = bothPopupsDialog.findViewById(R.id.puzzlePiece4);

        LinearLayout bagLayout = bothPopupsDialog.findViewById(R.id.bagLayout);
        GridView gridView = bagLayout.findViewById(R.id.gridView);
        Button closeButton = bothPopupsDialog.findViewById(R.id.closeButton);

        BagImageAdapter adapter = new BagImageAdapter(this, inventoryImages);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int itemResId = inventoryImages.get(position);
                    // item5가 아닌 경우에만 퍼즐 조각 배치
                    if (itemResId != R.drawable.chapter1_key) {
                        placePuzzlePiece(itemResId);
                        inventoryImages.remove(position); // GridView에서 사용된 퍼즐 조각 제거
                        usedPuzzlePieces.add(itemResId); // 사용된 퍼즐 조각 목록에 추가
                        adapter.notifyDataSetChanged(); // 어댑터 갱신
                        checkAllPuzzlePieces(); // 모든 퍼즐 조각이 올바르게 배치되었는지 확인
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bothPopupsDialog.dismiss();
                resetPuzzlePieces(); // 닫기 버튼을 눌렀을 때 퍼즐 조각을 초기화
            }
        });

        Window window = bothPopupsDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams wlp = window.getAttributes();
            wlp.gravity = Gravity.CENTER;
            window.setAttributes(wlp);
        }

        bothPopupsDialog.show();
    }

    private void placePuzzlePiece(int itemResId) {
        boolean correctPosition = false;

        switch (nextPuzzlePieceIndex) {
            case 0:
                puzzlePiece1.setImageResource(itemResId);
                if (correctPuzzlePositions.get(itemResId) == 0) {
                    correctPosition = true;
                }
                break;
            case 1:
                puzzlePiece2.setImageResource(itemResId);
                if (correctPuzzlePositions.get(itemResId) == 1) {
                    correctPosition = true;
                }
                break;
            case 2:
                puzzlePiece3.setImageResource(itemResId);
                if (correctPuzzlePositions.get(itemResId) == 2) {
                    correctPosition = true;
                }
                break;
            case 3:
                puzzlePiece4.setImageResource(itemResId);
                if (correctPuzzlePositions.get(itemResId) == 3) {
                    correctPosition = true;
                }
                break;
        }

        nextPuzzlePieceIndex++;
    }

    private void checkAllPuzzlePieces() {
        if (nextPuzzlePieceIndex == 4) { // 모든 퍼즐 조각이 배치되었는지 확인
            boolean allCorrect = true;
            for (int i = 0; i < 4; i++) {
                if (correctPuzzlePositions.get(usedPuzzlePieces.get(i)) != i) {
                    allCorrect = false;
                    break;
                }
            }

            if (allCorrect) {
                bothPopupsDialog.dismiss();
                addItemToInventory2(R.drawable.chapter1_key); // item5를 인벤토리에 추가
            }
        }
    }

    private void resetPuzzlePieces() {
        puzzlePiece1.setImageResource(0);
        puzzlePiece2.setImageResource(0);
        puzzlePiece3.setImageResource(0);
        puzzlePiece4.setImageResource(0);
        nextPuzzlePieceIndex = 0;

        // 사용된 퍼즐 조각을 인벤토리에 다시 추가
        inventoryImages.addAll(usedPuzzlePieces);
        usedPuzzlePieces.clear(); // 사용된 퍼즐 조각 목록 초기화
    }

    private void showPopupImage() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 바 제거
        dialog.setContentView(R.layout.chapter1_area1);
        dialog.show();
    }

    private void showPopupImage2() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 바 제거
        dialog.setContentView(R.layout.chapter1_area2);
        dialog.show();
    }

    private void showPopupImage4() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 바 제거
        dialog.setContentView(R.layout.chapter1_area4);
        dialog.show();
    }

    private void showPopupImage5() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 바 제거
        dialog.setContentView(R.layout.chapter1_area5);

        Button buttonInPopup = dialog.findViewById(R.id.buttonInPopup);
        buttonInPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // chapter1_chessboard로 이동
                Intent intent = new Intent(Chapter1Activity.this, Chapter1Chess.class);
                startActivityForResult(intent, 1);
                dialog.dismiss(); // 팝업 닫기
            }
        });

        dialog.show();
    }

    private void showPopupImage6() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 바 제거
        dialog.setContentView(R.layout.chapter1_area6);
        dialog.show();
    }

    private void playSFX_area1() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.chapter1_sfx_area1);
            mediaPlayer.setLooping(false); // 필요 시 반복 재생
        }
        mediaPlayer.start();
    }

    private void playSFX_steel() {
        if (mediaPlayer2 == null) {
            mediaPlayer2 = MediaPlayer.create(this, R.raw.chapter1_sfx_steel);
            mediaPlayer2.setLooping(false); // 필요 시 반복 재생
        }
        mediaPlayer2.start();
    }

    private void playSFX_drawer() {
        if (mediaPlayer3 == null) {
            mediaPlayer3 = MediaPlayer.create(this, R.raw.chapter1_sfx_drawer);
            mediaPlayer3.setLooping(false); // 필요 시 반복 재생
        }
        mediaPlayer3.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bgmPlayer != null) {
            bgmPlayer.release();
            bgmPlayer = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bgmPlayer != null && bgmPlayer.isPlaying()) {
            bgmPlayer.pause(); // 앱이 일시 중지될 때 BGM을 일시 정지합니다.
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bgmPlayer != null) {
            bgmPlayer.start(); // 앱이 다시 시작될 때 BGM을 재생합니다.
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            int itemId = data.getIntExtra("ITEM_ID", -1);
            if (itemId != -1) {
                if (!acquiredItems.contains(R.drawable.chapter1_puzzle3)){
                    addItemToInventory(itemId);
                }
            }
        }
    }
}