import enigma.core.Enigma;
import enigma.event.TextMouseEvent;
import enigma.event.TextMouseListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import enigma.console.TextAttributes;

public class Game {
   public enigma.console.Console cn = Enigma.getConsole("Mouse and Keyboard",71,41,15,1);
   public TextMouseListener tmlis; 
   public KeyListener klis;

   // ------ Standard variables for mouse and keyboard ------
   public int mousepr;          // mouse pressed?
   public int mousex, mousey;   // mouse text coords.
   public int keypr;   // key pressed?
   public int rkey;    // key   (for press/release)
   public static boolean BouncyGameLeftPressed = false;
   public static boolean BouncyGameRightPressed = false;
   public static boolean mousePressed = false;

// ----------------------------------------------------
   
   
   Game() throws Exception {   // --- Contructor
                 
      // ------ Standard code for mouse and keyboard ------ Do not change
      tmlis=new TextMouseListener() {
         public void mouseClicked(TextMouseEvent arg0) {}
         public void mousePressed(TextMouseEvent arg0) {
            if (!mousePressed) {
               mousePressed = true;
               mousex = arg0.getX();
               mousey = arg0.getY();
            }
         }
         public void mouseReleased(TextMouseEvent arg0) {}
      };
      cn.getTextWindow().addTextMouseListener(tmlis);
    
      klis=new KeyListener() {
         public void keyTyped(KeyEvent e) {}
         public void keyPressed(KeyEvent e) {
            if(keypr==0) {
               keypr=1;
               rkey=e.getKeyCode();
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
               setBouncyGameRightPressed(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
               setBouncyGameLeftPressed(true);
            }
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
               if (!isBouncyGameJumpPressed()) {
                  setBouncyGameJumpPressed(true);
               }
            }
         }
         public void keyReleased(KeyEvent e) {}
      };
      cn.getTextWindow().addKeyListener(klis);
      // ----------------------------------------------------
   }
   public void clear() {
      cn.getTextWindow().setCursorPosition(0,0);
      for (int i = 0; i < cn.getTextWindow().getRows() - 1; i++) {
         for (int j = 0; j < cn.getTextWindow().getColumns() - 1; j++) {
            System.out.print(" ");
         }
         System.out.println();
      }
      cn.getTextWindow().setCursorPosition(0,0);
   }

   public int getMousex() {
      return mousex;
   }

   public void setMousex(int mousex) {
      this.mousex = mousex;
   }

   public int getMousey() {
      return mousey;
   }

   public void setMousey(int mousey) {
      this.mousey = mousey;
   }
   public static boolean isBouncyGameJumpPressed() {
      return BouncyGameJumpPressed;
   }

   public static void setBouncyGameJumpPressed(boolean bouncyGameJumpPressed) {
      BouncyGameJumpPressed = bouncyGameJumpPressed;
   }

   public static boolean BouncyGameJumpPressed = false;

   public static boolean isBouncyGameLeftPressed() {
      return BouncyGameLeftPressed;
   }

   public static void setBouncyGameLeftPressed(boolean bouncyGameLeftPressed) {
      BouncyGameLeftPressed = bouncyGameLeftPressed;
   }

   public static boolean isBouncyGameRightPressed() {
      return BouncyGameRightPressed;
   }

   public static void setBouncyGameRightPressed(boolean bouncyGameRightPressed) {
      BouncyGameRightPressed = bouncyGameRightPressed;
   }

   public static boolean isMousePressed() {
      return mousePressed;
   }

   public static void setMousePressed(boolean mousePressed) {
      Game.mousePressed = mousePressed;
   }
}
