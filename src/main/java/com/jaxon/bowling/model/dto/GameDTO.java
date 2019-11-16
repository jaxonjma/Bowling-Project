package com.jaxon.bowling.model.dto;

public class GameDTO{
	
	private String player;
	
	private Integer result;
	
	private Integer attempt;
	
	private Integer frame;
	
	private Integer totalFrame;

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getAttempt() {
		return attempt;
	}

	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	public Integer getFrame() {
		return frame;
	}

	public void setFrame(Integer frame) {
		this.frame = frame;
	}

	public Integer getTotalFrame() {
		return totalFrame;
	}

	public void setTotalFrame(Integer totalFrame) {
		this.totalFrame = totalFrame;
	}
	
}
