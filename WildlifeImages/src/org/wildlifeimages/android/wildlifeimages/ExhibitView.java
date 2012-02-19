package org.wildlifeimages.android.wildlifeimages;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebView;
import android.widget.FrameLayout;

/**
 * A modified FrameLayout containing a {@link WebView} and a {@link MultiImageView}.
 * 
 * @author Graham Wilkinson 
 * 	
 */

public class ExhibitView extends FrameLayout implements DownloadListener{
	private WebView htmlView;
	private MultiImageView picView;
	private Context context = this.getContext();

	public ExhibitView(Context context, AttributeSet attrs) {
		super(context, attrs);
		htmlView = new WebView(context, attrs);
		htmlView.setVisibility(View.VISIBLE);

		htmlView.getSettings().setJavaScriptEnabled(true);
		htmlView.getSettings().setPluginsEnabled(true);

		htmlView.setDownloadListener(this);

		picView = new MultiImageView(context, attrs);
		picView.setBackgroundColor(0xFF000000);
		picView.setVisibility(View.INVISIBLE);

		this.addView(htmlView);
		this.addView(picView);
	}

	public void loadUrl(String shortUrl, ContentManager contentManager){
		String[] urlList = new String[1];
		urlList[0] = shortUrl;
		loadUrlList(urlList, contentManager);
	}

	public void loadUrlList(String[] shortUrlList, ContentManager contentManager){
		String[] extensionList = this.getContext().getResources().getStringArray(R.array.image_extensions);
		if (Common.isImageUrl(shortUrlList[0], extensionList)){
			loadImageUrl(shortUrlList, contentManager);
		} else {
			loadHtmlUrl(shortUrlList[0], contentManager);
		}
	}

	public void loadHtmlUrl(String htmlShortUrl, ContentManager contentManager){
		htmlView.loadUrl(contentManager.getBestUrl(htmlShortUrl));
		htmlView.setVisibility(View.VISIBLE);
		picView.setVisibility(View.INVISIBLE);
	}

	public void loadImageUrl(String[] imgShortUrl, ContentManager contentManager){
		picView.setImageBitmapList(imgShortUrl, contentManager);
		picView.setVisibility(View.VISIBLE);
		htmlView.setVisibility(View.INVISIBLE);
	}

	public void loadData(String data){
		htmlView.loadData(data, "text/html", null);
		htmlView.setVisibility(View.VISIBLE);
		picView.setVisibility(View.INVISIBLE);
	}

	public void onDownloadStart(String url, String userAgent,
			String contentDisposition, String mimetype, long contentLength) {
		Log.d(this.getClass().getName(), "Clicked link with type " + mimetype); //TODO
		url = url.replaceAll(ContentManager.ASSET_PREFIX, "");
		Log.w(this.getClass().getName(), url);

		AVActivity.start(context, url);
	}

	public void clear() {
		htmlView.loadUrl("");
		picView.setImageBitmap(null);
	}
}
