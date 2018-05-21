package com.hm.fomakin.passkeepres.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PassKeeperDbHelper extends SQLiteOpenHelper {

    private final Context context;
    private static String DB_NAME = "PassKeepers.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;
    private boolean needUpdate = false;

    public PassKeeperDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        if (Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.context = context;

        copyDataBase();

        try {
            updateDataBase();
        } catch (IOException ex) {
            throw new Error("UnableToUpdateDatabase");
        }
    }

    public void updateDataBase() throws IOException {
        if (needUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists()) {
                dbFile.delete();
            }

            copyDataBase();
            needUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    private void copyDBFile() throws IOException {
        InputStream in = context.getAssets().open(DB_NAME);
        OutputStream out = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        out.flush();
        out.close();
        in.close();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException ex) {
                throw new Error("ErrorCopyingDatabase");
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            needUpdate = true;
        }
    }
}
