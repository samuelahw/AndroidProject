package com.example.androidprojekti2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.example.androidprojekti2.databinding.FragmentThirdBinding;



public class ThirdFragment extends Fragment {

    private FragmentThirdBinding binding;
    boolean isChecked;
    private FilterOptionHolder filter;
    private GamesList gamelist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        gamelist = new ViewModelProvider(this.requireActivity()).get(GamesList.class);
        filter = new ViewModelProvider(this.requireActivity()).get(FilterOptionHolder.class);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lower = binding.lowerThan.getText().toString();
                String higher = binding.greaterThan.getText().toString();
                filter.setHigher(higher);
                filter.setLower(lower);
                if(isChecked) filter.changeSorter();
                filter.makeUrl();
                gamelist.resetList();
                Toast.makeText(getActivity(), "Changes applied", Toast.LENGTH_SHORT).show();

                NavHostFragment.findNavController(ThirdFragment.this)
                        .navigate(R.id.action_ThirdFragment_to_FirstFragment);

            }
        });

        binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                isChecked = !isChecked;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}