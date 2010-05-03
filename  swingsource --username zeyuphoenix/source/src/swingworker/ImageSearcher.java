/*
 * ImageSearcher.java
 *
 * Copyright 2007 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of
 * this software is authorized pursuant to the terms of the license
 * found at http://developers.sun.com/berkeley_license.html.
 */

package swingworker;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import java.net.URL;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * ImageSearcher searches images with titles or descriptions that contain the
 * search text. It publishes progress as it retrieves thumbnails, and it updates
 * the image list with intermediate results.
 * 
 * @author John O'Conner
 */
public class ImageSearcher extends SwingWorker<List<ImageInfo>, ImageInfo> {

	public ImageSearcher(DefaultListModel model, String key, String search) {
		this(model, key, search, 1);
	}

	public ImageSearcher(DefaultListModel model, String key, String search,
			int page) {
		this.model = model;
		this.key = key;
		this.search = search;
	}

	/**
	 * The image service returns an XML document that contains information about
	 * the matched images. Parse that document to extract that information and
	 * create a list of ImageInfo objects.
	 */
	private List<ImageInfo> parseImageInfo(InputStream is) {

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser saxParser;
		List<ImageInfo> imageList = new ArrayList<ImageInfo>();
		SearchHandler handler = new SearchHandler(imageList);
		try {
			saxParser = factory.newSAXParser();
			saxParser.parse(is, handler);
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (SAXException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return imageList;
	}

	@Override
	protected List<ImageInfo> doInBackground() {
		// sometimes the initial delay can be long,
		// so let the user know we're working on it.
		// by giving a little progress
		String searchURL = String.format("%s&api_key=%s" + "&per_page=%s"
				+ "&text=%s", SEARCH_URL, key, MAX_IMAGES, search);
		InputStream is = null;
		URL url = null;
		List<ImageInfo> infoList = null;
		try {
			url = new URL(searchURL);
			is = url.openStream();
			infoList = parseImageInfo(is);

			retrieveAndProcessThumbnails(infoList);
		} catch (MalformedURLException mfe) {
			mfe.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return infoList;
	}

	private Icon retrieveThumbNail(String strImageUrl) {
		ImageIcon thumbNail = null;
		URL imgUrl = null;
		try {

			imgUrl = new URL(strImageUrl);
			thumbNail = new ImageIcon(imgUrl);
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
			thumbNail = null;
		}

		return thumbNail;
	}

	private void retrieveAndProcessThumbnails(List<ImageInfo> infoList) {
		for (int x = 0; x < infoList.size() && !isCancelled(); ++x) {
			// http://static.flickr.com/{server-id}/{id}_{secret}_[mstb].jpg
			ImageInfo info = infoList.get(x);
			String strImageUrl = String.format("%s/%s/%s_%s_s.jpg", IMAGE_URL,
					info.getServer(), info.getId(), info.getSecret());
			Icon thumbNail = retrieveThumbNail(strImageUrl);
			info.setThumbnail(thumbNail);
			publish(info);
			setProgress(100 * (x + 1) / infoList.size());

		}
	}

	/**
	 * process is called as a result of this worker thread's calling the publish
	 * method.
	 * 
	 * As image thumbnails are retrieved, the worker publishes them to the list
	 * model.
	 * 
	 */
	protected void process(List<ImageInfo> infoList) {
		for (ImageInfo info : infoList) {
			if (isCancelled()) {
				break;
			}
			model.addElement(info);
		}

	}

	/*
	 * Provide this for the Mac OS X b88 version of Java SE 6, which hasn't yet
	 * implemented the syntax support for the above, standard process() syntax.
	 */
	protected void process(ImageInfo... infoList) {
		for (ImageInfo info : infoList) {
			if (isCancelled()) {
				break;
			}
			model.addElement(info);
		}
	}

	@Override
	protected void done() {
		// whether we retrieved anything or not
		// we're done, so set the progress indicator accordingly
		setProgress(100);
		if (isCancelled()) {
			return;
		}
		List<ImageInfo> infoList = null;
		try {
			infoList = (List<ImageInfo>) get();
		} catch (ExecutionException ex) {
			ex.printStackTrace();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		if (infoList.size() == 0) {
			ImageInfo info = new ImageInfo(null, null, null, null,
					"No images available", false, false, false);
			model.addElement(info);
		}
	}

	private String key;
	private String search;
	private DefaultListModel model;
	private final String SEARCH_URL = "http://api.flickr.com/services/rest/?"
			+ "method=flickr.photos.search";
	private final String IMAGE_URL = "http://static.flickr.com";
	private final int MAX_IMAGES = 100;

	class SearchHandler extends DefaultHandler {
		List<ImageInfo> imageList;

		public SearchHandler(List<ImageInfo> imageList) {
			this.imageList = imageList;
		}

		public void startDocument() throws SAXException {
			// called when beginning to process doc
		}

		public void endDocument() throws SAXException {
			// called after all elements processed
		}

		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			if (qName.equals("rsp")) {
				doRsp(attributes);
			} else if (qName.equals("photos")) {
				doPhotos(attributes);
			} else if (qName.equals("photo")) {
				doOnePhoto(attributes);
			}
		}

		private void doRsp(Attributes attributes) throws SAXException {
			String status = attributes.getValue("stat");
			if (!status.equalsIgnoreCase("ok")) {
				throw new SAXException("Response Error");
			}
		}

		private void doPhotos(Attributes attributes) {
			String strPage = attributes.getValue("page");
			String strPages = attributes.getValue("pages");
			String strPerPage = attributes.getValue("perpage");
			String strTotal = attributes.getValue("total");

			Integer.parseInt(strPage);
			Integer.parseInt(strPages);
			Integer.parseInt(strPerPage);
			Integer.parseInt(strTotal);
		}

		private void doOnePhoto(Attributes attributes) {
			String id = attributes.getValue("id");
			String owner = attributes.getValue("owner");
			String secret = attributes.getValue("secret");
			String server = attributes.getValue("server");
			String title = attributes.getValue("title");
			boolean bPublic = attributes.getValue("ispublic").equals("1");
			boolean bFriend = attributes.getValue("isfriend").equals("1");
			boolean bFamily = attributes.getValue("isfamily").equals("1");
			ImageInfo info = new ImageInfo(id, owner, secret, server, title,
					bPublic, bFriend, bFamily);
			imageList.add(info);
		}

		public void endElement(String uri, String localName, String qName)
				throws SAXException {

		}
	}
}
