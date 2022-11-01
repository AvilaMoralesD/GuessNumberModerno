package com.dami.guessnumbermoderno;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dami.guessnumbermoderno.databinding.FragmentEndPlayBinding;

public class EndPlayFragment extends Fragment {

    FragmentEndPlayBinding binding;

    public EndPlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEndPlayBinding.inflate(inflater);
        binding.btnRestart.setOnClickListener( view -> {
            NavHostFragment.findNavController(EndPlayFragment.this).navigate(R.id.action_endPlayFragment_to_configFragment);
                }

        );
        return binding.getRoot();
    }
}