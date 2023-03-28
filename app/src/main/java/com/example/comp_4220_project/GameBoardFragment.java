package com.example.comp_4220_project;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameBoardFragment extends Fragment {

    private static final String ARG_PARAM1 = "mode";

    private String mode;

    public GameBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mode Parameter 1.
     * @return A new instance of fragment GameBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameBoardFragment newInstance(String mode) {
        GameBoardFragment fragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mode = getArguments().getString(ARG_PARAM1);
        }
    }

    LinearLayout boardView, boardView2, b_linear;
    TextView textViewPlayerTurn;
    boolean[][] board, board2;
    int playerTurn;
    MediaPlayer hit, restore;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_board, container, false);
        boardView = view.findViewById(R.id.board);
        boardView2 = view.findViewById(R.id.board2);
        textViewPlayerTurn = view.findViewById(R.id.textViewPlayerTurn);
        board = ((GameActivity) getActivity()).getBoard();
        board2 = ((GameActivity) getActivity()).getBoard2();
        playerTurn = ((GameActivity) getActivity()).getPlayerTurn();
        textViewPlayerTurn.setText("Player " + playerTurn + "'s turn");
        b_linear = (LinearLayout) view.findViewById(R.id.b_linear);

        hit = MediaPlayer.create(getContext(), R.raw.hit);
        restore = MediaPlayer.create(getContext(), R.raw.restore);

        sp = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();

        drawBoard(board, boardView, (playerTurn == 1 && mode.equals("remove")) || (playerTurn == 2 && mode.equals("restore")), true);
        drawBoard(board2, boardView2, (playerTurn == 2 && mode.equals("remove")) || (playerTurn == 1 && mode.equals("restore")), false);
        return view;
    }

    public void drawBoard(boolean[][] board, LinearLayout layout, boolean disabled, boolean isPlayer1) {
        int N = board.length;
        int M = board[0].length;
        layout.removeAllViewsInLayout();
        for (int i = 0; i < N; i++) {
            LinearLayout boardRow = new LinearLayout((GameActivity) getActivity());
            for (int j = 0; j < M; j++) {
                ContextThemeWrapper gameActivity = new ContextThemeWrapper((GameActivity) getActivity(), R.style.board_option_buttons_style);
                Button btn = new Button(gameActivity);

                if (isPlayer1) {
                    btn.setText(Integer.toString(N-i));
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.button_tile_player1));
                } else {
                    btn.setText(Integer.toString(i+1));
                    btn.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.button_tile_player2));
                }

                btn.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                // make every tile disabled
                btn.setEnabled(false);

                // if the tile has been removed, grey it out so that it can still be pressed if restored
                if(board[i][j]) {
                    btn.setAlpha(0.5f);

                    // enable tiles that are on the edge of the player's own board
                    if (!disabled && mode.equals("restore")) {
                        if (playerTurn == 2 && i >= 1 && !board[i-1][j]) {
                            btn.setEnabled(true);
                        } else if (playerTurn == 1 && i < N - 1 && !board[i+1][j]) {
                            btn.setEnabled(true);
                        }
                    }
                }

                // if it is the enemy board, and it is on the edge (middle) then enable it
                if (!disabled && mode.equals("remove")) {
                    if (playerTurn == 1 && (i == N - 1 || board[i+1][j])) {
                        btn.setEnabled(true);
                    } else if (playerTurn == 2 && (i == 0 || board[i-1][j])) {
                        btn.setEnabled(true);
                    }
                }

                int finalI = i;
                int finalJ = j;
                btn.setOnClickListener(e -> {
                    if(mode.equals("restore") && !board[finalI][finalJ]) {
                        // cannot restore a tile that has not been removed
                        return;
                    } else if (mode.equals("restore") && board[finalI][finalJ]) {
                        // restore tile
                        sound(restore);
                        board[finalI][finalJ] = false;
                        GameActivity a = ((GameActivity) getActivity());
                        if(playerTurn == 1) {
                            a.setPlayer1NumRestored(a.getPlayer1NumRestored() + 1);
                        } else {
                            a.setPlayer2NumRestored(a.getPlayer2NumRestored() + 1);
                        }
                    }

                    if(mode.equals("remove") && board[finalI][finalJ]) {
                        // cannot remove already removed tile
                        return;
                    } else if (mode.equals("remove") && !board[finalI][finalJ]) {
                        // remove tile
                        sound(hit);
                        board[finalI][finalJ] = true;
                    }

                    if (isWinner()) {
                        Intent intent = new Intent(getActivity(), EndActivity.class);
                        intent.putExtra("winner", playerTurn);
                        startActivity(intent);
                        return;
                    }

                    ((GameActivity) getActivity()).setPlayerTurn(playerTurn == 1 ? 2 : 1);
                    getParentFragmentManager().popBackStack();
                });
                boardRow.addView(btn);
            }
            layout.addView(boardRow);
        }
    }

    public boolean isWinner() {
        int N = board.length;
        int M = board[0].length;
        for(int i = 0; i < M; i++) {
            if (playerTurn == 1) {
                if (board2[0][i]) return true;
            } else {
                if (board[N-1][i]) return true;
            }
        }
        return false;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) return;
        postponeEnterTransition();
        view.setBackgroundColor(Color.WHITE);
        view.post(() -> postponeEnterTransition(1500, TimeUnit.MILLISECONDS));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(restore != null){
            restore.release();
            restore = null;
        }
        if(hit != null){
            hit.release();
            hit = null;
        }
    }

    //if sound effects game setting is set to on, the MediaPlayer will play its audio
    public void sound(MediaPlayer mp){
        if(fx_mode){
            mp.start();
        }
    }

    //used to save the sharedPreferences as local variables
    public void loadPreferences(){
        m_mode = sp.getBoolean("music", false);
        fx_mode = sp.getBoolean("fx", false);
        dark_mode = sp.getBoolean("dark", false);
        if(dark_mode){
            b_linear.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else{
            b_linear.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }
}