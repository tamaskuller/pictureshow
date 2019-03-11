/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.util;


/**
 *
 * @author Kuller Tamas
 */
public interface Observer {
    public void update(Action action, Object subject);

    public enum Action {
        FRAME_READY,UNDERCONST_READY, ADMIN_DISABLED, DB_SAVE_FRAME, DB_DELETE_FRAME, DB_LOAD_FRAME, READY_FOR_DELETE, READY_FOR_SAVE

    }
}