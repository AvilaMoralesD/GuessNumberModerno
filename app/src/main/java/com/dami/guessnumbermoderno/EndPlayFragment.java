package com.dami.guessnumbermoderno;

import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dami.guessnumbermoderno.databinding.FragmentEndPlayBinding;
import com.dami.guessnumbermoderno.gamelogic.GameManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Angle;
import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.Spread;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;

/**
 * Tercer fragment
 */
public class EndPlayFragment extends Fragment {

    FragmentEndPlayBinding binding;
    GameManager gm;
    int nTries;
    int goal;
    boolean achieved;
    private Drawable drawable;
    private Shape.DrawableShape drawableShape;
    private Party[] parties;

    public EndPlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEndPlayBinding.inflate(inflater);
        gm = EndPlayFragmentArgs.fromBundle(getArguments()).getGameman();
        nTries = gm.getCurrentTry();
        goal = gm.getGoal();
        achieved = EndPlayFragmentArgs.fromBundle(getArguments()).getResult();
        if (achieved) {
            celebrate();
        } else {
            mock();
        }
        binding.btnRestart.setOnClickListener(view -> NavHostFragment.findNavController(EndPlayFragment.this).navigate(R.id.action_endPlayFragment_to_configFragment));
        return binding.getRoot();
    }

    /**
     * Metodo que realiza los cambios necesarios si ha ganado el juego
     */
    private void celebrate() {
        ((MainActivity) getActivity()).changeBars(R.color.green_light);
        binding.tvEndTries.setText(String.valueOf(nTries));
        parties = createCelebrationParties();
        binding.btnEnd.setOnClickListener(v -> {
            MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.partyhorn);
            mp.start();
            binding.konfettiView.start(parties[0], parties[1], parties[2]);
        });
    }

    /**
     * Este metodo cambia los colores, textos, cofettis y sonidos a la version de perder
     */
    private void mock() {
        ((MainActivity) getActivity()).changeBars(R.color.red_light);
        binding.btnEnd.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.red_light));
        binding.btnRestart.setBackgroundColor(ContextCompat.getColor(this.requireContext(), R.color.red_dark));
        binding.tvInferior.setText(R.string.youLostInferior);
        binding.tvSuperior.setText(R.string.sorry);
        binding.btnEnd.setText(R.string.cryButton);
        binding.emoji.setText(R.string.cryingEmoji);
        binding.tvEndTries.setText(String.valueOf(goal));
        binding.tvtitletries.setText(R.string.actualGoal);
        parties = createCryingParties();
        binding.btnEnd.setOnClickListener(v -> {
            MediaPlayer mp = MediaPlayer.create(this.getContext(), R.raw.crying);
            mp.start();
            binding.konfettiView.start(parties[0], parties[1]);
        });
    }

    /**
     * Metodo que crea un array de objetos Party necesarios para lanzar confetti
     *
     * @return Array de 3 objetos Party en laterales y parte superior
     */
    private Party[] createCelebrationParties() {
        EmitterConfig emitterConfig = new Emitter(5, TimeUnit.SECONDS).perSecond(30);
        Party pLeft = new PartyFactory(emitterConfig)
                .angle(Angle.RIGHT - 70)
                .spread(Spread.SMALL)
                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                .setSpeedBetween(10f, 50f)
                .position(new Position.Relative(0.0, 0.8))
                .build();
        Party pRight = new PartyFactory(emitterConfig)
                .angle(Angle.LEFT + 70)
                .spread(Spread.SMALL)
                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                .setSpeedBetween(10f, 50f)
                .position(new Position.Relative(1.0, 0.8))
                .build();
        Party pTop = new PartyFactory(emitterConfig)
                .angle(Angle.BOTTOM)
                .spread(Spread.ROUND)
                .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE))
                .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                .setSpeedBetween(0f, 15f)
                .position(new Position.Relative(0.0, 0.0).between(new Position.Relative(1.0, 0.0)))
                .build();
        return new Party[]{pLeft, pRight, pTop};
    }

    /**
     * Metodo que crea un array de objetos Party necesarios para llorar
     *
     * @return Array de 2 objetos Party en ojos
     */
    private Party[] createCryingParties() {
        drawable = ContextCompat.getDrawable(this.requireContext(), R.drawable.drop);
        drawableShape = new Shape.DrawableShape(drawable, true);
        EmitterConfig emitterConfig = new Emitter(8, TimeUnit.SECONDS).perSecond(40);
        Party pLeft = new PartyFactory(emitterConfig)
                .angle(95)
                .spread(Spread.SMALL)
                .shapes(drawableShape)
                .colors(Collections.singletonList(R.color.tears))
                .setSpeedBetween(5f, 10f)
                .position(new Position.Relative(0.447, 0.52))
                .build();
        Party pRight = new PartyFactory(emitterConfig)
                .angle(85)
                .spread(Spread.SMALL)
                .shapes(drawableShape)
                .colors(Collections.singletonList(R.color.tears))
                .setSpeedBetween(5f, 10f)
                .position(new Position.Relative(0.55, 0.52))
                .build();
        return new Party[]{pLeft, pRight};
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}

