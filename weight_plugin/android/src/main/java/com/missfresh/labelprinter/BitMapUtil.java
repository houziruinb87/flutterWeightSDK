package com.missfresh.labelprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

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

    //margin的高度
    private static final int marginTop2 = 2;
    private static final int marginTop3 = 3;
    private static final int marginBottom2 = 2;
    private static final int marginBottom3 = 3;
    private static final int marginLeft6 = 6;
    private static int subTitleTextSize = 20;
    private static int contentTextSize = 20;
    //虚线的高度
    private static int dashLineHeight = 100;
    private static int maxHeight = 293;
    private static int maxWidth = 440;

    private static int barCodeWidth = 163;
    private static int barCodeHeight = 163;
    /**
     * 60*8 40*8 我们实际的打印宽度只能是56*8
     * <p>
     * 448*
     * <p>
     * 常用的字体类型名称还有：
     * * Typeface.DEFAULT //常规字体类型
     * <p>
     * * Typeface.DEFAULT_BOLD //黑体字体类型
     * <p>
     * * Typeface.MONOSPACE //等宽字体类型
     * <p>
     * * Typeface.SANS_SERIF //sans serif字体类型
     * <p>
     * * Typeface.SERIF //serif字体类型
     * <p>
     * 除了字体类型设置之外，还可以为字体类型设置字体风格，如设置粗体：
     * Paint mp = new Paint();
     * Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
     * p.setTypeface( font );
     * <p>
     * 常用的字体风格名称还有：
     * * Typeface.BOLD //粗体
     * <p>
     * * Typeface.BOLD_ITALIC //粗斜体
     * <p>
     * * Typeface.ITALIC //斜体
     * <p>
     * * Typeface.NORMAL //常规
     * <p>
     * 但是有时上面那些设置在绘图过程中是不起作用的，所以还有如下设置方式：
     * Paint mp = new Paint();
     * mp.setFakeBoldText(true); //true为粗体，false为非粗体
     * mp.setTextSkewX(-0.5f);     //float类型参数，负数表示右斜，整数左斜
     * mp.setUnderlineText(true); //true为下划线，false为非下划线
     * mp.setStrikeThruText(true); //true为删除线，false为非删除线
     * <p>
     * Paint常用的方法还有：
     * mp.setTextSize(); //设置字体大小，int型，如12
     * mp.setStrokeWidth(w); //设置线宽，float型，如2.5f，默认绘文本无需设置（默认值好像为0），但假如设置了，再绘制文本的时候一定要恢复到0
     * <p>
     * 说明：对于中文粗体的设置，好像只能通过setFakeBoldText(true)来实现，尽管效果看起来不是很实在（字体中空效果）。实际发现，最后绘制的效果与手机硬件也有些关系，比如前面的绘图测试程序.
     *
     * @param context
     * @param title   打印标题
     *                * @param spec  打印规格
     *                * @param netWeight 净重
     *                * @param time  生产时间
     *                * @param storeCondition 存储条件
     *                * @param SNCode SN
     *                * @param materialCode 原料编码
     *                * @param SKUCode sku编码
     * @return
     */
    public static Bitmap createBitmap(Context context, String title, String spec, String netWeight, String time, String storeCondition, String SNCode, String materialCode, String SKUCode) {


        Bitmap bitmap = Bitmap.createBitmap(maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        drawTitle(canvas, title);
        drawLine(canvas, context);
        drawPic(canvas, context);
        //生成二维码内容
        String barCodeContent = createBarCodeString(SNCode, materialCode, SKUCode, time, netWeight);
        //生成显示的时间
        String textTime = convertTime(time);
        //生成显示的重量(g)
        String textWeight = convertWeight(netWeight);
        drawContent(canvas, spec, textWeight, textTime, storeCondition, SNCode, materialCode, SKUCode);
        drawBarCode(bitmap, barCodeContent);
        canvas.save();

        return bitmap;
    }

    /**
     * 将图片转为bitmap
     * @param context
     * @param drawableId
     * @return
     */
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

    /**
     * 画顶部标题
     *
     * @param canvas
     * @param title
     */
    private static void drawTitle(Canvas canvas, String title) {
        int totalTitleSize = 34;
        int totalSubTitleSize = 28;
        int totalTitleChangeSize = 21;
        //创建最顶部大标题的画笔
        Paint totalTitlePaint = new Paint();
        totalTitlePaint.setStyle(Paint.Style.FILL);
        totalTitlePaint.setAntiAlias(true);
        totalTitlePaint.setTextSize(totalTitleSize);
        //设置为粗体
        totalTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);


        //创建最顶部次大标题的画笔
        Paint totalSubTitlePaint = new Paint();
        totalSubTitlePaint.setStyle(Paint.Style.FILL);
        totalSubTitlePaint.setAntiAlias(true);
        totalSubTitlePaint.setTextSize(totalSubTitleSize);
        //设置为粗体
        totalSubTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);


        //创建最顶部大标题3行的画笔
        Paint totalTitleChangePaint = new Paint();
        totalTitleChangePaint.setStyle(Paint.Style.FILL);
        totalTitleChangePaint.setAntiAlias(true);
        totalTitleChangePaint.setTextSize(totalTitleChangeSize);
        //设置为粗体
        totalTitleChangePaint.setTypeface(Typeface.DEFAULT_BOLD);

        if (title != null) {
            if (title.length() <= 10) {
                canvas.drawText(title, 0, totalTitleSize + marginTop3 + marginBottom3, totalTitlePaint);
            } else if (title.length() <= 20) {
                String firstString = title.substring(0, 10);
                String secondString = title.substring(10);
                canvas.drawText(firstString, 0, totalTitleSize + marginTop3 + marginBottom3, totalTitlePaint);
                canvas.drawText(secondString, 0, (totalTitleSize + marginTop3 + marginBottom3) * 2, totalTitlePaint);

            } else if (title.length() <= 26) {
                String firstString = title.substring(0, 13);
                String secondString = title.substring(13);
                canvas.drawText(firstString, 0, totalSubTitleSize + marginTop3 + marginBottom3, totalSubTitlePaint);
                canvas.drawText(secondString, 0, (totalSubTitleSize + marginTop3 + marginBottom3) * 2, totalSubTitlePaint);

            } else if (title.length() <= 34) {
                String firstString = title.substring(0, 17);
                String secondString = title.substring(17);
                canvas.drawText(firstString, 0, totalTitleChangeSize + marginTop3 + marginBottom3, totalTitleChangePaint);
                canvas.drawText(secondString, 0, (totalTitleChangeSize + marginTop3 + marginBottom3) * 2, totalTitleChangePaint);

            }else {
                String firstString = title.substring(0, 17);
                String secondString = title.substring(17, 34);
                String thirdString = title.substring(34);
                canvas.drawText(firstString, 0, totalTitleChangeSize + marginTop3 + marginBottom3, totalTitleChangePaint);
                canvas.drawText(secondString, 0, (totalTitleChangeSize + marginTop3 + marginBottom3) * 2, totalTitleChangePaint);
                canvas.drawText(thirdString, 0, (totalTitleChangeSize + marginTop3 + marginBottom3) * 3, totalTitleChangePaint);

            }
        }
    }

    /**
     * 画虚线
     *
     * @param canvas
     * @param context
     */
    private static void drawLine(Canvas canvas, Context context) {
        //创建虚线画笔
        Paint mDashPaint = new Paint();
        mDashPaint.setColor(Color.BLACK);
        mDashPaint.setStrokeWidth(1);
        mDashPaint.setAntiAlias(true);
        //DashPathEffect是Android提供的虚线样式API，具体的使用可以参考下面的介绍
        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dashGap = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, metrics);
        float dashWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 6, metrics);
        mDashPaint.setPathEffect(new DashPathEffect(new float[]{dashWidth, dashGap}, 0));
        //画虚线
        canvas.drawLine(0, dashLineHeight, maxWidth, dashLineHeight, mDashPaint);

    }

    /**
     * 画右上角的图片
     *
     * @param canvas
     * @param context
     */
    private static void drawPic(Canvas canvas, Context context) {
        //创建图片的画笔
        Paint picPaint = new Paint();
        picPaint.setStyle(Paint.Style.FILL);
        picPaint.setAntiAlias(true);
        picPaint.setTextSize(contentTextSize);
        picPaint.setTypeface(Typeface.DEFAULT);
        //画图
        Bitmap bitmapPng = getBitmapFromDrawable(context, R.drawable.print_logo);
        Rect mSrcRect = new Rect(0, 0, bitmapPng.getWidth(), bitmapPng.getHeight());
        Rect mDestRect = new Rect(360, 10, 360 + 80, 10 + 80);
        canvas.drawBitmap(bitmapPng, mSrcRect, mDestRect, picPaint);

    }

    /**
     * 画详情
     *
     * @param canvas
     * @param spec
     * @param netWeight
     * @param time
     * @param storeCondition
     * @param SNCode
     * @param materialCode
     * @param SKUCode
     */
    private static void drawContent(Canvas canvas, String spec, String netWeight, String time, String storeCondition, String SNCode, String materialCode, String SKUCode) {
        //创建每一个小标题的画笔
        Paint subTitlePaint = new Paint();
        subTitlePaint.setStyle(Paint.Style.FILL);
        subTitlePaint.setAntiAlias(true);
        subTitlePaint.setTextSize(subTitleTextSize);
        subTitlePaint.setTypeface(Typeface.DEFAULT_BOLD);


        //创建具体内容的画笔
        Paint contentTextPaint = new Paint();
        contentTextPaint.setStyle(Paint.Style.FILL);
        contentTextPaint.setAntiAlias(true);
        contentTextPaint.setTextSize(contentTextSize);
        contentTextPaint.setTypeface(Typeface.DEFAULT);

        //画内容
        int textLineHeight = (marginTop2 + subTitleTextSize + marginTop2 + marginBottom2);
        //第一行
        int line1Height = (dashLineHeight) + textLineHeight;
        canvas.drawText("规        格", 0, line1Height, subTitlePaint);
        if (spec != null) {
            canvas.drawText(spec, 4 * subTitleTextSize+ marginLeft6, line1Height, contentTextPaint);
        }
        canvas.drawText("净重", (int) (maxWidth / 2) , line1Height, subTitlePaint);
        if (netWeight != null) {
            canvas.drawText(netWeight, (int) (maxWidth / 2)  + (2 * subTitleTextSize)+ marginLeft6, line1Height, contentTextPaint);
        }
        //第二行
        int line2Height = dashLineHeight + textLineHeight * 2;
        canvas.drawText("包装时间", 0, line2Height, subTitlePaint);
        if (time != null) {
            canvas.drawText(time, 4 * subTitleTextSize+ marginLeft6 , line2Height, contentTextPaint);
        }
        //第三行
        int line3Height = dashLineHeight + textLineHeight * 3;
        canvas.drawText("存储条件", 0, line3Height, subTitlePaint);
        if (storeCondition != null) {
            canvas.drawText(storeCondition, 4 * subTitleTextSize+ marginLeft6, line3Height, contentTextPaint);
        }
        //第四行
        int line4Height = dashLineHeight + textLineHeight * 4;
        canvas.drawText("S  N编码", 0, line4Height, subTitlePaint);
        if (SNCode != null) {
            canvas.drawText(SNCode, 4 * subTitleTextSize+ marginLeft6, line4Height, contentTextPaint);
        }
        //第五行
        int line5Height = dashLineHeight + textLineHeight * 5;
        canvas.drawText("原料编码", 0, line5Height, subTitlePaint);
        if (materialCode != null) {

            canvas.drawText(materialCode, 4 * subTitleTextSize+ marginLeft6, line5Height, contentTextPaint);
        }
        int line6Height = dashLineHeight + textLineHeight * 6;
        canvas.drawText("SKU编码", 0, line6Height, subTitlePaint);
        if (SKUCode != null) {

            //SKUCode要进行特殊换行判断
            if (SKUCode.length() <= 18) {

                //第6行(最多18个)
                canvas.drawText(SKUCode, 4 * subTitleTextSize+ marginLeft6, line6Height, contentTextPaint);

            } else {
                String firstString = SKUCode.substring(0, 18);
                String secondString = SKUCode.substring(18);
                //第6行(最多18个)
                canvas.drawText(firstString, 4 * subTitleTextSize+ marginLeft6, line6Height, contentTextPaint);
                //第7行(最多23)  40
                int line7Height = dashLineHeight + textLineHeight * 7;
                canvas.drawText(secondString, 0, line7Height, contentTextPaint);

            }
        }
    }
    /**
     * 生成简单二维码
     *
     * @param content                字符串内容
     *  width                  二维码宽度
     *  height                 二维码高度
     *  character_set          编码方式（一般使用UTF-8）
     *  error_correction_level 容错率 L：7% M：15% Q：25% H：35%
     *  margin                 空白边距（二维码与边框的空白区域）
     *  color_black            黑色色块
     *  color_white            白色色块
     * @return BitMap
     */
    private static void drawBarCode(Bitmap bitmap, String content) {
        String character_set = "UTF-8";
        String error_correction_level = "M";
        String margin = "0";
        int color_black = Color.BLACK;
        int color_white = Color.WHITE;
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
            //            // 空白边距设置
            if (!TextUtils.isEmpty(margin)) {
                hints.put(EncodeHintType.MARGIN, margin);
            }
            /** 2.将配置参数传入到QRCodeWriter的encode方法生成BitMatrix(位矩阵)对象 */
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, barCodeWidth, barCodeHeight, hints);

            /** 3.创建像素数组,并根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            int[] pixels = new int[barCodeWidth * barCodeHeight];
            for (int y = 0; y < barCodeHeight; y++) {
                for (int x = 0; x < barCodeWidth; x++) {
                    //bitMatrix.get(x,y)方法返回true是黑色色块，false是白色色块
                    if (bitMatrix.get(x, y)) {
                        pixels[y * barCodeWidth + x] = color_black;//黑色色块像素设置
                    } else {
                        pixels[y * barCodeWidth + x] = color_white;// 白色色块像素设置
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
            bitmap.setPixels(pixels, 0, barCodeWidth, maxWidth - barCodeWidth, maxHeight - barCodeHeight, barCodeWidth, barCodeHeight);
        } catch (WriterException e) {
            e.printStackTrace();
            //            return null;
        }
    }

    /**
     * 将20200101 转变为 2020/01/01
     *
     * @param time
     * @return
     */
    private static String convertTime(String time) {
        if (time != null && time.length() == 8) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(time.substring(0, 4));
            stringBuilder.append("/");
            stringBuilder.append(time.substring(4, 6));
            stringBuilder.append("/");
            stringBuilder.append(time.substring(6));
            return stringBuilder.toString();

        } else {
            return "";
        }
    }

    /**
     * 将20200101 转变为 2020/01/01
     *
     * @param weight
     * @return
     */
    private static String convertWeight(String weight) {
        if (weight != null) {

            return weight + "g";
        } else {
            return "";
        }
    }

    /**
     * 生成二维码
     *
     * @param SNCode
     * @param materialId
     * @param skuId
     * @param time
     * @param weight
     * @return
     */
    private static String createBarCodeString(String SNCode, String materialId, String skuId, String time, String weight) {
        StringBuilder stringBuilder = new StringBuilder();
        if (SNCode != null) {
            stringBuilder.append("id=" + SNCode + "&");
        } else {
            stringBuilder.append("id=" + "" + "&");
        }
        if (materialId != null) {
            stringBuilder.append("m=" + materialId + "&");
        } else {
            stringBuilder.append("m=" + "" + "&");
        }
        if (skuId != null) {
            stringBuilder.append("s=" + skuId + "&");
        } else {
            stringBuilder.append("s=" + "" + "&");
        }
        if (skuId != null) {
            stringBuilder.append("t=" + time + "&");
        } else {
            stringBuilder.append("t=" + "" + "&");
        }
        if (weight != null) {
            stringBuilder.append("w=" + weight);
        } else {
            stringBuilder.append("w=" + "");
        }
        return stringBuilder.toString();
    }
}
