/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tamas Kuller
 */
@Entity
@Table(name = "picture_component_table")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PictureComponentTable.findAll", query = "SELECT p FROM PictureComponentTable p")
    , @NamedQuery(name = "PictureComponentTable.findByComponentID", query = "SELECT p FROM PictureComponentTable p WHERE p.componentID = :componentID")
    , @NamedQuery(name = "PictureComponentTable.findByAdminEnabled", query = "SELECT p FROM PictureComponentTable p WHERE p.adminEnabled = :adminEnabled")
    , @NamedQuery(name = "PictureComponentTable.findByCurrBaseSizeHeight", query = "SELECT p FROM PictureComponentTable p WHERE p.currBaseSizeHeight = :currBaseSizeHeight")
    , @NamedQuery(name = "PictureComponentTable.findByCurrBaseSizeWidth", query = "SELECT p FROM PictureComponentTable p WHERE p.currBaseSizeWidth = :currBaseSizeWidth")
    , @NamedQuery(name = "PictureComponentTable.findByCurrbaselocationX", query = "SELECT p FROM PictureComponentTable p WHERE p.currbaselocationX = :currbaselocationX")
    , @NamedQuery(name = "PictureComponentTable.findByCurrbaselocationY", query = "SELECT p FROM PictureComponentTable p WHERE p.currbaselocationY = :currbaselocationY")
    , @NamedQuery(name = "PictureComponentTable.findByDefaultMotionType", query = "SELECT p FROM PictureComponentTable p WHERE p.defaultMotionType = :defaultMotionType")
    , @NamedQuery(name = "PictureComponentTable.findByIconString", query = "SELECT p FROM PictureComponentTable p WHERE p.iconString = :iconString")
    , @NamedQuery(name = "PictureComponentTable.findByImageName", query = "SELECT p FROM PictureComponentTable p WHERE p.imageName = :imageName")
    , @NamedQuery(name = "PictureComponentTable.findByImagePath", query = "SELECT p FROM PictureComponentTable p WHERE p.imagePath = :imagePath")
    , @NamedQuery(name = "PictureComponentTable.findByIsButtonComponent", query = "SELECT p FROM PictureComponentTable p WHERE p.isButtonComponent = :isButtonComponent")
    , @NamedQuery(name = "PictureComponentTable.findByIsPaneComponent", query = "SELECT p FROM PictureComponentTable p WHERE p.isPaneComponent = :isPaneComponent")
    , @NamedQuery(name = "PictureComponentTable.findByMinHeight", query = "SELECT p FROM PictureComponentTable p WHERE p.minHeight = :minHeight")
    , @NamedQuery(name = "PictureComponentTable.findByMinHeightTemp", query = "SELECT p FROM PictureComponentTable p WHERE p.minHeightTemp = :minHeightTemp")
    , @NamedQuery(name = "PictureComponentTable.findByMinWidth", query = "SELECT p FROM PictureComponentTable p WHERE p.minWidth = :minWidth")
    , @NamedQuery(name = "PictureComponentTable.findByMinWidthTemp", query = "SELECT p FROM PictureComponentTable p WHERE p.minWidthTemp = :minWidthTemp")
    , @NamedQuery(name = "PictureComponentTable.findByMotionRatio", query = "SELECT p FROM PictureComponentTable p WHERE p.motionRatio = :motionRatio")
    , @NamedQuery(name = "PictureComponentTable.findByMotiontypemapID", query = "SELECT p FROM PictureComponentTable p WHERE p.motiontypemapID = :motiontypemapID")
    , @NamedQuery(name = "PictureComponentTable.findByOrigSizeHeight", query = "SELECT p FROM PictureComponentTable p WHERE p.origSizeHeight = :origSizeHeight")
    , @NamedQuery(name = "PictureComponentTable.findByOrigSizeWidth", query = "SELECT p FROM PictureComponentTable p WHERE p.origSizeWidth = :origSizeWidth")
    , @NamedQuery(name = "PictureComponentTable.findByOriglocationX", query = "SELECT p FROM PictureComponentTable p WHERE p.origlocationX = :origlocationX")
    , @NamedQuery(name = "PictureComponentTable.findByOriglocationY", query = "SELECT p FROM PictureComponentTable p WHERE p.origlocationY = :origlocationY")
    , @NamedQuery(name = "PictureComponentTable.findByResizeBorderColor", query = "SELECT p FROM PictureComponentTable p WHERE p.resizeBorderColor = :resizeBorderColor")
    , @NamedQuery(name = "PictureComponentTable.findByShown", query = "SELECT p FROM PictureComponentTable p WHERE p.shown = :shown")
    , @NamedQuery(name = "PictureComponentTable.findBySizeParentRatioHeight", query = "SELECT p FROM PictureComponentTable p WHERE p.sizeParentRatioHeight = :sizeParentRatioHeight")
    , @NamedQuery(name = "PictureComponentTable.findBySizeParentRatioWidth", query = "SELECT p FROM PictureComponentTable p WHERE p.sizeParentRatioWidth = :sizeParentRatioWidth")
    , @NamedQuery(name = "PictureComponentTable.findBySizeRatioHeight", query = "SELECT p FROM PictureComponentTable p WHERE p.sizeRatioHeight = :sizeRatioHeight")
    , @NamedQuery(name = "PictureComponentTable.findBySizeRatioWidth", query = "SELECT p FROM PictureComponentTable p WHERE p.sizeRatioWidth = :sizeRatioWidth")
    , @NamedQuery(name = "PictureComponentTable.findByToolTipText", query = "SELECT p FROM PictureComponentTable p WHERE p.toolTipText = :toolTipText")
    , @NamedQuery(name = "PictureComponentTable.findByPosition", query = "SELECT p FROM PictureComponentTable p WHERE p.position = :position")})
public class PictureComponentTable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "component_ID")
    private Integer componentID;
    @Basic(optional = false)
    @Column(name = "adminEnabled")
    private short adminEnabled;
    @Basic(optional = false)
    @Column(name = "curr_base_size_height")
    private double currBaseSizeHeight;
    @Basic(optional = false)
    @Column(name = "curr_base_size_width")
    private double currBaseSizeWidth;
    @Basic(optional = false)
    @Column(name = "curr_base_locationX")
    private double currbaselocationX;
    @Basic(optional = false)
    @Column(name = "curr_base_locationY")
    private double currbaselocationY;
    @Column(name = "default_motion_type")
    private String defaultMotionType;
    @Basic(optional = false)
    @Column(name = "icon_string")
    private String iconString;
    @Column(name = "image_name")
    private String imageName;
    @Column(name = "image_path")
    private String imagePath;
    @Basic(optional = false)
    @Column(name = "is_button_component")
    private short isButtonComponent;
    @Basic(optional = false)
    @Column(name = "is_pane_component")
    private short isPaneComponent;
    @Basic(optional = false)
    @Column(name = "min_height")
    private double minHeight;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "min_height_temp")
    private Double minHeightTemp;
    @Basic(optional = false)
    @Column(name = "min_width")
    private double minWidth;
    @Column(name = "min_width_temp")
    private Double minWidthTemp;
    @Basic(optional = false)
    @Column(name = "motion_ratio")
    private double motionRatio;
    @Column(name = "motion_type_map_ID")
    private Integer motiontypemapID;
    @Basic(optional = false)
    @Column(name = "orig_size_height")
    private double origSizeHeight;
    @Basic(optional = false)
    @Column(name = "orig_size_width")
    private double origSizeWidth;
    @Basic(optional = false)
    @Column(name = "orig_locationX")
    private double origlocationX;
    @Basic(optional = false)
    @Column(name = "orig_locationY")
    private double origlocationY;
    @Column(name = "resize_border_color")
    private String resizeBorderColor;
    @Basic(optional = false)
    @Column(name = "shown")
    private short shown;
    @Basic(optional = false)
    @Column(name = "size_parent_ratio_height")
    private double sizeParentRatioHeight;
    @Basic(optional = false)
    @Column(name = "size_parent_ratio_width")
    private double sizeParentRatioWidth;
    @Basic(optional = false)
    @Column(name = "size_ratio_height")
    private double sizeRatioHeight;
    @Basic(optional = false)
    @Column(name = "size_ratio_width")
    private double sizeRatioWidth;
    @Column(name = "tool_tip_text")
    private String toolTipText;
    @Column(name = "position")
    private Integer position;
    @JoinColumn(name = "button_id", referencedColumnName = "button_ID")
    @ManyToOne
    private PictureButtonTable buttonId;
    @JoinColumn(name = "parentpane_ID", referencedColumnName = "pane_ID")
    @ManyToOne(optional = false)
    private PicturePaneTable parentpaneID;

    public PictureComponentTable() {
    }

    public PictureComponentTable(Integer componentID) {
        this.componentID = componentID;
    }

    public PictureComponentTable(Integer componentID, short adminEnabled, double currBaseSizeHeight, double currBaseSizeWidth, double currbaselocationX, double currbaselocationY, String iconString, short isButtonComponent, short isPaneComponent, double minHeight, double minWidth, double motionRatio, double origSizeHeight, double origSizeWidth, double origlocationX, double origlocationY, short shown, double sizeParentRatioHeight, double sizeParentRatioWidth, double sizeRatioHeight, double sizeRatioWidth) {
        this.componentID = componentID;
        this.adminEnabled = adminEnabled;
        this.currBaseSizeHeight = currBaseSizeHeight;
        this.currBaseSizeWidth = currBaseSizeWidth;
        this.currbaselocationX = currbaselocationX;
        this.currbaselocationY = currbaselocationY;
        this.iconString = iconString;
        this.isButtonComponent = isButtonComponent;
        this.isPaneComponent = isPaneComponent;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.motionRatio = motionRatio;
        this.origSizeHeight = origSizeHeight;
        this.origSizeWidth = origSizeWidth;
        this.origlocationX = origlocationX;
        this.origlocationY = origlocationY;
        this.shown = shown;
        this.sizeParentRatioHeight = sizeParentRatioHeight;
        this.sizeParentRatioWidth = sizeParentRatioWidth;
        this.sizeRatioHeight = sizeRatioHeight;
        this.sizeRatioWidth = sizeRatioWidth;
    }

    public Integer getComponentID() {
        return componentID;
    }

    public void setComponentID(Integer componentID) {
        this.componentID = componentID;
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

    public double getCurrbaselocationX() {
        return currbaselocationX;
    }

    public void setCurrbaselocationX(double currbaselocationX) {
        this.currbaselocationX = currbaselocationX;
    }

    public double getCurrbaselocationY() {
        return currbaselocationY;
    }

    public void setCurrbaselocationY(double currbaselocationY) {
        this.currbaselocationY = currbaselocationY;
    }

    public String getDefaultMotionType() {
        return defaultMotionType;
    }

    public void setDefaultMotionType(String defaultMotionType) {
        this.defaultMotionType = defaultMotionType;
    }

    public String getIconString() {
        return iconString;
    }

    public void setIconString(String iconString) {
        this.iconString = iconString;
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

    public short getIsButtonComponent() {
        return isButtonComponent;
    }

    public void setIsButtonComponent(short isButtonComponent) {
        this.isButtonComponent = isButtonComponent;
    }

    public short getIsPaneComponent() {
        return isPaneComponent;
    }

    public void setIsPaneComponent(short isPaneComponent) {
        this.isPaneComponent = isPaneComponent;
    }

    public double getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(double minHeight) {
        this.minHeight = minHeight;
    }

    public Double getMinHeightTemp() {
        return minHeightTemp;
    }

    public void setMinHeightTemp(Double minHeightTemp) {
        this.minHeightTemp = minHeightTemp;
    }

    public double getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(double minWidth) {
        this.minWidth = minWidth;
    }

    public Double getMinWidthTemp() {
        return minWidthTemp;
    }

    public void setMinWidthTemp(Double minWidthTemp) {
        this.minWidthTemp = minWidthTemp;
    }

    public double getMotionRatio() {
        return motionRatio;
    }

    public void setMotionRatio(double motionRatio) {
        this.motionRatio = motionRatio;
    }

    public Integer getMotiontypemapID() {
        return motiontypemapID;
    }

    public void setMotiontypemapID(Integer motiontypemapID) {
        this.motiontypemapID = motiontypemapID;
    }

    public double getOrigSizeHeight() {
        return origSizeHeight;
    }

    public void setOrigSizeHeight(double origSizeHeight) {
        this.origSizeHeight = origSizeHeight;
    }

    public double getOrigSizeWidth() {
        return origSizeWidth;
    }

    public void setOrigSizeWidth(double origSizeWidth) {
        this.origSizeWidth = origSizeWidth;
    }

    public double getOriglocationX() {
        return origlocationX;
    }

    public void setOriglocationX(double origlocationX) {
        this.origlocationX = origlocationX;
    }

    public double getOriglocationY() {
        return origlocationY;
    }

    public void setOriglocationY(double origlocationY) {
        this.origlocationY = origlocationY;
    }

    public String getResizeBorderColor() {
        return resizeBorderColor;
    }

    public void setResizeBorderColor(String resizeBorderColor) {
        this.resizeBorderColor = resizeBorderColor;
    }

    public short getShown() {
        return shown;
    }

    public void setShown(short shown) {
        this.shown = shown;
    }

    public double getSizeParentRatioHeight() {
        return sizeParentRatioHeight;
    }

    public void setSizeParentRatioHeight(double sizeParentRatioHeight) {
        this.sizeParentRatioHeight = sizeParentRatioHeight;
    }

    public double getSizeParentRatioWidth() {
        return sizeParentRatioWidth;
    }

    public void setSizeParentRatioWidth(double sizeParentRatioWidth) {
        this.sizeParentRatioWidth = sizeParentRatioWidth;
    }

    public double getSizeRatioHeight() {
        return sizeRatioHeight;
    }

    public void setSizeRatioHeight(double sizeRatioHeight) {
        this.sizeRatioHeight = sizeRatioHeight;
    }

    public double getSizeRatioWidth() {
        return sizeRatioWidth;
    }

    public void setSizeRatioWidth(double sizeRatioWidth) {
        this.sizeRatioWidth = sizeRatioWidth;
    }

    public String getToolTipText() {
        return toolTipText;
    }

    public void setToolTipText(String toolTipText) {
        this.toolTipText = toolTipText;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public PictureButtonTable getButtonId() {
        return buttonId;
    }

    public void setButtonId(PictureButtonTable buttonId) {
        this.buttonId = buttonId;
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
        hash += (componentID != null ? componentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PictureComponentTable)) {
            return false;
        }
        PictureComponentTable other = (PictureComponentTable) object;
        if ((this.componentID == null && other.componentID != null) || (this.componentID != null && !this.componentID.equals(other.componentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PictureComponentTable[ componentID=" + componentID + " ]";
    }
    
}
