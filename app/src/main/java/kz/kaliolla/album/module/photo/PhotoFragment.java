package kz.kaliolla.album.module.photo;

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
import kz.kaliolla.album.R;
import kz.kaliolla.album.models.ErrorModel;
import kz.kaliolla.album.models.Photo;

/**
 * Created by akaliolla on 23.02.2018.
 */

public class PhotoFragment extends Fragment implements PhotoView{
    private static final String ALBUM_ID = "id";
    public static long albumId;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.error)
    TextView error;
    private PhotoAdapter photoAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private PhotoPresenter presenter;

    public static Fragment getInstance(long albumId){
        PhotoFragment fragment = new PhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ALBUM_ID, albumId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        albumId = getArguments().getLong(ALBUM_ID);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initComponents();
        initRecycler();
        initListeners();
        loadPhotos(albumId);
    }

    private void initComponents() {
        presenter = new PhotoPresenterImpl(this);
        mLayoutManager = new LinearLayoutManager(getContext());
        photoAdapter = new PhotoAdapter(getContext());
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
            Toast.makeText(getContext(), errorModel.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
