package com.example.comp_4220_project;
import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class RollDieFragment extends Fragment {

    public RollDieFragment() {
        // Required empty public constructor
    }

    TextView player, player_x, player_y;
    ImageView die_1, die_2;
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };
    Button continueButton;
    View anim, score;
    MediaPlayer roll;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roll_die, container, false);
        die_1 = (ImageView) view.findViewById(R.id.die_1_image);
        die_2 = (ImageView) view.findViewById(R.id.die_2_image);
        player = (TextView) view.findViewById(R.id.player_id);
        player_x = (TextView) view.findViewById(R.id.player_x_score);
        player_y = (TextView) view.findViewById(R.id.player_y_score);
        anim = (LinearLayout) view.findViewById(R.id.animation_layout);
        score = (LinearLayout) view.findViewById(R.id.score_layout);
        roll = MediaPlayer.create(getContext(), R.raw.roll);

        sp = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        editor = sp.edit();
        loadPreferences();

        int player_1_score = ((GameActivity) requireActivity()).getPlayer_1_score();
        int player_2_score = ((GameActivity) requireActivity()).getPlayer_2_score();
        int turn = ((GameActivity) requireActivity()).getPlayerTurn();

        int i1 = rand_n(0, 5);
        int i2 = rand_n(0, 5);

        die_1.setImageResource(images[i1]);
        die_2.setImageResource(images[i2]);
        player_turn(turn, player_1_score, player_2_score);

        continueButton = view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            int p1_score = player_1_score;
            int p2_score = player_2_score;
            int player_score = 0;
            @Override
            public void onClick(View view) {

                int i1 = rand_n(0, 5);
                int i2 = rand_n(0, 5);
                die_1.setImageResource(images[i1]);
                die_2.setImageResource(images[i2]);
                player_score = (i1 + 1) + (i2 + 1);

                RollDieFragment rollDieFragment = new RollDieFragment();
                GameOptionsFragment gameOptionsFragment = new GameOptionsFragment();
                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if(turn == 1) {
                    sound(roll);
                    ((GameActivity) requireActivity()).setPlayer_1_score(player_score);
                    p1_score = ((GameActivity) requireActivity()).getPlayer_1_score();
                    player_turn(turn, p1_score, p2_score);
                    ((GameActivity) requireActivity()).setPlayerTurn(2);

                    fm = getParentFragmentManager();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.frame, rollDieFragment);
                    ft.commit();
                }

                if(turn == 2){
                    sound(roll);
                    ((GameActivity) requireActivity()).setPlayer_2_score(player_score);
                    p2_score = ((GameActivity) requireActivity()).getPlayer_2_score();
                    player_turn(turn, p1_score, p2_score);
                    if(p1_score == p2_score) {
                        ((GameActivity) requireActivity()).set_zero();
                        ((GameActivity) requireActivity()).setPlayerTurn(1);
                        fm = getParentFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frame, rollDieFragment);
                        ft.commit();
                    }
                    else if (p1_score > p2_score) {
                        toast_display(1);
                        ((GameActivity) requireActivity()).set_zero();
                        ((GameActivity) requireActivity()).setPlayerTurn(1);
                        ft.replace(R.id.frame, gameOptionsFragment);
                        ft.commit();
                    }
                    else {
                        toast_display(2);
                        ((GameActivity) requireActivity()).set_zero();
                        ((GameActivity) requireActivity()).setPlayerTurn(2);
                        fm = getParentFragmentManager();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.frame, gameOptionsFragment);
                        ft.commit();
                    }
                }
            }
        });
        return view;
    }
    public int rand_n(int min, int max) { return (int) ((Math.random() * (max - min)) + min); }
    public void player_turn(int turn, int score_1, int score_2){

        String steel_blue = "#3581B8";
        String english_violet = "#44344F";

        if(turn == 1){
            player_x.setBackgroundColor(Color.parseColor(steel_blue));

            player_x.setText(String.valueOf(score_1));
            player_x.setTextColor(Color.WHITE);
            player_y.setBackgroundColor(Color.parseColor(english_violet));

            player_y.setText(String.valueOf(score_2));
            player_y.setTextColor(Color.WHITE);
            player.setText(R.string.text_player_1_turn);
        }

        if(turn == 2){
            player_x.setBackgroundColor(Color.parseColor(english_violet));

            player_x.setText(String.valueOf(score_2));
            player_x.setTextColor(Color.WHITE);
            player_y.setBackgroundColor(Color.parseColor(steel_blue));

            player_y.setText(String.valueOf(score_1));
            player_y.setTextColor(Color.WHITE);
            player.setText(R.string.text_player_2_turn);
        }
    }

    public void toast_display(int i){
        String t = (i == 1) ? "Player 1 has High Score. Player 1 goes first!!" : "Player 2 has High Score. Player 2 goes first!!" ;
        Toast toast = Toast.makeText(getContext(), t, Toast.LENGTH_SHORT);
        toast.show();
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
        if(roll != null){
            roll.release();
            roll = null;
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
            anim.setBackgroundColor(getResources().getColor(R.color.black));
            score.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else{
            anim.setBackgroundColor(getResources().getColor(R.color.yellow));
            score.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }
}