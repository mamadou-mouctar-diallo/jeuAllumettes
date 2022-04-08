package fr.unice.l3.allumettes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import fr.unice.l3.allumettes.R;

public class Allumettes extends View {
    private int nombreTotalAlumette;
    private int nombreAllumetteVisible;
    private int nombreAllumetteSectionne;
    private Drawable allumette;
    private int allumetteWidth;
    private int allumetteHeight;
    private int allumetteTop;

    public Allumettes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.nombreTotalAlumette = 21;
        this.nombreAllumetteVisible = 15;
        this.nombreAllumetteSectionne = 5;
        this.allumetteWidth = 40;
        this.allumetteHeight = 300;
        this.allumette = context.getDrawable(R.drawable.allumette);
    }

    @Override
    public void onDraw(Canvas canvas){
        int left  = this.allumetteWidth;
        int right = this.allumetteWidth * 2;
        int top = this.allumetteTop;
        int bottom = this.allumetteHeight + top;
        Paint selectedPaint = new Paint();
        for (int i = 1; i <= this.nombreTotalAlumette; i++){




            if(i <= this.nombreAllumetteVisible){
                this.allumette.setBounds(left, top, right, bottom);
                this.allumette.draw(canvas);
                if(i > this.nombreAllumetteVisible - this.nombreAllumetteSectionne){
                    selectedPaint.setColor(Color.GREEN);
                    selectedPaint.setStrokeWidth(15);
                    selectedPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(left, top, right, bottom, selectedPaint);
                }
            }else{
                DashPathEffect dashEffect = new DashPathEffect(new float[]{25, 25}, 0);
                Paint removePaint = new Paint();
                removePaint.setPathEffect(dashEffect);
                removePaint.setStrokeWidth(5);
                removePaint.setStyle(Paint.Style.STROKE);
                canvas.drawRect(left, top, right, bottom, removePaint);
            }
            left = right + this.allumetteWidth;
            right = left + this.allumetteWidth;
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldW, int oldH){
        this.allumetteWidth = w /  (2 * this.nombreTotalAlumette);
        this.allumetteHeight = h - (2 * this.allumetteWidth);
        double ratio = this.allumetteWidth / this.allumetteHeight;
        double min = 0.1;
        double max = 0.2;

        if(this.rotatedScreen()) this.allumetteWidth = this.allumetteWidth - 1;

        if(ratio <= min){
            this.allumetteHeight = (int) (((double) this.allumetteWidth) / (min - ratio));
        } else if(ratio >= max) {
            this.allumetteWidth  = (int) (((double) this.allumetteHeight) / (max - ratio));
        }

        this.allumetteTop = (this.getHeight() / 2) - (this.allumetteHeight / 2);
    }
    public void setSelectedCount(int v){
        this.nombreAllumetteSectionne = v;
    }
    //        Method to check if the screen is rotated
    public boolean rotatedScreen(){
        return String.valueOf(this.getResources().getConfiguration().orientation).equals("2");
    }

    public void setVisibleCount(int v){
        this.nombreAllumetteVisible = v;
    }
    public void setTotal(int v){
        this.nombreTotalAlumette = v;
    }
}
