/*
 * RedirectActivity
 */
package nu.clw.intent.twimage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

/**
 * RedirectActivity
 * @author clworld $Author$
 * @version $Id$
 */
public class RedirectActivity extends Activity {
	/**
	 * onCreate
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// URL取得
		Uri uri = getIntent().getData();

		// twiccaで開く
		Intent intent = new Intent("android.intent.action.VIEW", uri);
		intent.addCategory("android.intent.category.DEFAULT");
		if (!this.openBy(intent, "jp.r246.twicca")) {
			// twiccaで開けなかった場合marketのリンクを開く
			this.openURL("https://play.google.com/store/apps/details?id=jp.r246.twicca");
		}
		this.finish();
	}

	/**
	 *
	 * @param intent Intent
	 * @param packageName パッケージ名
	 * @return 開けた場合true
	 */
	public boolean openBy(Intent intent, String packageName) {
		for (ResolveInfo ri : getPackageManager().queryIntentActivities(intent, 0)) {
			if (ri.activityInfo.packageName.equals(packageName)) {
				ComponentName cn = new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name);
				intent.setComponent(cn);
				this.startActivity(intent);
				return true;
			}
		}
		return false;
	}

	/**
	 * URLを開く
	 * @param url URL
	 */
	public void openURL(String url) {
		Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
		intent.addCategory("android.intent.category.DEFAULT");
		this.startActivity(intent);
	}

	/**
	 * onPause(画面から見えなくなった)
	 */
	@Override
	public void onPause() {
		super.onPause();

		// 別の画面に行った時点で終了する。
		this.finish();
	}
}
