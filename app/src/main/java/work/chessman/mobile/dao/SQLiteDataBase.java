package work.chessman.mobile.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import work.chessman.mobile.objects.Resume;
import work.chessman.mobile.objects.Vacancy;
import work.chessman.mobile.other.CustomDataResume;
import work.chessman.mobile.other.CustomDataVacancy;

import java.util.List;

public class SQLiteDataBase extends SQLiteOpenHelper {

    final public static String NAME_DATABASE = "HHruMobileDataBase";
    final public static String NAME_PARAM = "SQLiteDataBase";

    final private RepoVacancy repoVacancy = new RepoVacancy();
    final private RepoResume repoResume = new RepoResume();

    public SQLiteDataBase(Context context) {
        // конструктор суперкласса
        super(context, NAME_DATABASE, null, 1);
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
        repoResume.dropTable(db);
        onCreate(db);
    }
}
