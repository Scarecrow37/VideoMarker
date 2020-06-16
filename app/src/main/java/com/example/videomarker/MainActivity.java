package com.example.videomarker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import com.example.videomarker.ui.main.bookmark.BookmarkFragment;
import com.example.videomarker.ui.main.folder.FolderFragment;
import com.example.videomarker.ui.main.my.MyFragment;
import com.example.videomarker.ui.main.video.VideoFragment;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.widget.Toolbar;
import android.widget.SearchView;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    VideoFragment videoFragment;
    FolderFragment folderFragment;
    BookmarkFragment bookmarkFragment;
    MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); //툴바를 액션바(앱바)로 사용할 수 있는 메소드

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#D5355F"));
        tabLayout.setTabTextColors(Color.parseColor("#73D5355F"), Color.parseColor("#D5355F"));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        videoFragment=new VideoFragment();
        folderFragment=new FolderFragment();
        bookmarkFragment =new BookmarkFragment();
        myFragment=new MyFragment();
        adapter.addFragment(videoFragment,"VIDEO");
        adapter.addFragment(folderFragment,"FOLDER");
        adapter.addFragment(bookmarkFragment,"BOOKMARK");
        adapter.addFragment(myFragment,"MY");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.select:
                Toast.makeText(this, "1111",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Intent intent = new Intent(this, com.example.videomarker.SettingActivity.class);
                //액티비티 시작!
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.menu_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(),"검색을 완료했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(),"입력중입니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
        //123
    }
}