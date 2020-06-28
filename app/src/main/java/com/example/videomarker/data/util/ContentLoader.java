package com.example.videomarker.data.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.videomarker.data.entities.Data;

import java.nio.file.spi.FileSystemProvider;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ContentLoader {

//    public static final List<Data> datas = new ArrayList<>();
//    private static int id;
//    private static String name;
//    private static String dur;
//    private static int size;
//    private static String mime;
//    private static String added;

    public static List<Data> getContent(Context context) {
        List<Data> datas = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver(); //데이터를 가져오는 커넥터
        ContentLoader re = new ContentLoader();

        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String projections[] = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.MIME_TYPE,
                MediaStore.Video.Media.DATE_ADDED
        };
        Cursor c = resolver.query(uri, projections, null, null, null);

        if (c != null) {
            while (c.moveToNext()) {
                int index = c.getColumnIndex(projections[0]);
                int id = c.getInt(index);

                index = c.getColumnIndex(projections[1]);
                String name = c.getString(index);

                index = c.getColumnIndex(projections[2]);
                String millisDur = c.getString(index);
                long millis = Long.parseLong(millisDur);

                index = c.getColumnIndex(projections[3]);
                String bytesize = c.getString(index);
                int size = Integer.parseInt(bytesize);

                index = c.getColumnIndex(projections[4]);
                String mime = c.getString(index);

                index = c.getColumnIndex(projections[5]);
                String added = c.getString(index);

                Data data = new Data();

                data.setResId(id);
                data.setName(name);

                String changedTime = re.getReadableDuration(millis);
                data.setDur(changedTime);

                String changedSize = re.getReadableFileSize(size);
                data.setSize(changedSize);

                data.setMime(mime);
                data.setAdded(added);

                datas.add(data);
            }
        }
        c.close();
        return datas;
    }

    public String getReadableDuration(long millis) {
        //TODO: 60분 미만이어도 00:00:00 로 표시되는 현상
        String dur = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return dur;
    }

    public String getReadableFileSize(int size) {
        final int BYTES_IN_KILOBYTES = 1024;
        final DecimalFormat dec = new DecimalFormat("###.#");
        final String KILOBYTES = "KB";
        final String MEGABYTES = "MB";
        final String GIGABYTES = "GB";
        float fileSize = 0;
        String suffix = KILOBYTES;

        if(size > BYTES_IN_KILOBYTES) {
            fileSize = size / BYTES_IN_KILOBYTES;
            if(fileSize > BYTES_IN_KILOBYTES) {
                fileSize = fileSize / BYTES_IN_KILOBYTES;
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize = fileSize / BYTES_IN_KILOBYTES;
                    suffix = GIGABYTES;
                } else {
                    suffix = MEGABYTES;
                }
            }
        }
        return String.valueOf(dec.format(fileSize) + suffix);
    }

    //TODO: 이 메소드는 android api 29 부터 permission을 요구함 https://codechacha.com/ko/android-mediastore-remove-media-files/ 참고
    public void deleteContent(Context context, Uri contentUri, String id) {
        String mSelection = MediaStore.Video.Media._ID + "=?";
        String[] mSelectionsArgs = new String[] {id};

        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.delete(contentUri, mSelection, mSelectionsArgs);

    }

    public final int modifyContent(Context context, String updateValue, Uri contentUri) {

        final ContentValues value = new ContentValues();
        value.put(MediaStore.Video.Media.DISPLAY_NAME, updateValue);

        ContentResolver contentResolver = context.getContentResolver();
        int res = contentResolver.update(contentUri, value, null, null);
        return res;
        //TODO: 값은 잘 전달되고 로직 문제있음. Rename 사용해볼것. 또는 https://stackoverflow.com/questions/55314476/how-to-rename-a-file-in-android-knowing-only-its-media-content-uri 참고(DocumentContract)
    }

}
