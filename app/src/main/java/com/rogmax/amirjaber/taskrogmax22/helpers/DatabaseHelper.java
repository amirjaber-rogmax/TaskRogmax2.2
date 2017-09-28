package com.rogmax.amirjaber.taskrogmax22.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rogmax.amirjaber.taskrogmax22.models.Pet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amir Jaber on 9/28/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "userManager",
            TABLE_PETS = "pets",
            KEY_ID = "id",
            KEY_TYPE = "type",
            KEY_SUB_TYPE = "sub_type",
            KEY_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PETS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TYPE + " TEXT," + KEY_SUB_TYPE + " TEXT," + KEY_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);

        onCreate(db);
    }


    //----------------------TABLE PET METHODS---------------------------


    public void createPet(Pet pet) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TYPE, pet.getType());
        values.put(KEY_SUB_TYPE, pet.getSubType());
        values.put(KEY_NAME, pet.getName());

        db.insert(TABLE_PETS, null, values);
        db.close();
    }

    public Pet getPet(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PETS, new String[]{KEY_ID, KEY_TYPE, KEY_SUB_TYPE, KEY_NAME}, KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Pet pet = new Pet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        db.close();
        cursor.close();
        return pet;
    }

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
        values.put(KEY_NAME, pet.getName());

        return db.update(TABLE_PETS, values, KEY_ID + "=?", new String[]{String.valueOf(pet.getId())});
    }

    public List<Pet> getAllPets() {
        List<Pet> pets = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PETS, null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                pets.add(pet);
            } while (cursor.moveToNext());
        }
        return pets;
    }
}
