package t.application.imagetopdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.TooltipCompat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mukesh.imageproccessing.OnProcessingCompletionListener;
import com.mukesh.imageproccessing.PhotoFilter;
import com.mukesh.imageproccessing.filters.Brightness;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class editingactivity extends AppCompatActivity {
    Intent i2;
    Button crop;
    SeekBar seekBar;
    Bitmap bmp;

    ImageView image;
    int position;
    SeekBar seekbar2;
    PhotoFilter filter;
    CropImageView cropImageView;
    ArrayList<Uri> img2;
    float brightness = 0, contrast = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingactivity);
        seekBar = findViewById(R.id.seekBar);
        seekbar2 = findViewById(R.id.seekBar2);

        image = findViewById(R.id.image);
        cropImageView = findViewById(R.id.cropImageView);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-2308747207296972/4530214944");
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
       AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Intent i1 = getIntent();
//        GLSurfaceView s1 = findViewById(R.id.effectView);
//        filter=new PhotoFilter(s1, new OnProcessingCompletionListener() {
//            @Override
//            public void onProcessingComplete(Bitmap bitmap) {
//                filter.applyEffect(bmp, new Brightness());
//
//            }
//        });
        crop = findViewById(R.id.crop);
        i2 = new Intent(this, MainActivity.class);
        position = i1.getIntExtra("pos", 0);
        //   String img = i1.getStringExtra("image");
        img2 = i1.getParcelableArrayListExtra("image");

        image.setImageURI(img2.get(position));

        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), img2.get(position));

            //     bmp=decodeBitmapFromResource(getResources(),)
            image.setImageBitmap(bmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        seekBar.setProgress(50);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //image.getSource.setColorFilter(setBrightness(i+50));
                //  seekBar.setThumb(getThumb(i));
//                brightness = -255 + i * 5.1f;
//                Glide.with(editingactivity.this).load(enhanceImage(bmp, contrast, brightness)).into(image);


                //image.setImageBitmap(enhanceImage(bmp, contrast,brightness));

                //  TooltipCompat.setTooltipText(seekBar,"Brightness :"+Integer.toString(i));

//                        bmp = makeBrightnessBitmap(bmp, i - 50);
//                        Log.i("brightness measure", Integer.toString(i));
//                        image.setImageBitmap(bmp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), img2.get(position));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                brightness = -255 + seekBar.getProgress() * 5.1f;
                Glide.with(editingactivity.this).load(enhanceImage(bmp, contrast, brightness)).into(image);


            }
        });

        seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                contrast = i;
//
//                PrimeRun p = new PrimeRun(bmp, contrast, brightness);
//                new Thread(p).start();
                // bmp=enhanceImage(bmp, contrast, brightness);
             //   image.setImageBitmap(enhanceImage(bmp, contrast, brightness));

                // image.setImageBitmap(bmp);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                contrast = seekBar.getProgress();
                   image.setImageBitmap(enhanceImage(bmp, contrast, brightness));

            }
        });


        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                crop();
            }
        });

//        final int pos=Integer.parseInt(i1.getStringExtra("sss"));
        Button done = findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //    i2.putExtra("youstring",bit);
                // i2.putExtra("pos",pos);


                sendintent();
            }
        });

    }


    public void crop() {
        //  Uri img = getImageUri(this.getApplicationContext(),img2 );
        CropImage.activity(img2.get(position))
                .start(this);

        //   cropImageView.setImageBitmap(bmp);

//        cropImageView.setOnCropImageCompleteListener(new CropImageView.OnCropImageCompleteListener() {
//            @Override
//            public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
//                Bitmap cropped = view.getCroppedImage();
//                image.setImageBitmap(cropped);
//
//
//            }
//        });

    }

    public void sendintent() {
        Bitmap bmp = null;
        try {
            bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), img2.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap edited = enhanceImage(bmp, contrast, brightness);
        Uri image = getImageUri(getBaseContext(), edited);
        img2.set(position, image);
        i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//        bmp.compress(Bitmap.CompressFormat.PNG, 70, stream);
//        byte[] byteArray = stream.toByteArray();
        i2.putExtra("image", img2);
        i2.putExtra("pos", 2);
        startActivity(i2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                //  bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                img2.set(position, resultUri);
                image.setImageURI(resultUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap makeBrightnessBitmap(Bitmap original, int brightness) {
        Bitmap newBitmap = Bitmap.createBitmap(original.getWidth(), original.getHeight(), original.getConfig());
        int[] argb = new int[original.getWidth() * original.getHeight()];
        original.getPixels(argb, 0, original.getWidth(), 0, 0, original.getWidth(), original.getHeight());
        for (int i = argb.length - 1; i >= 0; --i) {
            int alpha = argb[i] >> 24;
            int red = (argb[i] >> 16) & 0xFF;
            int green = (argb[i] >> 8) & 0xFF;
            int blue = argb[i] & 0xFF;
            int red2 = red + brightness;
            if (red2 > 0xFF) red2 = 0xFF;
            if (red2 < 0) red2 = 0;
            int green2 = green + brightness;
            if (green2 > 0xFF) green2 = 0xFF;
            if (green2 < 0) green2 = 0;
            int blue2 = blue + brightness;
            if (blue2 > 0xFF) blue2 = 0xFF;
            if (blue2 < 0) blue2 = 0;
            int composite = (alpha << 24) | (red2 << 16) | (green2 << 8) | blue2;
            argb[i] = composite;
        }
        newBitmap.setPixels(argb, 0, original.getWidth(), 0, 0, original.getWidth(), original.getHeight());
        //   Store.lastBitmap   =   newBitmap;
        return newBitmap;
    }

    public static PorterDuffColorFilter setBrightness(int progress) {
        if (progress >= 100) {
            int value = (int) (progress - 100) * 255 / 100;

            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);

        } else {
            int value = (int) (100 - progress) * 255 / 100;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);


        }
    }

    public Bitmap enhanceImage(Bitmap mBitmap, float contrast, float brightness) {
        contrast = contrast / 10;
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0, brightness,
                        0, contrast, 0, 0, brightness,
                        0, 0, contrast, 0, brightness,
                        0, 0, 0, 1, 0
                });
        Bitmap mEnhancedBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap
                .getConfig());
        Canvas canvas = new Canvas(mEnhancedBitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(mBitmap, 0, 0, paint);
        // Uri img=getImageUri(this.getApplicationContext(),mEnhancedBitmap);
        return mEnhancedBitmap;
    }
//    private Drawable getThumb(int progress) {
//        View thumbView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.seekbar_tv, null, false);
//        ((TextView) thumbView.findViewById(R.id.tv)).setText(progress + "");
//        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
//        thumbView.draw(canvas);
//        return new BitmapDrawable(getResources(), bitmap);
//    }

    public static Bitmap decodeBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }



}
