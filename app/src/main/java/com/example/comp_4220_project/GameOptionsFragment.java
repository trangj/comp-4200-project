package com.example.comp_4220_project;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.concurrent.TimeUnit;

public class GameOptionsFragment extends Fragment {
    public GameOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView title;
    Button removeTileButton;
    Button restoreTileButton;
    TextView player_x, player_y;
    ImageView die_1, die_2;
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);
        title = view.findViewById(R.id.game_option_title);
        int player_turn = ((GameActivity) getActivity()).getPlayerTurn();
        if(player_turn == 1) title.setText(R.string.text_player_1_turn);
        else title.setText(R.string.text_player_2_turn);

        removeTileButton = view.findViewById(R.id.removeTileButton);
        restoreTileButton = view.findViewById(R.id.restoreTileButton);

        die_1 = (ImageView) view.findViewById(R.id.die_1_image);
        die_2 = (ImageView) view.findViewById(R.id.die_2_image);
        player_x = (TextView) view.findViewById(R.id.player_x_score);
        player_y = (TextView) view.findViewById(R.id.player_y_score);
        player_x.setBackgroundColor(Color.GREEN);
        player_y.setBackgroundColor(Color.LTGRAY);
        int i1 = rand_n(0, 5);
        int i2 = rand_n(0, 5);

        int x_score = 0;
        int y_score = 0;

        die_1.setImageResource(images[i1]);
        die_2.setImageResource(images[i2]);

        player_x.setText(String.valueOf(x_score));
        player_y.setText(String.valueOf(y_score));

        removeTileButton.setOnClickListener(new View.OnClickListener() {
            int player_x_score = 0;
            int player_y_score = 0;
            final int[] high_roll = { 0, 0 };
            @Override
            public void onClick(View view) {
                int i1 = rand_n(0, 5);
                int i2 = rand_n(0, 5);
                int i3 = rand_n(0, 5);

                die_1.setImageResource(images[i1]);
                die_2.setImageResource(images[i2]);

                if(player_turn == 1) {
                    player_x_score = (i1 + 1) + (i2 + 1);
                    player_y_score = i3;
                    high_roll[0] = player_x_score;
                    high_roll[1] = player_y_score;
                    player_x.setText(String.valueOf(player_x_score));
                    player_y.setText(String.valueOf(player_y_score));
                }

                else {
                    player_y_score = (i1 + 1) + (i2 + 1);
                    player_x_score = i3;
                    high_roll[0] = player_x_score;
                    high_roll[1] = player_y_score;
                    player_y.setText(String.valueOf(player_y_score));
                    player_x.setText(String.valueOf(player_x_score));
                }

                if(high_roll[0] > high_roll[1]){
                    FragmentManager fm = getParentFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    GameBoardFragment f = GameBoardFragment.newInstance("remove");
                    ft.replace(R.id.frame, f);
                    ft.addToBackStack(null);
                    ft.commit();
                }

                else {
                    if(player_turn == 1) ((GameActivity) getActivity()).setPlayerTurn(2);
                    else ((GameActivity) getActivity()).setPlayerTurn(1);
                    FragmentManager fm = getParentFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    GameOptionsFragment g = new GameOptionsFragment();
                    ft.replace(R.id.frame, g);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            }
        });

        restoreTileButton.setOnClickListener(e -> {
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            GameBoardFragment f = GameBoardFragment.newInstance("restore");
            ft.replace(R.id.frame, f);
            ft.addToBackStack(null);
            ft.commit();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) return;
        postponeEnterTransition();
        view.setBackgroundColor(Color.WHITE);
        view.post(() -> postponeEnterTransition(2000, TimeUnit.MILLISECONDS));
    }
    public int rand_n(int min, int max) { return (int) ((Math.random() * (max - min)) + min); }
}