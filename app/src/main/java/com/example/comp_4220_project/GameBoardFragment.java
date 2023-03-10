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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameBoardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameBoardFragment newInstance(String param1, String param2) {
        GameBoardFragment fragment = new GameBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LinearLayout boardView, boardView2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_board, container, false);
        boardView = view.findViewById(R.id.board);
        boardView2 = view.findViewById(R.id.board2);
        boolean[][] board = ((GameActivity) getActivity()).getBoard();
        boolean[][] board2 = ((GameActivity) getActivity()).getBoard2();
        int playerTurn = ((GameActivity) getActivity()).getPlayerTurn();
        drawBoard(board, boardView, playerTurn == 1);
        drawBoard(board2, boardView2, playerTurn == 2);
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

                if(board[i][j] || disabled) {
                    btn.setAlpha(0.5f);
                }
                if(disabled) {
                    btn.setEnabled(false);
                }

                int finalI = i;
                int finalJ = j;
                btn.setOnClickListener(e -> {
                    board[finalI][finalJ] = true;
                    getParentFragmentManager().popBackStack();
                });
                boardRow.addView(btn);
            }
            layout.addView(boardRow);
        }
    }
}