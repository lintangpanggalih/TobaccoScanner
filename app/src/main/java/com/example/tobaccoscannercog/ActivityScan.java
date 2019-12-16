package com.example.tobaccoscannercog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ActivityScan extends AppCompatActivity {

    private Camera mCamera;
    private ImageView imageView;
    private Camera.PictureCallback mPicture;
    private LinearLayout cameraPreview;
    RiwayatService riwayatService;
    private PreviewSurfaceView camView;
    private DrawingView drawingView;
    private CameraPreview mPreview;

    private Button capture, btnClose;
    public static Bitmap bitmap, rotatedbitmap;
    Dialog myDialog;
    private Context myContext;
    private static final String IMAGE_DIRECTORY = "/kamera";

    String response;

    private int previewWidth = 1280;
    private int previewHeight = 720;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mCamera = Camera.open();
        myContext=this;
        mCamera.setDisplayOrientation(90);
        cameraPreview = (LinearLayout) findViewById(R.id.cPreview);
        mPreview = new CameraPreview(myContext, mCamera);
        cameraPreview.addView(mPreview);

        camView = (PreviewSurfaceView) findViewById(R.id.preview_surface);
        SurfaceHolder camHolder = camView.getHolder();

        camHolder.addCallback(mPreview);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        camView.setListener(mPreview);
        //cameraPreview.changeExposureComp(-currentAlphaAngle);
        drawingView = (DrawingView) findViewById(R.id.drawing_surface);
        camView.setDrawingView(drawingView);

        capture = (Button) findViewById(R.id.btnCam);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.takePicture(null, null, mPicture);
            }
        });
        mCamera.startPreview();

    }

    public void ShowPopup(final Bitmap bitmap) {
        myDialog = new Dialog(this);
        myDialog.setCancelable(true);
        myDialog.setContentView(R.layout.hasil_capture);

        imageView = myDialog.findViewById(R.id.imgck);
        imageView.setImageBitmap(bitmap);
        imageView.setRotation(90);

        btnClose = (Button) myDialog.findViewById(R.id.closePop);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                onResume();
            }
        });

        Button btnSave = (Button) myDialog.findViewById(R.id.saveImg);
        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveImage(rotatedbitmap);
                myDialog.dismiss();
                onResume();
            }
        });

        Button btnUpload = myDialog.findViewById(R.id.proses);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(getResponse());

            }
        });

        if (!myDialog.isShowing()){
            onResume();
        }
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        myDialog.show();
    }

    public void onResume(){
        super.onResume();
        if (mCamera != null){
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }if (mCamera == null){
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mPicture = getPictureCallback();
            mPreview.refreshCamera(mCamera);
        }
        else{
            Log.d("nu", "no null");
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera(){
        if (mCamera != null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Camera.PictureCallback getPictureCallback(){
        final Camera.PictureCallback picture = new Camera.PictureCallback(){
            @Override
            public void onPictureTaken(byte[] data, Camera camera){
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            rotatedbitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            ShowPopup(bitmap);
//                Toast.makeText(getApplicationContext(), String.valueOf(bitmap), Toast.LENGTH_SHORT).show();
            }
        };
        return picture;
    }

    public String saveImage(Bitmap myBitmap) {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("ddMMyyyy");
        SimpleDateFormat tf = new SimpleDateFormat("HHmmss");
        String formattedDate = df.format(c);
        String formattedTime = tf.format(c);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 10, bytes);
        File directory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        if (!directory.exists()) {
            Log.d("dirrr", "" + directory.mkdirs());
            directory.mkdirs();
        }

        try {
            File f = new File(directory, "TBCS_"+formattedDate+"_"+formattedTime+ ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            System.out.println(f.getPath());
            Log.d(formattedDate, "File Saved::--->" + f.getAbsolutePath());
            setResponse(f.getPath());
            uploadToServer(f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    ///UPLOAD SERVER
    private void uploadToServer(String filePath) {
        Retrofit retrofit = APIClient.getRetrofit();
        RiwayatService uploadAPIs = retrofit.create(RiwayatService.class);

        Log.d("TAG", "File Path::--->" + filePath);
        //Create a file object using file path
        File file = new File(filePath);

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        Log.d("TAG", "Media Type::--->" +file.getName()+" "+ String.valueOf(fileReqBody));

        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
        Log.d("TAG", "Part::---> " +part);

        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        Log.d("TAG", "Description::---> " +description);

        Call<ServerResponse> call = uploadAPIs.uploadImage(part, description);
        Log.d("TAG", "Call::---> " +call);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse = (ServerResponse) response.body();
                System.out.println("SAWANGEN " + serverResponse.getMessage());
                if (serverResponse.getMessage().equals(null) ) {
                    Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), serverResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.v("Response : ", serverResponse.toString());
                }
//                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
            }
        });
    }


}
