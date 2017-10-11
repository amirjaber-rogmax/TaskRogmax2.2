package com.rogmax.amirjaber.taskrogmax22.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rogmax.amirjaber.taskrogmax22.models.Person;
import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Jaber on 9/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat
    private static final String LOG = "DatabaseHelper";

    // DB version
    private static final int DATABASE_VERSION = 3;

    // DB name and tables
    private static final String DATABASE_NAME = "userManager",

    // Table pets
    TABLE_PETS = "pets",
            KEY_TYPE = "type",
            KEY_SUB_TYPE = "sub_type",
            KEY_PET_NAME = "name",

    // Table people
    TABLE_PEOPLE = "people",
            KEY_PERSON_NAME = "person_name",
            KEY_AGE = "person_age",
            KEY_PET_ID = "pet_id",

    // Common table column
    KEY_ID = "id";

    // Table create statements
    private static final String CREATE_TABLE_PET = "CREATE TABLE " + TABLE_PETS + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TYPE + " TEXT," +
            KEY_SUB_TYPE + " TEXT," +
            KEY_PET_NAME + " TEXT" +
            ")";

    private static final String CREATE_TABLE_PERSON = "CREATE TABLE " + TABLE_PEOPLE + "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_PERSON_NAME + " TEXT," +
            KEY_AGE + " TEXT," +
            KEY_PET_NAME + " TEXT," +
            KEY_PET_ID + " INT, " +
            "FOREIGN KEY(" + KEY_PET_ID + ") REFERENCES " + TABLE_PETS + " (" + KEY_ID + ") " +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PET);
        db.execSQL(CREATE_TABLE_PERSON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PEOPLE);

        onCreate(db);
    }


    //#############################TABLE PET METHODS#############################//


    public void createPet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_SUB_TYPE, pet.getSubType());
        values.put(KEY_PET_NAME, pet.getName());


        Log.d(LOG, "createPet: Adding " + pet.getType() + " with property " + pet.getSubType() + " called " + pet.getName() + " to database " + TABLE_PETS);

        db.insert(TABLE_PETS, null, values);
        db.close();
    }

//    public Pet getPet(int id) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.query(TABLE_PETS, new String[]{KEY_ID, KEY_TYPE, KEY_SUB_TYPE, KEY_PET_NAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        assert cursor != null;
//        Pet pet = new Pet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
//        db.close();
//        cursor.close();
//        return pet;
//    }

    public void deletePet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_PETS, KEY_ID + "=?", new String[]{String.valueOf(pet.getId())});
        db.close();
    }

    public int getPetsCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PETS, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }


    public int updatePet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_SUB_TYPE, pet.getSubType());
        values.put(KEY_PET_NAME, pet.getName());

        int rowsAffected = db.update(TABLE_PETS, values, KEY_ID + "=?", new String[]{String.valueOf(pet.getId())});
        db.close();

        return rowsAffected;
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PETS, null);

        if (cursor.moveToFirst()) {
            do {
                pets.add(new Pet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return pets;
    }

//    public List<String> getAllSimplePets() {
//        ArrayList<String> petList = new ArrayList<>();
//        SQLiteDatabase db = getReadableDatabase();
//        String selectQuery = "SELECT * FROM " + TABLE_PETS;
//        Cursor cursor = db.rawQuery(selectQuery, null);
//        if (cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                String pName = cursor.getString(cursor.getColumnIndex("name"));
//                petList.add(pName);
//            }
//        }
//        db.close();
//        return petList;
//    }


    //#############################TABLE PERSON METHODS#############################//

    public void createPerson(Person person) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_PERSON_NAME, person.get_name());
        values.put(KEY_AGE, person.get_age());
        values.put(KEY_PET_NAME, person.get_pet());
        values.put(KEY_PET_ID, person.get_petId());

        Log.d(LOG, "createPerson: Adding " + person.get_name() + " age " + person.get_age() + " with pet " + person.get_pet() + " to database " + TABLE_PEOPLE + " with id " + KEY_PET_ID);

        db.insert(TABLE_PEOPLE, null, values);
        db.close();
    }

    public int getPeopleCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PEOPLE, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }

//    public List<Person> getAllPeople() {
//        List<Person> people = new ArrayList<>();
//
//        String selectQuery = "SELECT * FROM " + TABLE_PETS +
//                " INNER JOIN " + TABLE_PEOPLE +
//                " ON " + TABLE_PEOPLE + "." + KEY_PET_ID + " = " + TABLE_PETS + "." + KEY_ID;
//
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                people.add(new Person(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return people;
//    }


    public Pet findPetByName(String petName) {
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_PETS + " WHERE " + KEY_PET_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{petName});
        if (cursor.getCount() == 0 || cursor.getCount() > 1) {
            db.close();
            return null;
        }
        cursor.moveToNext();
        Pet pet = new Pet(cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_TYPE)),
                cursor.getString(cursor.getColumnIndex(KEY_SUB_TYPE)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_NAME)));
        db.close();
        return pet;
    }

}
