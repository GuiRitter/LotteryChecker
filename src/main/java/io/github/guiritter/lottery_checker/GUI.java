package io.github.guiritter.lottery_checker;

import static io.github.guiritter.graphical_user_interface.LabelledComponentFactory.buildLabelledComponent;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toList;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

import java.awt.BorderLayout;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GUI {

	static {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
	}

	public static void build() {
		var frame = new JFrame("Lottery Checker");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		var drawField = new JTextField();

		var drawComponent = buildLabelledComponent("Draw", drawField, SwingConstants.CENTER, SwingConstants.CENTER, 0);
		drawComponent.setBorder(new EmptyBorder(10, 10, 5, 10));
		frame.getContentPane().add(drawComponent, NORTH);

		var ticketListArea = new JTextArea(2, 20);
		var ticketListPane = new JScrollPane(ticketListArea);

		var ticketListComponent = buildLabelledComponent("Ticket list", ticketListPane, SwingConstants.CENTER, SwingConstants.CENTER, 0);
		ticketListComponent.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.getContentPane().add(ticketListComponent, BorderLayout.CENTER);
		
		var button = new JButton("Check");
		button.addActionListener(actionEvent -> {
			try {
				var builder = new StringBuilder();
				
				LotteryChecker.checkDraw(
					
					Stream.of(drawField.getText().split(" "))
						.filter(not(String::isBlank))
						.map(Long::parseLong)
						.collect(toList()),
					
					Stream.of(ticketListArea.getText().split("\n"))
						.filter(not(String::isBlank))
						.map(ticket -> Stream.of(ticket.split(" "))
							.filter(not(String::isBlank))
							.map(Long::parseLong)
							.collect(toList())
						)
						.collect(toList())
				).ticketResultList.stream()
					.forEach(ticketResult -> {
						ticketResult.numberList.stream()
							.forEach(number -> builder.append(number).append(" "));
						builder.append("> ")
							.append(ticketResult.hitCount)
							.append("\n");
					});

				ticketListArea.setText(builder.toString());
			} catch (Throwable t) {
				t.printStackTrace();
				showMessageDialog(frame, "Check terminal", "Error", ERROR_MESSAGE);
			}
		});

		var buttonPanel = new JPanel();
		buttonPanel.add(button);
		buttonPanel.setBorder(new EmptyBorder(5, 10, 10, 10));
		frame.getContentPane().add(buttonPanel, SOUTH);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
