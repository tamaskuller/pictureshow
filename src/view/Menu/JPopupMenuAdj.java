/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Menu;

import java.awt.Component;
import javax.swing.JPopupMenu;

/**
 *
 * @author Tamas Kuller
 */
public class JPopupMenuAdj extends JPopupMenu {
    private boolean menuActive=false;
    
        public JPopupMenuAdj() {
            super();
        }

        public JPopupMenuAdj(String label) {
            super(label);
        }

        @Override
        public void show(Component invoker, int x, int y) {
            if (menuActive)
                super.show(invoker, x, y); //To change body of generated methods, choose Tools | Templates.
        }

        public boolean isMenuActive() {
            return menuActive;
        }

        public void setMenuActive(boolean menuActive) {
            this.menuActive = menuActive;
        }
}
