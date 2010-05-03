/*
 * FlickrSearch.java
 *
 * Copyright 2007 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of
 * this software is authorized pursuant to the terms of the license
 * found at http://developers.sun.com/berkeley_license.html.
 */

package swingworker;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.event.IIOReadProgressListener;
import javax.imageio.stream.ImageInputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import java.net.URL;

/**
 * @author John O'Conner
 */
public class ImageRetriever extends SwingWorker<Icon, Void> {

	public ImageRetriever(JLabel lblImage, String strImageUrl) {
		this.strImageUrl = strImageUrl;
		this.lblImage = lblImage;
	}

	@Override
	protected Icon doInBackground() throws Exception {
		Icon icon = retrieveImage(strImageUrl);
		return icon;
	}

	private Icon retrieveImage(String strImageUrl)
			throws MalformedURLException, IOException {

		InputStream is = null;
		URL imgUrl = null;
		imgUrl = new URL(strImageUrl);
		is = imgUrl.openStream();
		ImageInputStream iis = ImageIO.createImageInputStream(is);
		Iterator<ImageReader> it = ImageIO.getImageReadersBySuffix("jpg");

		ImageReader reader = it.next();
		reader.setInput(iis);
		reader.addIIOReadProgressListener(new IIOReadProgressListener() {
			public void sequenceStarted(ImageReader source, int minIndex) {
			}

			public void sequenceComplete(ImageReader source) {
			}

			public void imageStarted(ImageReader source, int imageIndex) {
			}

			public void imageProgress(ImageReader source, float percentageDone) {
				setProgress((int) percentageDone);

			}

			public void imageComplete(ImageReader source) {
				setProgress(100);
			}

			public void thumbnailStarted(ImageReader source, int imageIndex,
					int thumbnailIndex) {
			}

			public void thumbnailProgress(ImageReader source,
					float percentageDone) {
			}

			public void thumbnailComplete(ImageReader source) {
			}

			public void readAborted(ImageReader source) {
			}

		});
		Image image = reader.read(0);
		Icon icon = new ImageIcon(image);
		return icon;
	}

	@Override
	protected void done() {
		Icon icon = null;
		String text = null;
		try {
			icon = get();
		} catch (Exception ignore) {
			ignore.printStackTrace();
			text = "Image unavailalbe";
		}
		lblImage.setIcon(icon);
		lblImage.setText(text);
	}

	private String strImageUrl;

	private JLabel lblImage;

}
