package com.example.videomarker.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.example.videomarker.R;
import com.example.videomarker.adapter.ViewPagerAdapter;
import com.example.videomarker.ui.main.bookmark.BookmarkFragment;
import com.example.videomarker.ui.main.folder.FolderFragment;
import com.example.videomarker.ui.main.my.MyFragment;
import com.example.videomarker.ui.main.video.VideoFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.SearchView;

import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

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
//        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#D5355F"));
        tabLayout.setTabTextColors(Color.parseColor("#73D5355F"), Color.parseColor("#D5355F"));

        checkPermission();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        videoFragment = new VideoFragment();
        folderFragment = new FolderFragment();
        bookmarkFragment = new BookmarkFragment();
        myFragment = new MyFragment();
        adapter.addFragment(videoFragment, "VIDEO");
        adapter.addFragment(folderFragment, "FOLDER");
        adapter.addFragment(bookmarkFragment, "BOOKMARK");
        adapter.addFragment(myFragment, "MY");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.select:
                Toast.makeText(this, "1111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting:
                Intent intent = new Intent(this, SettingActivity.class);
                //액티비티 시작!
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        searchView.setQueryHint("검색어를 입력하세요");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "검색을 완료했습니다.", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(), "입력중입니다.", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    public void checkPermission() {
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        || shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "Allow permission to access external storage", Toast.LENGTH_SHORT).show();
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
            } else {
//                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                setupViewPager(viewPager);
            }
        }
    }

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    // 앱을 실행하기 위해 필요한 퍼미션 정의
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};


    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if (requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {
                // 모든 퍼미션을 허용했다면 시작.
                setupViewPager(viewPager);
            } else {

                // 거부한 퍼미션이 있을경우
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {
                    Toast.makeText(this, "Need Permission to Run VideoMarker", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "You denied permission for VideoMarker. Need to Grant Permission at Settings to run this App", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}


//TODO: MediaStore변경에 따라 RecyclerView가 변화하도록 해야함.
// onBindViewHolder는 들어오는 데이터 값에 따라서 다른 결과값을 출력하도록 변경 필요.
// ContentLoader에서 들고있는데이터를 요청에 따라 일부분만 전달하도록 변경 필요.
// Permission 받은 후 실행시, 상단 메뉴바만 표시됨. 뭐가문젠지 모르겠음