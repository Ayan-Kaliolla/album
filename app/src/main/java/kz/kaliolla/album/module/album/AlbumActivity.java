package kz.kaliolla.album.module.album;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;
import kz.kaliolla.album.app.App;
import kz.kaliolla.album.models.Album;
import kz.kaliolla.album.models.ErrorModel;

public class AlbumActivity extends AppCompatActivity implements  AlbumView{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error)
    TextView error;
    private AlbumAdapter albumAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AlbumPresenter presenter;
    private boolean clear = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        initToolbar();
        initComponents();
        initRecycler();
        initListeners();
        if (savedInstanceState == null){
            loadAlbums();
        }else {
            setAlbums((List<Album>) App.getSavedObject());
            clear = true;
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

    private void initComponents() {
        presenter = new AlbumPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(this);
        albumAdapter = new AlbumAdapter(this);
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
            Toast.makeText(this, errorModel.getMessage() , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        App.saveObject(albumAdapter.getAlbums());
        clear = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (clear){
            App.saveObject(null);
        }
    }
}
