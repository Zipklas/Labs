package com.example.laba1.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laba1.database.Books;
import com.example.laba1.R;
import com.example.laba1.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    TextView Namebook;
    TextView Author;
    TextView Genre;
    TextView Year;
    TextView Rating;
    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Namebook = view.findViewById(R.id.book);
        Author = view.findViewById(R.id.author2);
        Genre = view.findViewById(R.id.genre);
        Year = view.findViewById(R.id.year);
        Rating = view.findViewById(R.id.rating);
        Bundle bundle = new Bundle();
        if(getArguments()!=null) {
            bundle= getArguments();
            Books b = (Books) bundle.getSerializable("key");
            Namebook.setText(b.getNameBook());
            Author.setText("Автор: " + b.getNameAuthor());
            Author.getPaint().setUnderlineText(true);
            Genre.setText("Жанр: " + b.getGenre().toString());
            Genre.getPaint().setUnderlineText(true);
            Year.setText("Год публикации : " + b.getPublicationDate().toString());
           Year.getPaint().setUnderlineText(true);
            Rating.setText("Рейтинг: " + b.getRating().toString());
            Rating.getPaint().setUnderlineText(true);
        }
        else{
            Namebook.setText("");
            Author.setText("" );
            Genre.setText("" );
            Year.setText("" );
            Rating.setText(" ");
        }





    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}