package io.github.guiritter.lottery_checker;

import static java.util.Collections.unmodifiableList;

import java.util.List;

public class DrawResult {

	public final List<Long> draw;

	public final List<TicketResult> ticketResultList;

	public DrawResult(List<Long> draw, List<TicketResult> ticketResultList) {
		this.draw = unmodifiableList(draw);
		this.ticketResultList = unmodifiableList(ticketResultList);
	}
}
