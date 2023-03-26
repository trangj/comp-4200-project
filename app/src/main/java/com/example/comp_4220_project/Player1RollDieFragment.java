package com.example.comp_4220_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class Player1RollDieFragment extends Fragment {

    public Player1RollDieFragment() {
        // Required empty public constructor
    }

    TextView player, player_x, player_y;
    ImageView die_1, die_2;
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };

    Button continueButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_player1_roll_die, container, false);
        die_1 = (ImageView) view.findViewById(R.id.die_1_image);
        die_2 = (ImageView) view.findViewById(R.id.die_2_image);
        player = (TextView) view.findViewById(R.id.player_id);
        player_x = (TextView) view.findViewById(R.id.player_x_score);
        player_y = (TextView) view.findViewById(R.id.player_y_score);
        player_x.setBackgroundColor(Color.GREEN);
        player_y.setBackgroundColor(Color.LTGRAY);
        player.setText(R.string.text_player_1_turn);
        int i1 = rand_n(0, 5);
        int i2 = rand_n(0, 5);

        int x_score = 0;
        int y_score = 0;

        die_1.setImageResource(images[i1]);
        die_2.setImageResource(images[i2]);

        player_x.setText(String.valueOf(x_score));
        player_y.setText(String.valueOf(y_score));

        continueButton = view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            int player_turn = 0;
            int player_score = 0;
            final int[] high_roll = { 0, 0 };
            @Override
            public void onClick(View view) {

                int i1 = rand_n(0, 5);
                int i2 = rand_n(0, 5);
                player_score = (i1 + 1) + (i2 + 1);
                high_roll[player_turn] = player_score;
                if(player_turn == 0) player_x.setText(String.valueOf(player_score));
                else player_y.setText(String.valueOf(player_score));
                player_turn++;

                if(player_turn == 1) player.setText(R.string.text_player_2_turn);
                if(player_turn == 2) player.setText(R.string.text_player_1_turn);

                die_1.setImageResource(images[i1]);
                die_2.setImageResource(images[i2]);

                if(high_roll[0] == high_roll[1]) player_turn = 0;

                if(player_turn == 2) {

                    if(high_roll[0] > high_roll[1]) ((GameActivity) requireActivity()).setPlayerTurn(1);
                    else ((GameActivity) requireActivity()).setPlayerTurn(2);
                    GameOptionsFragment f = new GameOptionsFragment();
                    FragmentManager fm = getParentFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frame, f);
                    ft.commit();
                }
            }
        });
        return view;
    }
    public int rand_n(int min, int max) { return (int) ((Math.random() * (max - min)) + min); }
}