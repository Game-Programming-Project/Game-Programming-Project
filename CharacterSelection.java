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

        //setting images for each button
        ImageIcon character1Icon = new ImageIcon(new ImageIcon("images/Player/1/Woodcutter.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        ImageIcon character2Icon = new ImageIcon(new ImageIcon("images/Player/2/GraveRobber.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));
        ImageIcon character3Icon = new ImageIcon(new ImageIcon("images/Player/3/SteamMan.png").getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT));

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
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(label, gbc);

        gbc.gridwidth = 1;

        gbc.gridy = 1;
        add(character1Button, gbc);

        gbc.gridx = 1;
        add(character2Button, gbc);

        gbc.gridx = 2;
        add(character3Button, gbc);
    }

    //send the selected character to the gamePanel
    public String getSelectedCharacter() {
        return selectedCharacter;
    }
}