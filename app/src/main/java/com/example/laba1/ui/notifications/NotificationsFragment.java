package com.example.laba1.ui.notifications;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.laba1.R;
import com.example.laba1.Userid;
import com.example.laba1.adapter;
import com.example.laba1.database.Books;
import com.example.laba1.database.Db;
import com.example.laba1.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;


public class NotificationsFragment extends Fragment {

    private List<Books> FavouriteBooks = new ArrayList<>();
    private FragmentNotificationsBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    private Db db;
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(view.getContext(), Db.class, "Db").build();
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewFavouriteBooks);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Thread thread1 = new Thread(() -> FavouriteBooks = db.getFavouriteBookDao().getFavouriteBookUser(Userid.user_id));
        thread1.start();
        try {
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        RecyclerView.Adapter adapter = new adapter(getContext(), FavouriteBooks, new adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Books myItem) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("key", myItem);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_notifications_to_navigation_dashboard, bundle);
            }

            @Override
            public void onItemLongClick(Books myItem) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true)
                        .setMessage("" + myItem.getNameAuthor())
                        .setTitle("" + myItem.getNameBook())
                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Убрать из избранного", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Thread thread = new Thread(() -> {
                                    db.getFavouriteBookDao().deleteFavouriteBook(Userid.user_id, myItem.getNameBook());

                                });
                                thread.start();
                                try {
                                    thread.join();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        })
                        .setView(R.layout.alert_dialog_view2);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}