/*
 * ImageInfo.java
 *
 * Copyright 2007 Sun Microsystems, Inc. ALL RIGHTS RESERVED Use of 
 * this software is authorized pursuant to the terms of the license 
 * found at http://developers.sun.com/berkeley_license.html.
 *
 */

package swingworker;

import javax.swing.Icon;

/**
 * ImageInfo stores information that can be used to download a single image from
 * the Flickr service.
 * 
 * @author John O'Conner
 */
public class ImageInfo {

	private String id;
	private String owner;
	private String secret;
	private String server;
	private String title;
	private boolean bPublic;
	private boolean bFriend;
	private boolean bFamily;
	private Icon thumbnail;

	public ImageInfo() {

	}

	public ImageInfo(String id, String owner, String secret, String server,
			String title, boolean bPublic, boolean bFriend, boolean bFamily) {

		this(id, owner, secret, server, title, bPublic, bFriend, bFamily, null);

	}

	public ImageInfo(String id, String owner, String secret, String server,
			String title, boolean bPublic, boolean bFriend, boolean bFamily,
			Icon thumbnail) {

		this.id = id;
		this.owner = owner;
		this.secret = secret;
		this.server = server;
		this.title = title;
		this.bPublic = bPublic;
		this.bFriend = bFriend;
		this.bFamily = bFamily;
		this.thumbnail = thumbnail;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return owner;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSecret() {
		return secret;

	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return server;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public boolean isFamily() {
		return bFamily;
	}

	public void setFamily(boolean bFamily) {
		this.bFamily = bFamily;
	}

	public boolean isFriend() {
		return bFriend;
	}

	public void setFriend(boolean bFriend) {
		this.bFriend = bFriend;
	}

	public boolean isPublic() {
		return bPublic;
	}

	public void setPublic(boolean bPublic) {
		this.bPublic = bPublic;
	}

	public void setThumbnail(Icon thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Icon getThumbnail() {
		return thumbnail;
	}
}
