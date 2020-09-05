package com.example.simplelist.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.graphics.PathUtils;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simplelist.R;
import com.example.simplelist.model.Task;
import com.example.simplelist.repository.TaskDBRepository;
import com.example.simplelist.utils.PhotoScale;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.UUID;

public class ShowPhotoDialogFragment extends DialogFragment {

    public static final int REQUEST_CODE_TAKE_PICTURE = 0;
    public static final String ARGS_UUID_TASK = "uuidTask";
    public static final String EXTRA_RESPOSE_UUID = "EXTRA_RESPOSE_TASK";
    public static final int REQUEST_CODE_OPEN_GALLERY = 2;
    private Button mButtonCamera,mButtonDelete,mButtonGallery;
    private ImageView mImageViewPhoto;
    private File mFilePhoto;

    public static ShowPhotoDialogFragment newInstance(String s) {
        ShowPhotoDialogFragment fragment = new ShowPhotoDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARGS_UUID_TASK,s);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilePhoto = TaskDBRepository.getInstance(getActivity()).getPhotoFile(getActivity(),TaskDBRepository.getInstance(getActivity()).get(UUID.fromString(getArguments().getString(ARGS_UUID_TASK))));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_photo_dialog,null);
        findViews(view);
        allListener();
        updateImageView();
        return view;
    }

    private void findViews(View view) {
        mButtonDelete = view.findViewById(R.id.delete_phpto);
        mButtonGallery = view.findViewById(R.id.select_from_gallery);
        mButtonCamera = view.findViewById(R.id.camera_photo);
        mImageViewPhoto = view.findViewById(R.id.imageView_photo);
    }

    private void allListener(){
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhoto.resolveActivity(getActivity().getPackageManager()) != null){
                    if (mFilePhoto==null){
                        return;
                    }
                    Uri fileURI = FileProvider.getUriForFile(getActivity(),
                            "com.example.android.fileprovider",mFilePhoto);

                    grantTemPermissionForTakePicture(takePhoto, fileURI);
                    takePhoto.putExtra(MediaStore.EXTRA_OUTPUT,fileURI);
                    startActivityForResult(takePhoto,REQUEST_CODE_TAKE_PICTURE);
                }
            }
        });

        mButtonGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_CODE_OPEN_GALLERY);
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFilePhoto.delete();
                updateImageView();
                setResult();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == REQUEST_CODE_TAKE_PICTURE){
            updateImageView();
            Uri photoUri = FileProvider.getUriForFile(
                    getActivity(),
                    "com.example.android.fileprovider",mFilePhoto);
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }


        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_OPEN_GALLERY){

            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap imageSelected = BitmapFactory.decodeStream(imageStream);
                try(FileOutputStream out = new FileOutputStream(mFilePhoto)) {
                    imageSelected.compress(Bitmap.CompressFormat.JPEG , 100 , out);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mImageViewPhoto.setImageBitmap(imageSelected);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//            File originFile = getFileOfUri(imageUri);
//            try {
//                copyFile(originFile,mFilePhoto);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }
    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    private void updateImageView(){
        if (mFilePhoto==null || !mFilePhoto.exists()){
            mImageViewPhoto.setImageResource(R.mipmap.ic_launcher_add_image2_foreground);
        }
        else {
            mImageViewPhoto.setVisibility(View.VISIBLE);
            Bitmap bitmap = PhotoScale.getScaledBitmap(mFilePhoto.getPath(), getActivity());
            mImageViewPhoto.setImageBitmap(bitmap);
        }
    }

    private void grantTemPermissionForTakePicture(Intent takePictureIntent, Uri photoURI) {
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(
                takePictureIntent,
                PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity: activities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                    photoURI,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private File getFileOfUri(Uri uri){
        return  new File(uri.getPath());
    }

    private void setResult(){
        Fragment fragment = getTargetFragment();
        String uuid =(getArguments().getString(ARGS_UUID_TASK));
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESPOSE_UUID,uuid);
        fragment.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
    }
}