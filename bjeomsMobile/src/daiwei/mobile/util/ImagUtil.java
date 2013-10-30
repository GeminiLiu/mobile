package daiwei.mobile.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * 图片处理类
 * @author changxiaofei
 * @time 2013-4-2 下午12:45:24
 */
public class ImagUtil {
	private static final String TAG = "ImagUtil";
	
	/**
	 * 缩放图片
	 * @param filename
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public static Bitmap scalePicture(String filename, int maxWidth, int maxHeight) {
		Bitmap bitmap = null;
		try {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			BitmapFactory.decodeFile(filename, opts);
			int srcWidth = opts.outWidth;
			int srcHeight = opts.outHeight;
			int desWidth = 0;
			int desHeight = 0;
			// 缩放比例
			double ratio = 0.0;
			if (srcWidth > srcHeight) {
				ratio = srcWidth / maxWidth;
				desWidth = maxWidth;
				desHeight = (int) (srcHeight / ratio);
			} else {
				ratio = srcHeight / maxHeight;
				desHeight = maxHeight;
				desWidth = (int) (srcWidth / ratio);
			}
			// 设置输出宽度、高度
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			newOpts.inSampleSize = (int) (ratio) + 1;
			newOpts.inJustDecodeBounds = false;
			newOpts.outWidth = desWidth;
			newOpts.outHeight = desHeight;
			bitmap = BitmapFactory.decodeFile(filename, newOpts);
		} catch (Exception e) {
			Log.e(TAG, new StringBuilder("scalePicture ").append(e.toString()).toString());
		}
		return bitmap;
	}
}
