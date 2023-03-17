package com.example.comp_4220_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameBoardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "mode";

    // TODO: Rename and change types of parameters
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

    LinearLayout boardView, boardView2;
    int playerTurn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_board, container, false);
        boardView = view.findViewById(R.id.board);
        boardView2 = view.findViewById(R.id.board2);
        boolean[][] board = ((GameActivity) getActivity()).getBoard();
        boolean[][] board2 = ((GameActivity) getActivity()).getBoard2();
        playerTurn = ((GameActivity) getActivity()).getPlayerTurn();
        drawBoard(board, boardView, (playerTurn == 1 && mode.equals("remove")) || (playerTurn == 2 && mode.equals("restore")));
        drawBoard(board2, boardView2, (playerTurn == 2 && mode.equals("remove")) || (playerTurn == 1 && mode.equals("restore")));
        return view;
    }

    public void drawBoard(boolean[][] board, LinearLayout layout, boolean disabled) {
        int N = board.length;
        int M = board[0].length;
        layout.removeAllViewsInLayout();
        for (int i = 0; i < N; i++) {
            LinearLayout boardRow = new LinearLayout((GameActivity) getActivity());
            for (int j = 0; j < M; j++) {
                Button btn = new Button((GameActivity) getActivity());
                btn.setText(i + "," + j + "," + board[i][j]);
                btn.setLayoutParams(new LinearLayout.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        1.0f
                ));

                // make every tile disabled
                btn.setEnabled(false);

                // if the tile has been removed, grey it out so that it can still be pressed if restored
                if(board[i][j]) {
                    btn.setAlpha(0.1f);

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
                        board[finalI][finalJ] = false;
                    }

                    if(mode.equals("remove") && board[finalI][finalJ]) {
                        // cannot remove already removed tile
                        return;
                    } else if (mode.equals("remove") && !board[finalI][finalJ]) {
                        // remove tile
                        board[finalI][finalJ] = true;
                    }

                    getParentFragmentManager().popBackStack();
                    int playerTurn = ((GameActivity) getActivity()).getPlayerTurn();
                    ((GameActivity) getActivity()).setPlayerTurn(playerTurn == 1 ? 2 : 1);
                });
                boardRow.addView(btn);
            }
            layout.addView(boardRow);
        }
    }
}