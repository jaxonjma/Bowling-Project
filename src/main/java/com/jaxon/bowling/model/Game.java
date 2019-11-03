package com.jaxon.bowling.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Game extends BaseEntity<Long> {
	
	@Column(name="PLAYER")
	private String player;
	
	@Column(name="RESULT")
	private Integer result;
	
	@Column(name="ATTEMPT")
	private Integer attempt;
	
	@Column(name="FRAME")
	private Integer frame;

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

	@Override
	public String getUniqueCode() {
		return this.player.concat(this.result.toString());
	}
	
}
