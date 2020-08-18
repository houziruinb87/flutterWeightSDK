package com.missfresh.labelprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.missfresh.weight.R;

import java.util.Hashtable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

public class BitMapUtil {
    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     * @param width                  二维码宽度
     * @param height                 二维码高度
     * @param character_set          编码方式（一般使用UTF-8）
     * @param error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     * @param margin                 空白边距（二维码与边框的空白区域）
     * @param color_black            黑色色块
     * @param color_white            白色色块
     * @return BitMap
     */
    public static Bitmap createQRCodeBitmap(String content, int width, int height,
                                            String character_set, String error_correction_level,
                                            String margin, int color_black, int color_white) {
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽和高>=0
        if (width < 0 || height < 0) {
            return null;
        }
        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap createTextBitmap(String netWeight, String tareWeight, String grossWeight, int textSize, int textCount) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bitmap = Bitmap.createBitmap(448, 224, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        paint.setTextSize(textSize);
        int sx = (448 - textSize * textCount) / 2;
        canvas.drawText(netWeight, sx, textSize + 50, paint);
        canvas.drawText(tareWeight, sx, (int) (textSize * 2.5) + 50, paint);
        canvas.drawText(grossWeight, sx, textSize * 4 + 50, paint);
        paint.setTextSize(38);
        canvas.drawText("OnePlusOne", sx + 80, 38, paint);
        return bitmap;
    }

    public static Bitmap createBitmap(Context context, String netWeight, String tareWeight, String grossWeight) {
      int titleTextSize = 36;
      int textCount = 7;


        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
//        Bitmap bitmap = Bitmap.createBitmap(448, 224, Bitmap.Config.ARGB_8888);
        Bitmap bitmap = Bitmap.createBitmap(448, 324, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        paint.setTextSize(titleTextSize);
//        int sx = (448 - titleTextSize * textCount) / 2;
        int sx = 0;
        int beginY = 100;
        canvas.drawText("净重: "+netWeight, sx, titleTextSize + beginY, paint);
        canvas.drawText("皮重: "+tareWeight, sx, (int) (titleTextSize * 2.5) +beginY, paint);
        canvas.drawText("毛重: "+grossWeight, sx, titleTextSize * 4 + beginY, paint);
        paint.setTextSize(38);
//        canvas.drawText("每日优鲜", sx + 80, 38, paint);





String content ="1234&567#abc=123,a=1,b=123" ;
        int width=150;
        int height=150;
        String character_set = "UTF-8";
        String error_correction_level = "H";
                String margin="1";
                int color_black = Color.BLACK;
                int color_white =  Color.WHITE;


        try {
            /** 1.设置二维码相关配置 */
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();
            // 字符转码格式设置
            if (!TextUtils.isEmpty(character_set)) {
                hints.put(EncodeHintType.CHARACTER_SET, character_set);
            }
            // 容错率设置
            if (!TextUtils.isEmpty(error_correction_level)) {
                hints.put(EncodeHintType.ERROR_CORRECTION, error_correction_level);
            }
            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * width + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * width + x] = color_white;// 白色色块像素设置
                    }
                }
            }
            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,并返回Bitmap对象 */

            //int[] pixels,  // 设置像素数组，对应点的像素被放在数组中的对应位置，像素的argb值全包含在该位置中
            //                int offset,    // 设置偏移量，我们截图的位置就靠此参数的设置
            //                int stride,    // 设置一行打多少像素，通常一行设置为bitmap的宽度，
            //                int x,         // 设置开始绘图的x坐标
            //                int y,         // 设置开始绘图的y坐标
            //                int width,     // 设置绘制出图片的宽度
            //                int height)    // 设置绘制出图片的高度
            bitmap.setPixels(pixels, 0, width, ( titleTextSize * textCount), 40, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }

        Bitmap bitmapPng = getBitmapFromDrawable(context, R.drawable.icon_print_banner);
        Rect mSrcRect = new Rect(0, 0, bitmapPng.getWidth(), bitmapPng.getHeight());
        Rect mDestRect = new Rect(0, 0, bitmapPng.getWidth(), bitmapPng.getHeight());
        canvas.drawBitmap(bitmapPng,mSrcRect,mDestRect,paint);
        canvas.save();

        return bitmap;
    }

    public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
                Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                return bitmap;
            } else {
                throw new IllegalArgumentException("unsupported drawable type");
            }
        }
        return null;
    }


}
