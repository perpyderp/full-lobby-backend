package com.perp.fulllobby.model;

public class ToggleLikeResponse {
    
    private boolean addedLike;

    public ToggleLikeResponse() {
        this.addedLike = false;
    }

    public ToggleLikeResponse(boolean addedLike) {
        this.addedLike = addedLike;
    }

	public boolean isAddedLike() {
		return addedLike;
	}

	public void setAddedLike(boolean addedLike) {
		this.addedLike = addedLike;
	}

	@Override
	public String toString() {
		return "ToggleLikeResponse [addedLike=" + addedLike + "]";
	}

}
