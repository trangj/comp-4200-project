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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Player1RollDieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Player1RollDieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Player1RollDieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Player1RollDieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Player1RollDieFragment newInstance(String param1, String param2) {
        Player1RollDieFragment fragment = new Player1RollDieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    TextView player, player_x, player_y;
    ImageView die_1, die_2;
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };

    Button continueButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                Log.d("Player:", String.valueOf(player_turn+1));
                Log.d("Roll:", String.valueOf(high_roll[player_turn]));
                if(player_turn == 0) player_x.setText(String.valueOf(player_score));
                else player_y.setText(String.valueOf(player_score));
                player_turn++;

                if(player_turn == 1) player.setText(R.string.text_player_2_turn);

                die_1.setImageResource(images[i1]);
                die_2.setImageResource(images[i2]);

                if(high_roll[0] == high_roll[1]) player_turn = 0;

                if(player_turn == 2) {
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