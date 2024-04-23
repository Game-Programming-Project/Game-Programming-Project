import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CharacterSelection extends JPanel {

    private JButton character1Button;
    private JButton character2Button;
    private JButton character3Button;
    private String selectedCharacter;

    public CharacterSelection(GamePanel gamePanel) {

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // setting images for each button
        ImageIcon character1Icon = new ImageIcon(new ImageIcon("images/Player/1/Woodcutter.png").getImage()
                .getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        ImageIcon character2Icon = new ImageIcon(new ImageIcon("images/Player/2/GraveRobber.png").getImage()
                .getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        ImageIcon character3Icon = new ImageIcon(new ImageIcon("images/Player/3/SteamMan.png").getImage()
                .getScaledInstance(120, 120, Image.SCALE_DEFAULT));

        character1Button = new JButton(character1Icon);
        character1Button.setPreferredSize(new Dimension(120, 120));

        character2Button = new JButton(character2Icon);
        character2Button.setPreferredSize(new Dimension(120, 120));

        character3Button = new JButton(character3Icon);
        character3Button.setPreferredSize(new Dimension(120, 120));

        // Make the buttons transparent
        character1Button.setContentAreaFilled(false);
        character2Button.setContentAreaFilled(false);
        character3Button.setContentAreaFilled(false);

        character1Button.setBorderPainted(false);
        character2Button.setBorderPainted(false);
        character3Button.setBorderPainted(false);

        // Add labels for displaying character stats
        JLabel character1Label = new JLabel(
                "<html><center>Lumberjack<br>Speed: 5<br>Strength: 8<br>Health: 10</center></html>");
        JLabel character2Label = new JLabel(
                "<html><center>Path Finder<br>Speed: 8<br>Strength: 6<br>Health: 10</center></html>");
        JLabel character3Label = new JLabel(
                "<html><center>Trail Blazer<br>Speed: 6<br>Strength: 6<br>Health: 15</center></html>");

        // Set font and alignment for character labels
        Font font = new Font("Arial", Font.PLAIN, 16);
        character1Label.setFont(font);
        character1Label.setHorizontalAlignment(JLabel.CENTER);
        character2Label.setFont(font);
        character2Label.setHorizontalAlignment(JLabel.CENTER);
        character3Label.setFont(font);
        character3Label.setHorizontalAlignment(JLabel.CENTER);

        // Add action listeners to buttons
        character1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCharacter = "1";
                gamePanel.setCharacter(selectedCharacter);
            }
        });

        character2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCharacter = "2";
                gamePanel.setCharacter(selectedCharacter);
            }
        });

        character3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedCharacter = "3";
                gamePanel.setCharacter(selectedCharacter);
            }
        });

        // Create a label and add it to the panel
        JLabel label = new JLabel("Select Your Character!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(label, gbc);

        // Add characters and labels to the panel
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        add(character1Button, gbc);
        gbc.gridx = 1;
        add(character2Button, gbc);
        gbc.gridx = 2;
        add(character3Button, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(character1Label, gbc);
        gbc.gridx = 1;
        add(character2Label, gbc);
        gbc.gridx = 2;
        add(character3Label, gbc);
    }

    // send the selected character to the gamePanel
    public String getSelectedCharacter() {
        return selectedCharacter;
    }
}
