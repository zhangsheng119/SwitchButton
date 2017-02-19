package com.duke.switchbutton_test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * @Author: duke
 * @DateTime: 2016-10-31 10:49
 * @Description: BitmapShader自定义图片：<br/>
 * 1、TRIANGLE(1),          //三角形 <br/>
 * 2、RECTANGLE(2),         //矩形 <br/>
 * 3、ROUNDED_RECTANGLE(3), //圆角矩形 <br/>
 * 4、FIVE_POINTED_STAR(4), //五角星 <br/>
 * 5、PENTAGON(5),          //五边形 <br/>
 * 6、HEXAGON(6),           //六边形 <br/>
 * 7、HEART(7),             //心形 <br/>
 * 8、CIRCULAR(8);          //圆形 <br/>
 * <p/>
 * 强制宽高为正方形(width==height)：<br/>
 * 1、FIVE_POINTED_STAR(4), //五角星 <br/>
 * 2、PENTAGON(5),          //五边形 <br/>
 * 3、HEXAGON(6),           //六边形 <br/>
 * 4、CIRCULAR(7);          //圆形 <br/>
 * <p/>
 * 部分形状顶点位置(上、下、左、右)：<br/>
 * 1、TRIANGLE(1),          //三角形 <br/>
 * 2、FIVE_POINTED_STAR(4), //五角星 <br/>
 * 3、PENTAGON(5),          //五边形 <br/>
 * 4、HEXAGON(6),           //六边形 <br/>
 */
public class ShaderImageView extends ImageView {
    //形状类型值
    private int typeValue;
    //部分形状的角度类型值
    private int typeVertexValue;
    //是否需要外边框(默认不需要false)
    private boolean isNeedShaderRing;
    //外边框宽度尺寸
    private int shaderRingWidth;
    //外边框颜色
    private int shaderRingColor;
    //[圆角矩形]圆滑角度
    private int roundedRectangleAngle;

    //默认图形类型(矩形-无任何处理)
    private static final int TYPE_VALUE_DEFAULT = ShaderType.RECTANGLE.getIntValue();
    //部分形状顶点位置(顶点在上方)
    private static final int TYPE_VERTEX_VALUE_DEFAULT = ShaderVertexType.VERTEX_TOP.getIntValue();
    //默认外边框宽度尺寸(无边框宽度)
    private static int SHADER_RING_WIDTH_DEFAULT = 0;
    //默认圆角矩形圆滑角度px
    private static int ROUNDED_RECTANGLE_ANGLE_DEFAULT = 0;
    //默认外边框颜色
    private static final int SHADER_RING_COLOR_DEFAULT = Color.BLUE;

    private Paint mPaint;
    private Path mPath;
    private BitmapShader mBitmapShader;

    /**
     * 设置形状类型
     *
     * @param typeValue 类型 <br/>
     *                  1、TRIANGLE(1),          //三角形 <br/>
     *                  2、RECTANGLE(2),         //矩形 <br/>
     *                  3、ROUNDED_RECTANGLE(3), //圆角矩形 <br/>
     *                  4、FIVE_POINTED_STAR(4), //五角星 <br/>
     *                  5、PENTAGON(5),          //五边形 <br/>
     *                  6、HEXAGON(6),           //六边形 <br/>
     *                  7、HEART(7),             //心形 <br/>
     *                  8、CIRCULAR(8);          //圆形 <br/>
     */
    public void setTypeValue(ShaderType typeValue) {
        this.typeValue = typeValue.getIntValue();
    }

    /**
     * 部分形状顶点位置
     *
     * @param typeVertexValue 顶点位置(上、下、左、右) <br/>
     *                        1、TRIANGLE(1),          //三角形 <br/>
     *                        2、FIVE_POINTED_STAR(4), //五角星 <br/>
     *                        3、PENTAGON(5),          //五边形 <br/>
     *                        4、HEXAGON(6),           //六边形 <br/>
     */
    public void setTypeVertexValue(ShaderVertexType typeVertexValue) {
        this.typeVertexValue = typeVertexValue.getIntValue();
    }

    /**
     * 是否需要外边框修饰
     *
     * @param needShaderRing 需要true，不需要false
     */
    public void setNeedShaderRing(boolean needShaderRing) {
        isNeedShaderRing = needShaderRing;
    }

    /**
     * 外边框宽度
     *
     * @param dimenId dimen资源的dp值
     */
    public void setShaderRingWidth(int dimenId) {
        this.shaderRingWidth = dimen2Px(dimenId);
    }

    /**
     * 外边框宽度
     *
     * @param dpVal dp值
     */
    public void setShaderRingWidthDP(int dpVal) {
        this.shaderRingWidth = dp2Px(dpVal);
    }

    /**
     * 设置外边框修饰颜色
     *
     * @param shaderRingColor 颜色值
     */
    public void setShaderRingColor(int shaderRingColor) {
        this.shaderRingColor = shaderRingColor;
    }

    /**
     * 设置圆角矩形的角度
     *
     * @param dimenId dimen资源的dp值
     */
    public void setRoundedRectangleAngle(int dimenId) {
        this.roundedRectangleAngle = dimen2Px(dimenId);
    }

    /**
     * 设置圆角矩形的角度
     *
     * @param dpVal
     */
    public void setRoundedRectangleAngleDP(int dpVal) {
        this.roundedRectangleAngle = dp2Px(dpVal);
    }

    public int getTypeValue() {
        return typeValue;
    }

    public int getTypeVertexValue() {
        return typeVertexValue;
    }

    public boolean isNeedShaderRing() {
        return isNeedShaderRing;
    }

    public int getShaderRingWidth() {
        return shaderRingWidth;
    }

    public int getShaderRingColor() {
        return shaderRingColor;
    }

    public int getRoundedRectangleAngle() {
        return roundedRectangleAngle;
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public ShaderImageView(Context context) {
        this(context, null, 0);
    }

    public ShaderImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShaderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //外边框初始值
        shaderRingWidth = SHADER_RING_WIDTH_DEFAULT;
        shaderRingColor = SHADER_RING_COLOR_DEFAULT;
        //形状类型默认矩形
        typeValue = TYPE_VALUE_DEFAULT;
        //形状顶点默认向上
        typeVertexValue = TYPE_VERTEX_VALUE_DEFAULT;
        //初始化自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShaderImageView, defStyleAttr, 0);
        int size = typedArray.getIndexCount();
        for (int i = 0; i < size; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.ShaderImageView_typeValue:
                    //形状类型值
                    typeValue = typedArray.getInt(attr, TYPE_VALUE_DEFAULT);
                    break;
                case R.styleable.ShaderImageView_typeVertexValue:
                    //部分形状的角度类型值
                    typeVertexValue = typedArray.getInt(attr, TYPE_VERTEX_VALUE_DEFAULT);
                    break;
                case R.styleable.ShaderImageView_isNeedShaderRing:
                    //是否需要外边框
                    isNeedShaderRing = typedArray.getBoolean(attr, false);
                    break;
                case R.styleable.ShaderImageView_shaderRingWidth:
                    //外边框宽度尺寸
                    shaderRingWidth = typedArray.getDimensionPixelOffset(attr, SHADER_RING_WIDTH_DEFAULT);
                    break;
                case R.styleable.ShaderImageView_shaderRingColor:
                    //外边框颜色
                    shaderRingColor = typedArray.getColor(attr, SHADER_RING_COLOR_DEFAULT);
                    break;
                case R.styleable.ShaderImageView_roundedRectangleAngle:
                    //圆角矩形圆滑角度
                    roundedRectangleAngle = typedArray.getDimensionPixelOffset(attr, ROUNDED_RECTANGLE_ANGLE_DEFAULT);
                    break;
            }
        }
        typedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPath = new Path();
    }

    /**
     * 测量view的宽高
     *
     * @param widthMeasureSpec  width MeasureSpec(数值和模式)
     * @param heightMeasureSpec height MeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()
                || typeValue == ShaderType.PENTAGON.getIntValue()
                || typeValue == ShaderType.HEXAGON.getIntValue()
                || typeValue == ShaderType.CIRCULAR.getIntValue()) {
            /**
             * 需要强制view的宽高为正方形
             * 1、FIVE_POINTED_STAR(4), //五角星 <br/>
             * 2、PENTAGON(5),          //五边形 <br/>
             * 3、HEXAGON(6),           //六边形 <br/>
             * 4、CIRCULAR(8);          //圆形 <br/>
             */
            widthSize = heightSize = Math.min(widthSize, heightSize);
        }
        //保存view测量结果
        setMeasuredDimension(widthSize, heightSize);
    }

    /**
     * 绘制界面
     *
     * @param canvas 画布
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //背景图片
        Drawable drawableBg = getBackground();
        if (drawableBg != null) {
            drawableBg.draw(canvas);
        }
        //src图片
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }
        Bitmap bitmap = drawableToBitamp(drawable);
        if (bitmap == null) {
            return;
        }
        //将bitmap作为着色器，就是在指定区域内绘制bitmap
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //设置变换矩阵(按比例缩放原图到view的大小)
        //mBitmapShader.setLocalMatrix(mMatrix);
        //设置shader
        mPaint.setShader(mBitmapShader);
        /**
         * 绘制需要的形状：<br/>
         * 1、TRIANGLE(1),          //三角形 <br/>
         * 2、RECTANGLE(2),         //矩形(不做任何处理) <br/>
         * 3、ROUNDED_RECTANGLE(3), //圆角矩形 <br/>
         * 4、FIVE_POINTED_STAR(4), //五角星 <br/>
         * 5、PENTAGON(5),          //五边形 <br/>
         * 6、HEXAGON(6),           //六边形 <br/>
         * 7、HEART(7),             //心形 <br/>
         * 8、CIRCULAR(8);          //圆形 <br/>
         */
        if (typeValue == ShaderType.TRIANGLE.getIntValue()) {
            //1、三角形
            drawTriangle(canvas);
        } else if (typeValue == ShaderType.RECTANGLE.getIntValue()) {
            //2、矩形(不做任何处理)
            drawRectangle(canvas);
        } else if (typeValue == ShaderType.ROUNDED_RECTANGLE.getIntValue()) {
            //3、圆角矩形
            drawRoundedRectangle(canvas);
        } else if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()) {
            //4、五角星
            drawFivePointedStar(canvas);
        } else if (typeValue == ShaderType.PENTAGON.getIntValue()) {
            //5、五边形
            drawPentagon(canvas);
        } else if (typeValue == ShaderType.HEXAGON.getIntValue()) {
            //6、六边形
            drawHexagon(canvas);
        } else if (typeValue == ShaderType.HEART.getIntValue()) {
            //7、心形
            drawHeart(canvas);
        } else if (typeValue == ShaderType.CIRCULAR.getIntValue()) {
            //8、圆形
            drawCircular(canvas);
        }
    }

    //1、三角形
    private void drawTriangle(Canvas canvas) {
        //1、绘制目标原图
        canvas.drawPath(createTrianglePath(), mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawPath(createTrianglePath(), mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //2、矩形(不做任何处理)
    private void drawRectangle(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    //3、圆角矩形
    private void drawRoundedRectangle(Canvas canvas) {
        RectF rectF = new RectF(shaderRingWidth / 2, shaderRingWidth / 2, getWidth() - shaderRingWidth / 2, getHeight() - shaderRingWidth / 2);
        canvas.drawRoundRect(rectF, roundedRectangleAngle, roundedRectangleAngle, mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawRoundRect(rectF, roundedRectangleAngle, roundedRectangleAngle, mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //4、五角星
    private void drawFivePointedStar(Canvas canvas) {
        /**
         * 五角星需要先绘制边框修饰
         */
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawPath(createFivePointedPath(getWidth() >> 1), mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
        canvas.drawPath(createFivePointedPath(getWidth() >> 1), mPaint);
    }

    //5、五边形
    private void drawPentagon(Canvas canvas) {
        canvas.drawPath(createFivePointedPath(getWidth() >> 1), mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawPath(createFivePointedPath(getWidth() >> 1), mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //6、六边形
    private void drawHexagon(Canvas canvas) {
        canvas.drawPath(createSixPointedPath(getWidth() >> 1), mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawPath(createSixPointedPath(getWidth() >> 1), mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //7、心形
    private void drawHeart(Canvas canvas) {
        canvas.drawPath(createHeartPath(), mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawPath(createHeartPath(), mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //8、圆形
    private void drawCircular(Canvas canvas) {
        //偏移量(可调整)，防止切边
        int offset = 2;
        canvas.drawCircle((getWidth() >> 1), (getHeight() >> 1), ((getWidth() - shaderRingWidth) >> 1) - offset, mPaint);
        if (isNeedShaderRing) {
            //2、绘制边框修饰
            setPaintStrokeAndParams();
            canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, ((getWidth() - shaderRingWidth) >> 1) - offset, mPaint);
            //重置画笔为shader模式
            resetPaint();
        }
    }

    //三角形路径(顶点上、下、左、右4种情况)
    private Path createTrianglePath() {
        mPath.reset();
        if (typeVertexValue == ShaderVertexType.VERTEX_RIGHT.getIntValue()) {
            //右
            mPath.moveTo(shaderRingWidth / 2, shaderRingWidth);//左上角
            mPath.lineTo(getWidth() - shaderRingWidth * 3 / 4, (getHeight() >> 1));//右中
            mPath.lineTo(shaderRingWidth / 2, getHeight() - shaderRingWidth);//左下角
            mPath.lineTo(shaderRingWidth / 2, shaderRingWidth);//左上角
        } else if (typeVertexValue == ShaderVertexType.VERTEX_BOTTOM.getIntValue()) {
            //下
            mPath.moveTo(shaderRingWidth, shaderRingWidth / 2);
            mPath.lineTo(getWidth() - shaderRingWidth, shaderRingWidth / 2);
            mPath.lineTo(getWidth() >> 1, getHeight() - shaderRingWidth);
            mPath.lineTo(shaderRingWidth, shaderRingWidth / 2);
        } else if (typeVertexValue == ShaderVertexType.VERTEX_LEFT.getIntValue()) {
            //左
            mPath.moveTo(shaderRingWidth * 5 / 4, getHeight() >> 1);
            mPath.lineTo(getWidth() - shaderRingWidth / 2, shaderRingWidth * 3 / 4);
            mPath.lineTo(getWidth() - shaderRingWidth / 2, getHeight() - shaderRingWidth * 3 / 4);
            mPath.lineTo(shaderRingWidth * 5 / 4, getHeight() >> 1);
        } else if (typeVertexValue == ShaderVertexType.VERTEX_TOP.getIntValue()) {
            //上
            mPath.moveTo(getWidth() >> 1, shaderRingWidth * 5 / 4);
            mPath.lineTo(getWidth() - shaderRingWidth * 3 / 4, getHeight() - shaderRingWidth / 2);
            mPath.lineTo(shaderRingWidth * 3 / 4, getHeight() - shaderRingWidth / 2);
            mPath.lineTo(getWidth() >> 1, shaderRingWidth * 5 / 4);
        }
        mPath.close();
        return mPath;
    }

    /**
     * 计算五角星、五边形需要的5个顶点路径
     *
     * @param radius
     * @return
     */
    private Path createFivePointedPath(int radius) {
        radius -= shaderRingWidth;
        mPath.reset();
        if (typeVertexValue == ShaderVertexType.VERTEX_RIGHT.getIntValue()) {
            //右
            if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()) {
                //五角星
                //点1
                mPath.moveTo((getWidth() >> 1) + radius - shaderRingWidth / 2, getHeight() >> 1);
                //点3
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(36)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius));
                //点5
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius));
                //点2
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius));
                //点4
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(36)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius));
            } else if (typeValue == ShaderType.PENTAGON.getIntValue()) {
                //五边形
                //点1
                mPath.moveTo((getWidth() >> 1) + radius - shaderRingWidth / 2, getHeight() >> 1);
                //点2
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius));
                //点3
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(36)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius));
                //点4
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(36)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius));
                //点5
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius));
            }
        } else if (typeVertexValue == ShaderVertexType.VERTEX_BOTTOM.getIntValue()) {
            //下
            if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()) {
                //五角星
                //点1
                mPath.moveTo(getWidth() >> 1, (getHeight() >> 1) + radius - shaderRingWidth / 2);
                //点3
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(54)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(54)) * radius) - shaderRingWidth / 2);
                //点5
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2);
                //点2
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2);
                //点4
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(54)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(54)) * radius) - shaderRingWidth / 2);
            } else if (typeValue == ShaderType.PENTAGON.getIntValue()) {
                //五边形
                //点1
                mPath.moveTo(getWidth() >> 1, (getHeight() >> 1) + radius - shaderRingWidth / 2);
                //点2
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2);
                //点3
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(54)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(54)) * radius) - shaderRingWidth / 2);
                //点4
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(54)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(54)) * radius) - shaderRingWidth / 2);
                //点5
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(18)) * radius) - shaderRingWidth / 2);
            }
        } else if (typeVertexValue == ShaderVertexType.VERTEX_LEFT.getIntValue()) {
            //左
            if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()) {
                //五角星
                //点1
                mPath.moveTo((getWidth() >> 1) - radius + shaderRingWidth / 2, getHeight() >> 1);
                //点3
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius));
                //点5
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius));
                //点2
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius));
                //点4
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius));
            } else if (typeValue == ShaderType.PENTAGON.getIntValue()) {
                //五边形
                //点1
                mPath.moveTo((getWidth() >> 1) - radius + shaderRingWidth / 2, getHeight() >> 1);
                //点2
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius));
                //点3
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius));
                //点4
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius));
                //点5
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2,
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius));
            }
        } else if (typeVertexValue == ShaderVertexType.VERTEX_TOP.getIntValue()) {
            //上
            if (typeValue == ShaderType.FIVE_POINTED_STAR.getIntValue()) {
                //五角星
                //点1
                mPath.moveTo(getWidth() >> 1, (getHeight() >> 1) - radius + shaderRingWidth / 2);
                //点3
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius),
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2);
                //点5
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2);
                //点2
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2);
                //点4
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius),
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2);
            } else if (typeValue == ShaderType.PENTAGON.getIntValue()) {
                //五边形
                //点1
                mPath.moveTo(getWidth() >> 1, (getHeight() >> 1) - radius + shaderRingWidth / 2);
                //点2
                mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2);
                //点3
                mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(36)) * radius),
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2);
                //点4
                mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(36)) * radius),
                        (getHeight() >> 1) + (float) (Math.cos(degree2Radian(36)) * radius) + shaderRingWidth / 2);
                //点5
                mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(18)) * radius),
                        (getHeight() >> 1) - (float) (Math.sin(degree2Radian(18)) * radius) + shaderRingWidth / 2);
            }
        }
        //闭合
        mPath.close();
        return mPath;
    }

    //绘制六边形路径
    private Path createSixPointedPath(int radius) {
        radius -= shaderRingWidth;
        mPath.reset();
        if (typeVertexValue == ShaderVertexType.VERTEX_LEFT.getIntValue()
                || typeVertexValue == ShaderVertexType.VERTEX_RIGHT.getIntValue()) {
            //左、右
            //点1
            mPath.moveTo((getWidth() >> 1) + radius, getHeight() >> 1);
            //点2
            mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(30)) * radius),
                    (getHeight() >> 1) + (float) (Math.cos(degree2Radian(30)) * radius));
            //点3
            mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(30)) * radius),
                    (getHeight() >> 1) + (float) (Math.cos(degree2Radian(30)) * radius));
            //点4
            mPath.lineTo((getWidth() >> 1) - radius, getHeight() >> 1);
            //点5
            mPath.lineTo((getWidth() >> 1) - (float) (Math.sin(degree2Radian(30)) * radius),
                    (getHeight() >> 1) - (float) (Math.cos(degree2Radian(30)) * radius));
            //点6
            mPath.lineTo((getWidth() >> 1) + (float) (Math.sin(degree2Radian(30)) * radius),
                    (getHeight() >> 1) - (float) (Math.cos(degree2Radian(30)) * radius));
        } else if (typeVertexValue == ShaderVertexType.VERTEX_TOP.getIntValue()
                || typeVertexValue == ShaderVertexType.VERTEX_BOTTOM.getIntValue()) {
            //上、下
            //点1
            mPath.moveTo(getWidth() >> 1, (getHeight() >> 1) - radius);
            //点2
            mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(30)) * radius),
                    (getHeight() >> 1) - (float) (Math.sin(degree2Radian(30)) * radius));
            //点3
            mPath.lineTo((getWidth() >> 1) + (float) (Math.cos(degree2Radian(30)) * radius),
                    (getHeight() >> 1) + (float) (Math.sin(degree2Radian(30)) * radius));
            //点4
            mPath.lineTo(getWidth() >> 1, (getHeight() >> 1) + radius);
            //点5
            mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(30)) * radius),
                    (getHeight() >> 1) + (float) (Math.sin(degree2Radian(30)) * radius));
            //点6
            mPath.lineTo((getWidth() >> 1) - (float) (Math.cos(degree2Radian(30)) * radius),
                    (getHeight() >> 1) - (float) (Math.sin(degree2Radian(30)) * radius));
        }
        mPath.close();
        return mPath;
    }

    /**
     * 绘制心形路径(此算法待深究，可以使用心形曲线方程)
     *
     * @return
     */
    private Path createHeartPath() {
        mPath.reset();
        int width = getWidth();
        int height = getHeight();
        //心形凹点
        mPath.moveTo(width / 2, height / 8 + shaderRingWidth / 2);
        //左边心形曲线(由上向下)
        mPath.cubicTo(-width / 8 + shaderRingWidth / 2, -height / 4 + shaderRingWidth / 2, -width / 5 + shaderRingWidth / 2, height / 2 + shaderRingWidth / 2, width / 2, height - shaderRingWidth / 2);
        //右边心形曲线(由下向上)
        mPath.cubicTo(width * 6 / 5 - shaderRingWidth / 2, height / 2 + shaderRingWidth / 2, width * 9 / 8 - shaderRingWidth / 2, -height / 4 + shaderRingWidth / 2, width / 2, height / 8 + shaderRingWidth / 2);
        mPath.close();
        return mPath;
    }

    /**
     * 设置画笔的样式，去除shader模式
     */
    private void setPaintStrokeAndParams() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(shaderRingColor);
        mPaint.setStrokeWidth(shaderRingWidth);
    }

    /**
     * 重置画笔，恢复shader模式
     */
    private void resetPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return resizeImage(bitmapDrawable.getBitmap());
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        if (w != getWidth() || h != getHeight()) {
            float scaleWidth = getWidth() * 1.0f / w;
            float scaleHeight = getHeight() * 1.0f / h;
            //计算当前drawable的宽高与当前view的宽高比例，调整drawable的大小
            float maxScale = Math.max(scaleWidth, scaleHeight);
            drawable.setBounds(0, 0, (int) (maxScale * w), (int) (maxScale * h));
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 等比缩放图片，原图不变形
     *
     * @param bitmap 原图bitmap
     * @return 等比缩放到view大小后的bitmap
     */
    private Bitmap resizeImage(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        if (w == getWidth() && h == getHeight()) {
            return bitmap;
        }
        //计算出缩放比例
        float scaleWidth = ((float) getWidth()) / w;
        float scaleHeight = ((float) getHeight()) / h;
        //创建变换矩阵
        Matrix matrix = new Matrix();
        //设置缩放参数(为了是图片不变形，应使用最大的缩放比例，宽高等比缩放)
        float maxScale = Math.max(scaleWidth, scaleHeight);
        matrix.postScale(maxScale, maxScale);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * sp、dp对应的dimen转换为px值
     *
     * @param dimenId dimen资源id
     * @return px值
     */
    private int dimen2Px(int dimenId) {
        return getResources().getDimensionPixelOffset(dimenId);
    }

    /**
     * dp value to px
     *
     * @param dpVal dp value
     * @return px
     */
    private int dp2Px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, getResources().getDisplayMetrics());
    }

    //角度2弧度
    private float degree2Radian(int degree) {
        //Math.toRadians(degree);//角度2弧度
        //Math.toDegrees(angrad);//弧度2角度
        return (float) (Math.PI * degree / 180);
    }

    /**
     * 形状枚举
     */
    public enum ShaderType {
        TRIANGLE(1),          //三角形
        RECTANGLE(2),         //矩形
        ROUNDED_RECTANGLE(3), //圆角矩形
        FIVE_POINTED_STAR(4), //五角星
        PENTAGON(5),          //五边形
        HEXAGON(6),           //六边形
        HEART(7),             //心形
        CIRCULAR(8);          //圆形

        ShaderType(int intValue) {
            this.intValue = intValue;
        }

        private int intValue;

        public int getIntValue() {
            return intValue;
        }
    }

    /**
     * 五边形、五边形、三角形顶点位置枚举
     */
    public enum ShaderVertexType {
        VERTEX_TOP(1),          //顶点top
        VERTEX_BOTTOM(2),       //顶点bottom
        VERTEX_LEFT(3),         //顶点left
        VERTEX_RIGHT(4);        //顶点right

        ShaderVertexType(int intValue) {
            this.intValue = intValue;
        }

        private int intValue;

        public int getIntValue() {
            return intValue;
        }
    }
}