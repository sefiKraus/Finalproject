package com.sefy.finalproject.Model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.URLUtil;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Lior on 8/27/2017.
 */

public class ImageManager {

/*Callback interfaces for our async methods*/


    public interface SaveImageListener{
        void complete(String url);
        void fail();
    }

    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }


    /*try to guess the name of a file from its url*/
    private String getLocalImageFileName(String url) {
        String name = URLUtil.guessFileName(url, null, null);
        return name;
    }






    /*saves image locally*/
    private void saveImageToFile(Bitmap imageBitmap, String imageFileName){
        try {
            File dir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (!dir.exists()) {
                dir.mkdir();
                Log.d("-====DEBUG====-","dir doesnt exist creating a new one");
            }
            File imageFile = new File(dir,imageFileName);
            imageFile.createNewFile();
            OutputStream out = new FileOutputStream(imageFile);
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
            addPicureToGallery(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*loads image from local storage*/
    private Bitmap loadImageFromFile(String imageFileName){
        Bitmap bitmap = null;
        try {
            File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File imageFile = new File(dir,imageFileName);
            InputStream inputStream = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(inputStream);
            Log.d("tag","got image from cache: " + imageFileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }



    /*add an image to the phone's picture gallery*/
    private void addPicureToGallery(File imageFile){
        //add the picture to the gallery so we dont need to manage the cache size
        /*
        Intent mediaScanIntent = new
                Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(imageFile);
        mediaScanIntent.setData(contentUri);
        getAppContext().sendBroadcast(mediaScanIntent);
*/
    }





    /*save image to firebase*/
    private void saveImage(Bitmap imageBmp, String name, final SaveImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference imagesRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.fail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                @SuppressWarnings("VisibleForTests") Uri downloadUrl = taskSnapshot.getDownloadUrl();
                listener.complete(downloadUrl.toString());
            }
        });
    }





    /*download image from firebase*/
    private void getImage(String url, final GetImageListener listener){
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            Log.d("TAG", url);
            StorageReference httpsReference = storage.getReferenceFromUrl(url);
            final long ONE_MEGABYTE = 1024 * 1024;
            httpsReference.getBytes(3 * ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    listener.onSuccess(image);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exception) {
                    Log.d("TAG", exception.getMessage());
                    listener.onFail();
                }
            });
        }
        catch(Exception e){

            e.printStackTrace();
        }
    }




    public void saveImageAndCache(final Bitmap imageBitmap, String name, final SaveImageListener listener) {
        //1. save the image remotly
       saveImage(imageBitmap, name, new SaveImageListener() {
            @Override
            public void complete(String url) {
                // 2. saving the file localy
                String localName = getLocalImageFileName(url);
                Log.d("TAG","cach image: " + localName);
                saveImageToFile(imageBitmap,localName); // synchronously save image locally
                listener.complete(url);
            }
            @Override
            public void fail() {
                listener.fail();
            }
        });
    }

    public void loadImageFromCache(final String url, final GetImageListener listener) {
        //1. first try to find the image on the device
        String localFileName = getLocalImageFileName(url);
        Bitmap image = loadImageFromFile(localFileName);
        if (image == null) { //if image not found - try downloading it from parse
           getImage(url, new GetImageListener() {
                @Override
                public void onSuccess(Bitmap image) {
                    //2. save the image localy
                    String localFileName = getLocalImageFileName(url);
                    Log.d("TAG","save image to cache: " + localFileName);
                    saveImageToFile(image,localFileName);
                    //3. return the image using the listener
                    listener.onSuccess(image);
                }
                @Override
                public void onFail() {
                    listener.onFail();
                }
            });
        }else {
            Log.d("TAG","OK reading cache image: " + localFileName);
            listener.onSuccess(image);
        }}







}
