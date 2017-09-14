package com.enjoy.hyc.util;

/**
 * Created by 1 on 2016/10/15.
 */
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by a on 2016/3/31.
 */
public class ImageUtils {
    /**
     * 从本地path中获取bitmap，压缩后保存小图片到本地
     *
     * @param context
     * @param path    图片存放的路径
     * @return 返回压缩后图片的存放路径
     */
    public static String saveBitmap(Context context, String path) {
        String compressdPicPath = "";


//        建议先将图片压缩到控件所显示的尺寸大小后，再压缩图片质量
//        首先得到手机屏幕的高宽，根据此来压缩图片，当然想要获取跟精确的控件显示的高宽（更加节约内存）,可以使用getImageViewSize();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;  // 屏幕宽度（像素）
        int height = displayMetrics.heightPixels;  // 屏幕高度（像素）
//        获取按照屏幕高宽压缩比压缩后的bitmap
        Bitmap bitmap = decodeSampledBitmapFromPath(path, width, height);

        String oldName = path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
        String name = oldName + "_compress.jpg";//★很奇怪oldName之前不能拼接字符串，只能拼接在后面，否则图片保存失败
        String saveDir = Environment.getExternalStorageDirectory()
                + "/compress";
        File dir = new File(saveDir);
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 保存入sdCard
        File file = new File(saveDir, name);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

/* options表示 如果不压缩是100，表示压缩率为0。如果是70，就表示压缩率是70，表示压缩30%; */
        int options = 20;
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        while (baos.toByteArray().length / 1024 > 200) {
// 循环判断如果压缩后图片是否大于500kb继续压缩
            baos.reset();
            options -= 10;

            if (options < 11) {//为了防止图片大小一直达不到500kb，options一直在递减，当options<0时，下面的方法会报错
                // 也就是说即使达不到500kb，也就压缩到10了
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
                break;
            }
// 这里压缩options%，把压缩后的数据存放到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(baos.toByteArray());
            out.flush();
            out.close();
            compressdPicPath = file.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressdPicPath;
    }


    /**
     * 根据图片要显示的宽和高，对图片进行压缩，避免OOM
     *
     * @param path
     * @param width  要显示的imageview的宽度
     * @param height 要显示的imageview的高度
     * @return
     */
    private static Bitmap decodeSampledBitmapFromPath(String path, int width, int height) {

//      获取图片的宽和高，并不把他加载到内存当中
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = caculateInSampleSize(options, width, height);
//      使用获取到的inSampleSize再次解析图片(此时options里已经含有压缩比 options.inSampleSize，再次解析会得到压缩后的图片，不会oom了 )
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;

    }

    /**
     * 根据需求的宽和高以及图片实际的宽和高计算SampleSize
     *
     * @param options
     * @param reqWidth  要显示的imageview的宽度
     * @param reqHeight 要显示的imageview的高度
     * @return
     * @compressExpand 这个值是为了像预览图片这样的需求，他要比所要显示的imageview高宽要大一点，放大才能清晰
     */
    private static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;

        int inSampleSize = 1;

        if (width >= reqWidth || height >= reqHeight) {

            int widthRadio = Math.round(width * 1.0f / reqWidth);
            int heightRadio = Math.round(width * 1.0f / reqHeight);

            inSampleSize = Math.max(widthRadio, heightRadio);

        }

        return inSampleSize;
    }

    /**
     * 缩小图片的大小
     * @param bgimage
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage, double newWidth,double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,(int) height, matrix, true);
        return bitmap;
    }


    /**
     * 将bitmap切割成正方形
     * @param bitmap
     * @return
     */
    public static Bitmap splitBitmap(Bitmap bitmap){
        int length=0;
        if (bitmap.getHeight()>bitmap.getWidth()){
            length=bitmap.getWidth();
            return Bitmap.createBitmap(bitmap, 0, (bitmap.getHeight()-bitmap.getWidth())/2,
                    length, length);
        }else {
            length=bitmap.getHeight();
            return Bitmap.createBitmap(bitmap,(bitmap.getWidth()-bitmap.getHeight())/2,0,length,length);
        }

    }

    /**
     * 将bitmap转化为图片保存并返回存储地址
     * @param bitmap bitmap 数据对象
     * @param fileName 保存文件名
     * @return
     */
    public static String saveBitmap(Bitmap bitmap,String fileName) {
        if (bitmap==null){
            return "";
        }
        File f = new File("/mnt/sdcard", fileName);
        FileOutputStream out=null;
        try {
            out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }

        return f.getPath();
    }

}
