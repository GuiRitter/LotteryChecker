package io.github.guiritter.lottery_checker;

import static java.util.stream.Collectors.toList;

import java.util.List;

/**
 * <p>Computes the result of a lottery draw against purchased tickets, showing how many hits each ticket got. That is, how many numbers in each ticket are contained in the draw.
 * 
 * <p>Can be used as a library or as a graphical user interface.
 */
public class LotteryChecker {

	/**
	 * <p>Computes how many hits each ticket got against the draw.
	 * @param draw list of winning numbers
	 * @param ticketList list of tickets, where each ticket is a list of numbers representing a candidate for winning the lottery
	 * @return an object containing the draw and the tickets, where each ticket is paired to how many hits it got
	 */
	public static DrawResult checkDraw(List<Long> draw, List<List<Long>> ticketList) {
		return new DrawResult(
			draw,
			ticketList.stream()
				.map(ticket -> checkTicket(draw, ticket))
				.collect(toList())
		);
	}

	/**
	 * <p>Computes how many hits this ticket got againts the draw.
	 * @param draw list of winning numbers
	 * @param ticket list of numbers representing a candidate for winning the lottery
	 * @return the ticket paired to how many hits it got
	 */
	public static TicketResult checkTicket(List<Long> draw, List<Long> ticket) {
		return new TicketResult(
			ticket,
			ticket.stream().reduce(
				0l,
				(previousSum, currentNumber) -> hitSum(draw, previousSum, currentNumber),
				Long::sum
			)
		);
	}

	/**
	 * <p>Checks if the draw contains one of a ticket's numbers and, if it does, returns the provided sum incremented.
	 * @param draw list of winning numbers
	 * @param previousSum previous count of ticket hits against the draw
	 * @param currentNumber one of the ticket's numbers
	 * @return updated count of ticket hits against the draw
	 */
	public static long hitSum(List<Long> draw, long previousSum, Long currentNumber) {
		return previousSum + (draw.contains(currentNumber) ? 1 : 0);
	}

	/*
	private static void test() {
		checkDraw(java.util.Arrays.asList(8l, 27l, 43l, 53l, 55l), java.util.Arrays.asList(
			java.util.Arrays.asList( 8l, 18l, 32l, 43l, 53l, 61l),
			java.util.Arrays.asList( 7l, 25l, 27l, 41l, 60l, 71l),
			java.util.Arrays.asList( 7l, 36l, 45l, 50l, 73l, 75l),
			java.util.Arrays.asList( 8l, 11l, 21l, 37l, 55l, 56l),
			java.util.Arrays.asList( 3l, 21l, 22l, 25l, 41l, 70l),
			java.util.Arrays.asList( 1l, 34l, 39l, 45l, 64l, 69l),
			java.util.Arrays.asList( 3l, 11l, 40l, 43l, 55l, 69l),
			java.util.Arrays.asList( 2l,  8l, 35l, 43l, 57l, 74l),
			java.util.Arrays.asList(24l, 27l, 53l, 55l, 64l, 70l),
			java.util.Arrays.asList( 5l, 06l, 27l, 53l, 63l, 80l)
		)).ticketResultList.stream()
			.forEach(ticketResult -> 
				System.out.format("%s %s\n", ticketResult.numberList, ticketResult.hitCount)
			);
	}
	*/

	/**
	 * Build a graphical user interface that executes the checking method.
	 * @param args not used
	 */
	public static void main(String args[]) {
		GUI.build();
	}
}
