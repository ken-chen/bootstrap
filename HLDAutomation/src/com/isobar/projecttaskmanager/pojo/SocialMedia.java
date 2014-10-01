package com.isobar.projecttaskmanager.pojo;

public class SocialMedia {
	private String facebook_title;
	private String facebook_description;
	private String facebook_image;
	
	private String tweet_message;
	private String tweet_url;
	private String tweet_hashTag;
	private String tweet_information;
	
	private String googlePlus_title;
	private String googlePlus_image;
	private String sendToFriend_message;
		
	private String pageUrl;

	public String getFacebook_title() {
		return facebook_title;
	}

	public void setFacebook_title(String facebook_title) {
		this.facebook_title = facebook_title;
	}

	public String getFacebook_description() {
		return facebook_description;
	}

	public void setFacebook_description(String facebook_description) {
		this.facebook_description = facebook_description;
	}

	public String getFacebook_image() {
		return facebook_image;
	}

	public void setFacebook_image(String facebook_image) {
		this.facebook_image = facebook_image;
	}

	public String getTweet_url() {
		return tweet_url;
	}

	public void setTweet_url(String tweet_url) {
		this.tweet_url = tweet_url;
	}

	public String getTweet_hashTag() {
		return tweet_hashTag;
	}

	public void setTweet_hashTag(String tweet_hashTag) {
		this.tweet_hashTag = tweet_hashTag;
	}

	public String getGooglePlus_title() {
		return googlePlus_title;
	}

	public void setGooglePlus_title(String googlePlus_title) {
		this.googlePlus_title = googlePlus_title;
	}

	public String getGooglePlus_image() {
		return googlePlus_image;
	}

	public void setGooglePlus_image(String googlePlus_image) {
		this.googlePlus_image = googlePlus_image;
	}

	public String getSendToFriend_message() {
		return sendToFriend_message;
	}

	public void setSendToFriend_message(String sendToFriend_message) {
		this.sendToFriend_message = sendToFriend_message;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}


	public String getTweet_message() {
		return tweet_message;
	}

	public void setTweet_message(String tweet_message) {
		this.tweet_message = tweet_message;
	}

	@Override
	public String toString() {
		return "SocialMedia [facebook_title=" + facebook_title
				+ ", facebook_description=" + facebook_description
				+ ", facebook_image=" + facebook_image + ", tweet_message="
				+ tweet_message + ", tweet_url=" + tweet_url
				+ ", tweet_hashTag=" + tweet_hashTag + ", googlePlus_title="
				+ googlePlus_title + ", googlePlus_image=" + googlePlus_image
				+ ", sendToFriend_message=" + sendToFriend_message
				+ ", pageUrl=" + pageUrl + "]";
	}

	public String getTweet_information() {
		return tweet_information;
	}

	public void setTweet_information(String tweet_information) {
		this.tweet_information = tweet_information;
	}

}
