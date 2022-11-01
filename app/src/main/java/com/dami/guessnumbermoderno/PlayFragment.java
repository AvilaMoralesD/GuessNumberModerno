package com.dami.guessnumbermoderno;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.dami.guessnumbermoderno.databinding.FragmentPlayBinding;
import com.dami.guessnumbermoderno.databinding.FragmentPlayBindingImpl;

import nl.dionsegijn.konfetti.core.Angle;

public class PlayFragment extends Fragment {

    private FragmentPlayBinding binding;
    int lastTry;
    int lastArrowDirection = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPlayBinding.inflate(inflater);
        binding.setGameManager(PlayFragmentArgs.fromBundle(getArguments()).getGameman());
        ((MainActivity) getActivity()).changeBars(R.color.light_blue, R.color.dark_blue, R.string.theGame);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btPlay.setOnClickListener(v -> {
            playGame();
        });
    }

    /**
     * Método asociado al botón de juego que emite un intento con el número dado en el EditText si es posible,
     * si no, finaliza el juego e indica si se ha perdido o ganado.
     */
    private void playGame() {
        String text = binding.etPlayNextGuess.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this.getActivity(), R.string.emptyGuess, Toast.LENGTH_SHORT).show();
            return;
        }
        int guessResult = binding.getGameManager().makeAGuess(Integer.parseInt(text));
        if (guessResult == 0) {
            PlayFragmentDirections.ActionPlayFragmentToEndPlayFragment action = PlayFragmentDirections.actionPlayFragmentToEndPlayFragment(binding.getGameManager(),true);
            NavHostFragment.findNavController(this).navigate(action);
            return;
        } else {   //Si no ha acertado actualiza los Views
            lastArrowDirection = updateViews(guessResult,lastArrowDirection);
        }

        if (!binding.getGameManager().canMakeGuess()) {   //Si se ha quedado sin intentos hacer Intent perdedor
            PlayFragmentDirections.ActionPlayFragmentToEndPlayFragment action = PlayFragmentDirections.actionPlayFragmentToEndPlayFragment(binding.getGameManager(),false);
            NavHostFragment.findNavController(this).navigate(action);
            return;
        }
        makeVisible();
    }

    /**
     * Método que actualiza la parte visual de la Activity, tanto los campos de texto como la flecha
     * @param result Si el número introducido es menor (-1) o mayor (1) que el objetivo
     * @param lastArrowDirection Indica el ángulo de la flecha tras el intento anterior
     * @return El ángulo de la flecha en este intento
     */
    private int updateViews(int result, int lastArrowDirection) {

        if (result == -1) {
            binding.imgArrow.startAnimation(rotateImage(lastArrowDirection, Angle.TOP));
        } else {
            binding.imgArrow.startAnimation(rotateImage(lastArrowDirection,Angle.BOTTOM));
        }
       /* vTries.setText(String.valueOf(gameman.getRemainingTries()));*/
        binding.tvPlayPreviousGuess.setText(String.valueOf(binding.etPlayNextGuess.getText()));
        binding.etPlayNextGuess.setText("");
        return result == -1? Angle.TOP : Angle.BOTTOM;
    }

    /**
     * Método que anima la flecha indicadora
     * @param fromAngle Angulo de inicio
     * @param toAngle Angulo final
     * @return Objeto de rotación
     */
    private RotateAnimation rotateImage(int fromAngle, int toAngle){
        RotateAnimation rotate = new RotateAnimation(fromAngle, toAngle, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(750);
        rotate.setFillAfter(true);
        rotate.setInterpolator(new LinearInterpolator());
        return rotate;
    }

    /**
     * Método que reactiva la visibilidad de los objetos en pantalla
     */
    public void makeVisible() {
        if (!binding.tvPlayLabelPreviousGuess.isShown()) {
            binding.tvPlayLabelPreviousGuess.setVisibility(View.VISIBLE);
            binding.tvPlayPreviousGuess.setVisibility(View.VISIBLE);
            binding.imgArrow.setVisibility(View.VISIBLE);
            binding.tvPlayLabelTriesLeft.setVisibility(View.VISIBLE);
            binding.tvPlayTriesLeft.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}