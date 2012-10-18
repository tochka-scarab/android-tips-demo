package com.horribile.tips.framework;

import com.horribile.tips.R;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Error Dialog
 * 
 * @author horribile
 * 
 */
public class ErrorDialog extends Dialog {

	/**
	 * Interface to process OK button clicks
	 * 
	 * @author horribile
	 * 
	 */
	public interface OnCloseListener {
		public void OnClose();
	}

	private OnCloseListener closeListener;
	private TextView textMessage = null;

	private static ErrorDialog error = null;

	/**
	 * Constructor
	 * 
	 * @param context
	 *            Context
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(16)
	protected ErrorDialog(Context context) {

		super(context, android.R.style.Theme_Translucent_NoTitleBar);

		// No title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.error_dialog);

		// Layers array
		Drawable[] arr = new Drawable[3];

		float roundedCorner[] = new float[] { 8, 8, 8, 8, 8, 8, 8, 8 };

		// First layer - to make a border
		GradientDrawable first = new GradientDrawable();
		first.setShape(GradientDrawable.RECTANGLE);
		first.setCornerRadii(roundedCorner);
		first.setStroke(2, Color.WHITE);

		// Second layer - background
		GradientDrawable second = new GradientDrawable();
		second.setShape(GradientDrawable.RECTANGLE);
		second.setCornerRadii(roundedCorner);
		second.setColor(Color.argb(255, 127, 0, 0));

		// Third layer - for the gloss effect
		GlossDrawable third = new GlossDrawable();

		arr[0] = first;
		arr[1] = second;
		arr[2] = third;

		LayerDrawable background = new LayerDrawable(arr);

		// Slightly transparent
		background.setAlpha(154);

		// To show the border
		background.setLayerInset(1, 2, 2, 2, 2);

		// Set the background
		if (Build.VERSION.SDK_INT >= 16) {
			((LinearLayout) findViewById(R.id.alert_wrapper))
					.setBackground(background);
		} else {
			((LinearLayout) findViewById(R.id.alert_wrapper))
					.setBackgroundDrawable(background);
		}

		// Title
		((TextView) findViewById(R.id.dialog_title)).setText(context
				.getResources().getString(R.string.error_title));

		// Here will be a message
		textMessage = ((TextView) findViewById(R.id.dialog_message));

		if (textMessage != null) {
			textMessage.setMovementMethod(new ScrollingMovementMethod());
		}

		// Exit button
		((Button) findViewById(R.id.ok)).setText("OK");

		((Button) findViewById(R.id.ok))
				.setOnClickListener(new android.view.View.OnClickListener() {

					public void onClick(View view) {
						ErrorDialog.this.dismiss();
						if (ErrorDialog.this.closeListener != null)
							ErrorDialog.this.closeListener.OnClose();
						if (error != null)
							error = null;

					}

				});

	}

	/**
	 * Shows the dialog
	 * 
	 * @param context
	 *            Context
	 * @param message
	 *            Message
	 * @param listener
	 *            Close listener
	 */
	public static void show(Context context, String message,
			OnCloseListener listener) {
		error = new ErrorDialog(context);
		error.setMessage(message);
		error.closeListener = listener;
		error.show();
	}

	/**
	 * Shows the dialog
	 * 
	 * @param context
	 *            Context
	 * @param message
	 *            Message
	 */
	public static void show(Context context, String message) {

		error = new ErrorDialog(context);
		error.setMessage(message);
		error.show();

	}

	/**
	 * Sets a message
	 * 
	 * @param message
	 *            Message
	 */
	private void setMessage(String message) {
		if (textMessage != null) {
			textMessage.setText(message);
		}
	}

	/**
	 * Gloss effect
	 * 
	 * @author horribile
	 */
	private class GlossDrawable extends ShapeDrawable {

		public GlossDrawable() {

			super();

			// The rectangle where we will draw
			float[] corners = new float[] { 8, 8, 8, 8, 8, 8, 8, 8 };
			super.setShape(new RoundRectShape(corners, null, null));
			this.getPaint().setColor(Color.TRANSPARENT);
			this.getPaint().setDither(true);
			this.getPaint().setAntiAlias(true);
			this.getPaint().setFilterBitmap(true);

		}

		@Override
		protected void onDraw(Shape shape, Canvas canvas, Paint paint) {

			// Save defaults
			Shader originalShader = paint.getShader();
			int originalColor = paint.getColor();

			// Set the drawing color
			paint.setColor(Color.argb(160, 255, 255, 255));

			float c = shape.getWidth();

			// Sides of the triangle
			float a =  android.util.FloatMath.sqrt((shape.getWidth() / 2)
					* (shape.getWidth() / 2) + (shape.getHeight() / 8)
					* (shape.getHeight() / 8));
			float b = a;

			// By Heron's formula we find the area of ​​triangle
			float p = (a + b + c) / 2;
			float S = android.util.FloatMath.sqrt(p * (p - a) * (p - b) * (p - c));

			// Find the radius of the inscribed circle
			float radius = (a * b * c) / (4 * S);

			// The rectangle to draw
			RectF rectf = new RectF(shape.getWidth() / 2 - radius,
					shape.getHeight() / 4 - radius * 2, shape.getWidth() / 2
							+ radius, shape.getHeight() / 4);

			// Set gradient and draw an oval with it
			float locations[] = new float[] { 0.6f, 1.0f };
			int colors[] = new int[] { 0x05FFFFFF, 0x5FFFFFFF };

			int centerX = (int) shape.getWidth() / 2;
			int centerY = (int) (-radius + shape.getHeight() / 2);

			RadialGradient gradient = new RadialGradient(centerX, centerY,
					radius, colors, locations, Shader.TileMode.CLAMP);
			paint.setShader(gradient);

			canvas.drawOval(rectf, paint);

			// Восстанавливаем настройки
			paint.setShader(originalShader);
			paint.setColor(originalColor);

		}

	}

	@Override
	public void onBackPressed() {
		((Button) findViewById(R.id.ok)).performClick();
	}

}
