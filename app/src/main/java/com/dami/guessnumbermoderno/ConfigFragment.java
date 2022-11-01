package com.dami.guessnumbermoderno;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.dami.guessnumbermoderno.databinding.FragmentConfigBinding;
import com.dami.guessnumbermoderno.gamelogic.GameManager;

/**
 * Primer fragment del juego, en este se introducen los datos del nombre de usuario y nemero de intentos del juego
 */
public class ConfigFragment extends Fragment {

    private FragmentConfigBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigBinding.inflate(inflater);
        binding.setGameManager(new GameManager());
        ((MainActivity) getActivity()).changeBars(R.color.light_blue, R.color.dark_blue, R.string.configBar);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btConfig.setOnClickListener(v -> {
            startGame();
        });
    }

    /**
     * Inicia el juego pasando al siguiente Fragment si el campo de nombre esta relleno
     */
    public void startGame() {
        String name = binding.etConfigInputName.getText().toString().trim();
        String tries = binding.etConfigInputTries.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this.getActivity(), R.string.emptyName, Toast.LENGTH_SHORT).show();
            return;
        }
        if (tries.isEmpty() || tries.equals("0")) {
            binding.getGameManager().setNTries(7);
        }
        ConfigFragmentDirections.ActionConfigFragmentToPlayFragment action = ConfigFragmentDirections.actionConfigFragmentToPlayFragment(binding.getGameManager());
        NavHostFragment.findNavController(ConfigFragment.this).navigate(action);
    }

    //-----------------------------------------


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}