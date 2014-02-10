package PremiumFisher;
 
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
 
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
 
import org.parabot.core.ui.components.LogArea;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.parabot.environment.input.Mouse;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.api.events.MessageEvent;
import org.rev317.api.events.listeners.MessageListener;
import org.rev317.api.methods.Bank;
import org.rev317.api.methods.BotMouse;
import org.rev317.api.methods.Camera;
import org.rev317.api.methods.Inventory;
import org.rev317.api.methods.Menu;
import org.rev317.api.methods.Npcs;
import org.rev317.api.methods.Players;
import org.rev317.api.methods.SceneObjects;
import org.rev317.api.methods.Skill;
import org.rev317.api.wrappers.interactive.Npc;
import org.rev317.api.wrappers.scene.SceneObject;
 
@ScriptManifest(author = "Kozs", category = Category.FISHING, description = "Fishes at Premium Skilling Zone For fast EXP", name = "KozsPremiumFisher", servers = { "PKHonor" }, version = 1.0)
 
public class KozsPremiumFisher extends Script implements Paintable, MessageListener {
 
        int Manta = 334;
        int caught = 0;
        static long StartTime;
        int PKP = 0;
        GUI g = new GUI();
        public boolean guiWait = true;
        private int FishSpot = 1;
 
    public static ArrayList<Strategy> strategies = new ArrayList<Strategy>();  
 
    static long startTime;
   
   public int startExp, currentExp, startLevel, currentLevel;
   
     
    public static String perHour(int gained) {
                return formatNumber((int) ((gained) * 3600000D / (System.currentTimeMillis() - startTime)));
        }
   
         
        public static String formatNumber(int start) {
        DecimalFormat nf = new DecimalFormat("0.0");
        double i = start;
        if(i >= 1000000) {
            return nf.format((i / 1000000)) + "m";
        }
        if(i >=  1000) {
            return nf.format((i / 1000)) + "k";
        }
        return ""+start;
    }
         
        public static String runTime(long i) {
        DecimalFormat nf = new DecimalFormat("00");
        long millis = System.currentTimeMillis() - i;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;
        return nf.format(hours) + ":" + nf.format(minutes) + ":" + nf.format(seconds);
        }
   
   
        public boolean onExecute() {
            startLevel = Skill.FISHING.getLevel();
            startExp = Skill.FISHING.getExperience();
               
            startTime = System.currentTimeMillis();
                g.setVisible(true);
            while (guiWait == true) {
                    sleep(500);
               
            }
        if(FishSpot == 2) {
        strategies.add(new BankShark());
        strategies.add(new FishShark());
        } else if(FishSpot == 3) {
         strategies.add(new BankManta());
         strategies.add(new FishManta());
        } else if(FishSpot == 4) {
        strategies.add(new BankRock());
        strategies.add(new FishRock());
        }
              provide(strategies);
    return true;  
}
 
public void onFinish() {  
 
LogArea.log("Thanks for Running KozsPremiumFisher, Post proggies on Parabot!");
 
 
}  
public class FishShark implements Strategy {
        Npc[] Shark = Npcs.getNearest(334);
 
    @Override
    public boolean activate() {
            return Shark[0].isOnScreen() && !Players.getLocal().isWalking() && Players.getLocal().getAnimation() == -1  && !Inventory.isFull() && Inventory.getCount(311) > 0;
    }
 
    @Override
    public void execute() {
            if(Shark != null){
            Camera.setPitch(true);
            Shark[0].interact("Harpoon");
            Time.sleep(1200);
            }
    }
}
 
public class BankShark implements Strategy {
       
    final SceneObject[] Banker = SceneObjects.getNearest(2213);
 final SceneObject Banks = Banker[0];
 
     @Override
     public boolean activate() {
             return Inventory.isFull();
     }
 
     @Override
     public void execute() {
 
     if(!Bank.isOpen()) {
             Camera.turnTo(Banks);
             Banks.interact("Use-quickly");
         Time.sleep(3000);
 } else {
         if (Bank.isOpen());
         Bank.depositAllExcept(311);
         Point p = new Point(630, 110);
         Mouse.getInstance().click(p);
         Time.sleep(1000);
         Camera.setRotation(730);
         Time.sleep(550);
         Camera.setPitch(true);
                        }
        }
        }
public class FishRock implements Strategy {
        Npc[] Rock = Npcs.getNearest(315);
 
    @Override
    public boolean activate() {
            return Rock[0].isOnScreen() && !Players.getLocal().isWalking() && Players.getLocal().getAnimation() == -1  && !Inventory.isFull() && Inventory.getCount(14618) > 0 && Inventory.getCount(307) > 0;
    }
 
    @Override
    public void execute() {
            if(Rock != null){
            Camera.setPitch(true);
            Rock[0].interact("Lure");
            Time.sleep(1200);
            }
    }
}
 
public class BankRock implements Strategy {
       
           final SceneObject[] Banker = SceneObjects.getNearest(2213);
           final SceneObject Banks = Banker[0];
 
               @Override
               public boolean activate() {
                       return Inventory.isFull();
               }
 
               @Override
               public void execute() {
 
               if(!Bank.isOpen()) {
                       Camera.turnTo(Banks);
                       Banks.interact("Use-quickly");
                   Time.sleep(3000);
           } else {
                   if (Bank.isOpen());
                   Bank.depositAllExcept(14618, 307);
                   Point p = new Point(630, 110);
                   Mouse.getInstance().click(p);
                   Time.sleep(1000);
                   Camera.setRotation(730);
                   Time.sleep(550);
                   Camera.setPitch(true);
                                }
                }
                }
 
public class FishManta implements Strategy {
        Npc[] Manta = Npcs.getNearest(334);
 
    @Override
    public boolean activate() {
            return Manta[0].isOnScreen() && !Players.getLocal().isWalking() && Players.getLocal().getAnimation() == -1  && !Inventory.isFull() && Inventory.getCount(303) > 0;
    }
 
    @Override
    public void execute() {
            if(Manta != null){
            Camera.setPitch(true);
            Manta[0].interact("Net");
            Time.sleep(1200);
            }
    }
}
 
public class BankManta implements Strategy {
       
        final SceneObject[] Banker = SceneObjects.getNearest(2213);
    final SceneObject Banks = Banker[0];
 
        @Override
        public boolean activate() {
                return Inventory.isFull();
        }
 
        @Override
        public void execute() {
 
               if(!Bank.isOpen()) {
               Camera.turnTo(Banks);
               Banks.interact("Use-quickly");
           Time.sleep(3000);
   } else {
           if (Bank.isOpen());
           Bank.depositAllExcept(303);
           Menu.interact("Close", new Point(487, 28));
           Time.sleep(250);
           Point p = new Point(630, 110);
           Mouse.getInstance().click(p);
           Time.sleep(500);
           Camera.setRotation(730);
           Time.sleep(550);
           Camera.setPitch(true);
                        }
        }
        }
 
public void drawMouse(Graphics g) {
    // Red dot
    g.setColor(Color.BLACK);
    g.drawOval(BotMouse.getMouseX() - 1, BotMouse.getMouseY() - 1, 2, 2);
    g.fillOval(BotMouse.getMouseX() - 1, BotMouse.getMouseY() - 1, 2, 2);
 
    // White rectangle
    g.setColor(Color.BLUE);
    g.drawRect(BotMouse.getMouseX() - 8, BotMouse.getMouseY() - 8, 16, 16);
 
    // Horizontal White Lines
    g.drawLine(0, BotMouse.getMouseY(), BotMouse.getMouseX() - 8,
                    BotMouse.getMouseY());
    g.drawLine(BotMouse.getMouseX() + 8, BotMouse.getMouseY(), 765,
                    BotMouse.getMouseY());
 
    // Vertical White Lines
    g.drawLine(BotMouse.getMouseX(), 0, BotMouse.getMouseX(),
                    BotMouse.getMouseY() - 8);
    g.drawLine(BotMouse.getMouseX(), BotMouse.getMouseY() + 8,
                    BotMouse.getMouseX(), 503);
}
       
 
@Override
public void paint(Graphics g1) {
       
        currentExp = Skill.FISHING.getExperience();
    currentLevel = Skill.FISHING.getLevel();
   
 
   
    int gains = currentExp - startExp;
    drawMouse(g1);
             g1.setColor(Color.white);
         g1.setFont(new Font("Verdana", Font.PLAIN, 16));
         
         g1.drawString("Kozs Premium Fisher", 8, 252);
         
         g1.setFont(new Font("Verdana", Font.PLAIN, 12));
         
         g1.drawString("Runtime: "+runTime(startTime), 8, 280);
         g1.drawString("XP Gained (P/Hour): "+(gains)/1000+"K ("+ perHour(gains)+")", 8, 300);
         g1.drawString("Level (Gained): "+Skill.FISHING.getLevel()+" (+"+(currentLevel-startLevel)+")", 8, 320);
         g1.drawString("PkHonor Points Obtained: "+PKP, 245, 300);
         if (FishSpot == 2){
                g1.drawString("Fish Caught (P/Hour): " + gains / 7500 + "( "+ perHour(gains / 7500) + " )", 245,280); //Shark
         }
         if (FishSpot == 3){
        g1.drawString("Fish Caught (P/Hour): " + gains / 9000 + "( "+ perHour(gains / 9000) + " )", 245,280); //Manta Ray
         }
         if (FishSpot == 4){
       g1.drawString("Fish Caught (P/Hour): " + gains / 11400 + "( "+ perHour(gains / 11400) + " )", 245,280); //Rocktail
         }
         if (FishSpot == 2){
                 g1.drawString("You are currently Fishing Sharks!", 245, 320);
         }
         if (FishSpot == 3){
                 g1.drawString("You are currently Fishing Manta Ray!", 245, 320);
         }
         if (FishSpot == 4){
                 g1.drawString("You are currently Fishing Rocktail!", 245, 320);
         }
       
}
 
 
@Override
public void messageReceived(MessageEvent m) {
        if (m.getMessage().contains("You received 1 PkHonor point, you now")) {
            PKP += 1;
 
 
}
}
 
class GUI extends JFrame {
 
        /**
         *
         */
        private static final long serialVersionUID = -848194423477171447L;
        private JPanel contentPane;
 
        /**
         * Launch the application.
         */
        public void main(String[] args) {
                EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                try {
                                        GUI frame = new GUI();
                                        frame.setVisible(true);
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                        }
                });
        }
 
        /**
         * Create the frame.
         */
        public GUI() {
                setTitle("Kozs Premium Fisher");
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setBounds(100, 100, 450, 300);
                contentPane = new JPanel();
                contentPane.setBackground(SystemColor.activeCaption);
                contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
                setContentPane(contentPane);
                contentPane.setLayout(null);
                final JComboBox fishToFish = new JComboBox();
                fishToFish.setModel(new DefaultComboBoxModel(new String[] {"Shark", "Manta Ray", "Rocktail"}));
                fishToFish.setBounds(165, 65, 89, 20);
                contentPane.add(fishToFish);
                JLabel lblNewLabel = new JLabel("");
        URL url;
        try {
                url = new URL("http://static1.wikia.nocookie.net/__cb20130723092010/soulsplit-rsps/images/c/c2/Fishingcapet.png");
        } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
        }
        Image image = null;
                try {
                        image = ImageIO.read(url);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
        lblNewLabel.setIcon(new ImageIcon(image));
                lblNewLabel.setBounds(63, 0, 130, 250);
        contentPane.add(lblNewLabel);
               
                JLabel lblNewLabel1 = new JLabel("");
        URL url1;
        try {
                url = new URL("http://static1.wikia.nocookie.net/__cb20130723092010/soulsplit-rsps/images/c/c2/Fishingcapet.png");
        } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
        }
        Image image1 = null;
                try {
                        image1 = ImageIO.read(url);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
        lblNewLabel1.setIcon(new ImageIcon(image));
        lblNewLabel1.setBounds(264, 25, 100, 209);
        contentPane.add(lblNewLabel1);
               
                JLabel lblNewLabel2 = new JLabel("");
        URL url2;
        try {
                url2 = new URL("http://static1.wikia.nocookie.net/__cb20120116165214/runescape/images/1/19/Fishing.png");
        } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
        }
        Image image2 = null;
                try {
                        image2 = ImageIO.read(url2);
                } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                }
        lblNewLabel2.setIcon(new ImageIcon(image2));
                lblNewLabel2.setBounds(264, 191, 70, 59);
                contentPane.add(lblNewLabel2);
               
                JLabel lblKozsFisher = new JLabel("Koz's Premium Fisher");
                lblKozsFisher.setFont(new Font("Sitka Subheading", Font.BOLD | Font.ITALIC, 18));
                lblKozsFisher.setBounds(133, 11, 206, 43);
                contentPane.add(lblKozsFisher);
               
                JButton btnStartFishing = new JButton("Start Botting!\r\n");
                btnStartFishing.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                                String chosen = fishToFish.getSelectedItem().toString();
                if(fishToFish.getSelectedItem().equals("Shark")) {
                                FishSpot = 2;
                        } else if (fishToFish.getSelectedItem().equals("Manta Ray")) {
                                FishSpot = 3;
                        } else if (fishToFish.getSelectedItem().equals("Rocktail")) {
                                FishSpot = 4;
                       
                         
                }
                        guiWait= false;
                                g.dispose();
                }});
                btnStartFishing.setBounds(165, 191, 89, 23);
                contentPane.add(btnStartFishing);
       
        }
}
}