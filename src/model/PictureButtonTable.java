/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@Table(name = "picture_button_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PictureButtonTable.findAll", query = "SELECT p FROM PictureButtonTable p")
    , @NamedQuery(name = "PictureButtonTable.findByButtonID", query = "SELECT p FROM PictureButtonTable p WHERE p.buttonID = :buttonID")})
public class PictureButtonTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "button_ID")
    private Integer buttonID;
    @OneToMany(mappedBy = "buttonId")
    private Collection<PictureComponentTable> pictureComponentTableCollection;
    @JoinColumn(name = "parentpane_ID", referencedColumnName = "pane_ID")
    @ManyToOne(optional = false)
    private PicturePaneTable parentpaneID;

    public PictureButtonTable() {
    }

    public PictureButtonTable(Integer buttonID) {
        this.buttonID = buttonID;
    }

    public Integer getButtonID() {
        return buttonID;
    }

    public void setButtonID(Integer buttonID) {
        this.buttonID = buttonID;
    }

    @XmlTransient
    public Collection<PictureComponentTable> getPictureComponentTableCollection() {
        return pictureComponentTableCollection;
    }

    public void setPictureComponentTableCollection(Collection<PictureComponentTable> pictureComponentTableCollection) {
        this.pictureComponentTableCollection = pictureComponentTableCollection;
    }

    public PicturePaneTable getParentpaneID() {
        return parentpaneID;
    }

    public void setParentpaneID(PicturePaneTable parentpaneID) {
        this.parentpaneID = parentpaneID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (buttonID != null ? buttonID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PictureButtonTable)) {
            return false;
        }
        PictureButtonTable other = (PictureButtonTable) object;
        if ((this.buttonID == null && other.buttonID != null) || (this.buttonID != null && !this.buttonID.equals(other.buttonID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PictureButtonTable[ buttonID=" + buttonID + " ]";
    }
    
}
