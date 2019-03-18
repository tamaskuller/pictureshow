/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tamas Kuller
 */
@Entity
@Table(name = "logins")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Logins.findAll", query = "SELECT l FROM Logins l")
    , @NamedQuery(name = "Logins.findByLoginID", query = "SELECT l FROM Logins l WHERE l.loginID = :loginID")
    , @NamedQuery(name = "Logins.findByLoginDate", query = "SELECT l FROM Logins l WHERE l.loginDate = :loginDate")
    , @NamedQuery(name = "Logins.findByHostName", query = "SELECT l FROM Logins l WHERE l.hostName = :hostName")
    , @NamedQuery(name = "Logins.findByIPaddress", query = "SELECT l FROM Logins l WHERE l.iPaddress = :iPaddress")})
public class Logins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "login_ID")
    private Integer loginID;
    @Column(name = "login_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginDate;
    @Column(name = "host_name")
    private String hostName;
    @Column(name = "IP_address")
    private String iPaddress;

    public Logins() {
    }

    public Logins(Integer loginID) {
        this.loginID = loginID;
    }

    public Integer getLoginID() {
        return loginID;
    }

    public void setLoginID(Integer loginID) {
        this.loginID = loginID;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getIPaddress() {
        return iPaddress;
    }

    public void setIPaddress(String iPaddress) {
        this.iPaddress = iPaddress;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginID != null ? loginID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logins)) {
            return false;
        }
        Logins other = (Logins) object;
        if ((this.loginID == null && other.loginID != null) || (this.loginID != null && !this.loginID.equals(other.loginID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Logins[ loginID=" + loginID + " ]";
    }
    
}
