		ImageIcon playIcon = new ImageIcon("images/play.png");
		Image scaledPlayImage = playIcon.getImage().getScaledInstance(200, 80, Image.SCALE_SMOOTH);
		playIcon = new ImageIcon(scaledPlayImage);
		playB = new JButton(playIcon);
		playB.setContentAreaFilled(false); // Make the button transparent
		playB.setBorderPainted(false); // Remove border

		// Set the action command for the play button
		playB.setActionCommand("Play");