package io.github.guiritter.lottery_checker;

import static java.util.Collections.unmodifiableList;

import java.util.List;

public class TicketResult {

	public final long hitCount;

	public final List<Long> numberList;

	public TicketResult(List<Long> numberList, long hitCount) {
		this.numberList = unmodifiableList(numberList);
		this.hitCount = hitCount;
	}
}
