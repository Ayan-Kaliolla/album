package kz.kaliolla.album.module.album;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;
import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.ErrorModel;

/**
 * Created by akaliolla on 23.02.2018.
 */

public class AlbumFragment extends Fragment implements AlbumView{

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error)
    TextView error;
    private AlbumAdapter albumAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlbumPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        initRecycler();
        initListeners();
        loadAlbums();
    }

    private void initComponents() {
        presenter = new AlbumPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        albumAdapter = new AlbumAdapter(getContext());
    }

    private void initRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);
        mRecyclerView.setAdapter(albumAdapter);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAlbums();
            }
        });
    }

    private void loadAlbums() {
        mRecyclerView.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        presenter.loadAlbums();
    }

    @Override
    public void setAlbums(List<Album> albums) {
        swipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        albumAdapter.setAlbums(albums);
    }

    @Override
    public void onError(ErrorModel errorModel) {
        swipeRefreshLayout.setRefreshing(false);
        if (albumAdapter.getItemCount() == 0) {
            error.setVisibility(View.VISIBLE);
            error.setText(errorModel.getMessage());
        }else {
            Toast.makeText(getContext(), errorModel.getMessage() , Toast.LENGTH_LONG).show();
        }
    }
}
