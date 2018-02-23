package kz.kaliolla.album.module.album;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import kz.kaliolla.album.R;

public class AlbumActivity extends AppCompatActivity{
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);
        initToolbar();
        Fragment fragment = new AlbumFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.content, fragment , AlbumFragment.class.getName()).commit();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
    }

}
