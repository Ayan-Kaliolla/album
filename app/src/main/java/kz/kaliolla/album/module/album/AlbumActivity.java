package kz.kaliolla.album.module.album;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;
import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.ErrorModel;

public class AlbumActivity extends AppCompatActivity implements AlbumView{
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error)
    TextView error;
    private AlbumAdapter albumAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlbumPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new AlbumPresenterImpl(this);
        initRecycler();
        initListeners();
        loadAlbums();
    }

    private void initListeners() {
        mRecyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
            @Override
            public void onViewRecycled(RecyclerView.ViewHolder holder) {
                presenter.loadAlbums();
            }
        });
    }


    private void initRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        albumAdapter = new AlbumAdapter(this);
        mRecyclerView.setAdapter(albumAdapter);
    }

    private void loadAlbums() {
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
        if (mRecyclerView.getVisibility() == View.GONE) {
            error.setVisibility(View.VISIBLE);
            error.setText(errorModel.getMessage());
        }else {
            Toast.makeText(this, errorModel.getMessage() , Toast.LENGTH_LONG).show();
        }
    }
}
