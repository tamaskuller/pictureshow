/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Menu;

import view.interfaces.TextLists;
import java.util.List;
import javax.swing.JMenuItem;

/**
 *
 * @author Tamas Kuller
 */
public class MenuItemFlexible extends JMenuItem implements TextLists {
    private List<String> texts;

    private MenuItemFlexible(int shortKey){
        super("", shortKey);
    }
    
//    public MenuItemFlexible(String name, List<String> texts, int mnemonic) {
//        super("", mnemonic);
//        setName(name);        
//        this.texts=texts;
//    }        
    
    public static class MenuItemBuild {
        MenuItemFlexible menuItem;

        public MenuItemBuild() {
        }
                
    
        public MenuItemBuild newInstance(int shortKey)
            {
            menuItem=new MenuItemFlexible(shortKey);
            return this;
           }
    
        public MenuItemBuild name(String name)
        {
            menuItem.setName(name);
            return this;
        }
    
        public MenuItemBuild toolTipText(String toolTipText)
        {
            menuItem.setToolTipText(toolTipText);
            return this;
        }
    
        public MenuItemBuild texts(List<String> texts)
        {
            menuItem.texts=texts;
            return this;
        }
            
        public MenuItemBuild setText(boolean isFirst)
        {
            menuItem.chooseText((isFirst)?0:1);
            return this;
        }
    
        public MenuItemFlexible build()
        {
            return menuItem;
        }            
    }


    @Override
    public void addText(String text) {
        if (!texts.contains(text))
            texts.add(text);
    }

    @Override
    public void removeText(String text) {
        texts.remove(text);
    }        

    @Override
    public String chooseText(int position) {
        this.setText(texts.get(position));
        return texts.get(position);
    }

    
    
    
    
    
    
    
}
