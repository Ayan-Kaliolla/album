package kz.kaliolla.album.module.photo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;

/**
 * Created by akaliolla on 22.02.2018.
 */

public class PhotoActivity extends AppCompatActivity  {

    public static final String ALBUM_ID = "id";
    private long id;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        initToolbar();
        id = getIntent().getLongExtra(ALBUM_ID, 1);
        getSupportFragmentManager().beginTransaction().add(R.id.content, PhotoFragment.getInstance(id), PhotoFragment.class.getName()).commit();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(true);
    }


}
