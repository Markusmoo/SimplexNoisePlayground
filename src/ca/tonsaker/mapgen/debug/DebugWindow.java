package ca.tonsaker.mapgen.debug;

import ca.tonsaker.mapgen.Main;
import ca.tonsaker.mapgen.Map;

import javax.swing.*;
import java.awt.event.*;

/**
 * TODO add ENTER key sends command
 * Created by Markus on 2/12/2017.
 */
public class DebugWindow extends JFrame implements ActionListener, ItemListener, KeyListener, MouseListener {

    private class Command{

        private final int VALUE_NA = 0;
        private final int VALUE_RANGE = 1;
        private final int VALUE_0_1 = 2;
        private final int VALUE_POS = 3;

        private final int RANGE_MAX = 255;
        private final int RANGE_MIN = 0;

        private final String VALUE_RANGE_ERROR = "Error: Value must be 0-255";
        private final String VALUE_0_1_ERROR = "Error: Value must be 0-1";
        private final String VALUE_POS_ERROR = "Error: Value must be a positive integer";

        private String[] commands =     {"help"  , "gridSize", "heightWater", "heightGrass", "heightRock", "noiseAmp", "heightOnMap"};
        private int[] commandsValType = {VALUE_NA,  VALUE_POS,   VALUE_RANGE,   VALUE_RANGE,  VALUE_RANGE,  VALUE_POS,     VALUE_0_1};

        public void parseCommand(String s) {
            String error = null;
            for (int idx = 0; idx < commands.length; idx++) {
                if (s.startsWith(commands[idx])) {
                    int val;
                    try{
                        val = Integer.parseInt(s.substring(commands[idx].length()).trim());
                    }catch(NumberFormatException e){
                        Log.d("Command value was incorrect.. try \"help\" for a list of commands");
                        return;
                    }
                    switch (commandsValType[idx]) {
                        case VALUE_NA:
                            break;
                        case VALUE_RANGE:
                            if (!(val >= RANGE_MIN && val <= RANGE_MAX)) error = VALUE_RANGE_ERROR;
                            break;
                        case VALUE_0_1:
                            if (val != 0 && val != 1) error = VALUE_0_1_ERROR;
                            break;
                        case VALUE_POS:
                            if (val < 0) {
                                error = VALUE_POS_ERROR;
                                break;
                            }
                    }
                    if (error != null) {
                        Log.d(error);
                        return;
                    }
                    switch (idx) {
                        case 0:
                            help();
                            return;
                        case 1:
                            gridSize(val);
                            return;
                        case 2:
                            heightWater(val);
                            return;
                        case 3:
                            heightGrass(val);
                            return;
                        case 4:
                            heightRock(val);
                            return;
                    }
                }
            }
            Log.d("Unknown command: \""+s+"\", try \"help\" for a list of commands.");
        }

        private void help(){
            Log.d("Try the following commands:\n\n" +
                    "heightWater val(0-255)\n" +
                    "heightGrass val(0-255)\n" +
                    "heightRock val(0-255)\n");
        }

        private void gridSize(int val){
            mainFrame.remapGridSize(val);
            Log.d("gridSize = " + val);
        }

        private void heightWater(int val){
            Map.HEIGHT_WATER = val;
            Log.d("heightWater = " + val);
        }

        private void heightGrass(int val){
            Map.HEIGHT_GRASS = val;
            Log.d("heightGrass = " + val);
        }

        private void heightRock(int val){
            Map.HEIGHT_ROCK = val;
            Log.d("heightRock = " + val);
        }

    }

    private JEditorPane console;
    private JCheckBox waterCheckBox;
    private JTextField consoleSendField;
    private JButton consoleSendButton;
    private JCheckBox grassCheckBox;
    private JCheckBox rockSCheckBox;
    private JButton generateButton;
    private JPanel container;

    private Command command;

    private Main mainFrame;

    public DebugWindow(Main main){
        super("RANDOM MAP GENERATION");
        this.setLocation(50,50);
        this.setSize(500,300);
        this.setResizable(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setContentPane(container);
        this.mainFrame = main;

        mainFrame.world.addMouseListener(this);

        consoleSendField.addKeyListener(this);

        generateButton.addActionListener(this);
        consoleSendButton.addActionListener(this);

        grassCheckBox.addItemListener(this);
        rockSCheckBox.addItemListener(this);
        waterCheckBox.addItemListener(this);

        command = new Command();

        this.setVisible(true);
    }

    private void sendCommand(){
        String command = consoleSendField.getText();
        if(command.equals("")) return;
        Log.d(command);
        consoleSendField.setText("");
        this.command.parseCommand(command);
    }

    void addToConsole(String text){
        console.setText(console.getText()+text+"\n");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src.equals(generateButton)){
            mainFrame.remap();
        }else if(src.equals(consoleSendButton)){
            sendCommand();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object src = e.getSource();

        if(src.equals(grassCheckBox)){
            Map.ENABLED_GRASS = grassCheckBox.isSelected();
        }else if(src.equals(rockSCheckBox)){
            Map.ENABLED_ROCK = rockSCheckBox.isSelected();
        }else if(src.equals(waterCheckBox)){
            Map.ENABLED_WATER = waterCheckBox.isSelected();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            sendCommand();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = mainFrame.world.gridX(e.getX());
        int y = mainFrame.world.gridY(e.getY());
        System.out.println(e.getX()+" "+e.getY());
        System.out.println(x+" "+y);
        int height = mainFrame.world.getTile(x,y).getHeight(); //TODO Throws NULL
        Log.d("Grid: ("+x+","+y+") Height = "+height);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
