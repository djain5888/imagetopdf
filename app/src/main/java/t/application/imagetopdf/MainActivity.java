package t.application.imagetopdf;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Optimizer;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.app.StatusBarManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.asksira.bsimagepicker.BSImagePicker;
import com.asksira.bsimagepicker.Utils;
import com.bumptech.glide.Glide;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.iceteck.silicompressorr.SiliCompressor;
import com.itextpdf.awt.geom.AffineTransform;
import com.itextpdf.awt.geom.misc.RenderingHints;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.LZWCompressor;
import com.itextpdf.text.pdf.parser.PdfImageObject;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import id.zelory.compressor.Compressor;

import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener,
        BSImagePicker.ImageLoaderDelegate,
        ActivityCompat.OnRequestPermissionsResultCallback,
        BSImagePicker.OnSelectImageCancelledListener
{
    private static final int LOADER_ID = 1000;
    String s="";
    Boolean wantToCloseDialog = false;

    private static final int PERMISSION_READ_STORAGE = 2001;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int PERMISSION_CAMERA = 2002;
    private static final int PERMISSION_WRITE_STORAGE = 2003;
    int MY_READ_EXTERNAL_REQUEST = 1;
    RelativeLayout relativerecycler,relativerecyclerfirst;
    RecyclerView recycler,textrecycler;
    TextView note1,note2;
    PDFView pdfView;
    public Runnable NameOfRunnable;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    ArrayList<Bitmap> b1 = new ArrayList<>();
    MyRecyclerViewAdapter a1;
    ArrayList<model> lis=new ArrayList<>();

    int REQ_CAMERA_IMAGE=1;
    TextView multiimage,singleimage;
    Button addimage;
    Button createpdf;
    Uri imageUri;
    ArrayList<Uri> uriList=new ArrayList<>();
    private File compress;
    private BSImagePicker multiSelectionPicker, singleimagePicker;
    int READ_STORAGE_PERMISSION_REQUEST_CODE = 3;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        relativerecyclerfirst=findViewById(R.id.imageeview);
        recycler = findViewById(R.id.recycler);
        relativerecycler=findViewById(R.id.relativerecycler);
        textrecycler=findViewById(R.id.textrecycler);
        note1=findViewById(R.id.note1);
        note2=findViewById(R.id.note2);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2308747207296972/3110172120");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        // Define ColorDrawable object and parse color
        // using parseColor method
        // with color hash code as its parameter


        singleimage=findViewById(R.id.tv_single_selection);
     //   addimage = findViewById(R.id.selectmore);
        createpdf = findViewById(R.id.makepdf);
        multiimage = findViewById(R.id.tv_multi_selection);
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
//                android.Manifest.permission.READ_CONTACTS,
//                android.Manifest.permission.WRITE_CONTACTS,
               android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };






        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(note1, "alpha", 1.2f, 0.5f);
        fadeOut.setDuration(1000);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(note1, "alpha", 0.5f, 1.2f);
        fadeIn.setDuration(1000);
        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet.start();
            }
        });

        mAnimationSet.start();

        ObjectAnimator fadeOut1 = ObjectAnimator.ofFloat(note2, "alpha", 1.2f, 0.5f);
        fadeOut1.setDuration(1000);
        ObjectAnimator fadeIn1 = ObjectAnimator.ofFloat(note2, "alpha", 0.5f, 1.2f);
        fadeIn1.setDuration(1000);
        final AnimatorSet mAnimationSet1 = new AnimatorSet();

        mAnimationSet1.play(fadeIn1).after(fadeOut1);

        mAnimationSet1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mAnimationSet1.start();
            }
        });

        mAnimationSet1.start();
        note1.setPaintFlags(note1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }



            singleimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        requestPermissions(new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)}, PERMISSION_WRITE_STORAGE);
                    } else {


                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
                            // ActivityCompat.requestPermissions(getParent(), new String[] {Manifest.permission.CAMERA}, REQ_CAMERA_IMAGE);

                        } else {
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, "New Picture");
                            values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                            imageUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                            startActivityForResult(intent, REQ_CAMERA_IMAGE);
                        }
                    }
                }
            });


            multiimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                        requestPermissions(new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)}, PERMISSION_WRITE_STORAGE);
                    } else {

//                    Toast.makeText(this, "permo write", Toast.LENGTH_SHORT).show();
                    }
                    multiSelectionPicker = new BSImagePicker.Builder("t.application.imagetopdf.fileprovider ")
                            .isMultiSelect() //Set this if you want to use multi selection mode.
                            .setMinimumMultiSelectCount(1) //Default: 1.
                            .setMaximumMultiSelectCount(20) //Default: Integer.MAX_VALUE (i.e. User can select as many images as he/she wants)
                            .setMultiSelectBarBgColor(android.R.color.white) //Default: #FFFFFF. You can also set it to a translucent color.
                            .setMultiSelectTextColor(R.color.primary_text) //Default: #212121(Dark grey). This is the message in the multi-select bottom bar.
                            .setMultiSelectDoneTextColor(R.color.colorAccent) //Default: #388e3c(Green). This is the color of the "Done" TextView.
                            .setOverSelectTextColor(R.color.error_text) //Default: #b71c1c. This is the color of the message shown when user tries to select more than maximum select count.
                            .disableOverSelectionMessage() //You can also decide not to show this over select message.
                            .build();
                    multiSelectionPicker.show(getSupportFragmentManager(), "picker");
                }
            });
            Intent next = getIntent();
            if (next != null) {
                Log.i("inintent","hello");
                int s = next.getIntExtra("pos", 0);
                ArrayList<Uri> img = next.getParcelableArrayListExtra("image");
                if (img != null) {
                    Log.i("urilist", Integer.toString(img.size()));
                    MyRecyclerViewAdapter a1 = new MyRecyclerViewAdapter(getBaseContext(), img);

                    recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
                    recycler.setAdapter(a1);
                    uriList = img;
                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    Gson gson = new Gson();
                    String json = sharedPrefs.getString("TAG", "");
                    Type type = new TypeToken<List<model>>() {
                    }.getType();
                    lis = gson.fromJson(json, type);
                    ArrayList<String> numb = new ArrayList<>();
                    numb.add("Page Number");
                    for (int i = 0; i < img.size(); i++) {
                        numb.add(Integer.toString(i + 1));

                    }

                    Adapter_for_text t1 = new Adapter_for_text(getApplicationContext(), numb, lis);
                    textrecycler.setLayoutManager(new LinearLayoutManager(this));
                    textrecycler.setAdapter(t1);
                    relativerecyclerfirst.setVisibility(View.VISIBLE);
                    relativerecycler.setVisibility(View.VISIBLE);
                    note2.setVisibility(View.VISIBLE);
                    note1.setVisibility(View.VISIBLE);


                }


            }
            createpdf.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View view) {
                    if (uriList.size() > 0) {


                        showalert();
                    } else

                        {
                            Toast.makeText(MainActivity.this, "Add photos to make Pdf", Toast.LENGTH_SHORT).show();
                        }

                }
            });
        }


    @Override
    public void loadImage(Uri imageUri, ImageView ivImage)
    {
        Glide.with(MainActivity.this).load(imageUri).into(ivImage);
    }

    @Override
    public void onMultiImageSelected(final List<Uri> uList, String tag)
    {
        for(int i=0;i<uList.size();i++)
        {
            String path = getRealPathFromURI(getBaseContext(), uList.get(i));
            File file = new File("file:///" + path);
            // File compress=new Compressor(getApplicationContext()).compressToFile(new File(path));


            uriList.add(uList.get(i));
        }
        make(uriList);

    }

    public void make(List<Uri> uriList)
    {

        ArrayList<String> numbers=new ArrayList<>();
        numbers.add("page number");

        for(int i=0;i<uriList.size();i++)
        {

            numbers.add(Integer.toString(i+1));
        }
        model mod=new model();
        mod.setAdded(false);
        mod.setText("");
        if(lis.size()==0)
        lis.add(mod);

        Adapter_for_text t1=new Adapter_for_text(getApplicationContext(),numbers,lis);
        textrecycler.setLayoutManager(new LinearLayoutManager(this));
        textrecycler.setAdapter(t1);

       // Log.i("thepathsis", uriList.get(1).getPath());

        for (int i = 0; i < uriList.size(); i++) {
            a1 = new MyRecyclerViewAdapter(getBaseContext(), uriList);
            recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
            recycler.setAdapter(a1);
            SharedPreferences.Editor editor = getSharedPreferences("hello", MODE_PRIVATE).edit();
        }
        if(uriList.size()>0){
            relativerecyclerfirst.setVisibility(View.VISIBLE);
        relativerecycler.setVisibility(View.VISIBLE);
        note2.setVisibility(View.VISIBLE);
        note1.setVisibility(View.VISIBLE);}
        else
        {
            relativerecyclerfirst.setVisibility(View.INVISIBLE);
            relativerecycler.setVisibility(View.INVISIBLE);
            note2.setVisibility(View.INVISIBLE);
            note1.setVisibility(View.INVISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void create(String s) throws IOException, DocumentException {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), "ImageToPdf");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }
        Document document2 = new Document();
        // File mydir = getApplicationContext().getDir("mydir", Context.MODE_PRIVATE); //Creating an internal dir;
        //ile fileWithinMyDir = new File(mydir, "myfile"); //Getting a file within the dir.
        FileOutputStream out = new FileOutputStream(mediaStorageDir.getPath() + "/"+s+".pdf");
        File paths = getExternalFilesDir(DIRECTORY_DOWNLOADS);

        String directoryPath = paths.getPath();
        document2.setPageSize(PageSize.A4);
        document2.setMargins(50, 45, 50, 60);
        document2.setMarginMirroring(false);
        Log.i("payt0", directoryPath);
        Log.i("yoursizeid",Integer.toString(lis.size()));
        PdfWriter writer = PdfWriter.getInstance(document2, out); //  Change pdf's name.
        document2.open();
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("App", "failed to create directory");
            }
        }
        for (int i = 0; i < uriList.size(); i++) {
            String path = getRealPathFromURI(getBaseContext(), uriList.get(i));
            File file = null;
            HeaderFooterPageEvent event = new HeaderFooterPageEvent(lis);
            writer.setPageEvent(event);
            file = new File("file:///" + path);
            if(file!=null) {

                compress = new Compressor(this).compressToFile(new File(path));
//            compress = new Compressor(this)
//                    .setMaxWidth(640)
//                    .setMaxHeight(480)
//                    .setQuality(90)
//                    .setCompressFormat(Bitmap.CompressFormat.PNG)
//                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                // .compressToFile(paths);
                Image image1 = Image.getInstance(compress.getPath());


                image1.scaleAbsolute(document2.getPageSize().getWidth() - (document2.getPageSize().getWidth() / 9), document2.getPageSize().getHeight() - (document2.getPageSize().getHeight() / 7));
                Log.i("thepagesizeis", Float.toString(document2.getPageSize().getWidth()));
                image1.setAlignment(Image.ALIGN_MIDDLE);
                document2.add(image1);
                document2.addTitle("Hello");
                document2.addCreationDate();
                document2.addSubject("english");
                document2.addHeader("your", "world");
            }


        }
//        compress =new Compressor().Builder(this)




//        PdfReader read=new PdfReader(mediaStorageDir.getPath()+"/"+s+".pdf");
      //  writer.setPdfVersion(PdfWriter.PDF_VERSION_1_5);
        writer.setCompressionLevel(PdfStream.BEST_COMPRESSION);

        //writer.setCompressionLevel(PdfStream.BEST_COMPRESSION);

          writer.setCompressionLevel(3);

        document2.close();
      //  manipulatePdf(mediaStorageDir.getPath()+"/"+s+".pdf",mediaStorageDir.getPath()+"/"+"he"+".pdf");
        OpenPDFFile(mediaStorageDir.getPath()+"/"+s+".pdf");


    }


    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {

    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    public void OpenPDFFile(String path) {
        File pdfFile = new File(path);//File path

        if (pdfFile.exists())
        {
            Intent i1=new Intent(this,Pdfview.class);
            i1.putExtra("path",path);
            i1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            Toast.makeText(this, "File saved in "+path, Toast.LENGTH_SHORT).show();
            this.finish();
            startActivity(i1);
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CAMERA_IMAGE && resultCode == RESULT_OK) {

            try {
                if (imageUri != null) {
                    //  String imageurl = getRealPathFromURI(imageUri);

                    uriList.add(imageUri);
                    make(uriList);
                } else {
                    Log.i("inelse0", "ww");
                    make(uriList);
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
//        else
//        {
//            make(uriList);
//        }
        }
    }



    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

   void showalert()
    {


        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.prompts, null);
        final TextView text=promptsView.findViewById(R.id.errmessage);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set prompts.xml to alertdialog builder

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        userInput.setText("");

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                if( !userInput.getText().toString().equals(""))
                                {
                                    s=userInput.getText().toString();
                                    wantToCloseDialog=true;
                                }

                                // get user input and set it to result
                                // edit text
                                //  result.setText(userInput.getText());
                            }
//                        })
//                .setNegativeButton("Cancel",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog,int id) {
//                                dialog.cancel();
//                            }
                        });

        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    //```
    //        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Exit", new DialogInterface.OnClickListener() {
    //            @Override
    //            public void onClick(DialogInterface dialogInterface, int i) {
    //                alertDialog.dismiss();
    //            }
    //        });```
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if( !userInput.getText().toString().equals(""))
                {
                    s=userInput.getText().toString();
                    wantToCloseDialog=true;
                }
                if(s.equals(""))
                {
                    text.setVisibility(View.VISIBLE);
                 //   text.setText("File name cannot be null");

                    wantToCloseDialog=false;
                }
                //Do stuff, possibly set wantToCloseDialog to true then...
                if(wantToCloseDialog) {
                    alertDialog.dismiss();
                    try {
                        create(s);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
      //  return s;

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             //   Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Cannot open Camera until you Provide permission", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==PERMISSION_READ_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Please grant permission to proceed", Toast.LENGTH_LONG).show();
            }
        }
        else if(requestCode==PERMISSION_WRITE_STORAGE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "We need permission to further proceed", Toast.LENGTH_LONG).show();
            }

        }
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    }



