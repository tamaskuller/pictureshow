/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tamas Kuller
 */
@Entity
@Table(name = "picture_frame_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PictureFrameTable.findAll", query = "SELECT p FROM PictureFrameTable p")
    , @NamedQuery(name = "PictureFrameTable.findByFrameID", query = "SELECT p FROM PictureFrameTable p WHERE p.frameID = :frameID")
    , @NamedQuery(name = "PictureFrameTable.findByAdminEnabled", query = "SELECT p FROM PictureFrameTable p WHERE p.adminEnabled = :adminEnabled")
    , @NamedQuery(name = "PictureFrameTable.findByCurrBaseSizeHeight", query = "SELECT p FROM PictureFrameTable p WHERE p.currBaseSizeHeight = :currBaseSizeHeight")
    , @NamedQuery(name = "PictureFrameTable.findByCurrBaseSizeWidth", query = "SELECT p FROM PictureFrameTable p WHERE p.currBaseSizeWidth = :currBaseSizeWidth")
    , @NamedQuery(name = "PictureFrameTable.findByFrameSizeHeight", query = "SELECT p FROM PictureFrameTable p WHERE p.frameSizeHeight = :frameSizeHeight")
    , @NamedQuery(name = "PictureFrameTable.findByFrameSizeWidth", query = "SELECT p FROM PictureFrameTable p WHERE p.frameSizeWidth = :frameSizeWidth")
    , @NamedQuery(name = "PictureFrameTable.findByFullState", query = "SELECT p FROM PictureFrameTable p WHERE p.fullState = :fullState")
    , @NamedQuery(name = "PictureFrameTable.findByName", query = "SELECT p FROM PictureFrameTable p WHERE p.name = :name")
    , @NamedQuery(name = "PictureFrameTable.findByOldBackGroundColor", query = "SELECT p FROM PictureFrameTable p WHERE p.oldBackGroundColor = :oldBackGroundColor")
    , @NamedQuery(name = "PictureFrameTable.findBySizeRatioContPaneHeight", query = "SELECT p FROM PictureFrameTable p WHERE p.sizeRatioContPaneHeight = :sizeRatioContPaneHeight")
    , @NamedQuery(name = "PictureFrameTable.findBySizeRatioContPaneWidth", query = "SELECT p FROM PictureFrameTable p WHERE p.sizeRatioContPaneWidth = :sizeRatioContPaneWidth")
    , @NamedQuery(name = "PictureFrameTable.findBySizeRatioHeight", query = "SELECT p FROM PictureFrameTable p WHERE p.sizeRatioHeight = :sizeRatioHeight")
    , @NamedQuery(name = "PictureFrameTable.findBySizeRatioWidth", query = "SELECT p FROM PictureFrameTable p WHERE p.sizeRatioWidth = :sizeRatioWidth")
    , @NamedQuery(name = "PictureFrameTable.findByImageName", query = "SELECT p FROM PictureFrameTable p WHERE p.imageName = :imageName")
    , @NamedQuery(name = "PictureFrameTable.findByImagePath", query = "SELECT p FROM PictureFrameTable p WHERE p.imagePath = :imagePath")
    , @NamedQuery(name = "PictureFrameTable.findBySaveDate", query = "SELECT p FROM PictureFrameTable p WHERE p.saveDate = :saveDate")
    , @NamedQuery(name = "PictureFrameTable.findBySystemRecord", query = "SELECT p FROM PictureFrameTable p WHERE p.systemRecord = :systemRecord")})
public class PictureFrameTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "frame_ID")
    private Integer frameID;
    @Basic(optional = false)
    @Column(name = "admin_enabled")
    private short adminEnabled;
    @Basic(optional = false)
    @Column(name = "curr_base_size_height")
    private double currBaseSizeHeight;
    @Basic(optional = false)
    @Column(name = "curr_base_size_width")
    private double currBaseSizeWidth;
    @Basic(optional = false)
    @Column(name = "frame_size_height")
    private double frameSizeHeight;
    @Basic(optional = false)
    @Column(name = "frame_size_width")
    private double frameSizeWidth;
    @Basic(optional = false)
    @Column(name = "full_state")
    private short fullState;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "old_back_ground_color")
    private String oldBackGroundColor;
    @Basic(optional = false)
    @Column(name = "size_ratio_cont_pane_height")
    private double sizeRatioContPaneHeight;
    @Basic(optional = false)
    @Column(name = "size_ratio_cont_pane_width")
    private double sizeRatioContPaneWidth;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "size_ratio_height")
    private Double sizeRatioHeight;
    @Column(name = "size_ratio_width")
    private Double sizeRatioWidth;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "save_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date saveDate;
    @Column(name = "system_record")
    private Short systemRecord;
    @OneToMany(mappedBy = "parentframeID")
    private Collection<PicturePaneTable> picturePaneTableCollection;

    public PictureFrameTable() {
    }

    public PictureFrameTable(Integer frameID) {
        this.frameID = frameID;
    }

    public PictureFrameTable(Integer frameID, short adminEnabled, double currBaseSizeHeight, double currBaseSizeWidth, double frameSizeHeight, double frameSizeWidth, short fullState, String name, double sizeRatioContPaneHeight, double sizeRatioContPaneWidth) {
        this.frameID = frameID;
        this.adminEnabled = adminEnabled;
        this.currBaseSizeHeight = currBaseSizeHeight;
        this.currBaseSizeWidth = currBaseSizeWidth;
        this.frameSizeHeight = frameSizeHeight;
        this.frameSizeWidth = frameSizeWidth;
        this.fullState = fullState;
        this.name = name;
        this.sizeRatioContPaneHeight = sizeRatioContPaneHeight;
        this.sizeRatioContPaneWidth = sizeRatioContPaneWidth;
    }

    public Integer getFrameID() {
        return frameID;
    }

    public void setFrameID(Integer frameID) {
        this.frameID = frameID;
    }

    public short getAdminEnabled() {
        return adminEnabled;
    }

    public void setAdminEnabled(short adminEnabled) {
        this.adminEnabled = adminEnabled;
    }

    public double getCurrBaseSizeHeight() {
        return currBaseSizeHeight;
    }

    public void setCurrBaseSizeHeight(double currBaseSizeHeight) {
        this.currBaseSizeHeight = currBaseSizeHeight;
    }

    public double getCurrBaseSizeWidth() {
        return currBaseSizeWidth;
    }

    public void setCurrBaseSizeWidth(double currBaseSizeWidth) {
        this.currBaseSizeWidth = currBaseSizeWidth;
    }

    public double getFrameSizeHeight() {
        return frameSizeHeight;
    }

    public void setFrameSizeHeight(double frameSizeHeight) {
        this.frameSizeHeight = frameSizeHeight;
    }

    public double getFrameSizeWidth() {
        return frameSizeWidth;
    }

    public void setFrameSizeWidth(double frameSizeWidth) {
        this.frameSizeWidth = frameSizeWidth;
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

    public String getOldBackGroundColor() {
        return oldBackGroundColor;
    }

    public void setOldBackGroundColor(String oldBackGroundColor) {
        this.oldBackGroundColor = oldBackGroundColor;
    }

    public double getSizeRatioContPaneHeight() {
        return sizeRatioContPaneHeight;
    }

    public void setSizeRatioContPaneHeight(double sizeRatioContPaneHeight) {
        this.sizeRatioContPaneHeight = sizeRatioContPaneHeight;
    }

    public double getSizeRatioContPaneWidth() {
        return sizeRatioContPaneWidth;
    }

    public void setSizeRatioContPaneWidth(double sizeRatioContPaneWidth) {
        this.sizeRatioContPaneWidth = sizeRatioContPaneWidth;
    }

    public Double getSizeRatioHeight() {
        return sizeRatioHeight;
    }

    public void setSizeRatioHeight(Double sizeRatioHeight) {
        this.sizeRatioHeight = sizeRatioHeight;
    }

    public Double getSizeRatioWidth() {
        return sizeRatioWidth;
    }

    public void setSizeRatioWidth(Double sizeRatioWidth) {
        this.sizeRatioWidth = sizeRatioWidth;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public Short getSystemRecord() {
        return systemRecord;
    }

    public void setSystemRecord(Short systemRecord) {
        this.systemRecord = systemRecord;
    }

    @XmlTransient
    public Collection<PicturePaneTable> getPicturePaneTableCollection() {
        return picturePaneTableCollection;
    }

    public void setPicturePaneTableCollection(Collection<PicturePaneTable> picturePaneTableCollection) {
        this.picturePaneTableCollection = picturePaneTableCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (frameID != null ? frameID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PictureFrameTable)) {
            return false;
        }
        PictureFrameTable other = (PictureFrameTable) object;
        if ((this.frameID == null && other.frameID != null) || (this.frameID != null && !this.frameID.equals(other.frameID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PictureFrameTable[ frameID=" + frameID + " ]";
    }
    
}
