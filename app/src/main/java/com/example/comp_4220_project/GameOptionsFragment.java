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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class GameOptionsFragment extends Fragment {
    public GameOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    TextView title, player_x, player_y;
    Button removeTileButton, restoreTileButton;
    ImageView die_1, die_2;
    View main;//LinearLayout
    MediaPlayer miss, roll, press;
    Boolean m_mode, fx_mode, dark_mode;//holds app settings
    SharedPreferences sp;// used to store settings as preferences
    SharedPreferences.Editor editor;// used to edit preferences
    int[] images = {R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five, R.drawable.six };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_options, container, false);
        title = view.findViewById(R.id.game_option_title);
        player_x = (TextView) view.findViewById(R.id.player_x_score);
        player_y = (TextView) view.findViewById(R.id.player_y_score);
        die_1 = (ImageView) view.findViewById(R.id.die_1_image);
        die_2 = (ImageView) view.findViewById(R.id.die_2_image);
        removeTileButton = (Button) view.findViewById(R.id.removeTileButton);
        restoreTileButton = (Button) view.findViewById(R.id.restoreTileButton);
        main = (LinearLayout) view.findViewById(R.id.main_layout);

        press = MediaPlayer.create(getContext(), R.raw.press);
        miss = MediaPlayer.create(getContext(), R.raw.miss);
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

        removeTileButton.setOnClickListener(new View.OnClickListener() {
            int p1_score = 0;
            int p2_score = 0;
            final int pt = turn;
            @Override
            public void onClick(View view) {
                int i1 = rand_n(0, 5);
                int i2 = rand_n(0, 5);
                int i3 = rand_n(0, 5);

                int player_x_score = (i1 + 1) + (i2 + 1);
                int player_y_score = (i3 + 1);

                die_1.setImageResource(images[i1]);
                die_2.setImageResource(images[i2]);

                FragmentManager fm = getParentFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if(pt == 1) {
                    ((GameActivity) requireActivity()).setPlayer_1_score(player_x_score);
                    ((GameActivity) requireActivity()).setPlayer_2_score(player_y_score);
                    p1_score = ((GameActivity) requireActivity()).getPlayer_1_score();
                    p2_score = ((GameActivity) requireActivity()).getPlayer_2_score();
                    player_turn(pt, p1_score, p2_score);

                    if(p1_score > p2_score){
                        sound(roll);
                        ((GameActivity) requireActivity()).set_zero();
                        GameBoardFragment f = GameBoardFragment.newInstance("remove");
                        ft.addToBackStack(null);
                        ft.replace(R.id.frame, f);
                        ft.commit();
                    }

                    else {
                        sound(miss);
                        ((GameActivity) requireActivity()).set_zero();
                        ((GameActivity) requireActivity()).setPlayerTurn(2);
                        GameOptionsFragment g = new GameOptionsFragment();
                        ft.addToBackStack(null);
                        ft.replace(R.id.frame, g);
                        ft.commit();
                    }
                }

                else {
                    ((GameActivity) requireActivity()).setPlayer_2_score(player_x_score);
                    ((GameActivity) requireActivity()).setPlayer_1_score(player_y_score);
                    p1_score = ((GameActivity) requireActivity()).getPlayer_1_score();
                    p2_score = ((GameActivity) requireActivity()).getPlayer_2_score();
                    player_turn(pt, p1_score, p2_score);

                    if(p2_score > p1_score){
                        sound(roll);
                        ((GameActivity) requireActivity()).set_zero();
                        GameBoardFragment f = GameBoardFragment.newInstance("remove");
                        ft.addToBackStack(null);
                        ft.replace(R.id.frame, f);
                        ft.commit();
                    }
                    else {
                        sound(miss);
                        ((GameActivity) requireActivity()).set_zero();
                        ((GameActivity) requireActivity()).setPlayerTurn(1);
                        GameOptionsFragment g = new GameOptionsFragment();
                        ft.addToBackStack(null);
                        ft.replace(R.id.frame, g);
                        ft.commit();
                    }
                }
            }
        });

        GameActivity a = ((GameActivity) getActivity());
        if(turn == 1 && a.getPlayer1NumRestored() >= a.getBoard().length - 1) {
            restoreTileButton.setEnabled(false);
        }
        if(turn == 2 && a.getPlayer2NumRestored() >= a.getBoard().length - 1) {
            restoreTileButton.setEnabled(false);
        }

        restoreTileButton.setOnClickListener(e -> {
            sound(press);
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
        view.post(() -> postponeEnterTransition(1500, TimeUnit.MILLISECONDS));
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
            title.setText(R.string.text_player_1_turn);
        }

        if(turn == 2){
            player_x.setBackgroundColor(Color.parseColor(english_violet));
            player_x.setText(String.valueOf(score_2));
            player_x.setTextColor(Color.WHITE);
            player_y.setBackgroundColor(Color.parseColor(steel_blue));
            player_y.setText(String.valueOf(score_1));
            player_y.setTextColor(Color.WHITE);
            title.setText(R.string.text_player_2_turn);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(press != null){
            press.release();
            press = null;
        }
        if(miss != null){
            miss.release();
            miss = null;
        }
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
            main.setBackgroundColor(getResources().getColor(R.color.black));
        }
        else{
            main.setBackgroundColor(getResources().getColor(R.color.yellow));
        }
    }

}