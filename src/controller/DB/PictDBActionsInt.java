/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.DB;


import controller.DB.exceptions.NonexistentEntityException;
import java.util.List;
import model.exceptions.PreexistingEntityException;
import view.interfaces.PictureFrameInterface;

/**
 *
 * @author Tamas Kuller
 */
public interface PictDBActionsInt {
    public void saveFrame(PictureFrameInterface pictureFrameInterface, String name) throws PreexistingEntityException;    
    public PictureFrameInterface loadFrame(String name) throws NonexistentEntityException;    
    public boolean deleteFrame(String name) throws NonexistentEntityException;    
    public List<Object[]> getFrameRecordsLimited();    
}
