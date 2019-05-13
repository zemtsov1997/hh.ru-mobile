package com.example.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myapplication.objects.Resume;
import com.example.myapplication.objects.Vacancy;

import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    final public static String NAME_DATABASE = "WorkDataBase";
    final public static String NAME_PARAM = "DBHelper";

    final private RepoVacancy repoVacancy = new RepoVacancy();
    final private RepoResume repoResume = new RepoResume();

    public DBHelper(Context context) {
        // конструктор суперкласса
        super(context, NAME_DATABASE, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // создаем таблицы с полями
        repoVacancy.createTable(db);
        repoResume.createTable(db);
        fillData(db);
    }

    private void fillData(SQLiteDatabase db) {
        //разбираем файл data.xml лежащий например в assets
        //и вставляем данные в базу
        //либо читаем лежащие там же sql-скрипты и выполняем с помощью все того же db.execSQL() или аналогично

        List<Vacancy> listVacancy = CustomDataVacancy.getAll();
        for(int i = 0; i < listVacancy.size(); i++) repoVacancy.insert(listVacancy.get(i), db);

        List<Resume> listResume = CustomDataResume.getAll();
        for(int i = 0; i < listResume.size(); i++) repoResume.insert(listResume.get(i), db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт
        repoVacancy.dropTable(db);
        db.execSQL(RepoResume.SQL_DROP_TABLE_RESUME);
        onCreate(db);
    }
}