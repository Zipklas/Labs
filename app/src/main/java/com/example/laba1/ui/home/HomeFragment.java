package com.example.laba1.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.laba1.Userid;
import com.example.laba1.database.Books;
import com.example.laba1.MyApiRetro;
import com.example.laba1.R;
import com.example.laba1.adapter;
import com.example.laba1.database.Db;
import com.example.laba1.databinding.FragmentHomeBinding;
import com.example.laba1.listItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    String []NameBooks;
    String []AuthorsNames;
    RecyclerView recyclerView;
    List<Books> books = new ArrayList<Books>();
    private Db db;
    List<Books> Arraybooks2 = new ArrayList<Books>();
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //NameBooks=getResources().getStringArray(R.array.namebooks);
        //AuthorsNames=getResources().getStringArray(R.array.authorsname);
        // Arraybooks= new ArrayList<listItem>();
        //Getnamebook();
        recyclerView = view.findViewById(R.id.recyclerView);
        db = Room.databaseBuilder(view.getContext(), Db.class, "Db").allowMainThreadQueries().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/Lpirskaya/JsonLab/master/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApiRetro myApiRetro = retrofit.create(MyApiRetro.class);
        Call<List<Books>> call = myApiRetro.getBooks();
        call.enqueue(new Callback<List<Books>>() {
            @Override
            public void onResponse(Call<List<Books>> call, Response<List<Books>> response) {

                if (response.code() != 200) {
                    Log.e("error1", "1234");
                    return;
                }

                List<Books> Arraybooks2 = response.body();
                for (Books book : Arraybooks2) {
                    Log.i("books", "" + book);
                    Books currentBook = db.getBookDao().getBook(book.getNameBook(),book.getNameAuthor());
                    if (currentBook == null) {
                        db.getBookDao().addBook(book);
                    }
                }

                for (int i = 0; i < Arraybooks2.size(); i++) {

                    books = db.getBookDao().getAllBook();
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    //GetDataBooks();
                    RecyclerView.Adapter adapter = new adapter(getContext(), books, new adapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Books myItem) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("key", myItem);
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_navigation_home_to_navigation_dashboard, bundle);
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
                                    .setPositiveButton("Добавить в избранное", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Thread thread = new Thread(() -> {
                                                 Books books1 = db.getFavouriteBookDao().getFavouriteBookUser(Userid.user_id,myItem.getNameBook());
                                                if (books1 == null) {
                                                    db.getFavouriteBookDao().addFavouriteBook(Userid.user_id,myItem.getNameBook());
                                                }
                                            });
                                            thread.start();
                                            try {
                                                thread.join();
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }
                                    })
                                    .setView(R.layout.alert_dialog_view);
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }
                    });
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Books>> call, Throwable t) {
                Log.e("msg", "null");
            }
        });


    }

public void kal(List<Books> arraybooks2) {
    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            for(Books books:arraybooks2)
          db.getBookDao().addBook(books);
    }

    });
    thread.start();
}



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}