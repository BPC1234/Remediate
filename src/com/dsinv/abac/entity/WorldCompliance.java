package com.dsinv.abac.entity;

public class WorldCompliance {
	
	private long id;
	private String findedResult;
	private long newReputationalReviewFK;
	private long existingReputationalReviewFK;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFindedResult() {
		return findedResult;
	}
	public void setFindedResult(String findedResult) {
		this.findedResult = findedResult;
	}
	public long getNewReputationalReviewFK() {
		return newReputationalReviewFK;
	}
	public void setNewReputationalReviewFK(long newReputationalReviewFK) {
		this.newReputationalReviewFK = newReputationalReviewFK;
	}
	public long getExistingReputationalReviewFK() {
		return existingReputationalReviewFK;
	}
	public void setExistingReputationalReviewFK(long existingReputationalReviewFK) {
		this.existingReputationalReviewFK = existingReputationalReviewFK;
	}
}
