package kz.kaliolla.album.module.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import kz.kaliolla.album.models.ErrorModel;
import kz.kaliolla.album.models.Photo;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class PhotoActivity extends AppCompatActivity implements PhotoView {

    public static final String ALBUM_ID = "id";
    private long albumId;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error)
    TextView error;
    private PhotoAdapter photoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotoPresenter presenter;
    private boolean clear = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        initToolbar();
        albumId = getIntent().getLongExtra(ALBUM_ID, 1);
        initComponents();
        initRecycler();
        initListeners();
        if (savedInstanceState == null) {
            loadPhotos(albumId);
        }else {
            setPhotos((List<Photo>) App.getSavedObject());
            clear = true;
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
    }

    private void initComponents() {
        presenter = new PhotoPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(this);
        photoAdapter = new PhotoAdapter(this);
    }

    private void initListeners() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPhotos(albumId);
            }
        });
    }

    private void initRecycler() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        swipeRefreshLayout.setColorSchemeResources(R.color.pink, R.color.indigo, R.color.lime);
        mRecyclerView.setAdapter(photoAdapter);
    }

    private void loadPhotos(long id) {
        swipeRefreshLayout.setRefreshing(true);
        mRecyclerView.setVisibility(View.GONE);
        presenter.loadPhotos(id);
    }

    @Override
    public void setPhotos(List<Photo> photos) {
        swipeRefreshLayout.setRefreshing(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        photoAdapter.setPhotos(photos);
    }

    @Override
    public void onError(ErrorModel errorModel) {
        swipeRefreshLayout.setRefreshing(false);
        if (mRecyclerView.getVisibility() == View.GONE) {
            error.setVisibility(View.VISIBLE);
            error.setText(errorModel.getMessage());
        } else {
            Toast.makeText(this, errorModel.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        App.saveObject(photoAdapter.getPhotos());
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
