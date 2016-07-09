// OLD SCRIPT AND PRETTY BAD CODE WARNING, Still worked great though ;) just looks akward 

import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.ScriptManifest;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.api.events.MessageEvent;
import org.rev317.api.events.listeners.MessageListener;
import org.rev317.api.methods.Camera;
import org.rev317.api.methods.Inventory;
import org.rev317.api.methods.Npcs;
import org.rev317.api.methods.Players;
import org.rev317.api.methods.Walking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;

import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;

import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.api.utils.Timer;
import org.rev317.api.methods.Bank;
import org.rev317.api.methods.SceneObjects;
import org.rev317.api.wrappers.interactive.Npc;
import org.rev317.api.wrappers.interactive.Player;
import org.rev317.api.wrappers.scene.Area;
import org.rev317.api.wrappers.scene.SceneObject;
import org.rev317.api.wrappers.scene.Tile;
import org.rev317.api.wrappers.walking.TilePath;

@ScriptManifest(author = "Jadsbutt", category = Category.FISHING, description = "A AioFisher for Sharks/Lobsters", name = "JAioFisher", servers = { "UltimateScape" }, version = 1.2)
public class Aiofisher extends Script implements Paintable, MessageListener {

    private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();
    
    private final Tile[] to_Fish = { new Tile(2595,3410,0), new Tile(2606,3412,0) };
    
    private final Tile[] to_Bank = { new Tile(2596,3410,0), new Tile(2587,3419,0) };
    
    private final Tile[] to_Bank2 = { new Tile(2604,3411,0), new Tile(2596,3410,0),new Tile(2587,3419) };
    
    private final Tile[] to_Fish2 = { new Tile(2596,3410,0), new Tile(2604,3415,0), new Tile(2611,3415) };
    
    public static Area bank = new Area (new Tile(2587, 3418, 0) , new Tile(2586, 3420, 0), new Tile(2587, 3422, 0), new Tile(2588, 3420, 0));
    
    private final Tile[] to_Bank3 = { new Tile(2562,3889,0), new Tile(2564,3889,0), new Tile(2566,3890,0), new Tile(2568,3892,0), new Tile(2571, 3895,0) };
    
    private final Tile[] to_Fish3 = { new Tile(2570,3894,0), new Tile(2568,3892,0), new Tile(2566,3890,0), new Tile(2563,3890,0), new Tile(2560,3891,0) };
    
    private final Tile[] to_Fish4 = { new Tile(2597,3410,0), new Tile(2604,3415,0) };
    
    private final Tile[] to_Bank4 = { new Tile(2596,3410,0), new Tile(2587,3419,0) };
    
    private final Font font1 = new Font("Gautami", 0, 20);
    private final Color color1 = new Color(127,255,0);
    private final Color color2 = new Color(255,215,0);
    private final Timer RUNTIME = new Timer();
    public int xp;
    public int fish;
    public int cash;
    private int Fishingarea = 1;
    public static int sharkid = 322;
    public static int lobsterid = 321;
    public boolean guiWait = true;
    public static boolean sharkChosen = false;
    public static boolean lobbyChosen = false;
    public static int[] stuff = {309, 314 };
    public int cage;
    Gui g = new Gui();
 
    @Override
    public boolean onExecute() {
    	g.setVisible(true);
        while (guiWait == true) {
                sleep(500);
    	
        }
    
        if(Fishingarea == 2) {
        strategies.add(new Fish());
        strategies.add(new Banker());
        strategies.add(new Walktofish());
        strategies.add(new Walktobank());
        } else if(Fishingarea == 3) {
         strategies.add(new Banker2());
         strategies.add(new Fish2());
         strategies.add(new Walktofish2());
         strategies.add(new Walktobank2());
        } else if(Fishingarea == 4) {
        strategies.add(new Banker3());
        strategies.add(new Fish3());
        strategies.add(new Walktofish3());
        strategies.add(new Walktobank3());
        } else if(Fishingarea == 5) {
            strategies.add(new Banker4());
            strategies.add(new Fish4());
            strategies.add(new Walktofish4());
            strategies.add(new Walktobank4());
        
        
        }
       
        provide(strategies);
        return true;
        }
    
    public class Fish implements Strategy {
    	
    	Npc[] sharky = Npcs.getNearest(322);
        @Override
        public boolean activate() {
        	final Player get = Players.getLocal();
       		return sharky[0].isOnScreen() && !Inventory.isFull() && get.getAnimation() == -1 && !get.isWalking();
        	
        	}
       
        @Override
        public void execute() {
        	 if(sharky != null){
                 sharky[0].interact("Harpoon");
                 Time.sleep(1200);
                 Camera.setRotation(90);
                 Time.sleep(600);
                 Camera.setPitch(true);
               
             }
            }
        }

    public class Banker implements Strategy {
        @Override
        public boolean activate() {
            return Inventory.isFull()
                    && !Players.getLocal().isWalking()
                    && !Players.getLocal().isInCombat()
                    && Players.getLocal().getAnimation() == -1;
        }

        @Override
        public void execute() {
        	 final SceneObject[] Banker = SceneObjects.getNearest(2213);
             final SceneObject Banks = Banker[0];
             if(!Bank.isOpen()) {
                     Banks.interact("Use-quickly");
                     Time.sleep(400);
             } else {
                     if (Bank.isOpen());
                     Bank.depositAllExcept(311);
     
             }
            }
        }
    
     public class Walktofish implements Strategy {
        @Override
        public boolean activate() {
       		return !Inventory.isFull();
        }

        @Override
        public void execute() {
        	   TilePath path = new TilePath(to_Fish);
               if(!path.hasReached() && Walking.readyForNextTile()) {
                  path.traverse();
                  
             }
            }
        }

    public class Walktobank implements Strategy {
    	   @Override
           public boolean activate() {
          		return Inventory.isFull();
           }

           @Override
           public void execute() {
           	   TilePath path = new TilePath(to_Bank);
                  if(!path.hasReached() && Walking.readyForNextTile()) {
                     path.traverse();
                }
               }
           }
    
    public class Banker2 implements Strategy {
        @Override
        public boolean activate() {
            return Inventory.isFull() 
                    && !Players.getLocal().isWalking()
                    && !Players.getLocal().isInCombat()
                    && Players.getLocal().getAnimation() == -1; 
        }

		@Override
		public void execute() {
		       final SceneObject[] Banker = SceneObjects.getNearest(2213);
	             final SceneObject Banks = Banker[0];
	             if(!Bank.isOpen()) {
	                     Banks.interact("Use-quickly");
	                     Time.sleep(400);
	             } else {
	                     if (Bank.isOpen());
	                     Bank.depositAllExcept(301);
			
		}
		}
    }

        public class Fish2 implements Strategy {
            
            Npc[] Lobby = Npcs.getNearest(321);
            @Override
            public boolean activate() {
                    final Player get = Players.getLocal();
                    return Lobby[0].isOnScreen() && !Inventory.isFull() && get.getAnimation() == -1 && !get.isWalking();
                   
                    }
         
            @Override
            public void execute() {
                     if(Lobby != null){
                     Lobby[0].interact("Cage");
                     Time.sleep(1200);
                     Camera.setRotation(90);
                     Time.sleep(600);
                     Camera.setPitch(true);
                   
                 }
                }
            }
       
    public class Walktofish2 implements Strategy {
        @Override
        public boolean activate() {
                    return !Inventory.isFull();
        }
     
        @Override
        public void execute() {
               TilePath path = new TilePath(to_Fish2);
               if(!path.hasReached() && Walking.readyForNextTile()) {
                  path.traverse();
                 
             }
            }
        }
     
    public class Walktobank2 implements Strategy {
        @Override
        public boolean activate() {
                    return Inventory.isFull();
        }
     
        @Override
        public void execute() {
               TilePath path = new TilePath(to_Bank2);
               if(!path.hasReached() && Walking.readyForNextTile()) {
                  path.traverse();
                 
             }
            }
        }
   
public class Banker3 implements Strategy {
    @Override
    public boolean activate() {
        return Inventory.isFull()
                && !Players.getLocal().isWalking()
                && !Players.getLocal().isInCombat()
                && Players.getLocal().getAnimation() == -1;
    }

    @Override
    public void execute() {
    	 final SceneObject[] Banker = SceneObjects.getNearest(5276);
         final SceneObject Banks = Banker[0];
         if(!Bank.isOpen()) {
                 Banks.interact("Use-quickly");
                 Time.sleep(400);
         } else {
                 if (Bank.isOpen());
                 Bank.depositAllExcept(stuff);
                 Time.sleep(700);
       
         }
        }
    }

public class Fish3 implements Strategy {
	
	Npc[] rocky = Npcs.getNearest(233);
    @Override
    public boolean activate() {
    	final Player get = Players.getLocal();
   		return rocky[0].isOnScreen() && !Inventory.isFull() && get.getAnimation() == -1 && !get.isWalking();
    	
    	}

    @Override
    public void execute() {
    	 if(rocky != null){
             rocky[0].interact("Bait");
             Time.sleep(1200);
             Camera.setRotation(270);
             Time.sleep(600);
             Camera.setPitch(true);
           
         }
        }
    }

public class Walktofish3 implements Strategy {
@Override
public boolean activate() {
		return !Inventory.isFull();
}

@Override
public void execute() {
	   TilePath path = new TilePath(to_Fish3);
       if(!path.hasReached() && Walking.readyForNextTile()) {
          path.traverse();
          
     }
    }
}

public class Walktobank3 implements Strategy {
@Override
public boolean activate() {
		return Inventory.isFull();
}

@Override
public void execute() {
	   TilePath path = new TilePath(to_Bank3);
       if(!path.hasReached() && Walking.readyForNextTile()) {
          path.traverse();
          
     }
    }
}




public class Banker4 implements Strategy {
    @Override
    public boolean activate() {
        return Inventory.isFull()
                && !Players.getLocal().isWalking()
                && !Players.getLocal().isInCombat()
                && Players.getLocal().getAnimation() == -1;
    }

    @Override
    public void execute() {
    	 final SceneObject[] Banker = SceneObjects.getNearest(2213);
         final SceneObject Banks = Banker[0];
         if(!Bank.isOpen()) {
                 Banks.interact("Use-quickly");
                 Time.sleep(400);
         } else {
                 if (Bank.isOpen());
                 Bank.depositAllExcept(303);
                 Time.sleep(700);
       
         }
        }
    }

public class Fish4 implements Strategy {
	
	Npc[] shrimp = Npcs.getNearest(320);
    @Override
    public boolean activate() {
    	final Player get = Players.getLocal();
   		return shrimp[0].isOnScreen() && !Inventory.isFull() && get.getAnimation() == -1 && !get.isWalking();
    	
    	}

    @Override
    public void execute() {
    	 if(shrimp != null){
             shrimp[0].interact("Net");
             Time.sleep(1200);
             Camera.setRotation(90);
             Time.sleep(600);
             Camera.setPitch(true);
           
         }
        }
    }

public class Walktofish4 implements Strategy {
@Override
public boolean activate() {
		return !Inventory.isFull();
}

@Override
public void execute() {
	   TilePath path = new TilePath(to_Fish4);
       if(!path.hasReached() && Walking.readyForNextTile()) {
          path.traverse();
          
     }
    }
}

public class Walktobank4 implements Strategy {
@Override
public boolean activate() {
		return Inventory.isFull();
}

@Override
public void execute() {
	   TilePath path = new TilePath(to_Bank4);
       if(!path.hasReached() && Walking.readyForNextTile()) {
          path.traverse();
          
     }
    }
}

    @Override
    public void onFinish() {

    }

	@Override
	public void paint(Graphics g) {
		 g.setColor(color2);
		 g.drawString("Xp: " + xp, 577, 268);
		 
		 g.setColor(color2);
		 g.drawString("Fish caught: " + fish, 577, 288);
		 
		 g.setColor(color2);
		 g.drawString("Runtime: " + RUNTIME, 577, 308);
		 
		 g.setColor(color2);
		 g.drawString("Cash Made In Pouch: " + cash, 577, 328);
		 
		 g.setColor(color1);
		 g.setFont(font1);
		 g.drawString("JAioFisher", 577, 248);
		
	}

	@Override
	public void messageReceived(MessageEvent me) {
		if (me.getMessage().contains("You catch a raw shark.")) {
		      fish += 1;
		      xp += 9713;
		      cash += 2331;
		} else if (me.getMessage().contains("You catch a raw lobster.")) {
		      fish += 1;
		      xp += 5827;
		      cash += 1398;
		} else if (me.getMessage().contains("You catch a raw rocktail.")) {
		      fish += 1;
		      xp += 26000;
		      cash += 6384;
		} else if (me.getMessage().contains("You catch a raw trout.")) {
		      fish += 1;
		      xp += 3500;
		      cash += 840;
		
		}
		
		}

	public class Gui extends JFrame {
		 
	    private JPanel contentPane;

	    /**
	     * Launch the application.
	     */
	    public void main(String[] args) {
	            EventQueue.invokeLater(new Runnable() {
	                    public void run() {
	                            try {
	                                    Gui frame = new Gui();
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
	    public Gui() {
	    	 initComponents();
	            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	            setBounds(100, 100, 129, 180);
	            contentPane = new JPanel();
	            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	            setContentPane(contentPane);
	            contentPane.setLayout(null);
	           
	            lblJaiofisher = new JLabel("jAIOFisher");
	            lblJaiofisher.setFont(new Font("Rod", Font.PLAIN, 13));
	            lblJaiofisher.setBounds(15, 11, 101, 16);
	            contentPane.add(lblJaiofisher);
	           
	            lblWhatToFish = new JLabel("What to fish?");
	            lblWhatToFish.setBounds(17, 49, 82, 14);
	            contentPane.add(lblWhatToFish);
	           
	            thingtofish = new JComboBox();
	            thingtofish.setModel(new DefaultComboBoxModel(new String[] {"Sharks", "Lobsters", "Rocktail", "Shrimps"}));
	            thingtofish.setBounds(17, 75, 82, 20);
	            contentPane.add(thingtofish);
	           
	            btnStart = new JButton("Start");
	            btnStart.addActionListener(new ActionListener() {
	            	public void actionPerformed(ActionEvent e) {
	            		String chosen = thingtofish.getSelectedItem().toString();
	            		if(thingtofish.getSelectedItem().equals("Sharks")) {
	        				Fishingarea = 2;
	        		} else if (thingtofish.getSelectedItem().equals("Lobsters")) {
	    				Fishingarea = 3;
	        		} else if (thingtofish.getSelectedItem().equals("Rocktail")) {
	    				Fishingarea = 4;
	        		} else if (thingtofish.getSelectedItem().equals("Shrimps")) {
	        			Fishingarea = 5;
	            		
	            		 
	            	}
	            		guiWait= false;
			    		g.dispose();
	            	}
	            });
	            btnStart.setBounds(10, 112, 89, 23);
	            contentPane.add(btnStart);
	    }

			 private void initComponents() {
			   lblJaiofisher = new JLabel();
			   lblWhatToFish = new JLabel();
	           thingtofish = new JComboBox();
		
	}
			 private JLabel lblJaiofisher;
		     private JButton btnStart;
		     private JComboBox thingtofish;
		     private JLabel lblWhatToFish;
	}
		     
	}
