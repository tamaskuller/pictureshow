/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tamas Kuller
 */
@Entity
@Table(name = "picture_pane_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PicturePaneTable.findAll", query = "SELECT p FROM PicturePaneTable p")
    , @NamedQuery(name = "PicturePaneTable.findByPaneID", query = "SELECT p FROM PicturePaneTable p WHERE p.paneID = :paneID")
    , @NamedQuery(name = "PicturePaneTable.findByAdminEnabled", query = "SELECT p FROM PicturePaneTable p WHERE p.adminEnabled = :adminEnabled")
    , @NamedQuery(name = "PicturePaneTable.findByButtonID", query = "SELECT p FROM PicturePaneTable p WHERE p.buttonID = :buttonID")
    , @NamedQuery(name = "PicturePaneTable.findByFullState", query = "SELECT p FROM PicturePaneTable p WHERE p.fullState = :fullState")
    , @NamedQuery(name = "PicturePaneTable.findByName", query = "SELECT p FROM PicturePaneTable p WHERE p.name = :name")
    , @NamedQuery(name = "PicturePaneTable.findByReorderMotionType", query = "SELECT p FROM PicturePaneTable p WHERE p.reorderMotionType = :reorderMotionType")})
public class PicturePaneTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pane_ID")
    private Integer paneID;
    @Basic(optional = false)
    @Column(name = "admin_enabled")
    private short adminEnabled;
    @Column(name = "button_ID")
    private Integer buttonID;
    @Basic(optional = false)
    @Column(name = "full_state")
    private short fullState;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "reorder_motion_type")
    private String reorderMotionType;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentpaneID")
    private Collection<PictureComponentTable> pictureComponentTableCollection;
    @JoinColumn(name = "parent_frame_ID", referencedColumnName = "frame_ID")
    @ManyToOne
    private PictureFrameTable parentframeID;
    @OneToMany(mappedBy = "parentpaneID")
    private Collection<PicturePaneTable> picturePaneTableCollection;
    @JoinColumn(name = "parent_pane_ID", referencedColumnName = "pane_ID")
    @ManyToOne
    private PicturePaneTable parentpaneID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentpaneID")
    private Collection<PictureButtonTable> pictureButtonTableCollection;

    public PicturePaneTable() {
    }

    public PicturePaneTable(Integer paneID) {
        this.paneID = paneID;
    }

    public PicturePaneTable(Integer paneID, short adminEnabled, short fullState, String reorderMotionType) {
        this.paneID = paneID;
        this.adminEnabled = adminEnabled;
        this.fullState = fullState;
        this.reorderMotionType = reorderMotionType;
    }

    public Integer getPaneID() {
        return paneID;
    }

    public void setPaneID(Integer paneID) {
        this.paneID = paneID;
    }

    public short getAdminEnabled() {
        return adminEnabled;
    }

    public void setAdminEnabled(short adminEnabled) {
        this.adminEnabled = adminEnabled;
    }

    public Integer getButtonID() {
        return buttonID;
    }

    public void setButtonID(Integer buttonID) {
        this.buttonID = buttonID;
    }

    public short getFullState() {
        return fullState;
    }

    public void setFullState(short fullState) {
        this.fullState = fullState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReorderMotionType() {
        return reorderMotionType;
    }

    public void setReorderMotionType(String reorderMotionType) {
        this.reorderMotionType = reorderMotionType;
    }

    @XmlTransient
    public Collection<PictureComponentTable> getPictureComponentTableCollection() {
        return pictureComponentTableCollection;
    }

    public void setPictureComponentTableCollection(Collection<PictureComponentTable> pictureComponentTableCollection) {
        this.pictureComponentTableCollection = pictureComponentTableCollection;
    }

    public PictureFrameTable getParentframeID() {
        return parentframeID;
    }

    public void setParentframeID(PictureFrameTable parentframeID) {
        this.parentframeID = parentframeID;
    }

    @XmlTransient
    public Collection<PicturePaneTable> getPicturePaneTableCollection() {
        return picturePaneTableCollection;
    }

    public void setPicturePaneTableCollection(Collection<PicturePaneTable> picturePaneTableCollection) {
        this.picturePaneTableCollection = picturePaneTableCollection;
    }

    public PicturePaneTable getParentpaneID() {
        return parentpaneID;
    }

    public void setParentpaneID(PicturePaneTable parentpaneID) {
        this.parentpaneID = parentpaneID;
    }

    @XmlTransient
    public Collection<PictureButtonTable> getPictureButtonTableCollection() {
        return pictureButtonTableCollection;
    }

    public void setPictureButtonTableCollection(Collection<PictureButtonTable> pictureButtonTableCollection) {
        this.pictureButtonTableCollection = pictureButtonTableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paneID != null ? paneID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PicturePaneTable)) {
            return false;
        }
        PicturePaneTable other = (PicturePaneTable) object;
        if ((this.paneID == null && other.paneID != null) || (this.paneID != null && !this.paneID.equals(other.paneID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PicturePaneTable[ paneID=" + paneID + " ]";
    }
    
}
